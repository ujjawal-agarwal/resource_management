package org.example.task;

import org.example.configuration.Configuration;
import org.example.configuration.ConfigurationType;

public class ServerInstanceTask extends Task{

    public ServerInstanceTask(String type, int cpu) {
        super(type);
        this.addRequirement(new Configuration(ConfigurationType.CPU, cpu));
    }
}
