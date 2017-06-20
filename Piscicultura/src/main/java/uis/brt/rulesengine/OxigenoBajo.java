package uis.brt.rulesengine;

import java.util.HashMap;

import org.easyrules.annotation.Rule;

import uis.brt.aggregator.DataAggregator;

import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Priority;

@Rule//(name = "Oxigeno Bajo", description = "Evalua el nivel de oxigeno y si esta por debajo de lo permitido y abre la valvula")
public class OxigenoBajo implements PlatformRule {

	private double medicion;
	DataAggregator dataaggregator = new DataAggregator(); 
	
	public String getName() {
		return "Oxigeno Bajo";
	}

	public String getDescription() {
		return "Evalua el nivel de oxigeno y si esta por debajo de lo necesario abre la valvula";
	}
	@Priority
	public int getPriority() {
		return 0;
	}
	@Condition
	public boolean evaluate() {
		return medicion <= 4;
	}
	@Action
	public void execute() throws Exception {
		System.out.println("OOOOOOOO EJECUCION: Las Valvulas han sido abiertas OOOOOOOO");
		dataaggregator.action(true);
	}

	public void setData(HashMap<String, Object> map) {
		if(map.containsKey("Oximetro"))
			this.medicion = (Double) map.get("Oximetro");
	}
	
}