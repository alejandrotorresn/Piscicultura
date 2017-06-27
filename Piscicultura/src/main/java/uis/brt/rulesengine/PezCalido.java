package uis.brt.rulesengine;

import java.util.HashMap;

import org.easyrules.annotation.Rule;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Priority;

@Rule
public class PezCalido implements PlatformRule {

	private int medicion;
	private boolean evalua;
	private String pez="";
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
		if(evalua)
			if(pez.equals("calido")){
				if(medicion >= 20 )
					mensaje = "EXCELENTE: la poblacion de peces calidos esta en optimas condiciones";
				else
					mensaje = "PELIGRO: el agua esta muy fria, poblacion en riesgo";
		
				return true;
			}
		
			return  false;
	}
	@Action
	public void execute() throws Exception {
		System.out.println(mensaje);
	}

	public void setData(HashMap<String, String> map) {
		//if(map.containsKey("tipo"))
		//	pez = map.get("tipo");
				
		if(map.containsValue("termometro")){
			medicion = Integer.parseInt(map.get("valor"));
			pez = map.get("pez");
			evalua = true;
		}
		else
			evalua = false;
	}
}