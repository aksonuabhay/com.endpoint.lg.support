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

package com.endpoint.lg.support.web;

import interactivespaces.SimpleInteractiveSpacesException;
import interactivespaces.service.web.server.HttpDynamicRequestHandler;
import interactivespaces.service.web.server.HttpRequest;
import interactivespaces.service.web.server.HttpResponse;
import interactivespaces.configuration.Configuration;

import java.io.IOException;

/**
 * An HTTP request handler which serves a <code>Configuration</code> as a
 * JavaScript object.
 * 
 * This is intended to be used for serving an entire live activity configuration
 * to its webapp.
 * 
 * To use this handler in a webapp, load the handler's path as a script. The
 * configuration will be in the global <code>IS.Configuration</code> object.
 * 
 * Example:
 * 
 * <pre>
 * {@code
 * @Override
 * public void onActivityStartup() {
 *   WebServer webserver = getWebServer();
 * 
 *   WebConfigHandler configHandler = new WebConfigHandler(getConfiguration());
 *   webserver.addDynamicContentHandler("is.config.js", false, configHandler);
 * }
 * }
 * </pre>
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class WebConfigHandler implements HttpDynamicRequestHandler {
  /**
   * The cached response.
   */
  private byte[] configResponse;

  /**
   * Creates a new WebConfigHandler with the given <code>Configuration</code>.
   * 
   * @param config
   *          the configuration to serve
   */
  public WebConfigHandler(Configuration config) {
    configResponse = WebConfig.generate(config).getBytes();
  }

  /**
   * Handles all HTTP requests by responding with the generated JavaScript
   * representation of the <code>Configuration</code>.
   * 
   * @param request
   *          the HTTP request
   * @param response
   *          the HTTP response handle
   */
  public void handle(HttpRequest request, HttpResponse response) {
    try {
      response.getOutputStream().write(configResponse);
    } catch (IOException e) {
      throw new SimpleInteractiveSpacesException("Error while writing config response", e);
    }
  }
}
