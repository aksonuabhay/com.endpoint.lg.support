package com.endpoint.lg.support.message;

import com.endpoint.lg.support.message.Scene;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;

/**
 * Class to represent a Presentation, as delivered by the director
 *
 * @author Joshua Tolley <josh@endpoint.com>
 */
public class Presentation {
    public String description, name, resource_uri;
    public Scene[] scenes;

    /**
     * Creates a Presentation object from a JSON Presentation string
     *
     * @param jsonPresentation a JSON representation of a Presentation
     * @return                 a Presentation class
     * @throws                 IOException if there's a problem parsing the JSON
     */
    public static Presentation fromJson(String jsonPresentation) throws IOException {
        ObjectMapper om = new ObjectMapper();
        Presentation p = om.readValue(jsonPresentation, Presentation.class);
        return p;
    }
}
