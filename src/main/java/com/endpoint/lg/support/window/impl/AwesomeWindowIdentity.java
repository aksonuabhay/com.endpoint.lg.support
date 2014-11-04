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

import com.endpoint.lg.support.lua.LuaRegex;
import com.endpoint.lg.support.window.WindowIdentity;
import com.endpoint.lg.support.window.WindowClassIdentity;
import com.endpoint.lg.support.window.WindowInstanceIdentity;
import com.endpoint.lg.support.window.WindowNameIdentity;

/**
 * Methods for generating lua script partials representing window identity.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class AwesomeWindowIdentity {
  public static String MATCH_NAME = "name";
  public static String MATCH_CLASS = "class";
  public static String MATCH_INSTANCE = "instance";

  /**
   * Looks up the string literal representation of the identity type.
   * 
   * @param identity
   *          the window's identity
   * @return string literal for matching this type of identity
   */
  public static String getRuleType(WindowIdentity identity) {
    Class<? extends WindowIdentity> clazz = identity.getClass();

    if (clazz == WindowNameIdentity.class)
      return MATCH_NAME;

    if (clazz == WindowClassIdentity.class)
      return MATCH_CLASS;

    if (clazz == WindowInstanceIdentity.class)
      return MATCH_INSTANCE;

    return null;
  }

  /**
   * Generates the rule table to match this window.
   * 
   * @param identity
   *          the window's identity
   * @return awful.rules rule table
   */
  public static String getRulePattern(WindowIdentity identity) {
    return String.format("{ %s = '%s' }", getRuleType(identity),
        LuaRegex.escape(identity.getIdentifier()));
  }

  /**
   * Generates a lua conditional to match this window
   * @param identity
   *          the window's identity
   * @return if condition for this window
   */
  public static String getConditionalPattern(WindowIdentity identity) {
    return String.format("%s == '%s'", getRuleType(identity),
        LuaRegex.escape(identity.getIdentifier()));
  }

  /**
   * Generates a script to remove all rules matching this window.
   * 
   * @param identity
   *          the window's identity
   * @return lua script for removing all rules matching the identity
   */
  public static String getRemovalScript(WindowIdentity identity) {
    return String
        .format(
            "for key,rule in pairs(awful.rules.rules) do if rule['rule']['%s'] == '%s' then table.remove(awful.rules.rules, key) end end",
            getRuleType(identity), LuaRegex.escape(identity.getIdentifier()));
  }
}
