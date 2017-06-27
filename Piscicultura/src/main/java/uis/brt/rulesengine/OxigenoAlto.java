package uis.brt.rulesengine;

import java.util.HashMap;

import org.easyrules.annotation.Rule;

import uis.brt.aggregator.DataAggregator;

import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Priority;

@Rule
public class OxigenoAlto implements PlatformRule {

	private double medicion;
	private boolean evalua;
	DataAggregator dataaggregator = new DataAggregator(); 

	public String getName() {
		return "Oxigeno Alto";
	}

	public String getDescription() {
		return "Evalua el nivel de oxigeno y si esta por encima de lo necesario cierra la valvula";
	}
	@Priority
	public int getPriority() {
		return 0;
	}
	@Condition
	public boolean evaluate() {
		if(evalua)
			return medicion >= 5;
		else
			return evalua;			
	}
	@Action
	public void execute() throws Exception {
		System.out.println("XXXXXXXX EJECUCION: Las Valvulas fueron cerradas exitosamente  XXXXXXXX");
		dataaggregator.action(false);
	}

	public void setData(HashMap<String, String> map) {
		if(map.containsValue("oximetro")){
			medicion = Double.parseDouble(map.get("valor"));
			evalua = true;
		}
		else
			evalua = false;
	}
}