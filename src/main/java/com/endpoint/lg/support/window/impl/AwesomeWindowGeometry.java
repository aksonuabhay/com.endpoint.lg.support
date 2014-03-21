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

package com.endpoint.lg.support.window.impl;

import java.util.List;

import com.google.common.collect.Lists;

import com.endpoint.lg.support.window.WindowGeometry;

/**
 * Generates partials for awful.rules to position windows.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class AwesomeWindowGeometry {
  /**
   * Get a list of properties for managing this geometry.
   * 
   * @param geometry
   *          the window's geometry
   * @return properties for setting the geometry
   */
  public static List<String> getProps(WindowGeometry geometry) {
    List<String> list = Lists.newArrayList("floating = true");

    list.add(String.format("width = %d", geometry.getWidth()));
    list.add(String.format("height = %d", geometry.getHeight()));

    return list;
  }

  /**
   * Gets a callback for managing this geometry.
   * 
   * @param geometry
   *          the window's geometry
   * @return lua callback for setting the geometry
   */
  public static String getCallback(WindowGeometry geometry) {
    return String.format("function(c) c:geometry({x=%d, y=%d}) end", geometry.getX(),
        geometry.getY());
  }
}
