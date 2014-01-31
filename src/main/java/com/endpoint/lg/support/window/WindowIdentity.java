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
public abstract class WindowIdentity {
  public enum IdType {
    NAME, CLASS, INSTANCE
  };

  protected String identifier;
  protected IdType type;
  
  protected void setIdentifier(String windowIdentifier, IdType type) {
    this.identifier = windowIdentifier;
    this.type = type;
  }

  /**
   * Returns the search string.
   * @return string matching a window property
   */
  public String getIdentifier() {
    return identifier;
  }
  
  /**
   * Returns the type of property to be searched.
   * @return property type
   */
  public IdType getType() {
    return type;
  }
}
