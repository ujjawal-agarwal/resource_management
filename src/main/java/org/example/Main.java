package org.example;

import org.example.configuration.Configuration;
import org.example.configuration.ConfigurationType;
import org.example.datacenter.AllocationStrategy;
import org.example.datacenter.DataCenter;
import org.example.resource.Resource;
import org.example.resource.ServerInstanceResource;
import org.example.task.Task;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static Map<ConfigurationType, Configuration> getCPUConfig(int units) {
        Map<ConfigurationType, Configuration> configurationMap = new HashMap<>();
        configurationMap.put(ConfigurationType.CPU, new Configuration(ConfigurationType.CPU, units));
        return configurationMap;
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        Resource r1 = new ServerInstanceResource("1", 5, 5);
        Resource r2 = new ServerInstanceResource("2", 4, 4);
        Resource r3 = new ServerInstanceResource("3", 3, 3);
        Resource r4 = new ServerInstanceResource("4", 2, 2);





        Task t1 = new Task("1", () -> {
            try {
                Thread.currentThread().sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, getCPUConfig(5));
        Task t2 = new Task("2", () -> {
            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, getCPUConfig(3));
        Task t3 = new Task("3", () -> {
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },getCPUConfig(3));




        DataCenter dataCenter = new DataCenter();

        dataCenter.addResource(r1);
        dataCenter.addResource(r2);

        dataCenter.submitTask(t1, AllocationStrategy.LEAST_PRICE);
        dataCenter.submitTask(t2, AllocationStrategy.LEAST_PRICE);
        dataCenter.submitTask(t3, AllocationStrategy.LEAST_PRICE);

        dataCenter.start();


    }
}