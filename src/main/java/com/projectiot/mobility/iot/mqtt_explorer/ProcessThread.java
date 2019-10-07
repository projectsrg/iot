package com.projectiot.mobility.iot.mqtt_explorer;

import java.util.concurrent.CountDownLatch;

public  class ProcessThread implements Runnable {

    CountDownLatch latch;
    long workDuration;
    String name;

    public ProcessThread(String name, CountDownLatch latch, long duration){
        this.name= name;
        this.latch = latch;
        this.workDuration = duration;
    }


    public void run() {
        try {
            System.out.println(name +" Processing Something for "+ workDuration/1000 + " Seconds");
            Thread.sleep(workDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name+ "completed its works");
        //when task finished.. count down the latch count...

        // basically this is same as calling lock object notify(), and object here is latch
        latch.countDown();
    }
}
