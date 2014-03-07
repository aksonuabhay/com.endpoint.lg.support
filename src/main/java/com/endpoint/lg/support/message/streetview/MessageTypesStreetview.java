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
