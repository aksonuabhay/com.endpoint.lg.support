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

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import com.endpoint.lg.support.domain.streetview.StreetviewLink;
import com.endpoint.lg.support.domain.streetview.StreetviewLinks;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the StreetviewLinks interface.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestStreetviewLinks {
  private final static String[] TEST_NAMES = { "at_1", "at_60", "at_178" };
  private final static double[] TEST_ANGLES = { 1, 60, 178 };
  private final static double[] EXPECTED = { 1, 1, 1, 1, 60, 60, 60, 60, 60, 60, 60, 60, 178, 178, 178,
      178, 178, 178, 178, 178, 178, 178, 178, 178, 178, 178, 178, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

  private static JsonBuilder testBuilder;
  private static JsonNavigator testMessage;

  @BeforeClass
  public static void setupMessage() {
    testBuilder = new JsonBuilder();
    JsonBuilder testArray = testBuilder.newArray(StreetviewLinks.FIELD_LINKS);

    for (int i = 0; i < TEST_NAMES.length; i++) {
      testArray.put(new StreetviewLink(TEST_NAMES[i], TEST_ANGLES[i]).getMap());
    }

    testMessage = new JsonNavigator(testBuilder.build());
  }

  /**
   * Test emptiness.
   */
  @Test
  public void testEmpty() {
    StreetviewLinks emptyLinks = new StreetviewLinks();

    assertFalse("Empty object has no links", emptyLinks.hasLinks());

    assertNull("Empty object has no nearest link", emptyLinks.getNearestLink(0));
    assertNull("Empty object has no furthest link", emptyLinks.getFurthestLink(0));

    assert (emptyLinks.getLinks().length == 0);
  }

  /**
   * Test message deserialization.
   */
  @Test
  public void testDeserialize() {
    StreetviewLinks links = new StreetviewLinks(testMessage);

    assertTrue("Deserialized object has links", links.hasLinks());

    StreetviewLink linkArray[] = links.getLinks();

    assert (TEST_NAMES.length == linkArray.length);

    for (int i = 0; i < TEST_NAMES.length; i++) {
      assertEquals(TEST_NAMES[i], linkArray[i].getPano());
      assertEquals(TEST_ANGLES[i], linkArray[i].getHeading(), 0);
    }
  }

  /**
   * Test finding the nearest and furthest links relative to a heading.
   */
  @Test
  public void testNearestAndFurthest() {
    StreetviewLinks links = new StreetviewLinks(testMessage);

    double testHeading, front, behind;

    for (int i = 0; i < 36; i++) {
      testHeading = i * 10;

      front = links.getNearestLink(testHeading).getHeading();
      behind = links.getFurthestLink(testHeading).getHeading();

      assertEquals(String.format("%.1f", testHeading), EXPECTED[i], front, 0);
      assertEquals(String.format("%.1f", testHeading), EXPECTED[(i + 18) % 36], behind, 0);

      testHeading = i * 10 + 360.0;

      front = links.getNearestLink(testHeading).getHeading();
      behind = links.getFurthestLink(testHeading).getHeading();

      assertEquals(String.format("%.1f", testHeading), EXPECTED[i], front, 0);
      assertEquals(String.format("%.1f", testHeading), EXPECTED[(i + 18) % 36], behind, 0);

      testHeading = i * 10 + 720.0;

      front = links.getNearestLink(testHeading).getHeading();
      behind = links.getFurthestLink(testHeading).getHeading();

      assertEquals(String.format("%.1f", testHeading), EXPECTED[i], front, 0);
      assertEquals(String.format("%.1f", testHeading), EXPECTED[(i + 18) % 36], behind, 0);

      testHeading = i * 10 - 360.0;

      front = links.getNearestLink(testHeading).getHeading();
      behind = links.getFurthestLink(testHeading).getHeading();

      assertEquals(String.format("%.1f", testHeading), EXPECTED[i], front, 0);
      assertEquals(String.format("%.1f", testHeading), EXPECTED[(i + 18) % 36], behind, 0);

      testHeading = i * 10 - 720.0;

      front = links.getNearestLink(testHeading).getHeading();
      behind = links.getFurthestLink(testHeading).getHeading();

      assertEquals(String.format("%.1f", testHeading), EXPECTED[i], front, 0);
      assertEquals(String.format("%.1f", testHeading), EXPECTED[(i + 18) % 36], behind, 0);
    }
  }
}
