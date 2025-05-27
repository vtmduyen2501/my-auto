package utils.helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileHelper {

    private static Properties properties;
    private static String filePath;
    
    

    public static void setPropertiesFilePath(String filePath) {
    	PropertiesFileHelper.filePath = filePath;
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Read value based on key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Create a new key-value pair or updates an existing key
    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            properties.store(fos, "Updated property " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if key exists
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }
}
