package org.example.resource;

import org.example.configuration.Configuration;
import org.example.configuration.ConfigurationType;
import org.example.task.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Resource {
    private double price;

    private final String id;
    private final AtomicBoolean isAvailable = new AtomicBoolean(true);

    private final Map<ConfigurationType, Configuration> configurations = new HashMap<>();


    public final String type;

    public Resource(String id, double price, String resourceType) {
        this.id = id;
        this.price = price;
        this.type = resourceType;
    }

    public String getId() {
        return id;
    }

    public CompletableFuture<Void> assignTask(Task task) {
        this.setAvailable(false);
        CompletableFuture<Void> future = new CompletableFuture<>();
        new Thread(
                () -> {
                    try {
                        System.out.println("Resource: " + id + " executing... " + "task: " + task.getId());
                        task.run();
                        this.setAvailable(true);
                        future.complete(null);
                    } catch (Exception e) {
                        future.completeExceptionally(e);
                    }
                }
        ).start();
        return future;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    private void setAvailable(boolean available) {
//        System.out.println("Setting resource " + id + " flag: " + available);
        if (available == isAvailable.compareAndExchange(!available, available))
            throw new RuntimeException("Resource: " + id + " flag already " + available);
    }

    public Map<ConfigurationType, Configuration> getConfigurations() {
        return configurations;
    }

    public boolean isEligible(Map<ConfigurationType, Configuration> configurations) {
        for (ConfigurationType type:
             configurations.keySet()) {
            Configuration configuration = this.getConfigurations().get(type);
            if (configuration == null || configuration.getUnits() < configurations.get(type).getUnits())
                return false;
        }
        return true;
    }

    public int comparePrice(Resource r) {
        return Double.compare(this.getPrice(), r.getPrice());
    }

    public abstract int compareConfiguration(Resource r);

}
