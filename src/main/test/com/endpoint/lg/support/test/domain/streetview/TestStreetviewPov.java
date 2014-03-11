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

package com.endpoint.lg.support.test.domain.streetview;

import static org.junit.Assert.*;
import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import java.util.Map;

import org.junit.Test;
import org.junit.BeforeClass;

import com.endpoint.lg.support.domain.streetview.StreetviewPov;

/**
 * Test a <code>StreetviewPov</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestStreetviewPov {
  private static final double TEST_HEADING = 98.5;
  private static final double TEST_PITCH = 23.42;
  private static final double OTHER_HEADING = 205;
  private static final double OTHER_PITCH = -50.1111;

  private static StreetviewPov pov;

  @BeforeClass
  public static void setup() {
    pov = new StreetviewPov(TEST_HEADING, TEST_PITCH);
  }

  /**
   * Test read access.
   */
  @Test
  public void testGetters() {
    assertEquals(TEST_HEADING, pov.getHeading(), 0);
    assertEquals(TEST_PITCH, pov.getPitch(), 0);
  }
  
  /**
   * Test write methods.
   */
  @Test
  public void testSetters() {
    StreetviewPov shifty = new StreetviewPov(TEST_HEADING, TEST_PITCH);
    
    shifty.setAll(OTHER_HEADING, OTHER_PITCH);
    
    assertEquals(OTHER_HEADING, shifty.getHeading(), 0);
    assertEquals(OTHER_PITCH, shifty.getPitch(), 0);
  }

  /**
   * Test comparisons.
   */
  @Test
  public void testComparison() {
    StreetviewPov same = new StreetviewPov(TEST_HEADING, TEST_PITCH);
    StreetviewPov different = new StreetviewPov(OTHER_HEADING, OTHER_PITCH);

    assertTrue(pov.equals(same));
    assertTrue(same.equals(pov));

    assertFalse(pov.equals(different));
    assertFalse(different.equals(pov));

    assertFalse(same.equals(different));
    assertFalse(different.equals(same));
  }
  
  /**
   * Test POV translation.
   */
  @Test
  public void testTranslation() {
    StreetviewPov translating = new StreetviewPov(TEST_HEADING, TEST_PITCH);
    
    translating.translate(OTHER_HEADING, OTHER_PITCH);
    
    assertEquals(TEST_HEADING + OTHER_HEADING, translating.getHeading(), 0);
    assertEquals(TEST_PITCH + OTHER_PITCH, translating.getPitch(), 0);
  }

  /**
   * Test serialization and deserialization.
   */
  @Test
  public void testSerialization() {
    JsonBuilder deserialized = pov.getJsonBuilder();

    Map<String, Object> data = deserialized.build();

    assertEquals(pov.getMap(), data);

    JsonNavigator message = new JsonNavigator(data);
    StreetviewPov reconstructed = new StreetviewPov(message);

    assertEquals(pov.getHeading(), reconstructed.getHeading(), 0);
    assertEquals(pov.getPitch(), reconstructed.getPitch(), 0);

    assertEquals(pov.getMap(), reconstructed.getMap());
  }
}
