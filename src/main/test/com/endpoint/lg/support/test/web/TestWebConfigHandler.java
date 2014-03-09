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
import interactivespaces.evaluation.SimpleExpressionEvaluator;
import interactivespaces.service.web.server.internal.netty.NettyWebServer;
import interactivespaces.service.web.server.WebServer;
import interactivespaces.util.data.json.JsonMapper;
import interactivespaces.util.data.json.JsonNavigator;

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

import java.util.Map;
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
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestWebConfigHandler {
  public static final String TEST_NAME = "TestWebConfigHandler";
  public static final int WEB_SERVER_PORT = 20202;
  public static final String HANDLER_PATH = "handler.js";

  private static JsonMapper mapper;
  private static WebServer server;
  private static URL url;
  private static Map<String, Object> expected;

  @BeforeClass
  public static void testSetup() {
    ScheduledExecutorService executorSvc = new DefaultScheduledExecutorService();
    Log log = new SimpleLog(TEST_NAME);

    server = new NettyWebServer(TEST_NAME, WEB_SERVER_PORT, executorSvc, log);

    Configuration config = new SimpleConfiguration(new SimpleExpressionEvaluator());

    config.setValue("foo", "bar");
    config.setValue("bim.bap", "boo");
    config.setValue("blam.a.lam.number", "0");
    config.setValue("null.a.lull", null);

    WebConfigHandler handler = new WebConfigHandler(config);
    server.addDynamicContentHandler(HANDLER_PATH, false, handler);

    expected = new JsonNavigator(config.getCollapsedMap()).getRoot();

    mapper = new JsonMapper();

    server.startup();
  }

  @Test
  public void test() {
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    String line = null;
    StringBuilder response;
    Map<String, Object> evaluated;

    try {
      url = new URL("http://localhost:" + WEB_SERVER_PORT + "/" + HANDLER_PATH);
    } catch (MalformedURLException e) {
      fail("Fix your URL: " + e.getMessage());
    }

    for (int i = 0; i < 1000; i++) {
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

      context.evaluateString(scope, response.toString(), TEST_NAME, 1, null);
      context.evaluateString(scope, String.format("_result = JSON.stringify(%s.%s);",
          WebConfigHandler.JS_GLOBAL_OBJECT, WebConfigHandler.JS_CONFIGURATION_OBJECT), TEST_NAME,
          1, null);

      Object result = scope.get("_result", scope);

      evaluated = mapper.parseObject(Context.toString(result));
      
      Context.exit();

      // Compare the evaluated JavaScript against the Configuration Map.
      assertEquals(expected, evaluated);
    }
  }

  @AfterClass
  public static void tearDown() {
    server.shutdown();
  }
}
