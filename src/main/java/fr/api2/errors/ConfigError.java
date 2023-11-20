package fr.api2.errors;

/**
 * Erreur personnalisée pour la configuration
 * voir classe config.Config()
 */
public class ConfigError extends Exception {
    public ConfigError(String message) {
        super(message);
    }
    
}
