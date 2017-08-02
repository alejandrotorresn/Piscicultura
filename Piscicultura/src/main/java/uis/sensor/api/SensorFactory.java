package uis.sensor.api;

import java.util.List;

import com.google.common.eventbus.EventBus;


public interface SensorFactory {
	
	public Sensor getSensor(String type, String id, EventBus bus);
	
	public Sensor isCapable();

}
