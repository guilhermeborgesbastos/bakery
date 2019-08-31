package com.gbastos.bakery.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

  public static String retrievePropertyValue(String propertyKey, String fileName) {

    String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String appConfigPath = rootPath + fileName;

    Properties appProps = new Properties();

    try {
      appProps.load(new FileInputStream(appConfigPath));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return appProps.getProperty(propertyKey);
  }
}
