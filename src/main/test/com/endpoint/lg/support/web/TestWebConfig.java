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

import static org.junit.Assert.*;

import interactivespaces.configuration.Configuration;
import interactivespaces.configuration.SimpleConfiguration;
import interactivespaces.evaluation.ExpressionEvaluator;
import interactivespaces.evaluation.SimpleEvaluationEnvironment;
import interactivespaces.evaluation.SimpleExpressionEvaluator;

import com.endpoint.lg.support.web.WebConfig;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.ScriptableObject;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the <code>WebConfig</code> JavaScript code generation.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestWebConfig {
  private static final String KEY_STRING = "string.so.normal";
  private static final String KEY_QUOTED_STRING = "string.quoted";
  private static final String KEY_TEMPLATE_STRING = "string.templated";

  private static final String VALUE_STRING = "foo";
  private static final String VALUE_QUOTED_STRING = "\"Lorem ipsum dolor 'mutt.'\"";
  private static final String VALUE_TEMPLATE_STRING = String.format("${%s} bar", KEY_STRING);
  private static final String EXPECTED_TEMPLATE_STRING = String.format("%s bar", VALUE_STRING);

  private static Configuration config;

  /**
   * A mock evaluator, so we don't have to build the live activity stack.
   */
  private static class MockExpressionEvaluator extends SimpleExpressionEvaluator {
    @Override
    public String evaluateStringExpression(String initial) {
      if (initial.equals(VALUE_TEMPLATE_STRING)) {
        return EXPECTED_TEMPLATE_STRING;
      } else {
        return initial;
      }
    }
  }

  @BeforeClass
  public static void setup() throws Exception {
    ExpressionEvaluator evaluator = new MockExpressionEvaluator();
    evaluator.setEvaluationEnvironment(new SimpleEvaluationEnvironment());

    config = new SimpleConfiguration(evaluator);

    config.setValue(KEY_STRING, VALUE_STRING);
    config.setValue(KEY_QUOTED_STRING, VALUE_QUOTED_STRING);
    config.setValue(KEY_TEMPLATE_STRING, VALUE_TEMPLATE_STRING);
  }

  /**
   * Pulls a String out of the given JavaScript scope.
   */
  public static String getResultString(Context context, ScriptableObject scope, String key)
      throws EvaluatorException {

    String expr =
        String.format("_result = %s.%s['%s'];", WebConfig.JS_GLOBAL_OBJECT,
            WebConfig.JS_CONFIGURATION_OBJECT, key);

    context.evaluateString(scope, expr, key, 1, null);

    Object result = scope.get("_result", scope);

    return result.toString();
  }

  /**
   * Test the generated JavaScript by evaluating it and comparing the resulting
   * object to the original <code>Configuration</code>.
   */
  @Test
  public void testJavaScript() {
    String webConfig = WebConfig.generate(config);

    Context context = Context.enter();
    ScriptableObject scope = context.initStandardObjects();

    try {
      context.evaluateString(scope, webConfig, "webConfig", 1, null);

      assertEquals(config.getPropertyString(KEY_STRING),
          getResultString(context, scope, KEY_STRING));

      assertEquals(config.getPropertyString(KEY_QUOTED_STRING),
          getResultString(context, scope, KEY_QUOTED_STRING));

      assertEquals(config.getPropertyString(KEY_TEMPLATE_STRING),
          getResultString(context, scope, KEY_TEMPLATE_STRING));

    } catch (EvaluatorException e) {
      fail("Failed to evaluate JavaScript: " + e.getMessage());
    } finally {
      Context.exit();
    }
  }
}
