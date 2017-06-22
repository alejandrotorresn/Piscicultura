package uis.brt.sensor.temperatura;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	HashMap<String, Object> map = new HashMap<String, Object>();
	//Properties prop;
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
	
	public void run() { //System.out.println("tamaño del mapa " + map.size());
		int value = ThreadLocalRandom.current().nextInt(5, 33);
		//System.out.println("*+ se crea valor aleatorio en termometro  " + value);
		map.put("Termometro", value);
		Message message = new Message("111", "Alevines", "1", map );
		thisEB.post(message);
	}

	public void configure(Properties props) {
		//load();
		Set keys = props.keySet(); // get set-view of keys
		Iterator itr = keys.iterator();

		while (itr.hasNext()) {
			String str = (String) itr.next();
			System.out.println("The value of " + str + " is "
					+ props.getProperty(str) + ".");
		}
		
		
	}
	
	public void load() {
		try {
			List<InputStream> streams = loadResources("termometro.properties", null);
			
			for (InputStream inputStream : streams) {

				System.out.println("encontrados " + inputStream);
				
				
				/*				
				Iterator i = streams.iterator();
				
				while(i.hasNext()){
					 Object x = i.next();
					System.out.println("objetos " + x);
				}*/
				//load(inputStream);// carga las propiedades de todos los config.properties
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<InputStream> loadResources(String name,
			ClassLoader classLoader) throws IOException {
		final List<InputStream> list = new ArrayList<InputStream>();
		final Enumeration<URL> systemResources = (classLoader == null ? ClassLoader
				.getSystemClassLoader() : classLoader).getResources(name);
		while (systemResources.hasMoreElements()) {
			URL url = systemResources.nextElement();
			System.out.println(url);
			list.add(url.openStream());
			//System.out.println("esta en loadResources " + list.toString());
		}
		//for (InputStream x : list) {System.out.println("lista " + x);}
		return list;
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