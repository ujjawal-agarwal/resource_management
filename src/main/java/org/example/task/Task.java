package org.example.task;

import org.example.configuration.Configuration;
import org.example.configuration.ConfigurationType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

enum TaskStatus {
    RUNNING,
    COMPLETED,
    PENDING;
}

public abstract class Task {

    private final String id = UUID.randomUUID().toString();

    private Long startTime;
    private Long endTime;

    private final String type;


    private final Map<ConfigurationType, Configuration> minimumRequirement = new HashMap<>();

    private TaskStatus status = TaskStatus.PENDING;

    public Task(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
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

    public void setStatus(TaskStatus status) {
        this.status = status;
        if(status.equals(TaskStatus.RUNNING)) {
            startTime = System.currentTimeMillis();
        }
        if(status.equals(TaskStatus.COMPLETED)) {
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


}
