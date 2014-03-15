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

package com.endpoint.lg.support.message;

import interactivespaces.util.data.json.JsonNavigator;

import com.google.common.collect.Maps;

import org.apache.commons.logging.Log;

import java.util.Map;

/**
 * A collection of {@link JsonMessageHandler} instances keyed by their message
 * type.
 * 
 * <p>
 * Messages need to be of the form
 * 
 * <p>
 * <code> { 'type': type, 'data': data } </code>
 * 
 * <p>
 * The {@code type} field will give the type of the message, e.g. viewsync, and
 * {@code data} will give the data of the message.
 * 
 * <p>
 * An example using anonymous classes would be
 * 
 * <pre>
 * <code>
 * handlers.registerHandler("viewSync", new WebsocketMessageHandler() {
 *   {@literal @}Override
 *   public  void handleMessage(String connectionId, JsonNavigator data)  {
 *     // Do something with message
 *   }
 * });
 * </code>
 * </pre>
 * 
 * @author Keith M. Hughes
 */
public class WebsocketMessageHandlers {

  /**
   * The handlers for each message type.
   */
  private final Map<String, WebsocketMessageHandler> handlers = Maps.newHashMap();

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
  public WebsocketMessageHandlers(Log log) {
    this.log = log;
  }

  protected Log getLog() {
    return log;
  }

  /**
   * Register a handler.
   * 
   * @param messageType
   *          the type of message to be handled
   * @param handler
   *          the handler
   */
  public void registerHandler(String messageType, WebsocketMessageHandler handler) {
    handlers.put(messageType, handler);
  }

  /**
   * Handle a message from a given connection.
   * 
   * @param connectionId
   *          ID of the connection
   * @param message
   *          the message which came in
   */
  public void handleMessage(String connectionId, Object message) {
    JsonNavigator m = new JsonNavigator(message);

    String messageType = m.getString(MessageWrapper.MESSAGE_FIELD_TYPE);
    if (messageType != null) {
      WebsocketMessageHandler handler = handlers.get(messageType);
      if (handler != null) {
        try {
          handler.handleMessage(connectionId, m.down(MessageWrapper.MESSAGE_FIELD_DATA));
        } catch (Exception e) {
          log.error(String.format("Error for message with type %s from connection %s", messageType,
              connectionId), e);
        }
      } else {
        log.warn(String.format("Message with unknown type %s came from connection %s", messageType,
            connectionId));
      }
    } else {
      log.warn(String.format("Message without a type came from connection %s", connectionId));
    }
  }
}
