package uis.brt.rulesengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.synth.SynthSeparatorUI;

import uis.brt.actuator.admin.ActuatorAdmin;
import uis.brt.aggregator.DataAggregator;
import uis.brt.context.ContextInformation;

import org.easyrules.api.RulesEngine;
import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;


public class RulesAdmin implements Runnable {

	RulesEngine rulesEngine;
	DataAggregator agreggator;
	ActuatorAdmin actuator;
	//ContextInformation context; // = new ContextInformation();
	HashMap<String, Object> state = new HashMap<String, Object>();
	// lista de las reglas almacenadas en el motor de reglas
	List<PlatformRule> rules = new ArrayList<PlatformRule>();
	// lista de las instancias de los estanques existentes
	List<ContextInformation> pond = new ArrayList<ContextInformation>();
	// Hahsmap con todos los dispositivos (sensores y actuadores del sistema)
	HashMap<String, HashMap<String, String>> bigmap = new HashMap<String, HashMap<String, String>>();
	
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

	public void setAgregator(DataAggregator agreggator, ActuatorAdmin actuator) {
		this.agreggator = agreggator;
		this.actuator = actuator;
	}
	
	public void start(){
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(this, 6, 4, TimeUnit.SECONDS);
	}

	public void run() {
		bigmap = agreggator.getState();
		for (Entry<String, HashMap<String, String>> sensorInd : bigmap.entrySet()) {
			for (PlatformRule rule : rules) {
				rule.setData(sensorInd.getValue(), pond, actuator);
			}
			rulesEngine.fireRules();
		}
	}

	public void register(PlatformRule claseregla) {//registro de las reglas a trabajar
		rules.add(claseregla); // registro en lista interna
		//System.out.println("manera como se estan almacenando en el rules admin las reglas \n" + rules + "\n");
		rulesEngine.registerRule(claseregla); // registro en motor de easyrules
	}
	
}
