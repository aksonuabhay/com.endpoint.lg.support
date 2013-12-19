package com.endpoint.lg.support.domain.evdev;

/**
 * A handler for <code>InputEvent</code>s.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public interface InputEventHandler {
  /**
   * Handle an <code>InputEvent</code>
   * 
   * @param event
   *          event payload
   */
  public void handleEvent(InputEvent event);
}
