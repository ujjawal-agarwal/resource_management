package org.example;

import org.example.configuration.Configuration;
import org.example.configuration.ConfigurationType;
import org.example.resource.Resource;
import org.example.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class DataCenter {
    private final List<Resource> resources = new ArrayList<>();
    private final PriorityQueue<Task> taskQueue = new PriorityQueue<>();

    //task id - resource id
    private final Map<String, String> taskResourceMapping = new HashMap<>();

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public void deleteResource(Resource resource) {
        resources.remove(resource);
    }

    public List<Resource> viewAvailableResources(String resourceType, Map<ConfigurationType, Configuration> configurationMap) {
        return resources.
                stream().
                filter(r -> r.isAvailable() && r.getType().equals(resourceType) && r.getConfigurations().equals(configurationMap)).
                collect(Collectors.toList());
    }

    public List<Resource> viewAllocatedResources(String resourceType) {
        return resources.
                stream().
                filter(r -> !r.isAvailable() && r.getType().equals(resourceType)).
                collect(Collectors.toList());
    }

    public void submitTask(Task task) {

        List<Resource> eligibleResources = getEligibleResources(task.getType(), task.getMinimumRequirement());

        if (!eligibleResources.isEmpty()) {
            Resource allocatedResource = eligibleResources.get(0);

        } else {
            System.out.println("No eligible resources available for task: " + task.getId());
            taskQueue.offer(task);
        }

    }

    private List<Resource> getEligibleResources(String taskType, Map<ConfigurationType, Configuration> minConfiguration) {
        List<Resource> re = resources.
                stream().
                filter(r -> r.isAvailable() && r.getType().equals(taskType)).
                collect(Collectors.toList());

        List<Resource> eligibleResources = new ArrayList<>();
        for (Resource r: re
             ) {
            for(ConfigurationType type: minConfiguration.keySet()) {
                if (minConfiguration.get(type).getUnits() > r.getConfigurations().get(type).getUnits()) {
                    continue;
                }
            }
            eligibleResources.add(r);

        }

        return eligibleResources;
    }

    public void checkTaskStatus(Task task) {
        System.out.println("task status: " + task.getStatus());
        System.out.println("resource allocated with id : " + taskResourceMapping.get(task.getId()));
        //null check
        System.out.println("start time: " + task.getStartTime());
        System.out.println("end time: " + task.getEndTime());

    }

}