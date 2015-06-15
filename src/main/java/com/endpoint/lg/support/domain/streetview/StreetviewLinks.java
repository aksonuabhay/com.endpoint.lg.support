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
import interactivespaces.util.data.json.StandardJsonNavigator;

/**
 * A collection of links to neighboring Street View panoramas.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class StreetviewLinks {

  /**
   * StreetviewLinks field for links to neighboring panos.
   */
  public static final String FIELD_LINKS = "links";

  private StreetviewLink[] links;

  public StreetviewLink[] getLinks() {
    return links;
  }

  public boolean hasLinks() {
    return links.length > 0;
  }

  /**
   * Constructs an empty link collection.
   */
  public StreetviewLinks() {
    links = new StreetviewLink[0];
  }

  /**
   * Constructs a link collection from given <code>JsonNavigator</code>.
   * 
   * <p>
   * Incoming data should be in the format: { "links": [ { "pano": <panoid>,
   * "heading": <heading> }, { ... } ] }
   */
  public StreetviewLinks(JsonNavigator json) {
    json.down(FIELD_LINKS);

    int numLinks = json.getSize();
    links = new StreetviewLink[numLinks];

    for (int i = 0; i < numLinks; i++) {
      JsonNavigator item = new StandardJsonNavigator(json.getItem(i));
      links[i] = new StreetviewLink(item);
    }

    json.up(); // leave it the way you found it
  }

  /**
   * Helper for finding the angular difference between two headings.
   * 
   * @param h1
   *          first heading in degrees (-inf, inf)
   * @param h2
   *          second heading in degrees (-inf, inf)
   * @return difference in degrees [0, 180)
   */
  private double headingDifference(double source, double target) {
    double diff = Math.abs(target - source) % 360;

    return diff >= 180 ? diff - (diff - 180) * 2 : diff;
  }

  /**
   * Get the nearest link to the given heading.
   * 
   * @param heading
   *          in degrees
   * @return nearest link or null if empty
   */
  public StreetviewLink getNearestLink(double heading) {
    StreetviewLink nearest = null;
    double nearestDifference = 180;

    for (StreetviewLink link : links) {
      double difference = headingDifference(heading, link.getHeading());

      if (difference < nearestDifference) {
        nearestDifference = difference;
        nearest = link;
      }
    }

    return nearest;
  }

  /**
   * Get the furthest link from the given heading.
   * 
   * @param heading
   *          in degrees
   * @return furthest link or null if empty
   */
  public StreetviewLink getFurthestLink(double heading) {
    return getNearestLink(heading - 180);
  }
}
