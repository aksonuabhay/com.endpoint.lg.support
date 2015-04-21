/*
 * Copyright (C) 2014 Google Inc.
 * Copyright (C) 2015 End Point Corporation
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

package com.endpoint.lg.support.window.impl;

import com.endpoint.lg.support.window.WindowGeometry;
import com.endpoint.lg.support.window.WindowIdentity;
import com.endpoint.lg.support.window.WindowVisibility;

/**
 * Generates xdotool flags for setting a window's geometry and visibility.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class XdotoolCommand {
  /**
   * Generates xdotool flags for setting a window's geometry and visibility.
   * 
   * @return xdotool flags
   */
  public static String getFlags(WindowIdentity identity, WindowGeometry geometry,
      WindowVisibility visibility) {
    String searchFlags = XdotoolWindowIdentity.getFlags(identity, !visibility.getVisible());
    String geometryFlags = XdotoolWindowGeometry.getFlags(geometry);
    String visibilityFlags = XdotoolWindowVisibility.getFlags(visibility);

    return String.format("%s %s %s", searchFlags, geometryFlags, visibilityFlags);
  }
}
