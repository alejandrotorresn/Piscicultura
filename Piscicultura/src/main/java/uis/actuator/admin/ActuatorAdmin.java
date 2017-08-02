package uis.actuator.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;

import com.google.common.eventbus.EventBus;

import uis.actuator.api.Actuator;
import uis.actuator.api.ActuatorFactory;

public class ActuatorAdmin {

	private ServiceLoader<ActuatorFactory> loader;
//	private ServiceLoader<Actuator> listloader;
	private EventBus bus;
	private Properties props;
	List<Actuator> actuator = new ArrayList<Actuator>();
	
/*	// getters and setters
	public List<Actuator> getActuator() {
		return actuator;
	}
	public void setActuator(Actuator actuator) {
		System.out.println("agregada instancia " + actuator);
		this.actuator.add(actuator);
	}
*/
	
	public ActuatorAdmin() {
	}
	
	public ActuatorAdmin(EventBus bus, Properties props) {
		this.bus = bus;
		this.props = props;
		loader = ServiceLoader.load(ActuatorFactory.class);
//		listloader = ServiceLoader.load(Actuator.class);
		LoadActuators();
	}
	
	public void LoadActuators(){
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {//Apertura del fichero y creacion de BufferedReader para poder hacer una lectura comoda
			//(disponer del metodo readLine()).
			archivo = new File ("ActuatorConfiguration.txt");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero y asignacion de variables a cada actuador
			System.out.println("\n ...... LOADING ACTUATORS ...... \n ");
			String id; String type; String state;
			while((id = br.readLine()) != null){
				type = br.readLine();
				state = br.readLine();
				startActuators(id, type);
				}
			System.out.println("\n ............................... \n ");
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			// En el finally cerramos el fichero, para asegurarnos que 
			// se cierra tanto si todo va bien como si salta una excepcion.
			try{                    
				if( null != fr ){   
					fr.close();     
				}                  
			}catch (Exception e2){ 
				e2.printStackTrace();
			}
		}
	}

	public void startActuators(String id, String type) {
		for (ActuatorFactory factory : loader) {
			Object created = factory.getActuator(type, id, bus);
			if(!(created == null) ){
				actuator.add((Actuator) created);
				System.out.println("Actuator successfully added");
			}
		}
	}
	
	// metodo que se ejecuta si alguna de las reglas del sistema se cumple en totalidad
	public void executeActuators(boolean change, String idAct, String type, String mensajeAction){
		
		for (Actuator actuator : this.actuator) {
			if( (idAct.equals(actuator.getId().toString()))  &&  (type.equals(actuator.getType().toString())) ){
				actuator.execute(change, mensajeAction);
			}
		}
	}

/*	public void stopActuators() {
		for (Actuator actuator : loader) {
			//actuator.stop();
		}
	}
*/
}
