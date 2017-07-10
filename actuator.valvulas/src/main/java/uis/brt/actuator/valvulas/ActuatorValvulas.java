package uis.brt.actuator.valvulas;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import uis.brt.actuator.api.Actuator;

public class ActuatorValvulas implements Actuator{

	private boolean state; // al no iniciarla toma por defecto false
	// momentaneamente variables id y type quemadas en el codigo pero
	// la idea es que se tome esta informacion del archivo config.properties para hacer este metodo mas generico
	private String id = "3", type = "Valvula";
	
	// gets and setters
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	

	public void configure(Properties props) {
		System.out.println("Id= " + props.getProperty("id".concat(id)));
		System.out.println("Type= " + props.getProperty("type".concat(id)));
		System.out.println("State= " + props.getProperty("state".concat(id)));
		
/*	metodo anterior que hacia que se imprimieran en pantalla todos los valores de la llaves guardadas
		Set keys = props.keySet(); // get set-view of keys
		Iterator itr = keys.iterator();

		while (itr.hasNext()) {
			String str = (String) itr.next();
			System.out.println("The actuator of " + str + " is "
					+ props.getProperty(str) + ".");
		}
*/ 
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
