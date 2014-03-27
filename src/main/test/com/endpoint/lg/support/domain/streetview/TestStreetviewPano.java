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
import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import java.util.Map;

import org.junit.Test;
import org.junit.BeforeClass;

import com.endpoint.lg.support.domain.streetview.StreetviewPano;

/**
 * Test <code>StreetviewPano</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestStreetviewPano {
  private static final String TEST_PANOID = "HALqEVwg85r1nYVO65yq9w";
  private static final String OTHER_PANOID = "zk8W4zc2LnxaC1hMOxrczQ";

  private static StreetviewPano pano;

  @BeforeClass
  public static void setup() {
    pano = new StreetviewPano(TEST_PANOID);
  }

  /**
   * Test read access.
   */
  @Test
  public void testGetter() {
    assertEquals(TEST_PANOID, pano.getPanoid());
  }

  /**
   * Test equality method.
   */
  @Test
  public void testEquality() {
    StreetviewPano same = new StreetviewPano(TEST_PANOID);
    StreetviewPano different = new StreetviewPano(OTHER_PANOID);

    assertTrue(pano.equals(same));
    assertTrue(same.equals(pano));

    assertFalse(pano.equals(different));
    assertFalse(different.equals(pano));

    assertFalse(same.equals(different));
    assertFalse(different.equals(same));
  }

  /**
   * Test serialization and deserialization.
   */
  @Test
  public void testSerialization() {
    JsonBuilder deserialized = pano.getJsonBuilder();

    Map<String, Object> data = deserialized.build();

    assertEquals(pano.getMap(), data);

    JsonNavigator message = new JsonNavigator(data);
    StreetviewPano reconstructed = new StreetviewPano(message);

    assertTrue(pano.equals(reconstructed));
    assertEquals(pano.getPanoid(), reconstructed.getPanoid());
    assertEquals(pano.getMap(), reconstructed.getMap());
  }
}
