/**
 *
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
 * <code> { 'type': type, 'data': data} } </code>
 *
 * <p>
 * The {@code type} field will give the type of the message, e.g. viewsync, and
 * {@code data} will give the data of the message.
 *
 * <p>
 * An example using anonymous classes would be
 *
 * <pre><code>
 * handlers.registerHandler("viewSync", new WebsocketMessageHandler() {
 *   @Override
 *   public  void handleMessage(String connectionId, JsonNavigator data)  {
 *     // Do something with message
 *   }
 * });
 * </code></pre>
 *
 * @author Keith M. Hughes
 */
public class WebsocketMessageHandlers {

  /**
   * Message key giving the type of the message.
   */
  public static final String MESSAGE_FIELD_TYPE = "type";

  /**
   * Message key giving the data of the message.
   */
  public static final String MESSAGE_FIELD_DATA = "data";

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

    String messageType = m.getString(MESSAGE_FIELD_TYPE);
    if (messageType != null) {
      WebsocketMessageHandler handler = handlers.get(messageType);
      if (handler != null) {
        try {
          handler.handleMessage(connectionId, m.down(MESSAGE_FIELD_DATA));
        } catch (Exception e) {
          log.error(String.format("Error for message with type %s from connection %s", messageType, connectionId), e);
        }
      } else {
        log.warn(String.format("Message with unknown type %s came from connection %s", messageType, connectionId));
      }
    } else {
      log.warn(String.format("Message without a type came from connection %s", connectionId));
    }
  }
}
