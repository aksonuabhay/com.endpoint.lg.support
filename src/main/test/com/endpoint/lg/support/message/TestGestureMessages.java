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

package com.endpoint.lg.support.message;

import java.util.Map;

import interactivespaces.service.image.gesture.Gesture;
import interactivespaces.service.image.gesture.Gesture.GestureState;
import interactivespaces.service.image.gesture.GestureHand;
import interactivespaces.service.image.gesture.GesturePointable;
import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;
import interactivespaces.util.geometry.Vector3;
import interactivespaces.service.image.gesture.leapmotion.LeapMotionGestureEndpoint;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Test <code>GestureMessages</code>.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class TestGestureMessages {
  private static final String TEST_GESTURE_ID = "42";
  private static final String TEST_GESTURE_TYPE = "swipe";
  private static final GestureState TEST_GESTURE_STATE = GestureState.UPDATE;
  private static final double TEST_GESTURE_DURATION = 4.2;

  private static final Gesture TEST_GESTURE = new Gesture(TEST_GESTURE_ID, TEST_GESTURE_TYPE,
      TEST_GESTURE_STATE, TEST_GESTURE_DURATION);;

  private static Map<String, Object> testGestureData;

  private static JsonNavigator testGestureMessage;

  private static JsonNavigator testGestureStateMessage;

  private static final Map<String, Gesture> TEST_GESTURES = ImmutableMap.of(TEST_GESTURE_ID,
      TEST_GESTURE);

  private static Map<String, Object> testGesturesData;

  private static JsonNavigator testGesturesMessage;

  private static final String TEST_HAND_ID = "23";
  private static final Vector3 TEST_HAND_PALM_POSITION = new Vector3(1.1, 2.2, 3.3);
  private static final Vector3 TEST_HAND_PALM_VELOCITY = new Vector3(4.4, 5.5, 6.6);
  private static final Vector3 TEST_HAND_PALM_NORMAL = new Vector3(7.7, 8.8, 9.9);
  private static final Vector3 TEST_HAND_DIRECTION = new Vector3(10.01, 11.11, 12.21);
  private static final Vector3 TEST_HAND_SPHERE_CENTER = new Vector3(13.31, 14.41, 15.51);
  private static final double TEST_HAND_SPHERE_RADIUS = 16.61;

  private static final GestureHand TEST_HAND = new GestureHand(TEST_HAND_ID,
      TEST_HAND_PALM_POSITION, TEST_HAND_PALM_VELOCITY, TEST_HAND_PALM_NORMAL, TEST_HAND_DIRECTION,
      TEST_HAND_SPHERE_CENTER, TEST_HAND_SPHERE_RADIUS);

  private static Map<String, Object> testHandData;

  private static JsonNavigator testHandMessage;

  private static final Map<String, GestureHand> TEST_HANDS = ImmutableMap.of(TEST_HAND_ID,
      TEST_HAND);

  private static Map<String, Object> testHandsData;

  private static JsonNavigator testHandsMessage;

  private static final String TEST_POINTABLE_ID = "88";
  private static final Vector3 TEST_POINTABLE_TIP_POSITION = new Vector3(1.1, 2.2, 3.3);
  private static final Vector3 TEST_POINTABLE_DIRECTION = new Vector3(4.4, 5.5, 6.6);
  private static final Vector3 TEST_POINTABLE_TIP_VELOCITY = new Vector3(7.7, 8.8, 9.9);
  private static final double TEST_POINTABLE_LENGTH = 10.01;
  private static final boolean TEST_POINTABLE_TOOL = false;

  private static final GesturePointable TEST_POINTABLE = new GesturePointable(TEST_POINTABLE_ID,
      TEST_POINTABLE_TIP_POSITION, TEST_POINTABLE_DIRECTION, TEST_POINTABLE_TIP_VELOCITY,
      TEST_POINTABLE_LENGTH, TEST_POINTABLE_TOOL);

  private static Map<String, Object> testPointableData;

  private static JsonNavigator testPointableMessage;

  private static final Map<String, GesturePointable> TEST_POINTABLES = ImmutableMap.of(
      TEST_POINTABLE_ID, TEST_POINTABLE);

  private static Map<String, Object> testPointablesData;

  private static JsonNavigator testPointablesMessage;

  /**
   * Constructs test fixtures.
   * 
   * @throws Exception
   */
  @BeforeClass
  static public void testSetup() throws Exception {
    testGestureData =
        new JsonBuilder()
            .put(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURE_ID, TEST_GESTURE_ID)
            .put(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURE_TYPE, TEST_GESTURE_TYPE)
            .put(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURE_STATE,
                TEST_GESTURE_STATE.toString())
            .put(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURE_DURATION, TEST_GESTURE_DURATION)
            .build();

    testGestureMessage = new JsonNavigator(ImmutableMap.of(TEST_GESTURE_ID, testGestureData));

    testGestureStateMessage = new JsonNavigator(testGestureData);

    testGesturesData = ImmutableMap.<String, Object>of(TEST_GESTURE_ID, testGestureData);

    testGesturesMessage =
        new JsonNavigator(ImmutableMap.of(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURES,
            testGesturesData));

    JsonBuilder testHandBuilder = new JsonBuilder();

    testHandBuilder.put(LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_ID, TEST_HAND_ID);
    testHandBuilder.put(LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_SPHERE_RADIUS,
        TEST_HAND_SPHERE_RADIUS);

    DomainMessages.serializeVector3(TEST_HAND_PALM_POSITION,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_PALM_POSITION, testHandBuilder);
    DomainMessages.serializeVector3(TEST_HAND_PALM_VELOCITY,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_PALM_VELOCITY, testHandBuilder);
    DomainMessages.serializeVector3(TEST_HAND_PALM_NORMAL,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_PALM_NORMAL, testHandBuilder);
    DomainMessages.serializeVector3(TEST_HAND_DIRECTION,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_DIRECTION, testHandBuilder);
    DomainMessages.serializeVector3(TEST_HAND_SPHERE_CENTER,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_SPHERE_CENTER, testHandBuilder);

    testHandData = testHandBuilder.build();

    testHandMessage = new JsonNavigator(ImmutableMap.of(TEST_HAND_ID, testHandData));

    testHandsData = ImmutableMap.<String, Object>of(TEST_HAND_ID, testHandData);

    testHandsMessage =
        new JsonNavigator(ImmutableMap.of(LeapMotionGestureEndpoint.LEAPMOTION_NAME_HANDS,
            testHandsData));

    JsonBuilder testPointableBuilder = new JsonBuilder();

    testPointableBuilder.put(LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_ID,
        TEST_POINTABLE_ID);
    testPointableBuilder.put(LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_LENGTH,
        TEST_POINTABLE_LENGTH);
    testPointableBuilder.put(LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_TOOL,
        TEST_POINTABLE_TOOL);

    DomainMessages.serializeVector3(TEST_POINTABLE_TIP_POSITION,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_TIP_POSITION, testPointableBuilder);
    DomainMessages.serializeVector3(TEST_POINTABLE_DIRECTION,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_DIRECTION, testPointableBuilder);
    DomainMessages.serializeVector3(TEST_POINTABLE_TIP_VELOCITY,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_TIP_VELOCITY, testPointableBuilder);

    testPointableData = testPointableBuilder.build();

    testPointableMessage = new JsonNavigator(ImmutableMap.of(TEST_POINTABLE_ID, testPointableData));

    testPointablesData = ImmutableMap.<String, Object>of(TEST_POINTABLE_ID, testPointableData);

    testPointablesMessage =
        new JsonNavigator(ImmutableMap.of(LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLES,
            testPointablesData));
  }

  /**
   * Compares two Vector3 objects.
   * 
   * @param expected
   *          model vector
   * @param actual
   *          vector under test
   */
  private static void assertVectorEquals(Vector3 expected, Vector3 actual) {
    assertEquals(expected.getV0(), actual.getV0(), 0);
    assertEquals(expected.getV1(), actual.getV1(), 0);
    assertEquals(expected.getV2(), actual.getV2(), 0);
  }

  /**
   * Tests packing a GestureState.
   */
  @Test
  public void testSerializeGestureState() {
    JsonBuilder data = new JsonBuilder();

    GestureMessages.serializeGestureState(TEST_GESTURE_STATE, data);

    JsonNavigator serialized = new JsonNavigator(data.build());

    assertEquals(TEST_GESTURE_STATE, GestureState.valueOf(serialized
        .getString(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURE_STATE)));
  }

  /**
   * Tests unpacking a GestureState.
   */
  @Test
  public void testDeserializeGestureState() {
    GestureState deserialized = GestureMessages.deserializeGestureState(testGestureStateMessage);

    assertEquals(TEST_GESTURE_STATE, deserialized);
  }

  /**
   * Checks a gesture message against the test fixture.
   * 
   * @param actual
   *          the message under test
   */
  private void checkGestureMessage(JsonNavigator actual) {
    assertTrue(actual.containsProperty(TEST_GESTURE_ID));

    actual.down(TEST_GESTURE_ID);

    assertEquals(TEST_GESTURE.getId(),
        actual.getString(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURE_ID));
    assertEquals(TEST_GESTURE.getType(),
        actual.getString(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURE_TYPE));
    assertEquals(TEST_GESTURE.getState(), GestureState.valueOf(actual
        .getString(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURE_STATE)));
    assertEquals(TEST_GESTURE.getDuration(),
        actual.getDouble(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURE_DURATION), 0);

    actual.up();
  }

  /**
   * Checks a gesture against the test fixture.
   * 
   * @param actual
   *          gesture to verify
   */
  private void checkGesture(Gesture actual) {
    assertNotNull(actual);

    assertEquals(TEST_GESTURE.getId(), actual.getId());
    assertEquals(TEST_GESTURE.getType(), actual.getType());
    assertEquals(TEST_GESTURE.getState(), actual.getState());
    assertEquals(TEST_GESTURE.getDuration(), actual.getDuration(), 0);
  }

  /**
   * Tests packing a Gesture.
   */
  @Test
  public void testSerializeGesture() {
    JsonBuilder data = new JsonBuilder();

    GestureMessages.serializeGesture(TEST_GESTURE, data);

    JsonNavigator serialized = new JsonNavigator(data.build());

    checkGestureMessage(serialized);
  }

  /**
   * Tests unpacking a Gesture.
   */
  @Test
  public void testDeserializeGesture() {
    Gesture deserialized = GestureMessages.deserializeGesture(testGestureMessage, TEST_GESTURE_ID);

    checkGesture(deserialized);
  }

  /**
   * Tests packing a Gesture Map such as from a LEAP endpoint.
   */
  @Test
  public void testSerializeGestures() {
    JsonBuilder data = new JsonBuilder();

    GestureMessages.serializeGestures(TEST_GESTURES, data);

    JsonNavigator serialized = new JsonNavigator(data.build());

    assertTrue(serialized.containsProperty(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURES));

    serialized.down(LeapMotionGestureEndpoint.LEAPMOTION_NAME_GESTURES);

    checkGestureMessage(serialized);
  }

  /**
   * Tests unpacking a Gesture Map.
   */
  @Test
  public void testDeserializeGestures() {
    Map<String, Gesture> deserialized = GestureMessages.deserializeGestures(testGesturesMessage);

    assertNotNull(deserialized);

    assertTrue(deserialized.containsKey(TEST_GESTURE_ID));

    checkGesture(deserialized.get(TEST_GESTURE_ID));
  }

  /**
   * Checks a GestureHand message against the test fixture.
   * 
   * @param actual
   *          the message under test
   */
  private static void checkHandMessage(JsonNavigator actual) {
    assertTrue(actual.containsProperty(TEST_HAND_ID));

    actual.down(TEST_HAND_ID);

    assertEquals(TEST_HAND.getId(),
        actual.getString(LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_ID));
    assertVectorEquals(TEST_HAND.getPalmPosition(), DomainMessages.deserializeVector3(actual,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_PALM_POSITION));
    assertVectorEquals(TEST_HAND.getPalmVelocity(), DomainMessages.deserializeVector3(actual,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_PALM_VELOCITY));
    assertVectorEquals(TEST_HAND.getPalmNormal(), DomainMessages.deserializeVector3(actual,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_PALM_NORMAL));
    assertVectorEquals(TEST_HAND.getDirection(), DomainMessages.deserializeVector3(actual,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_DIRECTION));
    assertVectorEquals(TEST_HAND.getSphereCenter(), DomainMessages.deserializeVector3(actual,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_SPHERE_CENTER));
    assertEquals(TEST_HAND.getSphereRadius(),
        actual.getDouble(LeapMotionGestureEndpoint.LEAPMOTION_NAME_HAND_SPHERE_RADIUS), 0);

    actual.up();
  }

  /**
   * Checks a GestureHand against the test fixture.
   * 
   * @param actual
   *          the hand under test
   */
  private static void checkHand(GestureHand actual) {
    assertNotNull(actual);

    assertEquals(TEST_HAND.getId(), actual.getId());
    assertVectorEquals(TEST_HAND.getPalmPosition(), actual.getPalmPosition());
    assertVectorEquals(TEST_HAND.getPalmVelocity(), actual.getPalmVelocity());
    assertVectorEquals(TEST_HAND.getPalmNormal(), actual.getPalmNormal());
    assertVectorEquals(TEST_HAND.getDirection(), actual.getDirection());
    assertVectorEquals(TEST_HAND.getSphereCenter(), actual.getSphereCenter());
    assertEquals(TEST_HAND.getSphereRadius(), actual.getSphereRadius(), 0);
  }

  /**
   * Tests packing a GestureHand object.
   */
  @Test
  public void testSerializeGestureHand() {
    JsonBuilder data = new JsonBuilder();

    GestureMessages.serializeGestureHand(TEST_HAND, data);

    JsonNavigator serialized = new JsonNavigator(data.build());

    checkHandMessage(serialized);
  }

  /**
   * Tests unpacking a GestureHand object.
   */
  @Test
  public void testDeserializeGestureHand() {
    GestureHand deserialized =
        GestureMessages.deserializeGestureHand(testHandMessage, TEST_HAND_ID);

    checkHand(deserialized);
  }

  /**
   * Tests packing GestureHand Maps such as from a LEAP endpoint.
   */
  @Test
  public void testSerializeGestureHands() {
    JsonBuilder data = new JsonBuilder();

    GestureMessages.serializeGestureHands(TEST_HANDS, data);

    JsonNavigator serialized = new JsonNavigator(data.build());

    assertTrue(serialized.containsProperty(LeapMotionGestureEndpoint.LEAPMOTION_NAME_HANDS));

    serialized.down(LeapMotionGestureEndpoint.LEAPMOTION_NAME_HANDS);

    checkHandMessage(serialized);
  }

  /**
   * Tests unpacking a GestureHand Map.
   */
  @Test
  public void testDeserializeGestureHands() {
    Map<String, GestureHand> deserialized =
        GestureMessages.deserializeGestureHands(testHandsMessage);

    assertNotNull(deserialized);

    assertTrue(deserialized.containsKey(TEST_HAND_ID));

    checkHand(deserialized.get(TEST_HAND_ID));
  }

  /**
   * Checks a GesturePointable message against the test fixture.
   * 
   * @param actual
   *          the message under test
   */
  private static void checkPointableMessage(JsonNavigator actual) {
    assertTrue(actual.containsProperty(TEST_POINTABLE_ID));

    actual.down(TEST_POINTABLE_ID);

    assertEquals(TEST_POINTABLE.getId(),
        actual.getString(LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_ID));
    assertVectorEquals(TEST_POINTABLE.getTipPosition(), DomainMessages.deserializeVector3(actual,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_TIP_POSITION));
    assertVectorEquals(TEST_POINTABLE.getDirection(), DomainMessages.deserializeVector3(actual,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_DIRECTION));
    assertVectorEquals(TEST_POINTABLE.getTipVelocity(), DomainMessages.deserializeVector3(actual,
        LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_TIP_VELOCITY));
    assertEquals(TEST_POINTABLE.getLength(),
        actual.getDouble(LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_LENGTH), 0);
    assertEquals(TEST_POINTABLE.isTool(),
        actual.getBoolean(LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLE_TOOL));

    actual.up();
  }

  /**
   * Checks a GesturePointable against the test fixture.
   * 
   * @param actual
   *          the pointable under test
   */
  private static void checkPointable(GesturePointable actual) {
    assertNotNull(actual);

    assertEquals(TEST_POINTABLE.getId(), actual.getId());
    assertVectorEquals(TEST_POINTABLE.getTipPosition(), actual.getTipPosition());
    assertVectorEquals(TEST_POINTABLE.getDirection(), actual.getDirection());
    assertVectorEquals(TEST_POINTABLE.getTipVelocity(), actual.getTipVelocity());
    assertEquals(TEST_POINTABLE.getLength(), actual.getLength(), 0);
    assertEquals(TEST_POINTABLE.isTool(), actual.isTool());
  }

  /**
   * Tests packing a GesturePointable object.
   */
  @Test
  public void testSerializeGesturePointable() {
    JsonBuilder data = new JsonBuilder();

    GestureMessages.serializeGesturePointable(TEST_POINTABLE, data);

    JsonNavigator serialized = new JsonNavigator(data.build());

    checkPointableMessage(serialized);
  }

  /**
   * Tests unpacking a GesturePointable object.
   */
  @Test
  public void testDeserializeGesturePointable() {
    GesturePointable deserialized =
        GestureMessages.deserializeGesturePointable(testPointableMessage, TEST_POINTABLE_ID);

    checkPointable(deserialized);
  }

  /**
   * Tests packing GesturePointable Maps such as from a LEAP endpoint.
   */
  @Test
  public void testSerializeGesturePointables() {
    JsonBuilder data = new JsonBuilder();

    GestureMessages.serializeGesturePointables(TEST_POINTABLES, data);

    JsonNavigator serialized = new JsonNavigator(data.build());

    assertTrue(serialized.containsProperty(LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLES));

    serialized.down(LeapMotionGestureEndpoint.LEAPMOTION_NAME_POINTABLES);

    checkPointableMessage(serialized);
  }

  /**
   * Tests unpacking a GesturePointable Map.
   */
  @Test
  public void testDeserializeGesturePointables() {
    Map<String, GesturePointable> deserialized =
        GestureMessages.deserializeGesturePointables(testPointablesMessage);

    assertNotNull(deserialized);

    assertTrue(deserialized.containsKey(TEST_POINTABLE_ID));

    checkPointable(deserialized.get(TEST_POINTABLE_ID));
  }
}
