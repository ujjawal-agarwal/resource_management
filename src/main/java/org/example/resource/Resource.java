package org.example.resource;

import org.example.configuration.Configuration;
import org.example.configuration.ConfigurationType;
import org.example.task.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Resource {
    double price;

    private final String id = UUID.randomUUID().toString();
    private final AtomicBoolean isAvailable = new AtomicBoolean(true);

    private final Map<ConfigurationType, Configuration> configurations = new HashMap<>();


    public final String type;

    public Resource(double price, String resourceType) {
        this.price = price;
        this.type = resourceType;
    }

    public String getId() {
        return id;
    }

    public void executeTask(Task task) {
        //task.run()
    }

    public String getType() {
        return type;
    }

    public void addConfiguration(Configuration configuration) {
        configurations.put(configuration.getType(), configuration);
    }

    public void deleteConfiguration(ConfigurationType configurationType) {
        configurations.put(configurationType, null);
    }

    public boolean isAvailable() {
        return isAvailable.get();
    }

    public void setAvailable(boolean available) {
        isAvailable.set(available);
    }

    public Map<ConfigurationType, Configuration> getConfigurations() {
        return configurations;
    }
}
