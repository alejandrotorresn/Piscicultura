package uis.brt.rulesengine;

import java.util.HashMap;
import java.util.List;

import uis.brt.actuator.admin.ActuatorAdmin;
import uis.brt.context.ContextInformation;

import org.easyrules.annotation.Rule;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Priority;

@Rule
public class OxigenoAlto implements PlatformRule {

	private HashMap<String, String> maptemp;
	private double previousvalue = -1;
	private List<ContextInformation> context;
	private boolean exe;
	private String accion = "";
	ActuatorAdmin actuator;

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
		//System.out.println("dentro de evaluate " + exe);
		if(exe){
			Double x = Double.parseDouble( maptemp.get("value"));
			if(x != previousvalue){
				previousvalue = x;	
				//System.out.println("dentro de if " + maptemp.get("value"));
				//System.out.println("x >= 5 Double "+ x);
				if(x >= 5)
					exe = true; 
				else
					exe = false;}
			else
				exe = false;
			}
		/*for(ContextInformation x : context){
			if(x.getDevices().containsValue("Oximetro")){
				String p = x.getDevices().keySet().toString();
			}
		}*/
		//System.out.println("exe " + exe);
		return exe;
	}
	@Action // solo se ejecutara, si el return del metodo evaluate, es true
	public void execute() throws Exception {
		String id =  maptemp.get("id");
		String type =  maptemp.get("type");
		for (ContextInformation ins : context) {
			//System.out.println("dentro de execute " + ins);
			//System.out.println("inst " + ins.getDevices().containsValue("Oximetro"));
			if(ins.getDevices().containsValue("Oximetro")){
				//System.out.println("tiene valor de oximetro " + ins.getDevices().containsValue("Oximetro"));
				//System.out.println("id: " + id + " " + ins.getDevices().containsKey(id) );
				if(ins.getDevices().containsKey(id)){
					accion = "XXXXXXXX EJECUCION: Las Valvulas del estanque " + ins.getElement() + " fueron cerradas exitosamente  XXXXXXXX";
					//System.out.println("la regla oxalto a establecido en false y la accion");
					actuator.executeActuators(false, accion);
					//System.out.println(accion);
				}}}
		
		//dataaggregator.action(false); comentareado hasta que sea capaz de enviar la info a los actuadores
	}

	public void setData(HashMap<String, String> map, List<ContextInformation> context, ActuatorAdmin actuator) {
		if(map.containsValue("Oximetro")){ // falta definir bien si se guarda como ahora, el tipo de device o se guarda es: el tipo de medicion
			//HashMap<String, String> t = new HashMap<String, String>();
			maptemp = map;
			this.context = context;
			this.actuator = actuator;
			exe = true;
			//System.out.println("map " + map + "\ncontext " + context + "\nexe " + exe);
		}
		else
			exe = false;
	}

	
}