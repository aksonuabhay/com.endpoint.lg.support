package com.endpoint.lg.support.message;

import java.util.Map;

import com.google.common.collect.Maps;

import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.data.json.JsonNavigator;
import interactivespaces.util.geometry.Vector3;
import interactivespaces.service.image.gesture.Gesture;
import interactivespaces.service.image.gesture.GestureHand;
import interactivespaces.service.image.gesture.GesturePointable;

import static com.endpoint.lg.support.message.DomainMessages.*;

/**
 * Create messages for gesture events.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class GestureMessages {
  /**
   * Deserialize all gestures from data.
   * 
   * @param data
   *          gesture data
   * @return all gestures from the data
   */
  public static Map<String, Gesture> deserializeGestures(JsonNavigator data) {
    Map<String, Gesture> gestures = Maps.newHashMap();

    data.down(GestureMessageFields.FIELD_GESTURES);

    Map<String, Object> dataGestures = data.getCurrentItem();

    for (String id : dataGestures.keySet()) {
      gestures.put(id, deserializeGesture(data, id));
    }

    data.up();

    return gestures;
  }

  /**
   * Deserialize a single gesture from data.
   * 
   * @param data
   *          gesture data
   * @param id
   *          key to the gesture
   * @return gesture from the data
   */
  public static Gesture deserializeGesture(JsonNavigator data, String id) {
    data.down(id);

    String type = data.getString(GestureMessageFields.FIELD_TYPE);
    Gesture.GestureState state = deserializeGestureState(data);
    Double duration = data.getDouble(GestureMessageFields.FIELD_DURATION);

    data.up();

    return new Gesture(id, type, state, duration);
  }

  /**
   * Deserialize a gesture state.
   * 
   * @param data
   *          gesture data
   * @return the gesture state
   */
  public static Gesture.GestureState deserializeGestureState(JsonNavigator data) {
    return Gesture.GestureState.valueOf(data.getString(GestureMessageFields.FIELD_STATE));
  }

  /**
   * Deserialize all hands from data.
   * 
   * @param data
   *          hand data
   * @return all hands from the data
   */
  public static Map<String, GestureHand> deserializeGestureHands(JsonNavigator data) {
    Map<String, GestureHand> hands = Maps.newHashMap();

    data.down(GestureMessageFields.FIELD_GESTURE_HANDS);

    Map<String, Object> dataHands = data.getCurrentItem();

    for (String id : dataHands.keySet()) {
      deserializeGestureHand(data, id);
    }

    data.up();

    return hands;
  }

  /**
   * Deserialize a single hand from data.
   * 
   * @param data
   *          hand data
   * @param id
   *          key to the hand
   * @return hand from the data
   */
  public static GestureHand deserializeGestureHand(JsonNavigator data, String id) {
    data.down(id);

    Vector3 direction = deserializeVector3(data, GestureMessageFields.FIELD_DIRECTION);
    Vector3 palmNormal = deserializeVector3(data, GestureMessageFields.FIELD_PALM_NORMAL);
    Vector3 palmPosition = deserializeVector3(data, GestureMessageFields.FIELD_PALM_POSITION);
    Vector3 palmVelocity = deserializeVector3(data, GestureMessageFields.FIELD_PALM_VELOCITY);
    Vector3 sphereCenter = deserializeVector3(data, GestureMessageFields.FIELD_SPHERE_CENTER);
    Double sphereRadius = data.getDouble(GestureMessageFields.FIELD_SPHERE_RADIUS);

    data.up();

    return new GestureHand(id, palmPosition, palmVelocity, palmNormal, direction, sphereCenter,
        sphereRadius);
  }

  /**
   * Deserialize all pointables from data.
   * 
   * @param data
   *          pointable data
   * @return all pointables from the data
   */
  public static Map<String, GesturePointable> deserializeGesturePointables(JsonNavigator data) {
    Map<String, GesturePointable> pointables = Maps.newHashMap();

    data.down(GestureMessageFields.FIELD_GESTURE_POINTABLES);

    Map<String, Object> dataPointables = data.getCurrentItem();

    for (String id : dataPointables.keySet()) {
      pointables.put(id, deserializeGesturePointable(data, id));
    }

    data.up();

    return pointables;
  }

  /**
   * Deserialize a single pointable from data.
   * 
   * @param data
   *          pointable data
   * @param id
   *          key to the pointable
   * @return pointable from the data
   */
  public static GesturePointable deserializeGesturePointable(JsonNavigator data, String id) {
    data.down(id);

    Vector3 direction = deserializeVector3(data, GestureMessageFields.FIELD_DIRECTION);
    Double length = data.getDouble(GestureMessageFields.FIELD_LENGTH);
    Vector3 tipPosition = deserializeVector3(data, GestureMessageFields.FIELD_TIP_POSITION);
    Vector3 tipVelocity = deserializeVector3(data, GestureMessageFields.FIELD_TIP_VELOCITY);
    Boolean isTool = data.getBoolean(GestureMessageFields.FIELD_IS_TOOL);

    data.up();

    return new GesturePointable(id, tipPosition, direction, tipVelocity, length, isTool);
  }

  /**
   * Serialize a collection of gestures to data.
   * 
   * @param gestures
   *          the gestures to serialize
   * @param data
   *          the data to write to
   */
  public static void serializeGestures(Map<String, Gesture> gestures, JsonBuilder data) {
    data.newObject(GestureMessageFields.FIELD_GESTURES);

    for (String id : gestures.keySet()) {
      serializeGesture(gestures.get(id), data);
    }

    data.up();
  }

  /**
   * Serialize a single gesture to data.
   * 
   * @param gesture
   *          the gesture to serialize
   * @param data
   *          the data to write to
   */
  public static void serializeGesture(Gesture gesture, JsonBuilder data) {
    String id = gesture.getId();

    data.newObject(id);

    data.put(GestureMessageFields.FIELD_ID, id);
    data.put(GestureMessageFields.FIELD_TYPE, gesture.getType());
    data.put(GestureMessageFields.FIELD_DURATION, gesture.getDuration());
    serializeGestureState(gesture.getState(), data);

    data.up();
  }

  /**
   * Serialize a gesture state.
   * 
   * @param state
   *          the state to serialize
   * @param data
   *          the data to write to
   */
  public static void serializeGestureState(Gesture.GestureState state, JsonBuilder data) {
    data.put(GestureMessageFields.FIELD_STATE, state.toString());
  }

  /**
   * Serialize a collection of hands to data.
   * 
   * @param hands
   *          the hands to serialize
   * @param data
   *          the data to write to
   */
  public static void serializeGestureHands(Map<String, GestureHand> hands, JsonBuilder data) {
    data.newObject(GestureMessageFields.FIELD_GESTURE_HANDS);

    for (String id : hands.keySet()) {
      serializeGestureHand(hands.get(id), data);
    }

    data.up();
  }

  /**
   * Serialize a hand to data.
   * 
   * @param hand
   *          the hand to serialize
   * @param data
   *          the data to write to
   */
  public static void serializeGestureHand(GestureHand hand, JsonBuilder data) {
    String id = hand.getId();

    data.newObject(id);

    data.put(GestureMessageFields.FIELD_ID, id);
    serializeVector3(hand.getPalmPosition(), GestureMessageFields.FIELD_PALM_POSITION, data);
    serializeVector3(hand.getPalmVelocity(), GestureMessageFields.FIELD_PALM_VELOCITY, data);
    serializeVector3(hand.getPalmNormal(), GestureMessageFields.FIELD_PALM_NORMAL, data);
    serializeVector3(hand.getSphereCenter(), GestureMessageFields.FIELD_SPHERE_CENTER, data);
    serializeVector3(hand.getDirection(), GestureMessageFields.FIELD_DIRECTION, data);
    data.put(GestureMessageFields.FIELD_SPHERE_RADIUS, hand.getSphereRadius());

    data.up();
  }

  /**
   * Serialize a collection of pointables to data.
   * 
   * @param pointables
   *          the pointables to serialize
   * @param data
   *          the data to write to
   */
  public static void serializeGesturePointables(Map<String, GesturePointable> pointables,
      JsonBuilder data) {
    data.newObject(GestureMessageFields.FIELD_GESTURE_POINTABLES);

    for (String id : pointables.keySet()) {
      serializeGesturePointable(pointables.get(id), data);
    }

    data.up();
  }

  /**
   * Serialize a single pointable to data.
   * 
   * @param pointable
   *          the pointable to be serialized
   * @param data
   *          the data to write to
   */
  public static void serializeGesturePointable(GesturePointable pointable, JsonBuilder data) {
    String id = pointable.getId();

    data.newObject(id);

    data.put(GestureMessageFields.FIELD_ID, id);
    data.put(GestureMessageFields.FIELD_LENGTH, pointable.getLength());
    data.put(GestureMessageFields.FIELD_IS_TOOL, pointable.isTool());
    serializeVector3(pointable.getDirection(), GestureMessageFields.FIELD_DIRECTION, data);
    serializeVector3(pointable.getTipPosition(), GestureMessageFields.FIELD_TIP_POSITION, data);
    serializeVector3(pointable.getTipVelocity(), GestureMessageFields.FIELD_TIP_VELOCITY, data);

    data.up();
  }
}
