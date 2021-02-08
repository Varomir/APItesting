package cdp.bonial.qa.helpers;

import org.apache.commons.configuration2.PropertiesConfiguration;

public class ConfigHelper {

    private static String replaceUuid(String origin, String uuid) {
        return origin.replace("{UUID}", uuid);
    }

    public static String getUrlFor(String serviceName, PropertiesConfiguration config) {
        return replaceUuid(config.getString(serviceName), config.getString("uuid", "dyco01"));
    }
}
