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

/**
 * Model for the current EV_ABS state of an input device.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class InputAbsState extends InputAxisState {
  /**
   * Make an <code>InputAbsState</code> for EV_ABS.
   */
  public InputAbsState() {
    super(EventTypes.EV_ABS, EventCodes.ABS_CNT);
  }

  /**
   * Override the clear method to not reset values.
   */
  @Override
  public void clear() {
    java.util.Arrays.fill(dirty, false);
  }

  /**
   * Serialize the state. Include all non-zero axes.
   * 
   * @return json representation of the axis state
   */
  public JsonBuilder getNonZeroAsJsonBuilder() {
    JsonBuilder json = new JsonBuilder();

    for (Integer i = 0; i < values.length; i++) {
      if (getValue(i) != 0) {
        json.put(i.toString(), getValue(i));
      }
    }

    return json;
  }
}
