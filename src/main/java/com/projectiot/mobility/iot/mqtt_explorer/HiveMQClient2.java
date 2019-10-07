package com.projectiot.mobility.iot.mqtt_explorer;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * 
 * @author guptaro1
 *
 */
public class HiveMQClient2 {

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
	 */
	public static final int NUMBER_OF_PUBLISHERS = 10;
	
	/**
	 * 
	 */
	public static final int NUMBER_OF_SUBSCRIBERS = 13;
	
	/**
	 * 
	 */
	public static String publisherClientID = "JH4KA7550NC0338";
	
	/**
	 * 
	 */
	public static String subscriberClientID = "SH4KA7550NC0338";
	/**
	 * 
	 */
	public static final int SLEEP_TIME_MS = 2000;

	/**
	 * 
	 */
	public static final int NUMBER_OF_MSGS_PUBLISHED = 500;
	
	/**
	 * 
	 */
	public static final String PUBLISH_MODE = "SEQ"; //"PAR";
	
	/**
	 * 
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String args[]) throws InterruptedException {
		
		HiveMQClient2 hmc = new HiveMQClient2();
		hmc.establishConnections();
		System.out.println("Connections established, ready to publish messages");
		Thread.sleep(HiveMQClient2.SLEEP_TIME_MS);
		
		hmc.subscribe();
		Thread.sleep(HiveMQClient2.SLEEP_TIME_MS);
		System.out.println("Messages Subscribed ");
		
		hmc.publish();
		Thread.sleep(HiveMQClient2.SLEEP_TIME_MS);
		System.out.println("Published messages");
		
		System.out.println("Disconneting Connections waiting for entire processing to finish");
		Thread.sleep(55000);
		hmc.disconnect();
		System.out.println("Exiting");
		System.exit(0);
	}
	
	/**
	 * 
	 */
	private void establishConnections() {
	
		System.out.println("Number of Publishers ::>> " + HiveMQClient2.NUMBER_OF_PUBLISHERS);
		System.out.println("Number of Subsribers ::>> " +HiveMQClient2.NUMBER_OF_SUBSCRIBERS);
		
		for(int i=0;i<HiveMQClient2.NUMBER_OF_PUBLISHERS;i++) {
			
			try {
				MQttClientFactory.getMqttClient(HiveMQClient2.serverURI,publisherClientID+"-"+i);
			} catch (MqttException e) {
				e.printStackTrace();
			}
		
		}
		
		for(int i=0;i<HiveMQClient2.NUMBER_OF_SUBSCRIBERS;i++) {
			
			try {
				MQttClientFactory.getMqttClient(HiveMQClient2.serverURI,subscriberClientID+"-"+i);
			} catch (MqttException e) {
				e.printStackTrace();
			}
		
		}
	}
		
	
	/**
	 * 
	 */
	private void publish() {
		MqttClient mqttClient = null;
		for(int i=0;i<HiveMQClient2.NUMBER_OF_PUBLISHERS;i++) {
				System.out.println("Starting connection for client ::"+ publisherClientID+"-"+i);
				try {
					mqttClient = MQttClientFactory.getMqttClient(HiveMQClient2.serverURI, publisherClientID+"-"+i);
					for(int j=0;j< NUMBER_OF_MSGS_PUBLISHED;j++) {
						EngineTempratureSensor sensor = new EngineTempratureSensor(mqttClient, HiveMQClient2.TOPIC);
						sensor.call();
						//Thread.sleep(1000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
	
	/**
	 * 
	 */
	private void subscribe() {
		MqttClient mqttClient = null;
		for(int i=0;i<HiveMQClient2.NUMBER_OF_SUBSCRIBERS;i++) {
				System.out.println("Starting connection for client ::"+ subscriberClientID+"-"+i);
				try {
					mqttClient = MQttClientFactory.getMqttClient(HiveMQClient2.serverURI,subscriberClientID+"-"+i);
					EngineTempratorSubscriber subscriber = new EngineTempratorSubscriber();
					subscriber.subscribe(mqttClient, HiveMQClient2.TOPIC);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	private void disconnect() throws InterruptedException {
		
		
		for(int i=0;i<HiveMQClient2.NUMBER_OF_PUBLISHERS;i++) {
			
			try {
				MQttClientFactory.getMqttClient(HiveMQClient2.serverURI,publisherClientID+"-"+i);
				System.out.println("Successfully Disconneted publisher ::>> "+publisherClientID+"-"+i);
			} catch (MqttException e) {
				e.printStackTrace();
			}
			Thread.sleep(HiveMQClient2.SLEEP_TIME_MS);
		}
		
		for(int i=0;i<HiveMQClient2.NUMBER_OF_SUBSCRIBERS;i++) {
			
			try {
				MQttClientFactory.getMqttClient(HiveMQClient2.serverURI,subscriberClientID+"-"+i);
				System.out.println("Successfully Disconneted subscriber ::>> "+subscriberClientID+"-"+i);
			} catch (MqttException e) {
				e.printStackTrace();
			}
			Thread.sleep(HiveMQClient2.SLEEP_TIME_MS);
		
		} 
		
	}
	
}
