package uis.sensor.oxigeno;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.google.common.eventbus.EventBus;

import uis.sensor.api.Message;
import uis.sensor.api.Sensor;

public class SensorOximetro implements Sensor, Runnable {

	private EventBus thisEB; 
	private String id;
	private String type = "oxigeno"; // definido desde ahora por estar en la clase SensorOximetro
	private Double value = -1.0;
	
	public void setBus(EventBus bus) {
		thisEB=bus;
	}
	
	public void start() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(this, 181, 6, TimeUnit.SECONDS);
	}

	public void stop() {
	}
	
	public void run() {
		value = ThreadLocalRandom.current().nextDouble(3.0, 6.0);// valor aleatorio
		Message message = new Message(id, type, value);
		thisEB.post(message);
	}

	public void configure(String id){//(Properties props) {

		this.id = id;
		System.out.println("Id= " + id);
		System.out.println("Type= " + type);
/*		System.out.println("Id= " + props.getProperty("id".concat(id)));
		System.out.println("Type= " + props.getProperty("type".concat(id)));
		System.out.println("Min medido= " + props.getProperty("minmedido".concat(id)));
		System.out.println("Max medido= " + props.getProperty("maxmedido".concat(id)));
*/		
/*	metodo anterior que hacia que se imprimieran en pantalla todos los valores de la llaves guardadas
		Set keys = props.keySet(); // get set-view of keys
		Iterator itr = keys.iterator();

		while (itr.hasNext()) {
			String str = (String) itr.next();
			System.out.println("The value of " + str + " is "
					+ props.getProperty(str) + ".");
		}
*/
	}
}