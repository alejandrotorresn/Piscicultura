package uis.sensor.oxigeno;

import com.google.common.eventbus.EventBus;

import uis.sensor.api.Sensor;
import uis.sensor.api.SensorFactory;

public class Factory implements SensorFactory {
	
	private String factory = "oxigeno";// definido ahora por estar en factory perteneciente al jar oxigeno
	private String type;
	private String id;
	private EventBus bus;

	public Sensor getSensor(String type, String id, EventBus bus) {
		this.type = type;
		this.id = id;
		this.bus = bus;
		return isCapable();
	}

	public Sensor isCapable() {
		if(type.equals(factory)){
			SensorOximetro sensor = new SensorOximetro();
			System.out.println("\n -- Sensor Found = " + sensor);//.getClass().getSimpleName());
			sensor.configure(id);
			sensor.setBus(bus);			
			sensor.start();
			return sensor;
		}
		return null;
	}

}