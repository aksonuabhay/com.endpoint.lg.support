/*
 * Copyright (C) 2014 Google Inc.
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

package com.endpoint.lg.support.viewsync;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import java.util.Map;

import com.endpoint.lg.support.domain.Location;
import com.endpoint.lg.support.domain.Orientation;
import com.endpoint.lg.support.message.MessageFields;

/**
 * Support class for the ViewSync messaging
 * 
 * @author Wojciech Ziniewicz <wojtek@endpoint.com>
 * 
 */

public class EarthViewSyncState {
  /**
   * The default planet, if a viewsync datagram does not specify one.
   */
  public static final String DEFAULT_PLANET = "earth";

  /**
   * The orientation: heading, tilt, roll, (range)
   */
  private Orientation orientation;

  /**
   * The location: lattitude, longitude, altitude
   */
  private Location location;

  /**
   * The planet: "sky", "mars", "moon", empty "" is Earth
   */
  private String planet;

  /**
   * The timestart
   */
  private Double timestart;

  /**
   * The timeend
   */
  private Double timeend;

  /**
   * The counter
   */
  private Double counter;

  /**
   * Getter for counter
   * 
   * @return counter
   */
  public Double getCounter() {
    return counter;
  }

  /**
   * Planet getter
   * 
   * @return planet
   */
  public String getPlanet() {
    return planet;
  }

  /**
   * Getter for time start
   * 
   * @return timestart
   */
  public Double getTimeStart() {
    return timestart;
  }

  /**
   * Getter for time end
   * 
   * @return timeend
   */
  public Double getTimeEnd() {
    return timeend;
  }

  /**
   * Getter for Location
   * 
   * @return Location
   * 
   * @see com.endpoint.lg.support.domain.Location
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Getter for Orientation
   * 
   * @return Orientation
   * 
   * @see com.endpoint.lg.support.domain.Orientation
   */
  public Orientation getOrientation() {
    return orientation;
  }

  /**
   * Setter for Location
   * 
   * @see com.endpoint.lg.support.domain.Location
   */
  public void setLocation(Location location) {
    this.location = location;
  }

  /**
   * Setter for Orientation
   * 
   * @see com.endpoint.lg.support.domain.Orientation
   */
  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }

  /**
   * Constructor for EarthViewSyncState
   * 
   * @param viewsyncData - udp packet from ROS
   */
  public EarthViewSyncState(String viewsyncData) {

    // http://code.google.com/p/liquid-galaxy/wiki/GoogleEarth_ViewSync
    String[] viewsyncDataParsed = viewsyncData.split("\\,", 10);

    double counter = Double.parseDouble(viewsyncDataParsed[0]);
    double latitude = Double.parseDouble(viewsyncDataParsed[1]);
    double longitude = Double.parseDouble(viewsyncDataParsed[2]);
    double altitude = Double.parseDouble(viewsyncDataParsed[3]);
    double heading = Double.parseDouble(viewsyncDataParsed[4]);
    double tilt = Double.parseDouble(viewsyncDataParsed[5]);
    double roll = Double.parseDouble(viewsyncDataParsed[6]);
    double timestart = Double.parseDouble(viewsyncDataParsed[7]);
    double timeend = Double.parseDouble(viewsyncDataParsed[8]);
    double range = 0; // we don't have range for Orientation object

    // the planet portion of the comma-separated-values from Earth might be
    // empty - need to account for that
    String planet = viewsyncDataParsed[9].equals("") ? DEFAULT_PLANET : viewsyncDataParsed[9];

    Location location = new Location(latitude, longitude, altitude);
    Orientation orientation = new Orientation(heading, range, tilt, roll);

    this.timestart = timestart;
    this.timeend = timeend;
    this.planet = planet;
    this.counter = counter;
    this.location = location;
    this.orientation = orientation;
    this.planet = planet;
  }

  // TODO: deserialize an EarthViewSyncState that has arrived from Ros
  /**
   * Deserialize json that has arrived from ROS
   */
  public EarthViewSyncState(JsonNavigator json) {
    location =
        new Location(json.getDouble(MessageFields.MESSAGE_FIELD_LOCATION_LATITUDE).doubleValue(),
            json.getDouble(MessageFields.MESSAGE_FIELD_LOCATION_LONGITUDE).doubleValue(), json
                .getDouble(MessageFields.MESSAGE_FIELD_LOCATION_ALTITUDE).doubleValue());
    orientation =
        new Orientation(json.getDouble(MessageFields.MESSAGE_FIELD_ORIENTATION_HEADING)
            .doubleValue(), json.getDouble(MessageFields.MESSAGE_FIELD_ORIENTATION_RANGE)
            .doubleValue(), json.getDouble(MessageFields.MESSAGE_FIELD_ORIENTATION_TILT)
            .doubleValue(), json.getDouble(MessageFields.MESSAGE_FIELD_ORIENTATION_ROLL)
            .doubleValue());
    timestart = json.getDouble(MessageFields.MESSAGE_FIELD_TIMESTART).doubleValue();
    timeend = json.getDouble(MessageFields.MESSAGE_FIELD_TIMEEND).doubleValue();
    planet = json.getString(MessageFields.MESSAGE_FIELD_PLANET);
    counter = json.getDouble(MessageFields.MESSAGE_FIELD_COUNTER).doubleValue();
  }

  /**
   * Serialize a JsonBuilder from EarthViewSyncState instance
   */
  public JsonBuilder getJsonBuilder() {
    // serialize a JsonBuilder from an EarthViewSyncState instance
    JsonBuilder json = new JsonBuilder();

    json.put(MessageFields.MESSAGE_FIELD_ORIENTATION_HEADING, orientation.getHeading());
    json.put(MessageFields.MESSAGE_FIELD_ORIENTATION_RANGE, orientation.getRange());
    json.put(MessageFields.MESSAGE_FIELD_ORIENTATION_TILT, orientation.getTilt());
    json.put(MessageFields.MESSAGE_FIELD_ORIENTATION_ROLL, orientation.getRoll());
    json.put(MessageFields.MESSAGE_FIELD_LOCATION_LATITUDE, location.getLatitude());
    json.put(MessageFields.MESSAGE_FIELD_LOCATION_LONGITUDE, location.getLongitude());
    json.put(MessageFields.MESSAGE_FIELD_LOCATION_ALTITUDE, location.getAltitude());
    json.put(MessageFields.MESSAGE_FIELD_TIMEEND, timeend);
    json.put(MessageFields.MESSAGE_FIELD_TIMESTART, timestart);
    json.put(MessageFields.MESSAGE_FIELD_COUNTER, counter);
    json.put(MessageFields.MESSAGE_FIELD_PLANET, planet);

    return json;
  }

  /**
   * Get a <code>Map</code> of the viewsync state.
   * 
   * @return map representation of the viewsync state
   */
  public Map<String, Object> getMap() {
    return getJsonBuilder().build();
  }
}
