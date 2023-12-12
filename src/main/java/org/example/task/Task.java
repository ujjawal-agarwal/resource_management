package org.example.task;

import org.example.configuration.Configuration;
import org.example.configuration.ConfigurationType;

import java.util.Map;

public class Task implements Runnable{

    private final String id;

    private final Runnable work;

    private Long startTime;
    private Long endTime;

    private final Map<ConfigurationType, Configuration> minimumRequirement;

    private TaskStatus status = TaskStatus.PENDING;

    public Task(String id, Runnable work, Map<ConfigurationType, Configuration> configurationMap) {
        this.id = id;
        this.work = work;
        this.minimumRequirement = configurationMap;
    }

    public Map<ConfigurationType, Configuration> getMinimumRequirement() {
        return minimumRequirement;
    }

    public void addRequirement(Configuration configuration) {
        minimumRequirement.put(configuration.getType(), configuration);
    }

    public void deleteRequirement(ConfigurationType configurationType) {
        minimumRequirement.remove(configurationType);
    }

    public String getId() {
        return id;
    }

    private void setStatus(TaskStatus status) {
        this.status = status;
        if(status.equals(TaskStatus.RUNNING)) {
            startTime = System.currentTimeMillis();
        }
        if(status.equals(TaskStatus.COMPLETED)) {
            System.out.println("Task " + id + " completed");
            endTime = System.currentTimeMillis();
        }
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public void run() {
        try {
            this.setStatus(TaskStatus.RUNNING);
            this.work.run();
        } finally {
            this.setStatus(TaskStatus.COMPLETED);
        }
    }


}
