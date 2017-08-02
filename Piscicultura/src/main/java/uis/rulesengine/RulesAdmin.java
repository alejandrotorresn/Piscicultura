package uis.rulesengine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import uis.actuator.admin.ActuatorAdmin;
import uis.aggregator.DataAggregator;
import uis.context.ContextInformation;
import uis.sensor.admin.SensorAdmin;

import org.easyrules.api.RulesEngine;
import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;


public class RulesAdmin implements Runnable {

	RulesEngine rulesEngine;
	DataAggregator agreggator;
	ActuatorAdmin actuatoradmin;
	SensorAdmin sensoradmin;
	// lista de las reglas almacenadas en el motor de reglas
	List<PlatformRule> rules = new ArrayList<PlatformRule>();
	// lista de las instancias de los estanques existentes
	List<ContextInformation> pond = new ArrayList<ContextInformation>();
	// Hahsmap con todos los dispositivos (sensores y actuadores del sistema)
	HashMap<String, HashMap<String, String>> bigmap = new HashMap<String, HashMap<String, String>>();
	Calendar fecha = Calendar.getInstance();
	
	public RulesAdmin(List<ContextInformation> pond){
		//se crea el motor de reglas basado en easy rules
		rulesEngine = aNewRulesEngine()
				//.withSkipOnFirstAppliedRule(true) // si cumple una regla salta la otra
				.withSilentMode(true).build(); // para que no se llene de basura la consola
		
		this.pond = pond;
	}	

	public DataAggregator getAgregator() {
		return agreggator;
	}

	public void setAgregator(DataAggregator agreggator, ActuatorAdmin actuator, SensorAdmin sensor) {
		this.agreggator = agreggator;
		this.actuatoradmin = actuator;
		this.sensoradmin = sensor;
	}
	
	public void start(){
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(this, 186, 4, TimeUnit.SECONDS);
	}

	public void run() {
		//sensoradmin.capture(); // todos los sensores capturan la informacion
		//fecha = Calendar.getInstance();
		System.out.println("### EJECUCION DE REGLAS ### ");
		bigmap = agreggator.getState(); // se actualiza el map con todos los datos en agreggator
		for (Entry<String, HashMap<String, String>> sensorInd : bigmap.entrySet()) {
			for (PlatformRule rule : rules) {
				rule.setData(sensorInd.getValue(), pond, actuatoradmin);// se actualizan datos en cada regla
			}
			rulesEngine.fireRules(); // se ejecutan todas las reglas
		}
	}

	public void register(PlatformRule claseregla) {//registro de las reglas a trabajar
		rules.add(claseregla); // registro en lista interna
		rulesEngine.registerRule(claseregla); // registro en motor de easyrules
	}
	
}
