package uis.sensor.api;

import java.util.Properties;
import com.google.common.eventbus.EventBus;

/**  Interface que deben implementar los futuros sensores 
 *   que se integraran al sistema embebido @author
 */
public interface Sensor {

	public void configure(String id);
	
	//Modifica el bus de comunicaciones  @param bus
	public void setBus(EventBus bus); 

	// Inicia el servicio del sensor
	public void start(); 

	// Detiene el servicio del sensor
	public void stop(); 
	
	public void run(); 
}
