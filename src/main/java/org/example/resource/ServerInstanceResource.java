package org.example.resource;

import org.example.configuration.Configuration;
import org.example.configuration.ConfigurationType;

public class ServerInstanceResource extends Resource{

    private final static String NAME = "SERVER_INSTANCE";

    public ServerInstanceResource(double price, int cpu) {
        super(price, NAME);
        this.addConfiguration(new Configuration(ConfigurationType.CPU, cpu));
    }
}
