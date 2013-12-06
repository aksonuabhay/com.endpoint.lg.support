/*
 * Copyright (C) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.endpoint.lg.support.message.querytxt;

/**
 * Message types for messages going to querytxt.
 *
 * @author Keith M. Hughes
 */
public class MessageTypesQueryTxt {

  /**
   * The querytxt message for taking a tour.
   */
  public static final String MESSAGE_TYPE_QUERYTXT_TOUR = "tour";

  /**
   * The querytxt message for playing a tour. {@code true} to start playing,
   * {@code false} to stop playing.
   */
  public static final String MESSAGE_FIELD_QUERYTXT_TOUR_PLAY = "play";

  /**
   * The querytxt message for specifying the tour name of a tour.
   */
  public static final String MESSAGE_FIELD_QUERYTXT_TOUR_TOURNAME = "tourName";

  /**
   * The querytxt message for searching.
   */
  public static final String MESSAGE_TYPE_QUERYTXT_SEARCH = "search";

  /**
   * The querytxt message for specifying the query for a search.
   */
  public static final String MESSAGE_FIELD_QUERYTXT_SEARCH_QUERY = "query";

  /**
   * The querytxt message for specifying the label for a search.
   */
  public static final String MESSAGE_FIELD_QUERYTXT_SEARCH_LABEL = "label";

  /**
   * The querytxt message for flyto.
   */
  public static final String MESSAGE_TYPE_QUERYTXT_FLYTO = "flyto";

  /**
   * The querytxt field for the flyto type field.
   */
  public static final String MESSAGE_FIELD_QUERYTXT_FLYTO_TYPE = "type";

  /**
   * The value for the flyto type parameter for getting a camera flyto.
   */
  public static final String MESSAGE_FIELD_VALUE_FLYTO_TYPE_CAMERA = "camera";

  /**
   * The value for the flyto type parameter for getting a lookat flyto.
   */
  public static final String MESSAGE_FIELD_VALUE_FLYTO_TYPE_LOOKAT = "lookat";

  /**
   * The querytxt message for planet.
   */
  public static final String MESSAGE_TYPE_QUERYTXT_PLANET = "planet";

  /**
   * The querytxt field for the planet destination field.
   */
  public static final String MESSAGE_FIELD_QUERYTXT_PLANET_DESTINATION = "destination";
}
