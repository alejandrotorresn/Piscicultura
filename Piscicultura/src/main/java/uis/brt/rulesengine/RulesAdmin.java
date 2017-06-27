package uis.brt.rulesengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import uis.brt.aggregator.DataAggregator;

import org.easyrules.api.RulesEngine;
import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;


public class RulesAdmin implements Runnable {

	RulesEngine rulesEngine;
	DataAggregator agregator;
	HashMap<String, HashMap> state = new HashMap<String, HashMap>();
	List<PlatformRule> rules = new ArrayList<PlatformRule>();
	
	public RulesAdmin(){
		//se crea el motor de reglas basado en easy rules
		rulesEngine = aNewRulesEngine()
				//.withSkipOnFirstAppliedRule(true) // si cumple una regla salta la otra
				.withSilentMode(true).build(); // para que no se llene de basura la consola
	}	

	public DataAggregator getAgregator() {
		return agregator;
	}

	public void setAgregator(DataAggregator agregator) {
		this.agregator = agregator;
	}
	
	public void start(){
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(this, 6, 4, TimeUnit.SECONDS);
	}

	public void run() {
		state = agregator.getState();

		for (Map.Entry<String, HashMap> temporal : state.entrySet()) {
			String id = temporal.getKey();
		    System.out.println("\n analizando el sensor= " + id);
			for (PlatformRule rule : rules) {
				rule.setData(state.get(id));
			}
			rulesEngine.fireRules();
		}	
	}

	public void register(PlatformRule claseregla) {//registro de las reglas a trabajar
		rules.add(claseregla); // registro en lista interna
		rulesEngine.registerRule(claseregla); // registro en motor de easyrules
	}
	
}
