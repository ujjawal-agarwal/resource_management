package org.example.resource;

import org.example.configuration.Configuration;
import org.example.configuration.ConfigurationType;

public class ServerInstanceResource extends Resource{

    public final static String NAME = "SERVER_INSTANCE";

    public ServerInstanceResource(String id, double price, int cpu) {
        super(id, price, NAME);
        this.addConfiguration(new Configuration(ConfigurationType.CPU, cpu));
    }


    @Override
    public int compareConfiguration(Resource r) {
        return Integer.compare(this.getConfigurations().get(ConfigurationType.CPU).getUnits(), r.getConfigurations().getOrDefault(ConfigurationType.CPU, Configuration.DEFAULT_CONFIGURATION).getUnits());
    }
}
