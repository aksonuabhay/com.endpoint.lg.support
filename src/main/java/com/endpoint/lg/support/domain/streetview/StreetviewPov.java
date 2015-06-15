/*
 * Copyright (C) 2013 Google Inc.
 * Copyright (C) 2015 End Point Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.endpoint.lg.support.domain.streetview;

import interactivespaces.util.data.json.JsonNavigator;
import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.StandardJsonBuilder; // Added by Abhay

import java.util.Map;

/**
 * A Street View POV, representing Maps API
 * <code>google.maps.StreetViewPov</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class StreetviewPov {
  /**
   * Pov field for heading.
   */
  public static final String FIELD_HEADING = "heading";

  /**
   * Pov field for pitch.
   */
  public static final String FIELD_PITCH = "pitch";

  /**
   * Heading, in degrees.
   */
  private double heading;

  /**
   * Pitch, in degrees.
   */
  private double pitch;

  /**
   * Retrieves the pov heading value.
   * 
   * @return heading in degrees
   */
  public double getHeading() {
    return heading;
  }

  /**
   * Retrieves the pov pitch value.
   * 
   * @return pitch in degrees
   */
  public double getPitch() {
    return pitch;
  }

  /**
   * Sets heading and pitch to the provided values.
   * 
   * @param heading
   *          absolute degrees of yaw
   * @param pitch
   *          absolute degrees of pitch
   */
  public void setAll(double heading, double pitch) {
    this.heading = heading;
    this.pitch = pitch;
  }

  /**
   * Modifies heading and pitch by the provided values.
   * 
   * @param heading
   *          relative degrees of yaw
   * @param pitch
   *          relative degrees of pitch
   */
  public void translate(double heading, double pitch) {
    this.heading += heading;
    this.pitch += pitch;
  }

  /**
   * Constructs from the given heading and pitch.
   * 
   * @param heading
   *          absolute degrees of yaw
   * @param pitch
   *          absolute degrees of pitch
   */
  public StreetviewPov(double heading, double pitch) {
    setAll(heading, pitch);
  }

  /**
   * Constructs from a <code>JsonNavigator</code>.
   * 
   * @param json
   *          with heading and pitch fields
   */
  public StreetviewPov(JsonNavigator json) {
    setAll(json.getDouble(FIELD_HEADING), json.getDouble(FIELD_PITCH));
  }

  /**
   * Get a <code>JsonBuilder</code> of the pov data.
   * 
   * @return json representation of the pov
   */
  public JsonBuilder getJsonBuilder() {
    JsonBuilder json = new StandardJsonBuilder(); // Changed by Abhay

    json.put(FIELD_HEADING, heading);
    json.put(FIELD_PITCH, pitch);

    return json;
  }

  /**
   * Get a <code>Map</code> of the pov data.
   * 
   * @return map representation of the pov
   */
  public Map<String, Object> getMap() {
    return getJsonBuilder().build();
  }

  /**
   * Compare this pov to another.
   * 
   * @param rpov
   *          to be compared
   * @return true if the pov are equal
   */
  public boolean equals(StreetviewPov rpov) {
    return (heading == rpov.getHeading() && pitch == rpov.getPitch());
  }
}
