package com.projectiot.mobility.iot.mqtt_explorer;

import java.util.UUID;
import java.util.concurrent.Callable;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Hello world!
 *
 */
public class App {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String topic = "/mytopic";
		String content = "Message from MqttPublishSample";
		int qos = 2;
		String broker = "tcp://g05usxn00574.g05.fujitsu.local:1883";
		String clientId = "client123";//UUID.randomUUID().toString();
		MemoryPersistence persistence = new MemoryPersistence();
		try {
				MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
				MqttConnectOptions connOpts = new MqttConnectOptions();
				connOpts.setCleanSession(true);
				connOpts.setConnectionTimeout(10);
				System.out.println("Connecting to broker: " + broker);
				sampleClient.connect(connOpts);
				IMMqttCallBack callback = new IMMqttCallBack(sampleClient);
				sampleClient.setCallback(callback);
				EngineTempratureSensor sensor = new EngineTempratureSensor(sampleClient, topic);
				sensor.call();
				EngineTempratorSubscriber subscriber = new EngineTempratorSubscriber();
				subscriber.printMessage();
				sampleClient.disconnect();
				System.out.println("Disconnected");
				System.exit(0);

		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
