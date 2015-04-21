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

/**
 * Model for the current EV_ABS state of an input device.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class InputAbsState {
  /**
   * Only update the state when events of this type are submitted.
   */
  public static final int TYPE = InputEventTypes.EV_ABS;

  /**
   * The number of axes for this type.
   */
  public static final int NUM_AXES = InputEventCodes.ABS_CNT;

  protected int values[];
  protected boolean dirty;

  protected void initAxes(int numAxes) {
    values = new int[numAxes];
    clean();
  }

  /**
   * Creates an InputAbsState.
   */
  public InputAbsState() {
    initAxes(NUM_AXES);
  }

  /**
   * Loads all axes from a json message.
   */
  protected void deserialize(JsonNavigator json) {
    for (String k : json.getCurrentItem().keySet()) {
      setValue(Integer.parseInt(k), json.getInteger(k));
    }
  }

  /**
   * Creates an InputAbsState from a serialized state.
   */
  public InputAbsState(JsonNavigator json) {
    initAxes(NUM_AXES);

    deserialize(json);
  }

  /**
   * Returns the value for the given axis.
   * 
   * @param code
   *          axis code
   * @return axis value
   */
  public int getValue(int axis) {
    return values[axis];
  }

  /**
   * Sets a value for an axis.
   * 
   * @param axis
   *          axis code
   * @param value
   *          axis value
   * @return true if the value changed
   */
  public boolean setValue(int axis, int value) {
    if (getValue(axis) != value) {
      values[axis] = value;
      dirty = true;
      return true;
    }

    return false;
  }

  /**
   * Update this state from an incoming event.
   * 
   * @param event
   *          an input event
   * @return true if the state changed
   */
  public boolean update(InputEvent event) {
    if (event.getType() == TYPE) {
      return setValue(event.getCode(), event.getValue());
    }

    return false;
  }

  /**
   * Serialize the state. Only include axes which are not zero.
   * 
   * @return json representation of the axis state
   */
  public JsonBuilder getJsonBuilder() {
    JsonBuilder json = new JsonBuilder();

    for (Integer i = 0; i < values.length; i++) {
      if (getValue(i) != 0) {
        json.put(i.toString(), getValue(i));
      }
    }

    return json;
  }

  /**
   * Clear the state's dirtiness.
   */
  public void clean() {
    dirty = false;
  }

  /**
   * Check for dirt.
   */
  public boolean isDirty() {
    return dirty;
  }

  /**
   * Zero the state.
   */
  public void zero() {
    java.util.Arrays.fill(values, 0);
    clean();
  }

  /**
   * Check for non-zero axes.
   */
  public boolean isNonZero() {
    for (int i = 0; i < values.length; i++) {
      if (getValue(i) != 0)
        return true;
    }

    return false;
  }
}
