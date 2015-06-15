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

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;
import interactivespaces.util.data.json.StandardJsonBuilder;

import java.util.Map;

/**
 * A reference to a neighboring Street View panorama, based on Maps API <code>google.maps.StreetViewLink</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class StreetviewLink {
  /**
   * Field for panoid.
   */
  public static final String FIELD_PANO = "pano";
  
  /**
   * Field for heading relative to the source pano.
   */
  public static final String FIELD_HEADING = "heading";
  
  private String pano;
  private double heading;
  
  /**
   * Retrieves the panoid.
   * @return panoid
   */
  public String getPano() {
    return pano;
  }
  
  /**
   * Retrieves the heading.
   * @return heading in degrees
   */
  public double getHeading() {
    return heading;
  }
  
  /**
   * Constructs a link with the given panoid and heading.
   * @param pano panoid string
   * @param heading direction from the source pano, in degrees
   */
  public StreetviewLink(String pano, double heading) {
    this.pano = pano;
    this.heading = heading;
  }
  
  /**
   * Constructs a link from a <code>JsonNavigator</code>.
   * @param json contains pano and heading fields
   */
  public StreetviewLink(JsonNavigator json) {
    pano = json.getString(FIELD_PANO);
    heading = json.getDouble(FIELD_HEADING);
  }
  
  /**
   * Retrieves a <code>JsonBuilder</code> representation of the link.
   * @return json object with link data
   */
  public JsonBuilder getJsonBuilder() {
    JsonBuilder json = new StandardJsonBuilder();
    
    json.put(FIELD_PANO, pano);
    json.put(FIELD_HEADING, heading);
    
    return json;
  }
  
  /**
   * Retrieves a <code>Map</code> representation of the link.
   * @return map object with link data
   */
  public Map<String, Object> getMap() {
    return getJsonBuilder().build();
  }
}
