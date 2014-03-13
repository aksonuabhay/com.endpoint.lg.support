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

package com.endpoint.lg.support.window;

/**
 * Model for absolute or relative window geometry.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class WindowGeometry {
  private int width;
  private int height;
  private int x;
  private int y;

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  /**
   * Constructs WindowGeometry with the given dimensions.
   * 
   * @param width
   *          width of the window in pixels
   * @param height
   *          height of the window in pixels
   * @param x
   *          x position of the window in pixels
   * @param y
   *          y position of the window in pixels
   */
  public WindowGeometry(int width, int height, int x, int y) {
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
  }

  /**
   * Modifies the geometry by the given relative dimensions.
   * 
   * @param relativeWidth
   *          number of pixels to add to width
   * @param relativeHeight
   *          number of pixels to add to height
   * @param relativeX
   *          number of pixels to add to x position
   * @param relativeY
   *          number of pixels to add to y position
   */
  public void offsetBy(int relativeWidth, int relativeHeight, int relativeX, int relativeY) {
    width += relativeWidth;
    height += relativeHeight;
    x += relativeX;
    y += relativeY;
  }

  /**
   * Modifies the geometry by the given relative geometry.
   * 
   * @param relativeGeometry
   *          geometry to add to this geometry
   */
  public void offsetBy(WindowGeometry relativeGeometry) {
    width += relativeGeometry.getWidth();
    height += relativeGeometry.getHeight();
    x += relativeGeometry.getX();
    y += relativeGeometry.getY();
  }
}
