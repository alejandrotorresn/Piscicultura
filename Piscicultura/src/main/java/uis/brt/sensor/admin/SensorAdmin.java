package uis.brt.sensor.admin;

import java.util.Properties;
import java.util.ServiceLoader;

import uis.brt.sensor.api.Sensor;

import com.google.common.eventbus.EventBus;

public class SensorAdmin {

	private ServiceLoader<Sensor> loader;
	private EventBus bus;
	private Properties props;
	
	
	public SensorAdmin(EventBus bus, Properties props) {
		this.bus = bus;
		this.props = props;
		loader = ServiceLoader.load(Sensor.class);
	}

	public void startSensors() {
		System.out.println("\n\n Bus " + bus);
		System.out.println("Loading sensors ...... \n ");
		for (Sensor sensor : loader) {
			System.out.println("\n -- Sensor Found = " + sensor);
			sensor.configure(props);
			sensor.setBus(bus);			
			sensor.start();
		}
	}

	public void stopSensors() {
		for (Sensor sensor : loader) {
			sensor.stop();
		}
	}

}
