package com.projectiot.mobility.iot.mqtt_explorer;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.Callable;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;

/**
 * 
 * @author guptaro1
 *
 */
public class EngineTempratureSensor implements Callable<Void>{

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	IMqttClient client = null;
	String topic = null;
	
	/**
	 * 
	 * @param client
	 * @param topic
	 */
	public EngineTempratureSensor(IMqttClient client,String topic) {
        this.client = client;
        this.topic = topic;
    }
	
	
	/**
	 * 
	 */
	public Void call() throws Exception {
		if ( !client.isConnected()) {
            return null;
        }           
        MqttMessage msg = readEngineTemp();
        msg.setQos(0);
        msg.setRetained(true);
        client.publish(topic,msg);
        System.out.println("Message sent  to client: " + client.getClientId() + " MSG: "+ msg );
        return null;        
	}
	
	/**
	 * 
	 * @return
	 */
	private MqttMessage readEngineTemp() {  
		Random rnd = new Random();
        double temp =  80 + rnd.nextDouble() * 20.0;     
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        JSONObject sensorData = new JSONObject();
        sensorData.put("Temp", String.format("%04.2f",temp));
        sensorData.put("Timestamp", sdf.format(timestamp));
        byte[] payload = sensorData.toJSONString().getBytes();        
        return new MqttMessage(payload);           
    }

}
