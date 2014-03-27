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

import static org.junit.Assert.*;
import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import java.util.Map;

import org.junit.Test;

import com.endpoint.lg.support.evdev.InputEvent;
import com.endpoint.lg.support.evdev.InputEventCodes;
import com.endpoint.lg.support.evdev.InputEventTypes;
import com.endpoint.lg.support.evdev.InputKeyEvent;

/**
 * Test <code>InputKeyEvent</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestInputKeyEvent {
  private static final int KEY_TYPE = InputEventTypes.EV_KEY;
  private static final int TEST_LEFT_CODE = InputEventCodes.BTN_LEFT;
  private static final int TEST_LEFT_VALUE = 0;

  /**
   * Test getters.
   */
  @Test
  public void testGetters() {
    InputEvent leftEvent = new InputKeyEvent(TEST_LEFT_CODE, TEST_LEFT_VALUE);

    assertEquals(KEY_TYPE, leftEvent.getType());
    assertEquals(TEST_LEFT_CODE, leftEvent.getCode());
    assertEquals(TEST_LEFT_VALUE, leftEvent.getValue());
  }
  
  /**
   * Test constructing from a raw <code>InputEvent</code>.
   */
  @Test
  public void testSuperConstructor() {
    InputEvent rawEvent = new InputEvent(KEY_TYPE, TEST_LEFT_CODE, TEST_LEFT_VALUE);
    
    InputKeyEvent leftEvent = new InputKeyEvent(rawEvent);
    
    assertEquals(KEY_TYPE, leftEvent.getType());
    assertEquals(TEST_LEFT_CODE, leftEvent.getCode());
    assertEquals(TEST_LEFT_VALUE, leftEvent.getValue());
  }

  /**
   * Test serialization and deserialization.
   */
  @Test
  public void testSerialization() {
    InputEvent leftEvent = new InputKeyEvent(TEST_LEFT_CODE, TEST_LEFT_VALUE);

    JsonBuilder deserialized = leftEvent.getJsonBuilder();

    Map<String, Object> data = deserialized.build();

    assertEquals(leftEvent.getMap(), data);

    JsonNavigator message = new JsonNavigator(data);
    InputKeyEvent reconstructed = new InputKeyEvent(message);

    assertEquals(KEY_TYPE, reconstructed.getType());
    assertEquals(TEST_LEFT_CODE, reconstructed.getCode());
    assertEquals(TEST_LEFT_VALUE, reconstructed.getValue());

    assertEquals(leftEvent.getMap(), reconstructed.getMap());
  }
}
