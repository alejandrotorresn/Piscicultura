package uis.sensor.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;

import com.google.common.eventbus.EventBus;

import uis.sensor.api.Sensor;
import uis.sensor.api.SensorFactory;

public class SensorAdmin {

	private ServiceLoader<SensorFactory> loader;
	private EventBus bus;
	private Properties props;
	List<Sensor> sensor = new ArrayList<Sensor>();
	
	public SensorAdmin(EventBus bus, Properties props) {
		this.bus = bus;
		this.props = props;
		loader = ServiceLoader.load(SensorFactory.class);
		LoadSensors();
	}

	public void LoadSensors(){
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {//Apertura del fichero y creacion de BufferedReader para poder hacer una lectura comoda
			//(disponer del metodo readLine()).
			archivo = new File ("SensorConfiguration.txt");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero y asignacion de variables a cada sensor
			System.out.println("\n ...... LOADING SENSORS ...... \n ");
			String id; String type;
			while((id = br.readLine()) != null){
				type = br.readLine();
				startSensors(id, type);
				}
			System.out.println("\n ............................. \n ");
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
	
	public void startSensors(String id, String type) {
		for (SensorFactory factory : loader) {
			Object created = factory.getSensor(type, id, bus);
			if(!(created == null)){
				sensor.add((Sensor) created);
				System.out.println("Sensor successfully added");
			}
		}
	}
	
	public void capture() {
		
		for (Sensor sensor : this.sensor) {
			sensor.run();
		}
	}
	

/*	public void stopSensors() {
		for (Sensor sensor : loader) {
			sensor.stop();
		}
	}
*/
}
