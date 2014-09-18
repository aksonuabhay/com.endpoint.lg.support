package com.endpoint.lg.support.message;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * A simple class to store Window descriptions in presentations
 *
 * @author Joshua Tolley <josh@endpoint.com>
 */
public class Window { 
    public String activity, presentation_viewport;
    public int height, width, x_coord, y_coord;
    public String[] assets;

    /**
     * Creates a Window object from a JSON Window string
     *
     * @param jsonWindow    a JSON representation of a Window
     * @return              a Window class
     * @throws              IOException if there's a problem parsing the JSON
     */
    public static Window fromJson(String jsonWindow) throws IOException {
        ObjectMapper om = new ObjectMapper();
        Window w = om.readValue(jsonWindow, Window.class);
        return w;
    }

    /*
     * Converts a window's viewport and coordinate properties into the proper
     * window_slug value, used by the earth client and kmlsync activities.
     */
    public String getWindowSlug() {
        String s = x_coord + ":" + y_coord + ":" + presentation_viewport;
        return s;
    }
}
