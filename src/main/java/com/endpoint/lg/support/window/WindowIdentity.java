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

/**
 * Identifies a window by a public, searchable property.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public abstract class WindowIdentity implements XdotoolCommand {
  public enum IdType {
    NAME, CLASS, INSTANCE
  };

  protected static final String COMMON_FLAGS = "search --maxdepth 1 --limit 1 --sync";
  protected static final String MATCH_ALL = "--all --name --class --classname";
  protected static final String MATCH_NAME = "--name";
  protected static final String MATCH_CLASS = "--class";
  protected static final String MATCH_INSTANCE = "--classname";

  protected String identifier;
  protected IdType type;

  protected void setIdentifier(String windowIdentifier, IdType type) {
    this.identifier = windowIdentifier;
    this.type = type;
  }

  /**
   * Returns the search string.
   * 
   * @return string matching a window property
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Returns flags used by all types of WindowIdentity.
   * 
   * @return common flags for xdotool search
   */
  protected String getCommonFlags() {
    return COMMON_FLAGS;
  }

  /**
   * Get flags used to match the window identifier.
   * 
   * @return match flags
   */
  protected String getMatchFlags() {
    switch (type) {
      case NAME:
        return MATCH_NAME;
      case CLASS:
        return MATCH_CLASS;
      case INSTANCE:
        return MATCH_INSTANCE;
      default:
        return MATCH_ALL;
    }
  }

  public String getFlags() {
    return getFlags(false);
  }

  /**
   * Gets search flags with an explicit --onlyvisible option.
   * 
   * @param onlyVisible
   *          only match visible windows
   * @return xdotool search flags
   */
  public String getFlags(boolean onlyVisible) {
    String commonFlags = getCommonFlags();

    if (onlyVisible) {
      commonFlags += " --onlyvisible";
    }

    return String.format("%s %s %s", commonFlags, getMatchFlags(), getIdentifier());
  }

  /**
   * Returns the type of property to be searched.
   * 
   * @return property type
   */
  public IdType getType() {
    return type;
  }
}
