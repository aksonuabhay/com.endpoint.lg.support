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
  private static StreetviewLinks emptyLinks;
  private static StreetviewLinks testLinks;
  private static double[] expected;

  @BeforeClass
  public static void testSetup() {
    JsonBuilder testBuilder = new JsonBuilder();
    JsonBuilder testArray = testBuilder.newArray(StreetviewLinks.FIELD_LINKS);

    testArray.put(new StreetviewLink("at_1", 1).getMap());
    testArray.put(new StreetviewLink("at_60", 60).getMap());
    testArray.put(new StreetviewLink("at_178", 178).getMap());

    testLinks = new StreetviewLinks(new JsonNavigator(testBuilder.build()));

    emptyLinks = new StreetviewLinks();

    expected =
        new double[] { 1, 1, 1, 1, 60, 60, 60, 60, 60, 60, 60, 60, 178, 178, 178, 178, 178, 178,
            178, 178, 178, 178, 178, 178, 178, 178, 178, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
  }

  @Test
  public void test() {
    assertFalse("Empty object has no links", emptyLinks.hasLinks());

    assertTrue("Test object has links", testLinks.hasLinks());

    assertEquals(3, testLinks.getLinks().length);

    double testHeading, front, behind;

    for (int i = 0; i < 36; i++) {
      testHeading = i * 10;

      front = testLinks.getNearestLink(testHeading).getHeading();
      behind = testLinks.getFurthestLink(testHeading).getHeading();

      assertEquals(String.format("%.1f", testHeading), expected[i], front, 0);
      assertEquals(String.format("%.1f", testHeading), expected[(i + 18) % 36], behind, 0);

      testHeading = i * 10 + 360.0;

      front = testLinks.getNearestLink(testHeading).getHeading();
      behind = testLinks.getFurthestLink(testHeading).getHeading();

      assertEquals(String.format("%.1f", testHeading), expected[i], front, 0);
      assertEquals(String.format("%.1f", testHeading), expected[(i + 18) % 36], behind, 0);

      testHeading = i * 10 + 720.0;

      front = testLinks.getNearestLink(testHeading).getHeading();
      behind = testLinks.getFurthestLink(testHeading).getHeading();

      assertEquals(String.format("%.1f", testHeading), expected[i], front, 0);
      assertEquals(String.format("%.1f", testHeading), expected[(i + 18) % 36], behind, 0);

      testHeading = i * 10 - 360.0;

      front = testLinks.getNearestLink(testHeading).getHeading();
      behind = testLinks.getFurthestLink(testHeading).getHeading();

      assertEquals(String.format("%.1f", testHeading), expected[i], front, 0);
      assertEquals(String.format("%.1f", testHeading), expected[(i + 18) % 36], behind, 0);

      testHeading = i * 10 - 720.0;

      front = testLinks.getNearestLink(testHeading).getHeading();
      behind = testLinks.getFurthestLink(testHeading).getHeading();

      assertEquals(String.format("%.1f", testHeading), expected[i], front, 0);
      assertEquals(String.format("%.1f", testHeading), expected[(i + 18) % 36], behind, 0);
    }
  }
}
