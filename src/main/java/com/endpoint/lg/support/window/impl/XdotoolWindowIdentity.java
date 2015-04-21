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

import com.endpoint.lg.support.window.WindowInstanceIdentity;
import com.endpoint.lg.support.window.WindowClassIdentity;
import com.endpoint.lg.support.window.WindowNameIdentity;
import com.endpoint.lg.support.window.WindowIdentity;

/**
 * Generates xdotool search flags for finding a window id.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class XdotoolWindowIdentity {
  private static final String COMMON_FLAGS = "search --maxdepth 1 --limit 1 --sync";
  private static final String MATCH_ALL = "--all --name --class --classname";
  private static final String MATCH_NAME = "--name";
  private static final String MATCH_CLASS = "--class";
  private static final String MATCH_INSTANCE = "--classname";
  private static final String ONLY_VISIBLE = "--onlyvisible";

  /**
   * Get flags for matching the identifer's search scope.
   * 
   * @return match flags
   */
  private static String getMatchFlags(WindowIdentity identity) {
    Class<? extends WindowIdentity> clazz = identity.getClass();

    if (clazz == WindowNameIdentity.class)
      return MATCH_NAME;

    if (clazz == WindowClassIdentity.class)
      return MATCH_CLASS;

    if (clazz == WindowInstanceIdentity.class)
      return MATCH_INSTANCE;

    return MATCH_ALL;
  }

  /**
   * Generates xdotool search flags with an explicit --onlyvisible option.
   * 
   * @param onlyVisible
   *          only match visible windows
   * @return xdotool search flags
   */
  public static String getFlags(WindowIdentity identity, boolean onlyVisible) {
    String commonFlags = COMMON_FLAGS;

    if (onlyVisible) {
      commonFlags += " " + ONLY_VISIBLE;
    }

    return String
        .format("%s %s %s", commonFlags, getMatchFlags(identity), identity.getIdentifier());
  }

  /**
   * Generates xdotool search flags. The window is assumed to not be visible.
   * 
   * @return xdotool search flags
   */
  public static String getFlags(WindowIdentity identity) {
    return getFlags(identity, false);
  }
}
