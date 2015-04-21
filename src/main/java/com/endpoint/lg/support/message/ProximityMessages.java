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

package com.endpoint.lg.support.message;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import com.endpoint.lg.support.proximity.ProximityEvent;

/**
 * Methods for serialization of proximity sensor events.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class ProximityMessages {
  public static final String FIELD_PROXIMITY = "proximity";
  public static final String FIELD_DISTANCE = "distance";
  public static final String FIELD_PRESENCE = "presence";
  
  /**
   * Deserializes a proximity sensor frame from a message.
   * 
   * @param data a message containing a proximity sensor frame
   * @return the deserialized frame
   */
  public static ProximityEvent deserializeProximityEvent(JsonNavigator data) {
    data.down(FIELD_PROXIMITY);
    
    int distance = data.getInteger(FIELD_DISTANCE);
    boolean presence = data.getBoolean(FIELD_PRESENCE);
    ProximityEvent proxEvent = new ProximityEvent(distance, presence);
    
    data.up();
    
    return proxEvent;
  }
  
  /**
   * Serializes a proximity sensor frame to a message.
   * 
   * @param proxEvent the proximity sensor frame
   * @param data message to serialize to
   */
  public static void serializeProximityEvent(ProximityEvent proxEvent, JsonBuilder data) {
    data.newObject(FIELD_PROXIMITY);
    
    data.put(FIELD_DISTANCE, proxEvent.getDistance());
    data.put(FIELD_PRESENCE, proxEvent.getPresence());
    
    data.up();
  }
}
