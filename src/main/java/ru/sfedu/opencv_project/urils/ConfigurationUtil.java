package ru.sfedu.opencv_project.urils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration utility. Allows to get configuration properties from the
 * default configuration file
 *
 * @author bjmailov
 */
public class ConfigurationUtil {

    private String defaultConfigPath;

    private Properties configuration = new Properties();

    /**
     * Hides default constructor
     */
    public ConfigurationUtil(String configPath) throws IllegalArgumentException {
        if (configPath == null) {
            throw new IllegalArgumentException("configPath cannot be NULL");
        }
        this.defaultConfigPath = configPath;
    }


    private Properties getConfiguration() throws IOException {
        if (configuration.isEmpty()) {
            loadConfiguration();
        }
        return configuration;
    }

    /**
     * Loads configuration from <code>defaultConfigPath</code>
     */
    private void loadConfiguration() {
        try (FileReader reader = new FileReader(defaultConfigPath)) {
            configuration.load(reader);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Gets configuration entry value
     *
     * @param key Entry key
     * @return Entry value by key
     */
    public String readConfig(String key) {
        try {
            return getConfiguration().getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}