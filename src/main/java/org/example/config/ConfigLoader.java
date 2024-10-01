package org.example.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties = new Properties();

    public ConfigLoader() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, the application.properties file could not be found.");
                return;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value != null && value.startsWith("${") && value.endsWith("}")) {
            // extraer el nombre de la variable de entorno
            String envVarName = value.substring(2, value.length() - 1);

            // obtener la variable de entorno
            String envValue = System.getenv(envVarName);

            // Devuelve la variable de entorno si existe, si no, el valor literal.
            return envValue != null ? envValue : value;
        }

        return value;
    }

}
