/*
 * Copyright (C) 2013 Google Inc.
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

/**
 * A helper for tracking the state of evdev absolute axes and keys/buttons.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class InputDeviceState {
  private int[] rel;
  private int[] abs;
  private int[] key;

  /**
   * Getter for EV_REL codes. EV_REL values are cumulative and
   * <code>flushRel()</code> should be called after reading all desired values.
   * 
   * @param code
   *          from <code>EventCodes</code>
   * @return last known axis value (cumulative)
   */
  public int getRel(int code) {
    return rel[code];
  }

  /**
   * Getter for EV_ABS codes.
   * 
   * @param code
   *          from <code>EventCodes</code>
   * @return last known axis value
   */
  public int getAbs(int code) {
    return abs[code];
  }

  /**
   * Getter for EV_KEY codes.
   * 
   * @param code
   *          from <code>EventCodes</code>
   * @return last known key/button value
   */
  public int getKey(int code) {
    return key[code];
  }

  /**
   * Flush cumulative EV_REL values. This should be called after reading all
   * desired values.
   */
  public void flushRel() {
    java.util.Arrays.fill(rel, 0);
  }

  /**
   * Initializes state arrays.
   */
  public InputDeviceState() {
    rel = new int[EventCodes.REL_CNT];
    abs = new int[EventCodes.ABS_CNT];
    key = new int[EventCodes.KEY_CNT];
  }

  /**
   * Updates the internal state from an incoming <code>InputEvent</code>.
   */
  public void update(InputEvent event) {
    if (event.getType() == EventTypes.EV_ABS) {
      abs[event.getCode()] = event.getValue();
    } else if (event.getType() == EventTypes.EV_KEY) {
      key[event.getCode()] = event.getValue();
    }
  }
}
