package uis.brt.actuator.mensaje;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import uis.brt.actuator.api.Actuator;

public class ActuatorMensaje implements Actuator{

	private String type = "mensaje"; // valor fijo, debido a que estamos en la clase especifica ActuatorMensaje
	private boolean state;
	private String id;
	
	// gets and setters
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	

	public void configure(String id) {//(Properties props) {

		this.id = id;
		System.out.println("Id= " + id);
		System.out.println("Type= " + type);
/*		System.out.println("Id= " + props.getProperty("id".concat(id)));
		System.out.println("Type= " + props.getProperty("type".concat(id)));
		System.out.println("State= " + props.getProperty("state".concat(id)));
*/		
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
		//if(state != neworder) aca no es necesario verificar debidoa que es una alerta que debe mostrarse 
			if(neworder)
				open(mensaje);
			else
				close(mensaje);
		}

}