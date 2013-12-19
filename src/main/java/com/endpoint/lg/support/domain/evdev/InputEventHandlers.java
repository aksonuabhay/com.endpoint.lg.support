package com.endpoint.lg.support.domain.evdev;

import com.endpoint.lg.support.domain.evdev.InputEventHandler;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * A collection of {@link InputEventHandler} instances keyed by their event type
 * and code. Based on WebsocketMessageHandlers by Keith M. Hughes.
 * 
 * <p>
 * An example using anonymous classes would be
 * 
 * <pre>
 * <code>
 * handlers.registerHandler(InputEvent.Types.EV_KEY, InputEvent.Codes.BTN_0, new InputEventHandler() {
 *   public void handleEvent(InputEvent event)  {
 *     // Do something with event
 *   }
 * });
 * </code>
 * </pre>
 * 
 * <p>
 * An example of registering all codes for a type (EV_ABS) would be
 * 
 * <pre>
 * <code>
 * handlers.registerHandler(InputEvent.Types.EV_ABS, InputEventHandlers.ALL_CODES, new InputEventHandler() {
 *   public void handleEvent(InputEvent event)  {
 *     // Do something with event
 *   }
 * });
 * </code>
 * </pre>
 * 
 * <p>
 * Handlers registered with the special ALL_CODES code will be called before a
 * more specific code handler.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class InputEventHandlers {
  /**
   * A special code value indicating that this handler should be used for all
   * events of this type.
   */
  public static final int ALL_CODES = -1;

  /**
   * The data structure organizing handlers by type and code.
   */
  private final Map<Integer, Map<Integer, InputEventHandler>> handlers = Maps.newHashMap();

  /**
   * Registers a handler.
   * 
   * @param type
   *          the <code>InputEvent</code> type to be handled
   * @param code
   *          the <code>InputEvent</code> code to be handled
   * @param handler
   *          the handler
   */
  public void registerHandler(int type, int code, InputEventHandler handler) {
    Map<Integer, InputEventHandler> codeHandlers = handlers.get(type);
    if (codeHandlers == null) {
      codeHandlers = Maps.newHashMap();
      handlers.put(type, codeHandlers);
    }

    codeHandlers.put(code, handler);
  }

  /**
   * Helper for finding a handler for the given <code>InputEvent</code> type and
   * code.
   */
  private InputEventHandler fetchHandler(int type, int code) {
    InputEventHandler handler = null;

    Map<Integer, InputEventHandler> codeHandlers = handlers.get(type);
    if (codeHandlers != null) {
      handler = codeHandlers.get(code);
    }

    return handler;
  }

  /**
   * Handles an <code>InputEvent</code>.
   * 
   * @param event
   *          the event which came in
   */
  public void handleEvent(InputEvent event) {
    InputEventHandler handler;

    // handle ALL_CODES handlers first
    handler = fetchHandler(event.getType(), ALL_CODES);
    if (handler != null)
      handler.handleEvent(event);

    handler = fetchHandler(event.getType(), event.getCode());
    if (handler != null)
      handler.handleEvent(event);
  }
}
