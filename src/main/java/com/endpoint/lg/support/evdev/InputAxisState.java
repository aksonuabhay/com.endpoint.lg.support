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

/**
 * A class for tracking and serialization of input device axes.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public abstract class InputAxisState {
  private int type;
  protected int values[];
  protected boolean dirty[];

  public int getType() {
    return type;
  }

  /**
   * Creates an InputAxisState with the given type and number of axes.
   * 
   * @param type
   *          a type from EventTypes
   * @param numAxes
   *          number of axes
   */
  public InputAxisState(int type, int numAxes) {
    this.type = type;

    values = new int[numAxes];
    dirty = new boolean[numAxes];

    clear();
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
      dirty[axis] = true;
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
    if (event.getType() == getType()) {
      return setValue(event.getCode(), event.getValue());
    }

    return false;
  }

  /**
   * Serialize the state. Only include axes which have been touched.
   * 
   * @return json representation of the axis state
   */
  public JsonBuilder getDirtyAsJsonBuilder() {
    JsonBuilder json = new JsonBuilder();

    for (Integer i = 0; i < values.length; i++) {
      if (isDirty(i)) {
        json.put(i.toString(), getValue(i));
      }
    }

    return json;
  }

  /**
   * Deserialize the state.
   * 
   * @param json
   *          incoming state message
   * @return true if the state changed
   */
  public boolean update(JsonNavigator json) {
    boolean updated = false;

    for (String k : json.getCurrentItem().keySet()) {
      if (setValue(Integer.parseInt(k), json.getInteger(k)))
        updated = true;
    }

    return updated;
  }

  /**
   * Clear the state.
   */
  public void clear() {
    java.util.Arrays.fill(values, 0);
    java.util.Arrays.fill(dirty, false);
  }

  /**
   * Check for dirty axes.
   */
  public boolean isDirty() {
    for (boolean b : dirty) {
      if (b)
        return true;
    }

    return false;
  }

  /**
   * Check for a particular dirty axis.
   */
  public boolean isDirty(int axis) {
    return dirty[axis];
  }
}
