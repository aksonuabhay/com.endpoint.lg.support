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

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.StandardJsonBuilder;

import java.util.Map;

/**
 * @author Keith M. Hughes
 */
public class MessageWrapper {

  /**
   * Message key giving the type of the message.
   */
  public static final String MESSAGE_FIELD_TYPE = "type";

  /**
   * Message key giving the data of the message.
   */
  public static final String MESSAGE_FIELD_DATA = "data";

  /**
   * Create a new typed message.
   *
   * <p>
   * The builder will be positioned in the data object.
   *
   * @param messageType
   *        the message type
   * @param data
   *        the data for the message
   *
   * @return a typed message
   */
  public static JsonBuilder newTypedMessage(String messageType, Map<String, Object> data) {
    JsonBuilder message = newTypedMessage(messageType);

    message.putAll(data);

    return message;
  }


  /**
   * Create a new typed message.
   *
   * <p>
   * The builder will be positioned in the data object.
   *
   * @param messageType
   *        the message type
   *
   * @return a typed message
   */
  public static JsonBuilder newTypedMessage(String messageType) {
    JsonBuilder message = new StandardJsonBuilder();

    message.put(MESSAGE_FIELD_TYPE, messageType);
    message.newObject(MESSAGE_FIELD_DATA);

    return message;
  }
}
