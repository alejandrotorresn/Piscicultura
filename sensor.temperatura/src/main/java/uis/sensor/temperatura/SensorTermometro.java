package uis.sensor.temperatura;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import upm_bmp280.BME280;

import com.google.common.eventbus.EventBus;

import uis.sensor.api.Message;
import uis.sensor.api.Sensor;

public class SensorTermometro implements Sensor, Runnable {

	private EventBus thisEB;
	private String id;
	private String type = "temperatura";// definido desde ahora por estar en la clase SensorTermometro
	private float value = -1.0f;

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
		if((System.getProperty("os.name")).startsWith("Windows")){
			value = (float) ThreadLocalRandom.current().nextDouble(5, 33);
		}
		else{
			if(id.equals("5")){
				BME280 sensor = new BME280();//sensor real
				sensor.update();
				value = sensor.getTemperature();
			}
			else
				value = (float) ThreadLocalRandom.current().nextDouble(5, 33);
			}

		Message message = new Message(id, type, value);// momentaneamente quemado en el codigo pero
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