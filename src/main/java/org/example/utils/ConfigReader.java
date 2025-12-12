package org.example.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("⚠️ config.properties not found!");
            } else {
                properties.load(input);
            }
        } catch (Exception e) {
            System.out.println("❌ Error loading config.properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static String getUsername(String userType) {
        return getProperty(userType + ".username");
    }

    public static String getPassword(String userType) {
        return getProperty(userType + ".password");
    }
}