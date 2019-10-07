package com.projectiot.mobility.iot.mqtt_explorer;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

	public static void main(String[] args) {
		
	    // Parent thread creating a latch object
	    CountDownLatch latch = new CountDownLatch(3);
	    new Thread(new ProcessThread("Worker1",latch, 2000)).start(); // time in millis.. 2 secs
	    new Thread(new ProcessThread("Worker2",latch, 6000)).start();//6 secs
	    new Thread(new ProcessThread("Worker3",latch, 4000)).start();//4 secs
	    System.out.println("waiting for Children processes to complete....");
	    try {
	        //current thread will get notified if all chidren's are done 
	        // and thread will resume from wait() mode.
	        latch.await();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    System.out.println("All Process Completed....");

	    System.out.println("Parent Thread Resuming work....");
     }
	  
}
