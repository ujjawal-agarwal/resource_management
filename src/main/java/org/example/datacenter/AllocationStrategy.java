package org.example.datacenter;

import org.example.configuration.Configuration;
import org.example.configuration.ConfigurationType;
import org.example.resource.Resource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public enum AllocationStrategy {
    LEAST_PRICE((eligibleResources) -> eligibleResources.sort(Resource::comparePrice)),
    BEST_TIME((eligibleResources) -> eligibleResources.sort(Collections.reverseOrder(Resource::compareConfiguration)));


    private final Consumer<List<Resource>> strategy;
    AllocationStrategy(Consumer<List<Resource>>  strategy) {
        this.strategy = strategy;
    }

    public List<Resource> allocateResources(List<Resource> eligibleResources, Map<ConfigurationType, Configuration> minConfiguration) {
        // todo combination of resources and based on price and execution
        if (eligibleResources.isEmpty())
            return List.of();
        this.strategy.accept(eligibleResources);
        return List.of(eligibleResources.get(0));
    }
}
