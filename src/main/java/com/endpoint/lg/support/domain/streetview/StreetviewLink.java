package com.endpoint.lg.support.domain.streetview;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import java.util.Map;

/**
 * A reference to a neighboring Street View panorama, based on Maps API <code>google.maps.StreetViewLink</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class StreetviewLink {
  /**
   * Field for panoid.
   */
  public static final String FIELD_PANO = "pano";
  
  /**
   * Field for heading relative to the source pano.
   */
  public static final String FIELD_HEADING = "heading";
  
  private String pano;
  private double heading;
  
  /**
   * Retrieves the panoid.
   * @return panoid
   */
  public String getPano() {
    return pano;
  }
  
  /**
   * Retrieves the heading.
   * @return heading in degrees
   */
  public double getHeading() {
    return heading;
  }
  
  /**
   * Constructs a link with the given panoid and heading.
   * @param pano panoid string
   * @param heading direction from the source pano, in degrees
   */
  public StreetviewLink(String pano, double heading) {
    this.pano = pano;
    this.heading = heading;
  }
  
  /**
   * Constructs a link from a <code>JsonNavigator</code>.
   * @param json contains pano and heading fields
   */
  public StreetviewLink(JsonNavigator json) {
    pano = json.getString(FIELD_PANO);
    heading = json.getDouble(FIELD_HEADING);
  }
  
  /**
   * Retrieves a <code>JsonBuilder</code> representation of the link.
   * @return json object with link data
   */
  public JsonBuilder getJsonBuilder() {
    JsonBuilder json = new JsonBuilder();
    
    json.put(FIELD_PANO, pano);
    json.put(FIELD_HEADING, heading);
    
    return json;
  }
  
  /**
   * Retrieves a <code>Map</code> representation of the link.
   * @return map object with link data
   */
  public Map<String, Object> getMap() {
    return getJsonBuilder().build();
  }
}
