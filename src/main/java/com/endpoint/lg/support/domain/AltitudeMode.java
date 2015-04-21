/*
 * Copyright (C) 2013 Google Inc.
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

package com.endpoint.lg.support.domain;

/**
 * @author Keith M. Hughes
 */
public enum AltitudeMode {
  /**
   * Altitude is absolute.
   */
  ABSOLUTE("absolute"),

  /**
   * Altitude is clamped to ground.
   */
  CLAMP_TO_GROUND("clampToGround"),

  /**
   * Altitude is clamped to sea floor.
   */
  CLAMP_TO_SEA_FLOOR("clampToSeaFloor"),

  /**
   * Altitude is relative to ground.
   */
  RELATIVE_TO_GROUND("relativeToGround"),

  /**
   * Altitude is relative to the sea floor.
   */
  RELATIVE_TO_SEA_FLOOR("relativeToSeaFloor");

  /**
   * The value of the enum to use when speaking to Google Earth.
   */
  private String value;

  private AltitudeMode(String value) {
    this.value = value;
  }

  /**
   * get the value for the string to be used.
   * 
   * @return the value
   */
  public String getValue() {
    return value;
  }
}
