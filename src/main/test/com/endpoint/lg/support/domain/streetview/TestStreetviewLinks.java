package com.endpoint.lg.support.domain.streetview;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the StreetviewLinks interface.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestStreetviewLinks {
  private static StreetviewLinks emptyLinks;
  private static StreetviewLinks testLinks;
  
  @BeforeClass
  public static void testSetup() {
    JsonBuilder testBuilder = new JsonBuilder();
    JsonBuilder testArray = testBuilder.newArray(StreetviewLinks.FIELD_LINKS);

    testArray.put(new StreetviewLink("at_90", 90).getMap());
    testArray.put(new StreetviewLink("at_270", 270).getMap());
    testArray.put(new StreetviewLink("at_300", 300).getMap());

    testLinks = new StreetviewLinks(new JsonNavigator(testBuilder.build()));
    
    emptyLinks = new StreetviewLinks();
  }
  
  @Test
  public void test() {
    assertFalse("Empty object has no links", emptyLinks.hasLinks());
    
    assertTrue("Test object has links", testLinks.hasLinks());
    
    assertEquals(3, testLinks.getLinks().length);

    assertEquals(90, testLinks.getNearestLink(90).getHeading(), 0);

    assertEquals(90, testLinks.getFurthestLink(270).getHeading(), 0);
    assertEquals(270, testLinks.getFurthestLink(90).getHeading(), 0);

    assertEquals("First wins if equidistant", 90, testLinks.getNearestLink(180).getHeading(), 0);

    assertEquals(300, testLinks.getNearestLink(-60).getHeading(), 0);
    assertEquals(270, testLinks.getNearestLink(-90).getHeading(), 0);
    assertEquals(90, testLinks.getNearestLink(-270).getHeading(), 0);
    assertEquals(270, testLinks.getNearestLink(-450).getHeading(), 0);
    assertEquals(90, testLinks.getNearestLink(-630).getHeading(), 0);

    assertEquals(90, testLinks.getNearestLink(450).getHeading(), 0);
    assertEquals(270, testLinks.getNearestLink(630).getHeading(), 0);
    assertEquals(300, testLinks.getNearestLink(660).getHeading(), 0);
  }
}
