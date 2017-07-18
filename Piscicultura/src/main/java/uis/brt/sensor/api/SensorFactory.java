package uis.brt.sensor.api;

import com.google.common.eventbus.EventBus;

public interface SensorFactory {
	
	public void getSensor(String type, String id, EventBus bus);
	
	public void isCapable();

}
