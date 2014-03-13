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

import interactivespaces.activity.binary.NativeActivityRunner;
import interactivespaces.activity.binary.NativeActivityRunnerFactory;

import com.google.common.collect.Maps;

import java.util.Map;

import org.apache.commons.logging.Log;

/**
 * A command to be sent to the window management facility.
 * 
 * <p>
 * A command has two components:
 * 
 * <p>
 * Search: How to find the window.
 * 
 * <p>
 * Directive: What to do with the window.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class WindowCommandRunnerFactory {
  public static final String XDOTOOL_BIN = "/usr/bin/xdotool";

  private NativeActivityRunnerFactory runnerFactory;
  private Log log;

  public WindowCommandRunnerFactory(NativeActivityRunnerFactory runnerFactory, Log log) {
    this.runnerFactory = runnerFactory;
    this.log = log;
  }

  public NativeActivityRunner getCommand(WindowIdentity identity, WindowGeometry geometry,
      WindowVisibility visibility) {
    NativeActivityRunner runner = runnerFactory.newPlatformNativeActivityRunner(log);

    Map<String, Object> runnerConfig = Maps.newHashMap();

    runnerConfig.put(NativeActivityRunner.ACTIVITYNAME, XDOTOOL_BIN);
    runnerConfig.put(NativeActivityRunner.FLAGS, String.format("%s %s %s",
        identity.getFlags(!visibility.getVisible()), geometry.getFlags(), visibility.getFlags()));

    runner.configure(runnerConfig);

    return runner;
  }
}
