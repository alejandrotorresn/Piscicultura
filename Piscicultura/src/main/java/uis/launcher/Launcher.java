package uis.launcher;

import uis.actuator.admin.ActuatorAdmin;
import uis.aggregator.DataAggregator;
import uis.config.ConfigAdmin;
import uis.context.ContextInformation;
import uis.persistence.Persistence;
import uis.rulesengine.OxigenoAlto;
import uis.rulesengine.OxigenoBajo;
import uis.rulesengine.PezCalido;
import uis.rulesengine.PezFrio;
import uis.rulesengine.PlatformRule;
import uis.rulesengine.RulesAdmin;
import uis.sender.RestSender;
import uis.sensor.admin.SensorAdmin;
import uis.web.WebInterface;
import upm_bmp280.BME280;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.eventbus.EventBus;

/**
 * Clase principal del sistema la cual se encarga de iniciar todos los sensores,
 * e iniciar los procesos de armado y enviado de mensajes a la plataforma
 */
public class Launcher {

	public static void main(String[] args) {

		ConfigAdmin configadmin = new ConfigAdmin();
		DataAggregator clasedatos = new DataAggregator();
		RestSender clienterest = new RestSender(configadmin.getProperty("uri"));
		Persistence basedatos = new Persistence();
		EventBus theEventBus = new EventBus();	
		ContextInformation context = new ContextInformation();
		// guarda las instancias creadas para cada estanque (pond) del sistema
		List<ContextInformation> pond = new ArrayList<ContextInformation>();

		theEventBus.register(clasedatos);
		theEventBus.post(configadmin);
		
/*		HashMap<String, String> ex1 = new HashMap<String, String>();
		ex1.put("5","temperatura"); ex1.put("3","temperatura"); ex1.put("56","mensaje"); ex1.put("59","valvula");
		context.testing("1", "1", "trucha", "frio", "engorde", ex1);
		pond.add(context);
		
		context = new ContextInformation();
		ex1 = new HashMap<String, String>();
		ex1.put("2","temperatura"); ex1.put("6","ph"); ex1.put("51","mensaje"); ex1.put("54","valvula");
		context.testing("2", "2", "mojarra", "calido", "levante", ex1);
		pond.add(context);
		
		context = new ContextInformation();
		ex1 = new HashMap<String, String>();
		ex1.put("57","mensaje"); ex1.put("55","valvula");
		context.testing("1", "3", "cachama", "calido", "iniciacion", ex1);
		pond.add(context);
		
		context = new ContextInformation();
		ex1 = new HashMap<String, String>();
		ex1.put("8","oxigeno"); ex1.put("1","temperatura"); ex1.put("52","mensaje"); ex1.put("53","valvula");
		context.testing("2", "4", "bagre", "calido", "engorde", ex1);
		pond.add(context);
*/	
/*		context = new ContextInformation();
		context.basic();
		pond.add(context);
*/
		SensorAdmin sensorAdmin = new SensorAdmin(theEventBus, configadmin);
		//sensorAdmin.startSensors();
		
		ActuatorAdmin actuatoradmin = new ActuatorAdmin(theEventBus, configadmin);
		//actuatoradmin.startActuators();
		
		System.out.println(" ");
		RulesAdmin rulesAdmin = new RulesAdmin(pond); // envia las instancias creadas de cada estanque
		rulesAdmin.setAgregator(clasedatos, actuatoradmin, sensorAdmin);
		PlatformRule oxyba = new OxigenoBajo();
		PlatformRule oxyal = new OxigenoAlto();
		PlatformRule pezfri = new PezFrio();
		PlatformRule pezcal = new PezCalido();
		rulesAdmin.register(oxyba);
		rulesAdmin.register(oxyal);
		rulesAdmin.register(pezfri);
		rulesAdmin.register(pezcal);
		rulesAdmin.start();

		try {
			WebInterface webinterface = new WebInterface(theEventBus, context, pond);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		clienterest.setProductorMensaje(clasedatos);
		basedatos.setProductorMensaje(clasedatos);
		clienterest.start();
		basedatos.start();
		 */
	}

}
