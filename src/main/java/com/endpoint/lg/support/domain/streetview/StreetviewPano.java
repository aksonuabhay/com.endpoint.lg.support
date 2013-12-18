package com.endpoint.lg.support.domain.streetview;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import java.util.Map;

/**
 * A Street View panorama, encapsulating several Maps API objects.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class StreetviewPano {
  /**
   * StreetviewPano field for panoid.
   */
  public static final String FIELD_PANOID = "panoid";

  private String panoid;

  /**
   * Retrieves the panoid value.
   * 
   * @return panoid for this pano
   */
  public String getPanoid() {
    return panoid;
  }

  /**
   * Constructs a pano with the given panoid and no links.
   * 
   * @param panoid
   */
  public StreetviewPano(String panoid) {
    this.panoid = panoid;
  }

  /**
   * Constructs a pano from a <code>JsonNavigator</code>.
   * 
   * @param json
   *          with pano data
   */
  public StreetviewPano(JsonNavigator json) {
    panoid = json.getString(FIELD_PANOID);
  }

  /**
   * Retrieves the pano as a <code>JsonBuilder</code>.
   * 
   * @return json representation of the pano
   */
  public JsonBuilder getJsonBuilder() {
    JsonBuilder json = new JsonBuilder();

    json.put(FIELD_PANOID, panoid);

    return json;
  }

  /**
   * Retrieves the pano as a <code>Map</code>.
   * 
   * @return map representation of the pano
   */
  public Map<String, Object> getMap() {
    return getJsonBuilder().build();
  }

  /**
   * Compare this pano to another.
   * 
   * @param rpano
   *          to be compared
   * @return true if the panos are equal
   */
  public boolean equals(StreetviewPano rpano) {
    return panoid == rpano.getPanoid();
  }
}
