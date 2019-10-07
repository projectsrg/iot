package com.projectiot.mobility.iot.mqtt_explorer;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author guptaro1
 *
 */
public class DeviceDB {

	/**
	 * 
	 */
	private static Map<String, Device> publishDeviceMap = null;// new HashMap<String, Device>();
	private static Map<String, Device> subscribeDeviceMap = null; //new HashMap<String, Device>();
	public static DeviceDB db = null;
	
	
	/**
	 * 
	 * @return
	 */
	public static synchronized DeviceDB getInstance() {
		if(db == null) {
			db = new DeviceDB();
		}
		return db;
	}
	
	/**
	 * 
	 */
	private DeviceDB() {
		
		publishDeviceMap = new HashMap<String,Device>();
		subscribeDeviceMap = new HashMap<String,Device>();
		
		Device device = null;
		
		for(int i = 0;i<=DeviceData.PDEVICES.length-1;i++) {
			device = new Device();
			device.setVin(DeviceData.PDEVICES[i]);
			device.setUser(DeviceData.USERS[i]);
			device.setPassword(DeviceData.PASSWORDS[i]);
			device.setDeviceType("Alert");
			publishDeviceMap.put(DeviceData.PDEVICES[i], device);
		}
		
		
		for(int i = 0;i<=DeviceData.SDEVICES.length-1;i++) {
			device = new Device();
			device.setVin(DeviceData.SDEVICES[i]);
			device.setUser(DeviceData.SUSERS[i]);
			device.setPassword(DeviceData.SPASSWORDS[i]);
			device.setDeviceType("Sensor");
			subscribeDeviceMap.put(DeviceData.SDEVICES[i], device);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String,Device> publisherDeviceData() {
		return publishDeviceMap;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String,Device> subscriberDeviceData() {
		return subscribeDeviceMap;
	}

}
