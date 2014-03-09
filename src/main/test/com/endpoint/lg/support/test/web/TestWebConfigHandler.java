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

package com.endpoint.lg.support.test.web;

import interactivespaces.configuration.Configuration;
import interactivespaces.configuration.SimpleConfiguration;
import interactivespaces.evaluation.ExpressionEvaluator;
import interactivespaces.evaluation.SimpleEvaluationEnvironment;
import interactivespaces.evaluation.SimpleExpressionEvaluator;
import interactivespaces.service.web.server.internal.netty.NettyWebServer;
import interactivespaces.service.web.server.WebServer;

import org.mozilla.javascript.EvaluatorException;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Context;
import com.endpoint.lg.support.web.WebConfigHandler;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;
import org.ros.concurrent.DefaultScheduledExecutorService;

import java.util.concurrent.ScheduledExecutorService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * Test the HTTP dynamic configuration handler.
 * 
 * TODO: Remove the WebServer components and properly mock HttpRequest and
 * HttpResponse.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestWebConfigHandler {
  public static final String TEST_NAME = "TestWebConfigHandler";
  public static final int WEB_SERVER_PORT = 20202;
  public static final String HANDLER_PATH = "handler.js";

  public static final String KEY_STRING = "string";
  public static final String KEY_QUOTED_STRING = "string.quoted";
  public static final String KEY_TEMPLATE_STRING = "string.templated";

  public static final String VALUE_STRING = "foo";
  public static final String VALUE_QUOTED_STRING = "\"Lorem ipsum dolor 'mutt.'\"";
  public static final String VALUE_TEMPLATE_STRING = String.format("${%s} bar", KEY_STRING);
  public static final String EXPECTED_TEMPLATE_STRING = String.format("%s bar", VALUE_STRING);

  private static Configuration config;
  private static WebServer server;
  private static URL url;

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
  public static void testSetup() {
    ScheduledExecutorService executorSvc = new DefaultScheduledExecutorService();
    Log log = new SimpleLog(TEST_NAME);

    server = new NettyWebServer(TEST_NAME, WEB_SERVER_PORT, executorSvc, log);

    ExpressionEvaluator evaluator = new MockExpressionEvaluator();
    evaluator.setEvaluationEnvironment(new SimpleEvaluationEnvironment());

    config = new SimpleConfiguration(evaluator);

    config.setValue(KEY_STRING, VALUE_STRING);
    config.setValue(KEY_QUOTED_STRING, VALUE_QUOTED_STRING);
    config.setValue(KEY_TEMPLATE_STRING, VALUE_TEMPLATE_STRING);

    WebConfigHandler handler = new WebConfigHandler(config);
    server.addDynamicContentHandler(HANDLER_PATH, false, handler);

    server.startup();
  }

  /**
   * Pulls a String out of the given JavaScript scope.
   */
  public static String getResultString(Context context, ScriptableObject scope, String key)
      throws EvaluatorException {

    String expr =
        String.format("_result = %s.%s['%s'];", WebConfigHandler.JS_GLOBAL_OBJECT,
            WebConfigHandler.JS_CONFIGURATION_OBJECT, key);

    context.evaluateString(scope, expr, key, 1, null);

    Object result = scope.get("_result", scope);

    return result.toString();
  }

  /**
   * Tests many requests to the handler.
   */
  @Test
  public void testRequests() {
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    String line = null;
    StringBuilder response;

    try {
      url = new URL("http://localhost:" + WEB_SERVER_PORT + "/" + HANDLER_PATH);
    } catch (MalformedURLException e) {
      fail("Fix your URL: " + e.getMessage());
    }

    for (int i = 0; i < 100; i++) {
      response = new StringBuilder();

      // Open a connection.
      try {
        connection = (HttpURLConnection) url.openConnection();
      } catch (IOException e) {
        fail("IOException during connection: " + e.getMessage());
      }

      // Verify the HTTP response code.
      try {
        assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
      } catch (IOException e) {
        fail("IOException reading response code: " + e.getMessage());
      }

      // Get a buffered reader from the connection.
      try {
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      } catch (IOException e) {
        fail("IOException fetching response stream: " + e.getMessage());
      }

      // Read the entire response body.
      try {
        while ((line = reader.readLine()) != null) {
          response.append(line);
        }
      } catch (IOException e) {
        fail("IOException while reading response: " + e.getMessage());
      } finally {
        try {
          reader.close();
        } catch (IOException e) {
          fail("IOException while closing response stream: " + e.getMessage());
        }
      }

      // Evaluate the response JavaScript.
      Context context = Context.enter();
      ScriptableObject scope = context.initStandardObjects();

      try {
        context.evaluateString(scope, response.toString(), "response", 1, null);

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

  @AfterClass
  public static void tearDown() {
    server.shutdown();
  }
}
