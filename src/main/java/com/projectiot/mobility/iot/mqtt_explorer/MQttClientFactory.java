package com.projectiot.mobility.iot.mqtt_explorer;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * 
 * @author guptaro1
 *
 */
public class MQttClientFactory {
	
	/**
	 * 
	 */
	private static HashMap<String, MqttClient> clientMap = new HashMap<String, MqttClient>();
	/**
	 * 
	 */
	private static final ReentrantLock lock = new ReentrantLock();

	/**
	 * 
	 * @param serverURI
	 * @param clientId
	 * @return
	 * @throws MqttException
	 */
	public static MqttClient getMqttClient(String serverURI, String clientId) 
		            throws MqttException{
		 String clientKey=serverURI.concat(clientId);
		 if(clientMap.get(clientKey)==null){
		     lock.lock();
		         if(clientMap.get(clientKey)==null){
		        	 MqttClientPersistence persistence = new MemoryPersistence();
		             MqttClient client = new MqttClient(serverURI, clientId, persistence);
		             MqttConnectOptions connOpts = new MqttConnectOptions();
		             IMMqttCallBack callback = new IMMqttCallBack(client);
		             client.setCallback(callback);
		             connOpts.setCleanSession(true);
		             client.connect(connOpts);
		             System.out.println("Connection Established for Client :: "+ clientId);
		             clientMap.put(clientKey, client);
		         }
		      lock.unlock();
		 }
		  return clientMap.get(clientKey);
	}
	
	
	 /**
     * client close  
     * @param clientKey
     * @throws MqttException
     */
    public static void close(
    		String serverURI, String clientId) 
            throws MqttException{
    	String clientKey=serverURI.concat(clientId);
        if(clientMap.get(clientKey)!=null){
            lock.lock();
            if(clientMap.get(clientKey)!=null){
                clientMap.get(clientKey).disconnect();
                clientMap.remove(clientKey);
            }
            lock.unlock();
        }
    }
    
}
