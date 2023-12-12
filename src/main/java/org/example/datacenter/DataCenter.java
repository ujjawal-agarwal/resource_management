package org.example.datacenter;

import org.example.configuration.Configuration;
import org.example.configuration.ConfigurationType;
import org.example.resource.Resource;
import org.example.resource.ServerInstanceResource;
import org.example.task.Task;
import org.example.task.TaskStatus;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class DataCenter {
    private final List<Resource> resources = new ArrayList<>();
    private final BlockingQueue<SimpleEntry<Task, AllocationStrategy>> taskQueue = new LinkedBlockingQueue<>();

    private final Thread thread;

    //task id - resource id
    private final Map<String, String> taskResourceMapping = new HashMap<>();

    public DataCenter() {
        this.thread = new Thread(this::processTasks);
    }

    public void start() {
        this.thread.start();
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

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

    public List<Resource> viewEligibleResources(String resourceType, Map<ConfigurationType, Configuration> configurationMap) {
        return resources.
                stream().
                filter(r -> r.isAvailable() && r.getType().equals(resourceType) && r.isEligible(configurationMap)).
                collect(Collectors.toList());
    }

    public List<Resource> viewAllocatedResources(String resourceType) {
        return resources.
                stream().
                filter(r -> !r.isAvailable() && r.getType().equals(resourceType)).
                collect(Collectors.toList());
    }

    public void submitTask(Task task, AllocationStrategy strategy) {
        taskQueue.offer(new SimpleEntry<>(task, strategy));
    }

    public void checkTaskStatus(Task task) {
        TaskStatus status = task.getStatus();
        System.out.println("task: " + task.getId() + " " + status);

        if (status.equals(TaskStatus.RUNNING)) {
//            System.out.println("resource allocated with id : " + taskResourceMapping.get(task.getId()));
            System.out.println("start time: " + task.getStartTime());
        }
        if (status.equals(TaskStatus.COMPLETED)) {
            System.out.println("start time: " + task.getStartTime());
            System.out.println("end time: " + task.getEndTime());
        }
    }

    private void processTasks() {
        while (true) {
            SimpleEntry<Task, AllocationStrategy> entry = taskQueue.peek();
            if (entry == null)
                continue;
            Task task = entry.getKey();
            AllocationStrategy strategy = entry.getValue();

            List<Resource> allocatedResources = strategy.allocateResources(viewEligibleResources(ServerInstanceResource.NAME, task.getMinimumRequirement()), task.getMinimumRequirement());
            if (allocatedResources.isEmpty())
                continue;
            taskQueue.remove();
            allocatedResources.forEach(r -> r.assignTask(task));
        }
    }

}