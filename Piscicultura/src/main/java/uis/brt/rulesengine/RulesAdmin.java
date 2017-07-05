package uis.brt.rulesengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import uis.brt.aggregator.DataAggregator;
import uis.brt.context.ContextInformation;

import org.easyrules.api.RulesEngine;
import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;


public class RulesAdmin implements Runnable {

	RulesEngine rulesEngine;
	DataAggregator agreggator;
	//ContextInformation context; // = new ContextInformation();
	HashMap<String, Object> state = new HashMap<String, Object>();
	List<PlatformRule> rules = new ArrayList<PlatformRule>();
	List<ContextInformation> context = new ArrayList<ContextInformation>();
	HashMap<String, HashMap<String, Object>> bigmap = new HashMap<String, HashMap<String, Object>>();
	
	public RulesAdmin(List<ContextInformation> estanques){
		//se crea el motor de reglas basado en easy rules
		rulesEngine = aNewRulesEngine()
				//.withSkipOnFirstAppliedRule(true) // si cumple una regla salta la otra
				.withSilentMode(true).build(); // para que no se llene de basura la consola
		
		this.context = estanques;
	}	

	public DataAggregator getAgregator() {
		return agreggator;
	}

	public void setAgregator(DataAggregator agreggator) {
		this.agreggator = agreggator;
	}
	
	public void start(){
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(this, 6, 4, TimeUnit.SECONDS);
	}

	public void run() {
		
		bigmap = agreggator.getState();
		for (Entry<String, HashMap<String, Object>> temporal : bigmap.entrySet()) {
			for (PlatformRule rule : rules) {
				rule.setData(temporal.getValue(), context);
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
