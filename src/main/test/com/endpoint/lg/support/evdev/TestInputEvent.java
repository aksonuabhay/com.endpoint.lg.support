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
import org.junit.BeforeClass;
import org.junit.Test;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import java.nio.ByteBuffer;
import java.util.Map;

import com.endpoint.lg.support.evdev.InputEvent;
import com.endpoint.lg.support.evdev.InputEventCodes;
import com.endpoint.lg.support.evdev.InputEventTypes;

/**
 * Test <code>InputEvent</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestInputEvent {
  private static final int TEST_SYN_TYPE = InputEventTypes.EV_SYN;
  private static final int TEST_SYN_CODE = InputEventCodes.SYN_REPORT;
  private static final int TEST_SYN_VALUE = 0;

  private static final int TEST_ABS_TYPE = InputEventTypes.EV_ABS;
  private static final int TEST_ABS_CODE = InputEventCodes.ABS_RZ;
  private static final int TEST_ABS_VALUE = 32;

  private static ByteBuffer synBuffer;

  @BeforeClass
  public static void setup() throws Exception {
    synBuffer = ByteBuffer.allocate(InputEvent.EVENT_SZ);
    // A buffer full of zeroes should result in a SYN event.
    synBuffer.put(new byte[InputEvent.EVENT_SZ]);
  }

  /**
   * Test getters.
   */
  @Test
  public void testGetters() {
    InputEvent synEvent = new InputEvent(TEST_SYN_TYPE, TEST_SYN_CODE, TEST_SYN_VALUE);

    assertEquals(TEST_SYN_TYPE, synEvent.getType());
    assertEquals(TEST_SYN_CODE, synEvent.getCode());
    assertEquals(TEST_SYN_VALUE, synEvent.getValue());
  }

  /**
   * Test setters.
   */
  @Test
  public void testSetters() {
    InputEvent synEvent = new InputEvent(TEST_SYN_TYPE, TEST_SYN_CODE, TEST_SYN_VALUE);

    synEvent.setType(TEST_ABS_TYPE);
    assertEquals(TEST_ABS_TYPE, synEvent.getType());

    synEvent.setCode(TEST_ABS_CODE);
    assertEquals(TEST_ABS_CODE, synEvent.getCode());

    synEvent.setValue(TEST_ABS_VALUE);
    assertEquals(TEST_ABS_VALUE, synEvent.getValue());
  }

  /**
   * Test reading an event from a buffer.
   */
  @Test
  public void testRaw() {
    InputEvent refined = new InputEvent(synBuffer);

    assertEquals(TEST_SYN_TYPE, refined.getType());
    assertEquals(TEST_SYN_CODE, refined.getCode());
    assertEquals(TEST_SYN_VALUE, refined.getValue());
  }

  /**
   * Test serialization and deserialization.
   */
  @Test
  public void testSerialization() {
    InputEvent synEvent = new InputEvent(TEST_SYN_TYPE, TEST_SYN_CODE, TEST_SYN_VALUE);

    JsonBuilder deserialized = synEvent.getJsonBuilder();

    Map<String, Object> data = deserialized.build();

    assertEquals(synEvent.getMap(), data);

    JsonNavigator message = new JsonNavigator(data);
    InputEvent reconstructed = new InputEvent(message);

    assertEquals(TEST_SYN_TYPE, reconstructed.getType());
    assertEquals(TEST_SYN_CODE, reconstructed.getCode());
    assertEquals(TEST_SYN_VALUE, reconstructed.getValue());

    assertEquals(synEvent.getMap(), reconstructed.getMap());
  }
}
