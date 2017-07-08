package uis.brt.actuator.valvulas;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import uis.brt.actuator.api.Actuator;

public class ActuatorValvulas implements Actuator{

	private boolean state; // al no iniciarla toma por defecto false
	
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

	public void open(String mensaje) { // abre valvulas
		state = true; // se actualiza el estado del actuator a abierto
		System.out.println(mensaje);
		
		
	}

	public void close(String mensaje) { // cierra valvulas
		state = false; // se actualiza el estado del actuator a cerrado
		System.out.println(mensaje);
		
	}
	
	//recibe las nuevas ordenes enviadas desde las reglas del sistema
	public void execute(boolean neworder, String mensaje) {
		if(state != neworder)
			if(neworder)
				open(mensaje);
			else
				close(mensaje);
		else
			System.out.println("sin cambios en valvulas");
		
	}

}
