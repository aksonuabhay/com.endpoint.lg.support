package com.endpoint.lg.support.message;

import interactivespaces.util.data.json.JsonNavigator;

/**
 * A handler for messages.
 *
 * @author Keith M. Hughes
 */
public interface WebsocketMessageHandler {

  /**
   * Handle a message which has come over the websocket.
   *
   * <p>
   * The navigator will be placed in the data object.
   *
   * @param connectionId
   *        connection ID for the websocket which sent the message
   * @param data
   *        the data for the message
   */
  public void handleMessage(String connectionId, JsonNavigator data);
}
