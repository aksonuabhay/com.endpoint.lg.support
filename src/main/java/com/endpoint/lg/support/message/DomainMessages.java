/*
 * Copyright (C) 2013-2014 Google Inc.
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

package com.endpoint.lg.support.message;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;
import interactivespaces.util.geometry.Vector3;

import com.endpoint.lg.support.domain.Location;
import com.endpoint.lg.support.domain.Orientation;

/**
 * Create messages for various domain objects.
 * 
 * @author Keith M. Hughes
 */
public class DomainMessages {

  /**
   * Get a 3d vector out of the data.
   * 
   * @param data
   *          the incoming data
   * @param field
   *          the message field for the vector
   * @return a vector
   */
  public static Vector3 deserializeVector3(JsonNavigator data, String field) {
    data.down(field);

    Double v0 = data.getDouble(0);
    Double v1 = data.getDouble(1);
    Double v2 = data.getDouble(2);

    data.up();

    return new Vector3(v0, v1, v2);
  }

  /**
   * Write a 3d vector into data.
   * 
   * @param vector
   *          the vector
   * @param field
   *          message field
   * @param data
   *          the data to write to
   */
  public static void serializeVector3(Vector3 vector, String field, JsonBuilder data) {
    data.newArray(field);

    data.put(vector.getV0());
    data.put(vector.getV1());
    data.put(vector.getV2());

    data.up();
  }

  /**
   * Get an orientation out of the data.
   * 
   * @param data
   *          the incoming data
   * 
   * @return an orientation for the data
   */
  public static Orientation deserializeOrientation(JsonNavigator data) {
    data.down(MessageFields.MESSAGE_FIELD_ORIENTATION);

    Double heading = data.getDouble(MessageFields.MESSAGE_FIELD_ORIENTATION_HEADING);
    Double range = data.getDouble(MessageFields.MESSAGE_FIELD_ORIENTATION_RANGE);
    Double tilt = data.getDouble(MessageFields.MESSAGE_FIELD_ORIENTATION_TILT);
    Double roll = data.getDouble(MessageFields.MESSAGE_FIELD_ORIENTATION_ROLL);

    data.up();

    return new Orientation(heading, range, tilt, roll);
  }

  /**
   * Write an orientation object into the current data.
   * 
   * @param orientation
   *          the orientation
   * @param data
   *          the data to write to
   */
  public static void serializeOrientation(Orientation orientation, JsonBuilder data) {
    data.newObject(MessageFields.MESSAGE_FIELD_ORIENTATION);

    data.put(MessageFields.MESSAGE_FIELD_ORIENTATION_HEADING, orientation.getHeading());
    data.put(MessageFields.MESSAGE_FIELD_ORIENTATION_RANGE, orientation.getRange());
    data.put(MessageFields.MESSAGE_FIELD_ORIENTATION_TILT, orientation.getTilt());
    data.put(MessageFields.MESSAGE_FIELD_ORIENTATION_ROLL, orientation.getRoll());

    data.up();
  }

  /**
   * Get an location out of the data.
   * 
   * @param data
   *          the incoming data
   * 
   * @return a location for the data
   */
  public static Location deserializeLocation(JsonNavigator data) {
    data.down(MessageFields.MESSAGE_FIELD_LOCATION);

    Double altitude = data.getDouble(MessageFields.MESSAGE_FIELD_LOCATION_ALTITUDE);
    Double latitude = data.getDouble(MessageFields.MESSAGE_FIELD_LOCATION_LATITUDE);
    Double longitude = data.getDouble(MessageFields.MESSAGE_FIELD_LOCATION_LONGITUDE);

    data.up();

    return new Location(latitude, longitude, altitude);
  }

  /**
   * Serialize an location into the data.
   * 
   * @param data
   *          the incoming data
   * 
   * @return a location for the data
   */
  public static void serializeLocation(Location location, JsonBuilder data) {
    data.newObject(MessageFields.MESSAGE_FIELD_LOCATION);

    data.put(MessageFields.MESSAGE_FIELD_LOCATION_ALTITUDE, location.getAltitude());
    data.put(MessageFields.MESSAGE_FIELD_LOCATION_LATITUDE, location.getLatitude());
    data.put(MessageFields.MESSAGE_FIELD_LOCATION_LONGITUDE, location.getLongitude());

    data.up();
  }
}
