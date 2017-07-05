package uis.brt.rulesengine;

import java.util.HashMap;
import java.util.List;

import org.easyrules.annotation.Rule;

import uis.brt.aggregator.DataAggregator;
import uis.brt.context.ContextInformation;

import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Priority;

@Rule//(name = "Oxigeno Bajo", description = "Evalua el nivel de oxigeno y si esta por debajo de lo permitido y abre la valvula")
public class OxigenoBajo implements PlatformRule {

	private HashMap<String, Object> maptemp;
	//private double medicion;
	private List<ContextInformation> context;
	private boolean exe;
	private String accion = "";
	//DataAggregator dataaggregator = new DataAggregator(); 
	
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
		if(exe)
			if((Integer.parseInt((String) maptemp.get("value"))) <= 4)
				exe = true;
			else
				exe = false;
		/*for(ContextInformation x : context){
			if(x.getDevices().containsValue("Oximetro")){
				String p = x.getDevices().keySet().toString();
			}
		}*/
		return exe;
	}
	@Action // solo se ejecutara, si el return del metodo evaluate, es true
	public void execute() throws Exception {
		String id = (String) maptemp.get("id");
		String type = (String) maptemp.get("type");
		for (ContextInformation ins : context) {
			if(ins.getDevices().containsValue("Oximetro"))
				if(id.equals(ins.getDevices().get(id)))
					accion = "OOOOOOOO EJECUCION: Las Valvulas del estanque " + ins.getElement() + " han sido abiertas OOOOOOOO";
		}
		System.out.println(accion);
		//dataaggregator.action(true); comentareado hasta que sea capaz de enviar la info a los actuadores
	}

	public void setData(HashMap<String, Object> map, List<ContextInformation> context) {
		if(map.containsValue("Oximetro")){ // falta definir bien si se guarda como ahora, el tipo de device o se guarda es: el tipo de medicion
			//HashMap<String, String> t = new HashMap<String, String>();
			maptemp = map;
			this.context = context;
			exe = true;
		}
		else
			exe = false;
	}
	
}