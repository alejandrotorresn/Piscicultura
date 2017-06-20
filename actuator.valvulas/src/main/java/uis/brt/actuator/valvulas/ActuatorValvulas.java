package uis.brt.actuator.valvulas;

import java.util.Properties;
import uis.brt.actuator.api.Actuator;

public class ActuatorValvulas implements Actuator, Runnable {

	private boolean state = false;
	
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void configure(Properties props) {
		// TODO Auto-generated method stub
		
	}

	public boolean state() {
		// TODO Auto-generated method stub
		return state;
	}

	public void open() {
		state = true;
		System.out.println("OOOOOOOO EJECUCION: Las Valvulas han sido abiertas OOOOOOOO");
		
		
	}

	public void close() {
		state = false;
		System.out.println("XXXXXXXX EJECUCION: Las Valvulas fueron cerradas exitosamente  XXXXXXXX");
		
	}

	public void execute(boolean newstate) {
		if(state = newstate)
			open();
		else
			close();
		
	}

}
