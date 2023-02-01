package com.uncodia.openaispigot;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileReader {
    public static String readOpenAIKey() {
        Properties properties = new Properties();

        try {
            // Load the properties file

            properties.load(new FileInputStream("plugins\\OpenAISpigot\\config.properties"));

            // Get the value of a property
            String propertyValue = properties.getProperty("OPENAI_API_KEY");
            return propertyValue;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
