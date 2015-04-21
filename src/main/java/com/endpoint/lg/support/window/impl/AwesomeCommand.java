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

import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.base.Joiner;

import com.endpoint.lg.support.window.WindowGeometry;
import com.endpoint.lg.support.window.WindowIdentity;
import com.endpoint.lg.support.window.WindowVisibility;

/**
 * Methods for generating awesome-client commands to position windows via the
 * awesome API.
 * 
 * @see <a href="http://awesome.naquadah.org/doc/api/index.html">Awesome API
 *      Reference</a>
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class AwesomeCommand {
  /**
   * Location of sh.
   */
  public static final String SH_BIN = "/bin/sh";

  /**
   * Location of awesome-client.
   */
  public static final String AWESOME_CLIENT_BIN = "/usr/bin/awesome-client";

  /**
   * Common setup for commands.
   * 
   * @return common command header
   */
  private static List<String> setupCommands() {
    List<String> commands = Lists.newArrayList();

    commands.add(SH_BIN);
    commands.add("-c");

    return commands;
  }

  /**
   * Get a command for setting the window state via instant application of an
   * awful rule.
   * 
   * @param identity
   *          the window's identity
   * @param geometry
   *          the window's intended geometry
   * @param visibility
   *          the window's intended visibility
   * @return command for converging the window
   */
  public static List<String> getConvergenceCommand(WindowIdentity identity,
      WindowGeometry geometry, WindowVisibility visibility) {
    List<String> commands = setupCommands();

    commands.add(String.format("echo \"%s\" | %s",
        getConvergenceScript(identity, geometry, visibility), AWESOME_CLIENT_BIN));

    return commands;
  }

  /**
   * Get a command for cleaning up the awful rule.
   * 
   * @param identity
   *          the window's identity
   * @return command for clearing the window rule
   */
  public static List<String> getShutdownCommand(WindowIdentity identity) {
    List<String> commands = setupCommands();

    commands.add(String.format("echo \"%s\" | %s", getRemovalScript(identity), AWESOME_CLIENT_BIN));

    return commands;
  }

  /**
   * Builds the lua script for converging the window.
   * 
   * @param identity
   *          the window's identity
   * @param geometry
   *          the window's geometry
   * @param visibility
   *          the window's visibility
   * @return script for converging the window
   */
  private static String getConvergenceScript(WindowIdentity identity, WindowGeometry geometry,
      WindowVisibility visibility) {

    String rule = String.format("rule = %s", AwesomeWindowIdentity.getRulePattern(identity));

    List<String> propList = Lists.newArrayList("border_width = 0", "size_hints_honor = false");
    propList.addAll(AwesomeWindowGeometry.getProps(geometry));
    propList.addAll(AwesomeWindowVisibility.getProps(visibility));

    String properties = String.format("properties = { %s }", Joiner.on(",").join(propList));

    String callback = String.format("callback = %s", AwesomeWindowGeometry.getCallback(geometry));

    String entry = String.format("{ %s, %s, %s }", rule, properties, callback);

    String add = String.format("table.insert(awful.rules.rules, 1, %s)", entry);

    String remove = AwesomeWindowIdentity.getRemovalScript(identity);

    String apply =
        String
            .format(
                "for k,c in pairs(client.get()) do if awful.rules.match(c, %s) then awful.rules.apply(c) end end",
                AwesomeWindowIdentity.getRulePattern(identity));

    return Joiner.on(" ").join(remove, add, apply);
  }

  /**
   * Returns a shell command to raise a window
   */
  public static List<String> raiseWindow(WindowIdentity identity) {
    return clientApiCommand(identity, "c:raise()");
  }

  /**
   * Returns a shell command to lower a window
   */
  public static List<String> lowerWindow(WindowIdentity identity) {
    return clientApiCommand(identity, "c:lower()");
  }

  /**
   * Returns a shell command to run a Lua command on a specific window
   *
   * @param identity
   *          the window's identity
   * @param winCmd
   *          the command to run on the window. The Lua variable describing the window will be called "c"
   * @return shell command to run winCmd on the window
   */
  public static List<String> clientApiCommand(WindowIdentity identity, String winCmd) {
    List<String> commands = setupCommands();

    commands.add(String.format(
        "echo \"for k,c in pairs(client.get()) do if awful.rules.match(c, %s) then %s end end\" | %s",
        AwesomeWindowIdentity.getRulePattern(identity),
        winCmd, AWESOME_CLIENT_BIN));

    return commands;
  }

  /**
   * Builds the lua script for cleaning up the awful rule.
   * 
   * @param identity
   *          the window's identity
   * @return script for clearing the window rule
   */
  private static String getRemovalScript(WindowIdentity identity) {
    return AwesomeWindowIdentity.getRemovalScript(identity);
  }
}
