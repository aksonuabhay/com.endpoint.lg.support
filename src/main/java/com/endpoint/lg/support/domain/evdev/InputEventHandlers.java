/*
 * Copyright (C) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

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
 * {@code
 * handlers.registerHandler(InputEvent.Types.EV_KEY, InputEvent.Codes.BTN_0, new InputEventHandler() {
 *   public void handleEvent(InputEvent event)  {
 *     // Do something with event
 *   }
 * });
 * }
 * </pre>
 * 
 * <p>
 * An example of registering all codes for a type (EV_ABS) would be
 * 
 * <pre>
 * {@code
 * handlers.registerHandler(InputEvent.Types.EV_ABS, InputEventHandlers.ALL_CODES, new InputEventHandler() {
 *   public void handleEvent(InputEvent event)  {
 *     // Do something with event
 *   }
 * });
 * }
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
