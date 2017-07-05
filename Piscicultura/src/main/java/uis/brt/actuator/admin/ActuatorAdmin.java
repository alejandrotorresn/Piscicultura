package uis.brt.actuator.admin;

import java.util.Properties;
import java.util.ServiceLoader;

import uis.brt.actuator.api.Actuator;

import com.google.common.eventbus.EventBus;

public class ActuatorAdmin {

	private ServiceLoader<Actuator> loader;
	private EventBus bus;
	private Properties props;
	
	
	public ActuatorAdmin(EventBus bus, Properties props) {
		this.bus = bus;
		this.props = props;
		loader = ServiceLoader.load(Actuator.class);
	}

	public void startSensors() {
		System.out.println("\n\n Bus " + bus);
		System.out.println("Loading actuator ...... \n ");
		for (Actuator actuator : loader) {
			System.out.println("\n -- Actuator Found = " + actuator);
			actuator.configure(props);
			actuator.state(false);
			//actuator.setBus(bus);			
			//actuator.start();
		}
	}

	public void stopSensors() {
		for (Actuator actuator : loader) {
			//actuator.stop();
		}
	}

}
