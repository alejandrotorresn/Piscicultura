package uis.brt.sensor.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.ServiceLoader;

import uis.brt.sensor.api.SensorFactory;
import uis.brt.sensor.api.Sensor;

import com.google.common.eventbus.EventBus;

public class SensorAdmin {

	private ServiceLoader<SensorFactory> loader;
	private EventBus bus;
	private Properties props;
	
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
			factory.getSensor(type, id, bus);
		}
	}

/*	public void stopSensors() {
		for (Sensor sensor : loader) {
			sensor.stop();
		}
	}
*/
}
