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

package com.endpoint.lg.support.lua;

/**
 * Methods for dealing with Lua regex syntax.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class LuaRegex {
  /**
   * Escape Lua regex for the given string.
   * 
   * @param in
   *          a string to be escaped
   * @return the escaped string
   */
  public static String escape(String in) {
    String out = in;

    out = out.replace("%", "%%");
    out = out.replace("^", "%^");
    out = out.replace("$", "%$");
    out = out.replace("(", "%(");
    out = out.replace(")", "%)");
    out = out.replace("[", "%[");
    out = out.replace("]", "%]");
    out = out.replace(".", "%.");
    out = out.replace("*", "%*");
    out = out.replace("+", "%+");
    out = out.replace("-", "%-");
    out = out.replace("?", "%?");
    out = out.replace("\0", "%z");

    return out;
  }
}
