package uis.actuator.api;

import com.google.common.eventbus.EventBus;

public interface ActuatorFactory {
	
	public Actuator getActuator(String type, String id, EventBus bus);
	
	public Actuator isCapable();

}
