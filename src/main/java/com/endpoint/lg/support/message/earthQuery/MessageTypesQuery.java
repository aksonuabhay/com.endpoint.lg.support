/*
 * Copyright (C) 2013 Google Inc.
 * Copyright (C) 2015 End Point Corporation
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

package com.endpoint.lg.support.message.earthQuery;

/**
 * Message types for messages going to Google Earth.
 *
 * @author Keith M. Hughes
 */
public class MessageTypesQuery {

  /**
   * The query message for taking a tour.
   */
  public static final String MESSAGE_TYPE_QUERYFILE_TOUR = "tour";

  /**
   * The query message for playing a tour. {@code true} to start playing,
   * {@code false} to stop playing.
   */
  public static final String MESSAGE_FIELD_QUERYFILE_TOUR_PLAY = "play";

  /**
   * The query message for specifying the tour name of a tour.
   */
  public static final String MESSAGE_FIELD_QUERYFILE_TOUR_TOURNAME = "tourName";

  /**
   * The query message for searching.
   */
  public static final String MESSAGE_TYPE_QUERYFILE_SEARCH = "search";

  /**
   * The query message for specifying the query for a search.
   */
  public static final String MESSAGE_FIELD_QUERYFILE_SEARCH_QUERY = "query";

  /**
   * The query message for specifying the label for a search.
   */
  public static final String MESSAGE_FIELD_QUERYFILE_SEARCH_LABEL = "label";

  /**
   * The query message for flyto.
   */
  public static final String MESSAGE_TYPE_QUERYFILE_FLYTO = "flyto";

  /**
   * The query field for the flyto type field.
   */
  public static final String MESSAGE_FIELD_QUERYFILE_FLYTO_TYPE = "type";

  /**
   * The value for the flyto type parameter for getting a camera flyto.
   */
  public static final String MESSAGE_FIELD_VALUE_FLYTO_TYPE_CAMERA = "camera";

  /**
   * The value for the flyto type parameter for getting a lookat flyto.
   */
  public static final String MESSAGE_FIELD_VALUE_FLYTO_TYPE_LOOKAT = "lookat";

  /**
   * The query message for planet.
   */
  public static final String MESSAGE_TYPE_QUERYFILE_PLANET = "planet";

  /**
   * The query field for the planet destination field.
   */
  public static final String MESSAGE_FIELD_QUERYFILE_PLANET_DESTINATION = "destination";
}
