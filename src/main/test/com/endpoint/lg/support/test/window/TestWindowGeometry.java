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

package com.endpoint.lg.support.test.window;

import static org.junit.Assert.*;

import com.endpoint.lg.support.window.WindowGeometry;

import org.junit.Test;

/**
 * Test a <code>WindowGeometry</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestWindowGeometry {
  public static final int TEST_WIDTH = 640;
  public static final int TEST_HEIGHT = 480;
  public static final int TEST_X = 200;
  public static final int TEST_Y = 350;

  public static final int RELATIVE_WIDTH = -160;
  public static final int RELATIVE_HEIGHT = 160;
  public static final int RELATIVE_X = 80;
  public static final int RELATIVE_Y = -80;

  /**
   * Test the constructor, setters and getters.
   */
  @Test
  public void testModel() {
    WindowGeometry geometry = new WindowGeometry(TEST_WIDTH, TEST_HEIGHT, TEST_X, TEST_Y);

    assertEquals(TEST_WIDTH, geometry.getWidth());
    assertEquals(TEST_HEIGHT, geometry.getHeight());
    assertEquals(TEST_X, geometry.getX());
    assertEquals(TEST_Y, geometry.getY());

    geometry.setWidth(RELATIVE_WIDTH);
    assertEquals(RELATIVE_WIDTH, geometry.getWidth());

    geometry.setHeight(RELATIVE_HEIGHT);
    assertEquals(RELATIVE_HEIGHT, geometry.getHeight());

    geometry.setX(RELATIVE_X);
    assertEquals(RELATIVE_X, geometry.getX());

    geometry.setY(RELATIVE_Y);
    assertEquals(RELATIVE_Y, geometry.getY());
  }

  /**
   * Test mutating geometry by values.
   */
  @Test
  public void testLiteralOffset() {
    WindowGeometry geometry = new WindowGeometry(TEST_WIDTH, TEST_HEIGHT, TEST_X, TEST_Y);

    geometry.offsetBy(RELATIVE_WIDTH, RELATIVE_HEIGHT, RELATIVE_X, RELATIVE_Y);

    assertEquals(TEST_WIDTH + RELATIVE_WIDTH, geometry.getWidth());
    assertEquals(TEST_HEIGHT + RELATIVE_HEIGHT, geometry.getHeight());
    assertEquals(TEST_X + RELATIVE_X, geometry.getX());
    assertEquals(TEST_Y + RELATIVE_Y, geometry.getY());
  }

  /**
   * Test mutating geometry by another geometry.
   */
  @Test
  public void testGeometryOffset() {
    WindowGeometry geometry = new WindowGeometry(TEST_WIDTH, TEST_HEIGHT, TEST_X, TEST_Y);

    WindowGeometry relativeGeometry =
        new WindowGeometry(RELATIVE_WIDTH, RELATIVE_HEIGHT, RELATIVE_X, RELATIVE_Y);

    geometry.offsetBy(relativeGeometry);

    assertEquals(TEST_WIDTH + RELATIVE_WIDTH, geometry.getWidth());
    assertEquals(TEST_HEIGHT + RELATIVE_HEIGHT, geometry.getHeight());
    assertEquals(TEST_X + RELATIVE_X, geometry.getX());
    assertEquals(TEST_Y + RELATIVE_Y, geometry.getY());
  }
}
