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

package com.endpoint.lg.support.test.evdev;

import static com.endpoint.lg.support.evdev.InputEventCodes.*;
import static com.endpoint.lg.support.evdev.InputEventTypes.*;

import com.endpoint.lg.support.evdev.InputAbsState;

import com.endpoint.lg.support.evdev.InputRelState;
import com.endpoint.lg.support.evdev.InputEvent;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test <code>InputRelState</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestInputRelState {
  private static final int TEST_REL_X_TYPE = EV_REL;
  private static final int TEST_REL_X_CODE = REL_X;
  private static final int TEST_REL_X_VALUE = 12;

  private static final int TEST_ABS_X_TYPE = EV_ABS;
  private static final int TEST_ABS_X_CODE = ABS_X;
  private static final int TEST_ABS_X_VALUE = 12;

  private static InputEvent relXEvent;
  private static InputEvent absXEvent;

  @BeforeClass
  public static void testSetup() throws Exception {
    relXEvent = new InputEvent(TEST_REL_X_TYPE, TEST_REL_X_CODE, TEST_REL_X_VALUE);
    absXEvent = new InputEvent(TEST_ABS_X_TYPE, TEST_ABS_X_CODE, TEST_ABS_X_VALUE);
  }

  /**
   * Verify that the initial state zeroes all possible EV_REL axes.
   */
  @Test
  public void testInitAxes() {
    InputRelState initState = new InputRelState();

    for (int i = 0; i < REL_CNT; i++) {
      assertEquals(0, initState.getValue(i));
    }
  }

  /**
   * Verify that submitting a REL_X event changes the value cumulatively.
   */
  @Test
  public void testAbsEvent() {
    InputRelState relState = new InputRelState();

    assertTrue(relState.update(relXEvent));

    assertEquals(TEST_REL_X_VALUE, relState.getValue(TEST_REL_X_CODE));

    // EV_REL updates are relative, so verify that update is cumulative.
    assertTrue(relState.update(relXEvent));

    assertEquals(TEST_REL_X_VALUE * 2, relState.getValue(TEST_REL_X_CODE));
  }

  /**
   * Verify that EV_ABS events are ignored.
   */
  @Test
  public void testRelEvent() {
    InputRelState relState = new InputRelState();

    assertFalse(relState.update(absXEvent));

    assertFalse(relState.isDirty());
    assertFalse(relState.isNonZero());
  }
}
