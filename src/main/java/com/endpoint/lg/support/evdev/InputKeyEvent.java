/*
 * Copyright (C) 2014 Google Inc.
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

/**
 * A message class for key and button events. Intended to simplify messaging on
 * routes specific to EV_KEY events.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class InputKeyEvent extends InputEvent {
  /**
   * Constructs an <code>InputKeyMessage</code> with the provided values.
   * 
   * @param type
   *          key event type
   * @param code
   *          key event code
   */
  public InputKeyEvent(int code, int value) {
    super(InputEventTypes.EV_KEY, code, value);
  }

  /**
   * Constructs an <code>InputKeyMessage</code> from an <code>InputEvent</code>.
   * 
   * @param event
   *          event data
   */
  public InputKeyEvent(InputEvent event) {
    super(InputEventTypes.EV_KEY, event.getCode(), event.getValue());
  }

  /**
   * Constructs an <code>InputKeyMessage</code> from a
   * <code>JsonNavigator</code>.
   * 
   * @param json
   *          event data
   */
  public InputKeyEvent(JsonNavigator json) {
    super(InputEventTypes.EV_KEY, json.getInteger(InputEvent.FIELD_CODE), json
        .getInteger(InputEvent.FIELD_VALUE));
  }

  /**
   * Serialize an <code>InputEvent</code> as an <code>InputKeyEvent</code>.
   * 
   * @param keyEvent
   *          event data
   * @return event data as a <code>JsonBuilder</code>
   */
  public static JsonBuilder serialize(InputEvent keyEvent) {
    JsonBuilder json = new StandardJsonBuilder();

    json.put(InputEvent.FIELD_CODE, keyEvent.getCode());
    json.put(InputEvent.FIELD_VALUE, keyEvent.getValue());

    return json;
  }

  /**
   * Retrieves a <code>JsonBuilder</code> representation of the event.
   * 
   * @return event data as <code>JsonBuilder</code>
   */
  public JsonBuilder getJsonBuilder() {
    JsonBuilder json = new StandardJsonBuilder();

    json.put(InputEvent.FIELD_CODE, code);
    json.put(InputEvent.FIELD_VALUE, value);

    return serialize(this);
  }

  /**
   * Retrieves a <code>Map</code> representation of the event.
   * 
   * @return event data as a <code>Map</code>
   */
  public Map<String, Object> getMap() {
    return getJsonBuilder().build();
  }
}
