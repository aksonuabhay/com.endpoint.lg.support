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
  private Integer width;
  private Integer height;
  private Integer x;
  private Integer y;

  public Integer getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public Integer getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public Integer getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  /**
   * Translates by the given relative dimensions.
   */
  public void offsetBy(int relativeWidth, int relativeHeight, int relativeX, int relativeY) {
    this.width += relativeWidth;
    this.height += relativeHeight;
    this.x += relativeX;
    this.y += relativeY;
  }

  /**
   * Translates by the given relative geometry.
   */
  public void offsetBy(WindowGeometry relativeGeometry) {
    this.width += relativeGeometry.getWidth();
    this.height += relativeGeometry.getHeight();
    this.x += relativeGeometry.getX();
    this.y += relativeGeometry.getY();
  }

  /**
   * Constructs WindowGeometry with the given dimensions.
   */
  public WindowGeometry(int width, int height, int x, int y) {
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
  }
}
