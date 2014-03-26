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

package com.endpoint.lg.support.domain.streetview;

import static org.junit.Assert.*;

import java.util.Map;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import org.junit.BeforeClass;
import org.junit.Test;

import com.endpoint.lg.support.domain.streetview.StreetviewLink;

/**
 * Test <code>StreetviewLink</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestStreetviewLink {
  private static final String TEST_PANOID = "HALqEVwg85r1nYVO65yq9w";
  private static final double TEST_HEADING = 125;

  private static StreetviewLink link;

  @BeforeClass
  public static void setup() {
    link = new StreetviewLink(TEST_PANOID, TEST_HEADING);
  }

  /**
   * Test getters.
   */
  @Test
  public void testValues() {
    assertEquals(TEST_PANOID, link.getPano());
    assertEquals(TEST_HEADING, link.getHeading(), 0);
  }

  /**
   * Test model serialization and deserialization.
   */
  @Test
  public void testSerialization() {
    JsonBuilder deserialized = link.getJsonBuilder();

    Map<String, Object> data = deserialized.build();

    assertEquals(link.getMap(), data);

    JsonNavigator message = new JsonNavigator(data);
    StreetviewLink reconstructed = new StreetviewLink(message);

    assertEquals(link.getPano(), reconstructed.getPano());
    assertEquals(link.getHeading(), reconstructed.getHeading(), 0);

    assertEquals(link.getMap(), reconstructed.getMap());
  }

}
