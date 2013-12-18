package com.endpoint.lg.support.domain.streetview;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import java.util.Map;

/**
 * A collection of links to neighboring Street View panoramas.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class StreetviewLinks {

  /**
   * StreetviewLinks field for links to neighboring panoids.
   */
  public static final String FIELD_LINKS = "links";
  
  private StreetviewLink[] links;

  public StreetviewLink[] getLinks() {
    return links;
  }

  /**
   * Constructs an empty link collection.
   */
  public StreetviewLinks() {
    links = new StreetviewLink[0];
  }

  /**
   * Constructs a link collection from given <code>JsonNavigator</code>.
   * 
   * @param json
   *          array of links
   */
  public StreetviewLinks(JsonNavigator json) {
    int numLinks = json.getSize();
    links = new StreetviewLink[numLinks];

    for (int i = 0; i < numLinks; i++) {
      links[i] = new StreetviewLink(json.down(i));
    }
  }
}
