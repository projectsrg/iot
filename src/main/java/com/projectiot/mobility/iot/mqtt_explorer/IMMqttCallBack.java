package com.projectiot.mobility.iot.mqtt_explorer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class IMMqttCallBack implements MqttCallback {
    
    private MqttClient mqttClient;
    
    //private StringRedisTemplate redisTemplate;
    
    /**
     * @param mqttClient
     * @param redisTemplate 
     */
    public IMMqttCallBack(MqttClient mqttClient) {
        super();
		// this.mqttClient = mqttClient;
		// this.redisTemplate = redisTemplate;
    }

    /* (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
     */
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost on instance \""  
                + "\" with cause \"" + cause.getMessage() + "\" Reason code "   
                + ((MqttException)cause).getReasonCode() + "\" Cause \""   
                + ((MqttException)cause).getCause() +  "\"");      
            cause.printStackTrace();  

    }

    /* (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
		/*
		 * redisTemplate.opsForList().leftPush(
		 * MqttClientFactory.redisArrivedPrefix.concat(mqttClient.getClientId()),
		 * topic.concat(";").concat(message.toString()));
		 * TopicsHandlesMap.getHandle(topic).handle(mqttClient,topic, message);
		 */
        System.out.println("Message Arrived  for topic::>> " + topic +" message: "+ message.getPayload());      
    }

    /* (non-Javadoc)
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {  
            System.out.println("Delivery token \"" + token.hashCode()  
                + "\" received by instance \""  + "\"");  
          } catch (Exception e) {  
            e.printStackTrace();  
          }  

    }
    
}
