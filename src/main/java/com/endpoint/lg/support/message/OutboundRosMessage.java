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

package com.endpoint.lg.support.message;

import interactivespaces.util.data.json.JsonBuilder;

/**
 * Represents an outgoing Ros message. This is intended for use with
 * <code>EventBus</code> to simplify interactions between Ros activities and
 * their subclasses.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class OutboundRosMessage {
  private String channel;
  private JsonBuilder jsonBuilder;

  /**
   * @return the channel on which to publish this message
   */
  public String getChannel() {
    return channel;
  }

  /**
   * @return the message body
   */
  public JsonBuilder getJsonBuilder() {
    return jsonBuilder;
  }

  /**
   * Creates an OutboundRosMessage with the given channel and message.
   * 
   * @param channel
   *          route on which to publish this message
   * @param message
   *          the message body
   */
  public OutboundRosMessage(String channel, JsonBuilder json) {
    this.channel = channel;
    this.jsonBuilder = json;
  }
}
