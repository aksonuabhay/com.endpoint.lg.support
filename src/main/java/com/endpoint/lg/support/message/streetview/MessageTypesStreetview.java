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
   * Message type for a Ros POV sync from the master.
   */
  public static final String MESSAGE_TYPE_STREETVIEW_SYNC_POV = "sync_pov";
  
  /**
   * Message type for a Ros pano sync from the master.
   */
  public static final String MESSAGE_TYPE_STREETVIEW_SYNC_PANO = "sync_pano";
  
  /**
   * Message type for a refresh request.
   */
  public static final String MESSAGE_TYPE_STREETVIEW_REFRESH = "refresh";
}
