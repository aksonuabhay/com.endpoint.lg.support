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

package com.endpoint.lg.support.evdev;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import com.endpoint.lg.support.evdev.InputAbsState;
import com.endpoint.lg.support.evdev.InputEvent;
import static com.endpoint.lg.support.evdev.InputEventTypes.*;
import static com.endpoint.lg.support.evdev.InputEventCodes.*;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test <code>InputAbsState</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestInputAbsState {
  private static final int TEST_ABS_X_TYPE = EV_ABS;
  private static final int TEST_ABS_X_CODE = ABS_X;
  private static final int TEST_ABS_X_VALUE = 12;

  private static final int TEST_REL_X_TYPE = EV_REL;
  private static final int TEST_REL_X_CODE = REL_X;
  private static final int TEST_REL_X_VALUE = 12;

  private static InputEvent absXEvent;
  private static InputEvent relXEvent;

  @BeforeClass
  public static void testSetup() throws Exception {
    absXEvent = new InputEvent(TEST_ABS_X_TYPE, TEST_ABS_X_CODE, TEST_ABS_X_VALUE);
    relXEvent = new InputEvent(TEST_REL_X_TYPE, TEST_REL_X_CODE, TEST_REL_X_VALUE);
  }

  /**
   * Verify that dirtiness is tracked effectively.
   */
  @Test
  public void testDirtiness() {
    InputAbsState absState = new InputAbsState();

    assertFalse(absState.isDirty());
    assertFalse(absState.isNonZero());

    assertTrue(absState.update(absXEvent));

    assertTrue(absState.isDirty());
    assertTrue(absState.isNonZero());

    absState.clean();

    assertFalse(absState.isDirty());
    assertTrue(absState.isNonZero());
  }

  /**
   * Verify that the state can be zeroed.
   */
  @Test
  public void testZero() {
    InputAbsState absState = new InputAbsState();

    absState.update(absXEvent);

    absState.zero();

    assertFalse(absState.isDirty());
    assertFalse(absState.isNonZero());
  }

  /**
   * Verify that the initial state zeroes all possible EV_ABS axes.
   */
  @Test
  public void testInitAxes() {
    InputAbsState initState = new InputAbsState();

    for (int i = 0; i < ABS_CNT; i++) {
      assertEquals(0, initState.getValue(i));
    }
  }

  /**
   * Verify that submitting an ABS_X event changes the value absolutely.
   */
  @Test
  public void testAbsEvent() {
    InputAbsState absState = new InputAbsState();

    assertTrue(absState.update(absXEvent));

    assertEquals(TEST_ABS_X_VALUE, absState.getValue(TEST_ABS_X_CODE));

    // EV_ABS updates are absolute, so verify that update is idempotent.
    assertFalse(absState.update(absXEvent));

    assertEquals(TEST_ABS_X_VALUE, absState.getValue(TEST_ABS_X_CODE));
  }

  /**
   * Verify that EV_REL events are ignored.
   */
  @Test
  public void testRelEvent() {
    InputAbsState absState = new InputAbsState();

    assertFalse(absState.update(relXEvent));

    assertFalse(absState.isDirty());
    assertFalse(absState.isNonZero());
  }

  /**
   * Verify that a clean, zero state can be serialized and deserialized
   * properly.
   */
  @Test
  public void testCleanSerialization() {
    InputAbsState absState = new InputAbsState();

    JsonBuilder serialized = absState.getJsonBuilder();

    JsonNavigator message = new JsonNavigator(serialized.build());
    InputAbsState reconstructed = new InputAbsState(message);

    assertFalse(reconstructed.isDirty());
    assertFalse(reconstructed.isNonZero());
  }

  /**
   * Verify that a dirty state can be serialized and deserialized properly.
   */
  @Test
  public void testDirtySerialization() {
    InputAbsState absState = new InputAbsState();

    absState.update(absXEvent);

    JsonBuilder serialized = absState.getJsonBuilder();

    JsonNavigator message = new JsonNavigator(serialized.build());
    InputAbsState reconstructed = new InputAbsState(message);

    assertEquals(TEST_ABS_X_VALUE, reconstructed.getValue(TEST_ABS_X_CODE));

    assertTrue(reconstructed.isDirty());
    assertTrue(reconstructed.isNonZero());
  }
}
