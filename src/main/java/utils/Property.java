package main.java.utils;

import java.io.*;
import java.util.Properties;

public class Property {

    private static Properties prop;

    static {
        prop = new Properties();
        try {
            prop.load(new FileReader("./src/main/resources/config.properties"));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
	
}
