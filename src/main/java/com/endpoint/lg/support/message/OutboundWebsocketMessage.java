package com.endpoint.lg.support.message;

import interactivespaces.util.data.json.JsonBuilder;

/**
 * Represents an outgoing web socket message. This is intended for use with
 * <code>EventBus</code> to simplify interactions between web activities and
 * their subclasses.
 * 
 * <p>
 * The connectionId is not stored, it is assumed that the message will be sent
 * to all web socket connections.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class OutboundWebsocketMessage {
  private JsonBuilder jsonBuilder;

  /**
   * @return the message body
   */
  public JsonBuilder getJsonBuilder() {
    return jsonBuilder;
  }

  /**
   * Creates an OutboundWebsocketMessage with the given channel and message.
   * 
   * @param connectionId
   *          connection on which to publish this message
   * @param message
   *          the message body
   */
  public OutboundWebsocketMessage(JsonBuilder json) {
    this.jsonBuilder = json;
  }
}
