package uis.brt.actuator.valvula;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.EventBus;

import uis.brt.actuator.admin.ActuatorAdmin;
import uis.brt.actuator.api.Actuator;
import uis.brt.actuator.api.ActuatorFactory;

public class Factory implements ActuatorFactory {
	
	private String factory = "valvula"; // valor fijo, debido a que estamos en la clase especifica de valvulas
	private String type;
	private String id;
	private EventBus bus;

	public Actuator getActuator(String type, String id, EventBus bus) {
		this.type = type;
		this.id = id;
		this.bus = bus;
		return isCapable();
	}

	public Actuator isCapable() {
		if(type.equals(factory)){
			ActuatorValvula actuator = new ActuatorValvula();
			System.out.println("\n -- Actuator Found = " + actuator);//.getClass().getSimpleName());
			
			actuator.configure(id);
			actuator.state(false); // falta definir si siempre sera false
			return actuator;
		}
		return null;
	}

}