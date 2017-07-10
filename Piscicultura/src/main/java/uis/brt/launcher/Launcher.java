package uis.brt.launcher;

import uis.brt.actuator.admin.ActuatorAdmin;
import uis.brt.aggregator.DataAggregator;
import uis.brt.config.ConfigAdmin;
import uis.brt.context.ContextInformation;
import uis.brt.persistence.Persistence;
import uis.brt.rulesengine.OxigenoAlto;
import uis.brt.rulesengine.OxigenoBajo;
import uis.brt.rulesengine.PezCalido;
import uis.brt.rulesengine.PezFrio;
import uis.brt.rulesengine.PlatformRule;
import uis.brt.rulesengine.RulesAdmin;
import uis.brt.sender.RestSender;
import uis.brt.sensor.admin.SensorAdmin;
import uis.brt.web.WebInterface;

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
		
		HashMap<String, String> ex1 = new HashMap<String, String>();
		ex1.put("1","Termometro"); ex1.put("2","Oximetro"); ex1.put("0","Phmetro");
		context.testing("1", "1", "cachama", "frio", "iniciacion", ex1);//configadmin.getRoute());
		pond.add(context);
		
		context = new ContextInformation();
		context.basic();
		pond.add(context);

		SensorAdmin sensorAdmin = new SensorAdmin(theEventBus, configadmin);
		sensorAdmin.startSensors();
		
		ActuatorAdmin actuatoradmin = new ActuatorAdmin(theEventBus, configadmin);
		actuatoradmin.startActuators();
		
		System.out.println(" ");
		RulesAdmin rulesAdmin = new RulesAdmin(pond); // envia las instancias creadas de cada estanque
		rulesAdmin.setAgregator(clasedatos, actuatoradmin);
		PlatformRule oxyba = new OxigenoBajo();
		PlatformRule oxyal = new OxigenoAlto();
		//PlatformRule pezfri = new PezFrio();
		//PlatformRule pezcal = new PezCalido();
		rulesAdmin.register(oxyba);
		rulesAdmin.register(oxyal);
		//rulesAdmin.register(pezfri);
		//rulesAdmin.register(pezcal);
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
