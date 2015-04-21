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
 * A location in space.
 *
 * @author Keith M. Hughes
 */
public class Location {

  /**
   * The latitude.
   */
  private Double latitude;

  /**
   * The longitude.
   */
  private Double longitude;

  /**
   * The altitude.
   */
  private Double altitude;

  public Location() {
  }

  public Location(Double latitude, Double longitude, Double altitude) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.altitude = altitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getAltitude() {
    return altitude;
  }

  public void setAltitude(Double altitude) {
    this.altitude = altitude;
  }
}
