package uis.brt.rulesengine;

import java.util.HashMap;
import java.util.List;

import org.easyrules.annotation.Rule;

import uis.brt.context.ContextInformation;

import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Priority;

@Rule
public class PezCalido implements PlatformRule {

	private int medicion;
	private String pez = "";
	private String mensaje = "defecto";

	public String getName() {
		return "Pez calido";
	}

	public String getDescription() {
		return "Evalua la medición del termometro y de acuerdo a la temperatura devuelve una advertencia";
	}
	@Priority
	public int getPriority() {
		return 0;
	}
	@Condition
	public boolean evaluate() {
		Boolean eval = false;
		if(pez.equals("calido")){
			eval = true;
			if(medicion >= 20 )
				mensaje = "EXCELENTE: la poblacion de peces calidos esta en optimas condiciones";
			else
				mensaje = "PELIGRO: el agua esta muy fria, poblacion en riesgo";
			}
		return eval;
	}
	@Action
	public void execute() throws Exception {
		System.out.println(mensaje);
	}

	public void setData(HashMap<String, Object> map, List<ContextInformation> context) {
		if(map.containsKey("tipo"))
			pez = (String) map.get("tipo");
				
		if(map.containsKey("Termometro"))
			this.medicion = (Integer) map.get("Termometro");
	}
	
}