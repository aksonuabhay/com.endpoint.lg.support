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
 * Model for window identity. Intended for uniquely identifying a window by one
 * or more fields.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public abstract class WindowIdentity {
  protected String windowName;
  protected String windowClass;
  protected String windowInstance;

  public WindowIdentity setName(String wname) {
    this.windowName = wname;
    return this;
  }

  public WindowIdentity setClass(String wclass) {
    this.windowClass = wclass;
    return this;
  }

  public WindowIdentity setInstance(String winstance) {
    this.windowInstance = winstance;
    return this;
  }

  public String getWindowName() {
    return windowName;
  }

  public String getWindowClass() {
    return windowClass;
  }

  public String getWindowInstance() {
    return windowInstance;
  }
}
