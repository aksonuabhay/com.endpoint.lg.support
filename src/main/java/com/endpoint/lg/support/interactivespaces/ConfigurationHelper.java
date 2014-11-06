package com.endpoint.lg.support.interactivespaces;

import interactivespaces.configuration.Configuration;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/*
 * Simple class for dealing with configuration option "arrays"
 * e.g. my.config.value.1, my.config.value.2
 *
 * Interactive Spaces doesn't support more than 255 characters in a
 * configuration option. Though that will likely change soon, it gets painful
 * to read a bunch of configuration options in one long line. This allows us to
 * break them up a bit.
 *
 * @author Joshua Tolley <josh@endpoint.com>
 */
public class ConfigurationHelper {
    /*
     * Takes a Configuration object and a key to search for. Returns a
     * List<String> containing the values of the provided, and all not-null
     * configuration keys formed by appending .1, .2, .3, etc. to the key.
     */
    public static List<String> getConfigurationStrings(Configuration config, String key) {
        List<String> l = new ArrayList<String>();
        String value;
        int i = 1;

        value = config.getPropertyString(key);
        if (value != null) l.add(value);

        while (true) {
            value = config.getPropertyString(String.format("%s.%d", key, i));
            if (value != null) {
                l.add(value);
            }
            else { break; }
            i++;
        };
        return l;
    }

    /*
     * Same as getConfigurationStrings, but it concatenates all the strings in
     * the list into one resulting string
     */
    public static String getConfigurationConcat(Configuration config, String key) {
        List<String> l = getConfigurationStrings(config, key);
        return StringUtils.join(l, " ");
    }
}
