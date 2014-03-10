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

/**
 * Event types as defined in input.h.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class InputEventTypes {
  // to convert from input.h:
  // #define ([A-Z0-9_]*) *([^\/\n ]*)
  // public static final int \1 = \2;
  // remove all #ifdef blocks

  public static final int EV_SYN = 0x00;
  public static final int EV_KEY = 0x01;
  public static final int EV_REL = 0x02;
  public static final int EV_ABS = 0x03;
  public static final int EV_MSC = 0x04;
  public static final int EV_SW = 0x05;
  public static final int EV_LED = 0x11;
  public static final int EV_SND = 0x12;
  public static final int EV_REP = 0x14;
  public static final int EV_FF = 0x15;
  public static final int EV_PWR = 0x16;
  public static final int EV_FF_STATUS = 0x17;
  public static final int EV_MAX = 0x1f;
  public static final int EV_CNT = (EV_MAX + 1);
}
