/*
 * Copyright (C) 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.endpoint.lg.support.window;

import interactivespaces.activity.binary.NativeApplicationRunner;
import interactivespaces.activity.impl.BaseActivity;
import interactivespaces.configuration.Configuration;
import interactivespaces.configuration.SystemConfiguration;
import interactivespaces.util.resource.ManagedResource;

import com.endpoint.lg.support.window.impl.XdotoolCommandRunnerFactory;

/**
 * <h2>Window Management Interface for Activities</h2>
 * 
 * <h4>Configuring a Live Activity to Target a Viewport</h4>
 * 
 * <p>
 * Set the <code>lg.window.viewport.target</code> key to a defined viewport.
 * 
 * <pre>
 * <code>lg.window.viewport.target=center</code>
 * </pre>
 * 
 * <h4>Native Activity Window Management</h4>
 * 
 * <p>
 * Adding basic window placement to a native activity requires only that the
 * window properties contain a unique identifier string.
 * 
 * <p>
 * Different applications have different facilities for setting window
 * attributes; in the case of Google Earth, passing -name to the executable will
 * override the window instance.
 * 
 * <p>
 * By creating a WindowIdentity with a unique string and referencing that string
 * in the application arguments, the window can be found and positioned.
 * 
 * <pre>
 * <code>
 * import com.endpoint.lg.support.window.WindowIdentity;
 * import com.endpoint.lg.support.window.WindowInstanceIdentity;
 * import com.endpoint.lg.support.window.ManagedWindow;
 * 
 * private ManagedWindow window;
 * 
 * {@literal @}Override
 * public void onActivitySetup() {
 * 
 *   WindowIdentity windowId = new WindowInstanceIdentity(getUuid());
 * 
 *   window = new ManagedWindow(this, windowId);
 * 
 *   addManagedResource(window);
 * 
 *   // ...
 * }
 * </code>
 * </pre>
 * 
 * <h4>Browser Windows</h4>
 * 
 * <p>
 * The browser window instance will, by default, be set to
 * "Google-chrome ({activity.tmpdir}/google-chrome)", so searching for the
 * activity tempdir should suffice.
 * 
 * <p>
 * Note that the controller and live activity configurations can override the
 * browser executable and arguments.
 * 
 * <pre>
 * <code>
 * File tmpdir = getActivityFilesystem().getTempDataDirectory();
 * windowId = new WindowInstanceIdentity(tmpdir.getAbsolutePath());
 * </code>
 * </pre>
 * 
 * <h4>Handling Configuration Updates</h4>
 * 
 * <p>
 * When the live activity receives new configuration, the window can be
 * instantly repositioned accordingly.
 * 
 * <pre>
 * <code>
 * {@literal @}Override
 * public void onActivityConfigurationUpdate(Map<String, Object> update) {
 * 
 *   if (window != null) // configuration update handler is sometimes called pre-setup
 *     window.update();
 * }
 * </code>
 * </pre>
 * 
 * <h4>Configuring Static Offsets</h4>
 * 
 * <p>
 * What if you need to offset the window to hide a toolbar? Configuration
 * defaults to the rescue:
 * 
 * <pre>
 * <code>
 * ### make the window 22 pixels taller
 * 
 * lg.window.rel.height=22
 * 
 * ### move the window up 22 pixels to hide the top
 * 
 * lg.window.rel.y=-22
 * </code>
 * </pre>
 * 
 * <h4>Hiding and Showing the Window</h4>
 * 
 * <pre>
 * <code>
 * window.setVisible(false); // hide the window
 * 
 * window.setVisible(true); // show the window
 * </code>
 * </pre>
 * 
 * <h4>Configuring Viewports</h4>
 * 
 * <p>
 * Viewports can be configured in the controller's config/ path for global
 * access by all live activities.
 * 
 * <p>
 * Defining a viewport in the live activity's configuration will work, too.
 * 
 * <pre>
 * <code>
 * ### configure the center viewport
 * ### 1080x1920 shifted to the right by 1920
 * 
 * lg.window.viewport.center.width=1080
 * lg.window.viewport.center.height=1920
 * lg.window.viewport.center.x=1920
 * lg.window.viewport.center.y=0
 * </code>
 * </pre>
 * 
 * @see WindowIdentity
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class ManagedWindow implements ManagedResource {
  /**
   * Configuration key for viewport name.
   */
  public static final String CONFIG_KEY_VIEWPORT_TARGET = "lg.window.viewport.target";

  /**
   * Configuration key for window width relative to viewport.
   */
  public static final String CONFIG_KEY_WINDOW_REL_WIDTH = "lg.window.rel.width";

  /**
   * Configuration key for window height relative to viewport.
   */
  public static final String CONFIG_KEY_WINDOW_REL_HEIGHT = "lg.window.rel.height";

  /**
   * Configuration key for window x placement relative to viewport.
   */
  public static final String CONFIG_KEY_WINDOW_REL_X = "lg.window.rel.x";

  /**
   * Configuration key for window y placement relative to viewport.
   */
  public static final String CONFIG_KEY_WINDOW_REL_Y = "lg.window.rel.y";

  /**
   * Configuration key for viewport width.
   */
  public static final String CONFIG_KEY_VIEWPORT_WIDTH = "lg.window.viewport.%s.width";

  /**
   * Configuration key for viewport height.
   */
  public static final String CONFIG_KEY_VIEWPORT_HEIGHT = "lg.window.viewport.%s.height";

  /**
   * Configuration key for viewport x offset.
   */
  public static final String CONFIG_KEY_VIEWPORT_X = "lg.window.viewport.%s.x";

  /**
   * Configuration key for viewport y offset.
   */
  public static final String CONFIG_KEY_VIEWPORT_Y = "lg.window.viewport.%s.y";

  // TODO: these should be defined somewhere else
  private static final String PLATFORM_LINUX = "linux";
  private static final String PLATFORM_OSX = "osx";

  private BaseActivity activity;

  private WindowIdentity identity;
  private WindowGeometry finalGeometry;
  private WindowGeometry geometryOffset;
  private WindowVisibility visibility;

  private XdotoolCommandRunnerFactory runnerFactory;
  private NativeApplicationRunner runner;

  /**
   * Ensures that window management is supported for this platform.
   * 
   * @return true if the platform is supported
   */
  private boolean checkPlatformSupport() {
    String platform =
        activity.getConfiguration().getPropertyString(SystemConfiguration.PLATFORM_OS);

    if (platform.equals(PLATFORM_LINUX))
      return true;

    if (platform.equals(PLATFORM_OSX))
      return false;

    return false;
  }

  /**
   * Fetches the viewport geometry.
   * 
   * @return absolute viewport geometry
   */
  private WindowGeometry findViewportGeometry() {
    Configuration activityConfig = activity.getConfiguration();

    String viewportName = activityConfig.getPropertyString(CONFIG_KEY_VIEWPORT_TARGET, null);

    if (viewportName == null) {
      activity.getLog().debug("Viewport not configured");
      return null; // bypass if viewport not configured
    }

    String widthKey = String.format(CONFIG_KEY_VIEWPORT_WIDTH, viewportName);
    String heightKey = String.format(CONFIG_KEY_VIEWPORT_HEIGHT, viewportName);
    String xKey = String.format(CONFIG_KEY_VIEWPORT_X, viewportName);
    String yKey = String.format(CONFIG_KEY_VIEWPORT_Y, viewportName);

    Integer width = activityConfig.getPropertyInteger(widthKey, null);
    Integer height = activityConfig.getPropertyInteger(heightKey, null);
    Integer x = activityConfig.getPropertyInteger(xKey, null);
    Integer y = activityConfig.getPropertyInteger(yKey, null);

    if (width != null && height != null && x != null && y != null) {
      return new WindowGeometry(width, height, x, y);
    } else {
      activity.getLog().error("Viewport configuration is incomplete for " + viewportName);
      return null; // bypass if viewport configuration is incomplete
    }
  }

  /**
   * Gets relative window position from the activity configuration.
   * 
   * @return relative window geometry
   */
  private WindowGeometry findRelativeGeometry() {
    Configuration activityConfig = activity.getConfiguration();

    int relativeWidth = activityConfig.getPropertyInteger(CONFIG_KEY_WINDOW_REL_WIDTH, 0);
    int relativeHeight = activityConfig.getPropertyInteger(CONFIG_KEY_WINDOW_REL_HEIGHT, 0);
    int relativeX = activityConfig.getPropertyInteger(CONFIG_KEY_WINDOW_REL_X, 0);
    int relativeY = activityConfig.getPropertyInteger(CONFIG_KEY_WINDOW_REL_Y, 0);

    return new WindowGeometry(relativeWidth, relativeHeight, relativeX, relativeY);
  }

  /**
   * Combines viewport geometry with offsets.
   * 
   * @return shifted absolute window geometry
   */
  private WindowGeometry calculateGeometry() {
    WindowGeometry geometry = findViewportGeometry();

    if (geometry == null)
      return null; // bypass when viewport geometry is not found

    geometry.offsetBy(findRelativeGeometry());

    geometry.offsetBy(geometryOffset);

    return geometry;
  }

  /**
   * Positions the window, picking up any changes in configuration.
   */
  private void positionWindow() {
    if (!checkPlatformSupport()) {
      activity.getLog().warn("Window management not implemented for this platform");
      return;
    }

    finalGeometry = calculateGeometry();

    if (finalGeometry == null)
      return; // bypass when geometry could not be found

    shutdown();

    runner = runnerFactory.getRunner(identity, finalGeometry, visibility);

    runner.startup();
  }

  /**
   * Initialize the factory for managed window command runners.
   */
  private void initRunnerFactory() {
    runnerFactory =
        new XdotoolCommandRunnerFactory(activity.getController().getNativeActivityRunnerFactory(),
            activity.getLog());
  }

  /**
   * Constructs a ManagedWindow for the given activity.
   * 
   * @param activity
   *          the activity responsible for the window
   * @param identity
   *          identifier for the window
   */
  public ManagedWindow(BaseActivity activity, WindowIdentity identity) {
    this.activity = activity;
    this.identity = identity;
    this.geometryOffset = new WindowGeometry(0, 0, 0, 0);
    this.visibility = new WindowVisibility(false);

    initRunnerFactory();
  }

  /**
   * Constructs a ManagedWindow for the given activity with a relative window
   * geometry offset.
   * 
   * @param activity
   *          the activity responsible for the window
   * @param identity
   *          identifier for the window
   * @param geometryOffset
   *          relative geometry within the configured viewport
   */
  public ManagedWindow(BaseActivity activity, WindowIdentity identity, WindowGeometry geometryOffset) {
    this.activity = activity;
    this.identity = identity;
    this.geometryOffset = geometryOffset;
    this.visibility = new WindowVisibility(false);

    initRunnerFactory();
  }

  /**
   * Spin down any outstanding window placement commands.
   */
  @Override
  public void shutdown() {
    if (runner != null)
      runner.shutdown();
  }

  /**
   * Start managing the window.
   */
  @Override
  public void startup() {
    positionWindow();
  }

  /**
   * Sync the window, causing a startup if not already started.
   */
  public void update() {
    startup();
  }

  /**
   * Changes window visibility.
   */
  public void setVisible(boolean visible) {
    visibility = new WindowVisibility(visible);
    update();
  }
}
