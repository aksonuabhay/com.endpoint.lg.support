package com.endpoint.lg.support.message;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * A simple class to store Window descriptions in presentations
 *
 * @author Joshua Tolley <josh@endpoint.com>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        Window w = (Window) o;

        if (
            activity.equals(w.activity) &&
            presentation_viewport.equals(w.presentation_viewport) &&
                // NB! Here's another place we're hardcoding the fact that
                // we ignore all but the first asset in a Window message
            assets[0].equals(w.assets[0]) &&
            height == w.height &&
            width == w.width &&
            x_coord == w.x_coord &&
            y_coord == w.y_coord
        ) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "WINDOW: viewport - " + presentation_viewport +
            ", activity - " + activity +
            ", height - " + height +
            ", width - " + width +
            ", x_coord - " + x_coord +
            ", y_coord - " + y_coord +
            ", assets[0] - " + assets[0];
    }
}
