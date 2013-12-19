/*
 * Copyright (C) 2013 Google Inc.
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

import interactivespaces.util.data.json.JsonNavigator;

/**
 * A handler for Ros messages.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public interface RosMessageHandler {
  /**
   * Handle a message which has come in over a ROS route.
   * 
   * @param json
   *          message payload
   */
  public void handleMessage(JsonNavigator json);
}