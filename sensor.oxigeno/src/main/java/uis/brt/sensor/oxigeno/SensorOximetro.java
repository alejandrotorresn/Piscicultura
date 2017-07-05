package uis.brt.sensor.oxigeno;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.google.common.eventbus.EventBus;

import uis.brt.sensor.api.Message;
import uis.brt.sensor.api.Sensor;


public class SensorOximetro implements Sensor, Runnable {

	private EventBus thisEB; 
	//HashMap<String, Object> map = new HashMap<String, Object>();

	public void setBus(EventBus bus) {
		thisEB=bus;
	}
	
	public void start() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(this, 2, 6, TimeUnit.SECONDS);
	}

	public void stop() {
	}
	
	public void run() {
		Double value = ThreadLocalRandom.current().nextDouble(3.0, 6.0);
		//System.out.println("*+ se crea valor aleatorio en sensor oxigeno " + value);
		//map.put("Oximetro", value);
		Message message = new Message("2", "Oximetro", value);
		thisEB.post(message);
	}

	public void configure(Properties props) {
		Set keys = props.keySet(); // get set-view of keys
		Iterator itr = keys.iterator();

		while (itr.hasNext()) {
			String str = (String) itr.next();
			System.out.println("The value of " + str + " is "
					+ props.getProperty(str) + ".");
		}
		
	}
}