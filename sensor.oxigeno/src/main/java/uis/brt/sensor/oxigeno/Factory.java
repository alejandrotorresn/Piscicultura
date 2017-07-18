package uis.brt.sensor.oxigeno;

import com.google.common.eventbus.EventBus;

import uis.brt.sensor.api.SensorFactory;

public class Factory implements SensorFactory {
	
	private String factory = "oximetro";
	private String type;
	private String id;
	private EventBus bus;

	public void getSensor(String type, String id, EventBus bus) {
		this.type = type;
		this.id = id;
		this.bus = bus;
		isCapable();
	}

	public void isCapable() {
		if(type.equals(factory)){
			SensorOximetro sensor = new SensorOximetro();
			System.out.println("\n -- Sensor Found = " + sensor);//.getClass().getSimpleName());
			sensor.configure(id);
			sensor.setBus(bus);			
			sensor.start();
			System.out.println("Sensor successfully added");
		}
	}

}