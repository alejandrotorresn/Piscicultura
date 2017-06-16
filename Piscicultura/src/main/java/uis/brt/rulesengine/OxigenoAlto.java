package uis.brt.rulesengine;

import java.util.HashMap;

import org.easyrules.annotation.Rule;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Priority;

@Rule
public class OxigenoAlto implements PlatformRule {

	private double medicion;

	public String getName() {
		return "Oxigeno Alto";
	}

	public String getDescription() {
		return "Evalua el nivel de oxigeno y si esta por encima de lo necesario cierra la valvula";
	}
	@Priority
	public int getPriority() {
		return 1;
	}
	@Condition
	public boolean evaluate() {
		return medicion >= 5;
	}
	@Action
	public void execute() throws Exception {
		System.out.println("XXXXXXXX EJECUCION: Las Valvulas fueron cerradas exitosamente  XXXXXXXX");
	}

	public void setData(HashMap<String, Object> map) {
		if(map.containsKey("Oximetro"))
			this.medicion = (Double) map.get("Oximetro");
	}
	
}