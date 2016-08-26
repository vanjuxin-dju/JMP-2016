package com.epam.example.carracing;

import org.apache.log4j.Logger;

public class Car implements Runnable {
    
    private static final long MAX_DISTANCE = 10000;

    Logger log = Logger.getLogger(getClass());
    
    private long friction; 
    private long distance; 
    
    private String name;
    
    public Car(String name, long friction) {
        this.name = name;
        this.friction = 100;
    }

    @Override
    public void run() {
        try {
            while (distance < MAX_DISTANCE) {
                Thread.sleep(friction);
                distance += 100;
                log.info(name + " " + distance);
            }
        } catch (InterruptedException e) {
            log.error(e);
        }
    }

}