package uis.brt.actuator.valvulas;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import uis.brt.actuator.api.Actuator;

public class ActuatorValvulas implements Actuator, Runnable {

	private boolean state;
	
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void configure(Properties props) {
		Set keys = props.keySet(); // get set-view of keys
		Iterator itr = keys.iterator();

		while (itr.hasNext()) {
			String str = (String) itr.next();
			System.out.println("The actuator of " + str + " is "
					+ props.getProperty(str) + ".");
		}
	}

	public void state(boolean state) {

		this.state = state;
	}

	public void open() {
		state = true;
		System.out.println("OOOOOOOO EJECUCION: Las Valvulas han sido abiertas OOOOOOOO");
		
		
	}

	public void close() {
		state = false;
		System.out.println("XXXXXXXX EJECUCION: Las Valvulas fueron cerradas exitosamente  XXXXXXXX");
		
	}

	public void execute(boolean neworder) {
		if(neworder)
			open();
		else
			close();
		
	}

}
