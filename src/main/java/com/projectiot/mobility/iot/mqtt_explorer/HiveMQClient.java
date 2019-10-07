package com.projectiot.mobility.iot.mqtt_explorer;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * 
 * @author guptaro1
 *
 */
public class HiveMQClient {

	/**
	 * 
	 */
	public static final String serverURI ="tcp://g05usxn00574.g05.fujitsu.local:1883";
	/**
	 * 
	 */
	public static final String TOPIC= "/ford/mobility/iot/sensors";
	/**
	 * 
	 */
	public static final int QOS = 2;
	
	/**
	 * 
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String args[]) throws InterruptedException {
		HiveMQClient hmc = new HiveMQClient();
		
		hmc.establishConnections();
		System.out.println("Connections established, ready to publish messages");
		Thread.sleep(10000);
		
		
		hmc.publish();
		Thread.sleep(10000);
		System.out.println("Published messages");
		hmc.subscribe();
		Thread.sleep(10000);
		System.out.println("Messages Subscribed messages");
		System.out.println("Disconneting messages");
		Thread.sleep(10000);
		hmc.disconnect();
		System.out.println("Exiting");
		System.exit(0);
		
	}
	
	/**
	 * 
	 */
	private void establishConnections() {
		Map<String,Device> deviceMap =  DeviceDB.getInstance().publisherDeviceData();
		String clientID = null;
		Device device = null;
		if(deviceMap != null) {
			System.out.println(deviceMap.size());
			
			Iterator deviceIterator = deviceMap.entrySet().iterator();
			while(deviceIterator.hasNext()) {
				Map.Entry mapElement = (Map.Entry)deviceIterator.next();
				clientID = (String) mapElement.getKey();
				System.out.println("Starting connection for client ::"+ clientID);
				try {
					MQttClientFactory.getMqttClient(HiveMQClient.serverURI,clientID);
				} catch (MqttException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 
		} 
	}
		
	
	/**
	 * 
	 */
	private void publish() {
		Map<String,Device> deviceMap =  DeviceDB.getInstance().publisherDeviceData();
		String clientID = null;
		Device device = null;
		MqttClient mqttClient = null;
		
		if(deviceMap != null) {
			System.out.println(deviceMap.size());
			Iterator deviceIterator = deviceMap.entrySet().iterator();
			while(deviceIterator.hasNext()) {
				Map.Entry mapElement = (Map.Entry)deviceIterator.next();
				clientID = (String) mapElement.getKey();
				System.out.println("Starting connection for client ::"+ clientID);
				try {
					mqttClient = MQttClientFactory.getMqttClient(HiveMQClient.serverURI,clientID);
					
					
					EngineTempratureSensor sensor = new EngineTempratureSensor(mqttClient, HiveMQClient.TOPIC);
					sensor.call();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 
		} 
		
	}
	
	/**
	 * 
	 */
	private void subscribe() {
		Map<String,Device> deviceMap =  DeviceDB.getInstance().publisherDeviceData();
		String clientID = null;
		Device device = null;
		MqttClient mqttClient = null;
		
		if(deviceMap != null) {
			System.out.println(deviceMap.size());
			Iterator deviceIterator = deviceMap.entrySet().iterator();
			while(deviceIterator.hasNext()) {
				Map.Entry mapElement = (Map.Entry)deviceIterator.next();
				clientID = (String) mapElement.getKey();
				System.out.println("Starting connection for client ::"+ clientID);
				try {
					mqttClient = MQttClientFactory.getMqttClient(HiveMQClient.serverURI,clientID);
					EngineTempratorSubscriber subscriber = new EngineTempratorSubscriber();
					subscriber.subscribe(mqttClient, HiveMQClient.TOPIC);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 
		} 
	}
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	private void disconnect() throws InterruptedException {
		Map<String,Device> deviceMap =  DeviceDB.getInstance().publisherDeviceData();
		String clientID = null;
		Device device = null;
		MqttClient mqttClient = null;
		
		if(deviceMap != null) {
			System.out.println(deviceMap.size());
			Iterator deviceIterator = deviceMap.entrySet().iterator();
			while(deviceIterator.hasNext()) {
				Map.Entry mapElement = (Map.Entry)deviceIterator.next();
				clientID = (String) mapElement.getKey();
				Thread.sleep(1000);
				System.out.println(">> ");
				
				System.out.println("Starting disconnection for client ::"+ clientID);
				try {
					mqttClient = MQttClientFactory.getMqttClient(HiveMQClient.serverURI,clientID);
					
					mqttClient.disconnect();
					System.out.println("Client Disconnected : "+ clientID);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 
		} 
		
	}
	
}
