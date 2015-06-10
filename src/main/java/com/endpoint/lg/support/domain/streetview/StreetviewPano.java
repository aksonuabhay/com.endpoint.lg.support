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
import interactivespaces.util.data.json.StandardJsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import java.util.Map;

/**
 * A Street View panorama, encapsulating several Maps API objects.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class StreetviewPano {
  /**
   * StreetviewPano field for panoid.
   */
  public static final String FIELD_PANOID = "panoid";

  private String panoid;

  /**
   * Retrieves the panoid value.
   * 
   * @return panoid for this pano
   */
  public String getPanoid() {
    return panoid;
  }

  /**
   * Constructs a pano with the given panoid and no links.
   * 
   * @param panoid
   */
  public StreetviewPano(String panoid) {
    this.panoid = panoid;
  }

  /**
   * Constructs a pano from a <code>JsonNavigator</code>.
   * 
   * @param json
   *          with pano data
   */
  public StreetviewPano(JsonNavigator json) {
    panoid = json.getString(FIELD_PANOID);
  }

  /**
   * Retrieves the pano as a <code>JsonBuilder</code>.
   * 
   * @return json representation of the pano
   */
  public JsonBuilder getJsonBuilder() {
    JsonBuilder json = new StandardJsonBuilder();

    json.put(FIELD_PANOID, panoid);

    return json;
  }

  /**
   * Retrieves the pano as a <code>Map</code>.
   * 
   * @return map representation of the pano
   */
  public Map<String, Object> getMap() {
    return getJsonBuilder().build();
  }

  /**
   * Compare this pano to another.
   * 
   * @param rpano
   *          to be compared
   * @return true if the panos are equal
   */
  public boolean equals(StreetviewPano rpano) {
    return panoid == rpano.getPanoid();
  }
}
