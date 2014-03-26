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

package com.endpoint.lg.support.viewsync;

import static org.junit.Assert.*;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.python.google.common.collect.Lists;
import org.junit.BeforeClass;

import com.google.common.base.Joiner;

import com.endpoint.lg.support.viewsync.EarthViewSyncState;
import org.junit.Test;

/**
 * Test <code>EarthViewSyncState</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestEarthViewSyncState {
  /**
   * Earth viewsync datagrams default to planet Earth.
   */
  private static final String EXPECTED_PLANET_DEFAULT = "earth";

  private static final int TEST_COUNT = 0;
  private static final double TEST_LAT = 1.1;
  private static final double TEST_LNG = 2.2;
  private static final double TEST_ALT = 3.3;
  private static final double TEST_HDG = 4.4;
  private static final double TEST_TILT = 5.5;
  private static final double TEST_ROLL = 6.6;
  private static final double TEST_TIME_START = 7.7;
  private static final double TEST_TIME_END = 8.8;
  private static final String TEST_PLANET_MARS = "mars";

  private static List<Double> dblFields;
  private static String dblString;

  private static String dgramDefault;
  private static String dgramMars;
  private static List<String> dgramsUnderLength;
  private static List<String> dgramsNotNumeric;

  @BeforeClass
  public static void setupDatagrams() throws Exception {
    dblFields =
        Lists.newArrayList(TEST_LAT, TEST_LNG, TEST_ALT, TEST_HDG, TEST_TILT, TEST_ROLL,
            TEST_TIME_START, TEST_TIME_END);

    dblString = Joiner.on(',').join(dblFields);

    dgramDefault = String.format("%d,%s,", TEST_COUNT, dblString);
    dgramMars = dgramDefault + "mars";

    dgramsUnderLength = Lists.newArrayList();
    for (Integer i = 0; i < 10; i++) {
      List<Integer> fields = Lists.newArrayList();
      for (Integer j = 0; j < i; j++) {
        fields.add(j);
      }
      String dgram = Joiner.on(',').join(fields);
      dgramsUnderLength.add(dgram);
      if (i < 9) {
        dgramsUnderLength.add(dgram + ',');
      }
    }

    dgramsNotNumeric = Lists.newArrayList();
    for (Integer i = 0; i < 9; i++) {
      List<String> fields = Lists.newArrayList();
      for (Integer j = 0; j < 10; j++) {
        fields.add(j.toString());
      }
      fields.set(i, UUID.randomUUID().toString());
      dgramsNotNumeric.add(Joiner.on(',').join(fields));
    }
  }

  /**
   * Helper for verifying numeric test fields.
   * 
   * @param state
   *          the state to test
   */
  private static final void verifyNumericFields(EarthViewSyncState state) {
    assertEquals((double) TEST_COUNT, state.getCounter(), 0);
    assertEquals(TEST_LAT, state.getLocation().getLatitude(), 0);
    assertEquals(TEST_LNG, state.getLocation().getLongitude(), 0);
    assertEquals(TEST_ALT, state.getLocation().getAltitude(), 0);
    assertEquals(TEST_HDG, state.getOrientation().getHeading(), 0);
    assertEquals(TEST_TILT, state.getOrientation().getTilt(), 0);
    assertEquals(TEST_ROLL, state.getOrientation().getRoll(), 0);
    assertEquals(TEST_TIME_START, state.getTimeStart(), 0);
    assertEquals(TEST_TIME_END, state.getTimeEnd(), 0);
  }

  /**
   * Verify that the default planet matches the observed Earth client default.
   */
  @Test
  public void testDefaultPlanet() {
    assertEquals(EXPECTED_PLANET_DEFAULT, EarthViewSyncState.DEFAULT_PLANET);
  }

  /**
   * Test a standard datagram with no explicit planet set.
   */
  @Test
  public void testDefaultPlanetDatagram() {
    EarthViewSyncState state = new EarthViewSyncState(dgramDefault);

    verifyNumericFields(state);
    assertEquals(EarthViewSyncState.DEFAULT_PLANET, state.getPlanet());
  }

  /**
   * Test a datagram with an explicit planet set.
   */
  @Test
  public void testMarsDatagram() {
    EarthViewSyncState state = new EarthViewSyncState(dgramMars);

    verifyNumericFields(state);
    assertEquals(TEST_PLANET_MARS, state.getPlanet());
  }

  /**
   * Test datagrams with less than the expected number of fields.
   */
  @Test
  public void testUnderLengthDatagrams() {
    @SuppressWarnings("unused")
    EarthViewSyncState state;

    for (String dgram : dgramsUnderLength) {
      try {
        state = new EarthViewSyncState(dgram);
        fail("No exception raised for under-length datagram");
      } catch (NumberFormatException e) {
        // ...
      } catch (ArrayIndexOutOfBoundsException e) {
        // ...
      }
    }
  }

  /**
   * Test datagrams with non-numeric values in numeric fields.
   */
  @Test
  public void testNonNumericFieldDatagrams() {
    @SuppressWarnings("unused")
    EarthViewSyncState state;

    for (String dgram : dgramsNotNumeric) {
      try {
        state = new EarthViewSyncState(dgram);
        fail("No exception raised for non-numeric field datagram");
      } catch (NumberFormatException e) {
        // ...
      }
    }
  }

  /**
   * Test serialization and deserialization.
   */
  @Test
  public void testSerialization() {
    EarthViewSyncState state = new EarthViewSyncState(dgramDefault);

    JsonBuilder serialized = state.getJsonBuilder();

    Map<String, Object> data = serialized.build();

    assertEquals(data, state.getMap());

    JsonNavigator message = new JsonNavigator(data);
    EarthViewSyncState reconstructed = new EarthViewSyncState(message);

    verifyNumericFields(reconstructed);
    assertEquals(EarthViewSyncState.DEFAULT_PLANET, reconstructed.getPlanet());

    assertEquals(state.getMap(), reconstructed.getMap());
  }
}
