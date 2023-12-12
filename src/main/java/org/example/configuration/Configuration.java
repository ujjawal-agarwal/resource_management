package org.example.configuration;

public class Configuration {

    public final static Configuration DEFAULT_CONFIGURATION = new Configuration(null, 0);

    private final ConfigurationType type;

    private int units = 0;

    public Configuration(ConfigurationType type, int units) {
        this.type = type;
        this.units = units;
    }

    public ConfigurationType getType() {
        return type;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
