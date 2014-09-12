package com.endpoint.lg.support.message;

import com.endpoint.lg.support.message.Window;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;

/**
 * Represents a Scene object as delivered by the director
 *
 * @author Joshua Tolley <josh@endpoint.com>
 */
public class Scene {
    public String description, name, resource_uri, slug;
    public int duration;
    public Window[] windows;

    /**
     * Creates a Scene object from a JSON Scene string
     *
     * @param jsonScene     a JSON representation of a Scene
     * @return              a Scene class
     * @throws              IOException if there's a problem parsing the JSON
     */
    public static Scene fromJson(String jsonScene) throws IOException
    {
        ObjectMapper om = new ObjectMapper();
        Scene s = om.readValue(jsonScene, Scene.class);
        return s;
    }
}
