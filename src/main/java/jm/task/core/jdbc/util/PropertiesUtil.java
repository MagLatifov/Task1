package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        PROPERTIES.clear();
        try (InputStream inputstream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties") ) {
            PROPERTIES.load(inputstream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PropertiesUtil() {
        throw new AssertionError("Создавать экземпляр класса PropertiesUtil нельзя!!!");
    }
    public static String getProp(String key) {
        return PROPERTIES.getProperty(key);
    }
}
