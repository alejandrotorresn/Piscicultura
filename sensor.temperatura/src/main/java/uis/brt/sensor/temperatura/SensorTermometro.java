package uis.brt.sensor.temperatura;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import uis.brt.sensor.api.Message;
import uis.brt.sensor.api.Sensor;

import com.google.common.eventbus.EventBus;

public class SensorTermometro implements Sensor, Runnable {

	private EventBus thisEB;
	private String id="1", type="Termometro";
	//private String pez = "";

	public void setBus(EventBus bus) {
		thisEB=bus;
	}
	
	public void start() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(this, 1, 6, TimeUnit.SECONDS);
	}

	public void stop() {
	}
	
	public void run() {  
		int value = ThreadLocalRandom.current().nextInt(5, 33);
		//System.out.println("*+ se crea valor aleatorio en termometro  " + value);
		Message message = new Message(id, type, value);// momentaneamente quemado en el codigo pero
		// la idea es que se tome esta informacion del archivo config.properties para hacer este metodo mas generico
		thisEB.post(message);
	}

	public void configure(Properties props) {
		System.out.println("Id= " + props.getProperty("id".concat(id)));
		System.out.println("Type= " + props.getProperty("type".concat(id)));
		System.out.println("Min medido= " + props.getProperty("minmedido".concat(id)));
		System.out.println("Max medido= " + props.getProperty("maxmedido".concat(id)));

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
	/*
	public void TipoPez(){
		int v = ThreadLocalRandom.current().nextInt(1, 3);
		switch (v) {
		case 1:
			map.put("tipo","frio");
			break;
			
		case 2:
			map.put("tipo","calido");
			break;
		default:
			break;
		}
	}*/
	
}