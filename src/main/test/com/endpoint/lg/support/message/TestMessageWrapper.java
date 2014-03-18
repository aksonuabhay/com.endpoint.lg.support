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

package com.endpoint.lg.support.message;

import static org.junit.Assert.*;

import java.util.Map;

import interactivespaces.util.data.json.JsonBuilder;

import org.junit.BeforeClass;
import org.junit.Test;
import org.python.google.common.collect.Maps;

/**
 * Test <code>MessageWrapper</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestMessageWrapper {
  private static final String TEST_TYPE = "test.message";
  private static Map<String, Object> emptyData;
  private static Map<String, Object> testData;

  @BeforeClass
  public static void setupData() throws Exception {
    emptyData = Maps.newHashMap();

    testData = Maps.newHashMap();

    testData.put("foo", "bar");
    testData.put("number", 1);
  }

  /**
   * Test an empty message, built with only a type.
   */
  @Test
  public void testEmptyMessage() {
    JsonBuilder json = MessageWrapper.newTypedMessage(TEST_TYPE);

    Map<String, Object> built = json.build();

    assertEquals(TEST_TYPE, built.get(MessageWrapper.MESSAGE_FIELD_TYPE));
    assertEquals(emptyData, built.get(MessageWrapper.MESSAGE_FIELD_DATA));
  }

  /**
   * Test a message with some data.
   */
  @Test
  public void testDataMessage() {
    JsonBuilder json = MessageWrapper.newTypedMessage(TEST_TYPE, testData);

    Map<String, Object> built = json.build();

    assertEquals(TEST_TYPE, built.get(MessageWrapper.MESSAGE_FIELD_TYPE));
    assertEquals(testData, built.get(MessageWrapper.MESSAGE_FIELD_DATA));
  }
}
