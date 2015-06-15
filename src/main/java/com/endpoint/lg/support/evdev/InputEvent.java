/*
 * Copyright (C) 2013 Google Inc.
 * Copyright (C) 2015 End Point Corporation
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
import interactivespaces.util.data.json.StandardJsonBuilder;

import java.util.Map;
import java.nio.ByteBuffer;

/**
 * An input event, representing the data structure in linux/input.h.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class InputEvent {
  /**
   * The size of a raw event struct.
   */
  public static final int EVENT_SZ = 24;

  /**
   * Byte offset of a raw event struct's <code>type</code> attribute.
   */
  public static final int OFFSET_TYPE = 16;

  /**
   * Byte offset of a raw event struct's <code>code</code> attribute.
   */
  public static final int OFFSET_CODE = 18;

  /**
   * Byte offset of a raw event struct's <code>value</code> attribute.
   */
  public static final int OFFSET_VALUE = 20;

  /**
   * InputEvent field for event type.
   */
  public static final String FIELD_TYPE = "type";

  /**
   * InputEvent field for event code.
   */
  public static final String FIELD_CODE = "code";

  /**
   * InputEvent field for event value.
   */
  public static final String FIELD_VALUE = "value";

  /**
   * The event type. Though this is an unsigned short in the kernel struct, we
   * use an int.
   */
  protected int type;

  /**
   * The event code. Though this is an unsigned short in the kernel struct, we
   * use an int.
   */
  protected int code;

  /**
   * The event value.
   */
  protected int value;

  public int getType() {
    return type;
  }
  public void setType(int type) {
    this.type = type;
  }

  public int getCode() {
    return code;
  }
  public void setCode(int code) {
    this.code = code;
  }

  public int getValue() {
    return value;
  }
  public void setValue(int value) {
    this.value = value;
  }

  /**
   * Constructs an <code>InputEvent</code> with the provided values.
   * 
   * @param type
   *          event type
   * @param code
   *          event code
   * @param value
   *          event value
   */
  public InputEvent(int type, int code, int value) {
    this.type = type;
    this.code = code;
    this.value = value;
  }

  /**
   * Constructs an <code>InputEvent</code> from a <code>ByteBuffer</code>.
   * 
   * @param buffer
   *          a buffer of size <code>EVENT_SZ</code> read from an evdev device
   */
  public InputEvent(ByteBuffer buffer) {
    type = sliceToShort(buffer, OFFSET_TYPE);
    code = sliceToShort(buffer, OFFSET_CODE);
    value = sliceToInt(buffer, OFFSET_VALUE);
  }

  /**
   * Constructs an <code>InputEvent</code> from a <code>JsonNavigator</code>.
   * 
   * @param json
   *          event data
   */
  public InputEvent(JsonNavigator json) {
    type = json.getInteger(FIELD_TYPE).intValue();
    code = json.getInteger(FIELD_CODE).intValue();
    value = json.getInteger(FIELD_VALUE).intValue();
  }

  /**
   * Retrieves a <code>Map</code> representation of the event.
   * 
   * @return event data as a <code>Map</code>
   */
  public Map<String, Object> getMap() {
    return getJsonBuilder().build();
  }

  /**
   * Retrieves a <code>JsonBuilder</code> representation of the event.
   * 
   * @return event data as <code>JsonBuilder</code>
   */
  public JsonBuilder getJsonBuilder() {
    JsonBuilder json = new StandardJsonBuilder();

    json.put(FIELD_TYPE, type);
    json.put(FIELD_CODE, code);
    json.put(FIELD_VALUE, value);

    return json;
  }

  /**
   * Grabs an <code>int</code> out of a <code>ByteBuffer</code>. Flips
   * endian-ness for compatibility.
   * 
   * @param raw
   *          a ByteBuffer
   * @param start
   *          the starting index of the desired int
   * @return an int pulled from the buffer
   */
  private static int sliceToInt(ByteBuffer buffer, int start) {
    return (int) ((buffer.get(start) & 0xFF) | (buffer.get(start + 1) & 0xFF) << 8
        | (buffer.get(start + 2) & 0xFF) << 16 | (buffer.get(start + 3) & 0xFF) << 24);
  }

  /**
   * Grabs a <code>short</code> out of a <code>ByteBuffer</code>. Flips
   * endian-ness for compatibility.
   * 
   * @param raw
   *          a ByteBuffer
   * @param start
   *          the starting index of the desired short
   * @return a short pulled from the buffer
   */
  private static short sliceToShort(ByteBuffer buffer, int start) {
    return (short) ((buffer.get(start) & 0xFF) | (buffer.get(start + 1) & 0xFF) << 8);
  }
}
