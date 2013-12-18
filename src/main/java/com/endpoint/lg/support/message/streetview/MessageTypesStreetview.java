package com.endpoint.lg.support.message.streetview;

/**
 * Message types for messages regarding Street View.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class MessageTypesStreetview {
  /**
   * Message type for a POV change.
   */
  public static final String MESSAGE_TYPE_STREETVIEW_POV = "pov";
  
  /**
   * Message type for a pano change.
   */
  public static final String MESSAGE_TYPE_STREETVIEW_PANO = "pano";
  
  /**
   * Message type for links change.
   */
  public static final String MESSAGE_TYPE_STREETVIEW_LINKS = "links";
  
  /**
   * Message type for remote logging.
   */
  public static final String MESSAGE_TYPE_STREETVIEW_LOG = "log";
  
  /**
   * Message type for a refresh request.
   */
  public static final String MESSAGE_TYPE_STREETVIEW_REFRESH = "refresh";
}
