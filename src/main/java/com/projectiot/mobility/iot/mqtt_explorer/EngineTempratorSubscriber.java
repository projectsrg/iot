package com.projectiot.mobility.iot.mqtt_explorer;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class EngineTempratorSubscriber {
	
	// private static Logger log = LoggerFactory.setLogger(EngineTempratorSubscriber.class);


	String topic        = "/mytopic";
    String content      = "Message from MqttPublishSample";
    int qos             = 2;
    String broker       = "tcp://localhost:1883";
    String clientId     = "client1234";// UUID.randomUUID().toString();
    MemoryPersistence persistence = new MemoryPersistence();
    
    @Deprecated
    public void printMessage() {
	    try{
	    	
	        MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
	        MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setCleanSession(true);
	        connOpts.setConnectionTimeout(10);
	        System.out.println("Connecting to broker: "+broker);
	        sampleClient.connect(connOpts);
	        IMMqttCallBack callback = new IMMqttCallBack(sampleClient);
			sampleClient.setCallback(callback);
	        
	        sampleClient.subscribe(topic);
	        CountDownLatch receivedSignal = new CountDownLatch(10);
	        
	        sampleClient.subscribe(topic, (t, msg) -> {
	            byte[] payload = msg.getPayload();
	            System.out.println("[I82] Message received: topic={}, payload={} "+ topic +" " +  new String(payload));
	            receivedSignal.countDown();
	        });
	        
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	
	}
    
    /**
     * 
     * @param mqttClient
     * @param topic
     */
    public void subscribe(MqttClient mqttClient,String topic) {
    	try {
			mqttClient.subscribe(topic);
	        CountDownLatch receivedSignal = new CountDownLatch(10);
			mqttClient.subscribe(topic, (t, msg) -> {
				    byte[] payload = msg.getPayload();
				    System.out.println("Message received: topic: "+ topic +" , payload :  " +  new String(payload));
				    receivedSignal.countDown();
				});
			} catch (MqttException e) {
			
				e.printStackTrace();
			}
    	
    	
    }
    
    
    
    
}
