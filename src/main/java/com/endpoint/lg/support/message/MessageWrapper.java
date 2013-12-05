/**
 *
 */
package com.endpoint.lg.support.message;

import interactivespaces.util.data.json.JsonBuilder;

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
    JsonBuilder message = new JsonBuilder();

    message.put(MESSAGE_FIELD_TYPE, messageType);

    return message;
  }
}
