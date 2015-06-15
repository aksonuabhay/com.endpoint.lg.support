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

package com.endpoint.lg.support.message;

import interactivespaces.util.data.json.JsonNavigator;
import interactivespaces.util.data.json.StandardJsonNavigator; // Added by Abhay

import org.apache.commons.logging.Log;
import com.google.common.collect.Maps;
import java.util.Map;

/**
 * A collection of {@link RosMessageHandler} instances keyed by their message
 * type. Based on {@link WebsocketMessageHandlers} by Keith M. Hughes.
 * 
 * <p>
 * An example using anonymous classes would be
 * 
 * <pre>
 * <code>
 * handlers.registerHandler("viewSync", new RosMessageHandler() {
 *   {@literal @}Override
 *   public  void handleMessage(JsonNavigator json)  {
 *     // Do something with message
 *   }
 * });
 * </code>
 * </pre>
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class RosMessageHandlers {
  /**
   * The handlers for each channel.
   */
  private final Map<String, RosMessageHandler> handlers = Maps.newHashMap();

  /**
   * The logger for this handler.
   */
  private final Log log;

  /**
   * Construct a handlers object.
   * 
   * @param log
   *          the log to use
   */
  public RosMessageHandlers(Log log) {
    this.log = log;
  }

  protected Log getLog() {
    return log;
  }

  /**
   * Register a handler.
   * 
   * @param channel
   *          the channel to be handled
   * @param handler
   *          the handler
   */
  public void registerHandler(String channel, RosMessageHandler handler) {
    handlers.put(channel, handler);
  }

  /**
   * Handle a message from a given channel.
   * 
   * @param channel
   *          the channel the message came in on
   * @param message
   *          the message which came in
   */
  public void handleMessage(String channel, Map<String, Object> message) {
    JsonNavigator m = new StandardJsonNavigator(message); // Changed by Abhay

    RosMessageHandler handler = handlers.get(channel);
    if (handler != null) {
      try {
        handler.handleMessage(m);
      } catch (Exception e) {
        log.error(String.format("Error for message on channel %s", channel), e);
      }
    } else {
      log.warn(String.format("Message from unknown channel %s", channel));
    }
  }
}
