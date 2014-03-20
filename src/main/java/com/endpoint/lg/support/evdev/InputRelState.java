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

import interactivespaces.util.data.json.JsonNavigator;

/**
 * Model for the current EV_REL state of an input device.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class InputRelState extends InputAbsState {
  /**
   * Only update the state when events of this type are submitted.
   */
  public static final int TYPE = InputEventTypes.EV_REL;

  public InputRelState() {
    initAxes(InputEventCodes.REL_CNT);
  }

  /**
   * Creates an InputRelState from a JSON message.
   * 
   * @param json
   *          message with axis values
   */
  public InputRelState(JsonNavigator json) {
    initAxes(InputEventCodes.REL_CNT);

    for (String k : json.getCurrentItem().keySet()) {
      setValue(Integer.parseInt(k), json.getInteger(k));
    }
  }

  /**
   * Update this state from an incoming event. Values are cumulative.
   * 
   * @param event
   *          an input event
   * @return true if the state changed
   */
  @Override
  public boolean update(InputEvent event) {
    if (event.getType() == TYPE) {
      return setValue(event.getCode(), event.getValue() + getValue(event.getCode()));
    }

    return false;
  }
}
