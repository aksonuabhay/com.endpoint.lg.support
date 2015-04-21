/*
 * Copyright (C) 2014 Google Inc.
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

package com.endpoint.lg.support.web;

import interactivespaces.configuration.Configuration;
import interactivespaces.evaluation.ExpressionEvaluator;
import interactivespaces.util.data.json.JsonMapper;

import java.util.Map;

/**
 * Generates a JavaScript snippet that reproduces the given
 * <code>Configuration</code>.
 * 
 * <p>
 * The configuration will be in the global <code>IS.Configuration</code> object.
 * 
 * <pre>
 * <code>
 * var activityName = IS.Configuration['space.activity.name'];
 * 
 * var port = Number(IS.Configuration['space.activity.webapp.port']);
 * </code>
 * </pre>
 * 
 * @see WebConfigHandler
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class WebConfig {
  /**
   * Name of the Interactive Spaces JavaScript global object.
   */
  public static final String JS_GLOBAL_OBJECT = "IS";

  /**
   * Name of the configuration object under the global object.
   */
  public static final String JS_CONFIGURATION_OBJECT = "Configuration";

  /**
   * Generates JavaScript which reproduces the given <code>Configuration</code>.
   * All configuration values are evaluated as Strings.
   * 
   * @param config
   *          the configuration to convert to a JavaScript object
   */
  public static String generate(Configuration config) {
    Map<String, String> configMap = config.getCollapsedMap();
    ExpressionEvaluator evaluator = config.getExpressionEvaluator();

    for (String key : configMap.keySet()) {
      configMap.put(key, evaluator.evaluateStringExpression(configMap.get(key)));
    }

    String json = new JsonMapper().toString(configMap);

    return String.format("var %1$s = %1$s || {}; %1$s.%2$s = %3$s;", JS_GLOBAL_OBJECT,
        JS_CONFIGURATION_OBJECT, json);
  }
}
