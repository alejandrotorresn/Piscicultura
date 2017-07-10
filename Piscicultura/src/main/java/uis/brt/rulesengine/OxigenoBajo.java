package uis.brt.rulesengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import uis.brt.actuator.admin.ActuatorAdmin;
import uis.brt.context.ContextInformation;

import org.easyrules.annotation.Rule;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Priority;

@Rule//(name = "Oxigeno Bajo", description = "Evalua el nivel de oxigeno y si esta por debajo de lo permitido y abre la valvula")
public class OxigenoBajo implements PlatformRule {

	// valores fijos para esta regla
	private String AssociatedSensorType = "Oximetro";
	private String AssociatedActuatorType = "Valvula";
	
	private HashMap<String, String> sensorInd;
	private double previousvalue = -1;
	private List<ContextInformation> pond;
	private boolean exe;
	private String accion = "";
	ActuatorAdmin actuator;
	
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
		//System.out.println("dentro de evaluate " + exe);
		if(exe){
			Double x = Double.parseDouble( sensorInd.get("value"));
			if(x != previousvalue){
				previousvalue = x;
				//System.out.println("dentro de if " + maptemp.get("value"));
				//System.out.println("x <= 4 Double "+ x);
				if(x <= 4)
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
		String id =  sensorInd.get("id"); String type =  sensorInd.get("type");
		for (ContextInformation ins : pond) {
			//System.out.println("dentro de execute " + ins);
			//System.out.println("inst " + ins.getDevices().containsValue("Oximetro"));
			if(ins.getDevices().containsValue(type)){
				//System.out.println("tiene valor de oximetro " + ins.getDevices().containsValue("Oximetro"));
				//System.out.println("id: " + id + " " + ins.getDevices().containsKey(id) );
				if(ins.getDevices().containsKey(id)){// evalua si el contexto tiene algun device con el mismo id que se analiza ahora
					String IdOfActuatorToExecute = "";
					if(ins.getDevices().containsValue(AssociatedActuatorType)){ // evalua si el contexto tiene algun device con el mismo tipo de actuador de la regla
						for(Entry<String, String> x : ins.getDevices().entrySet())
							if(AssociatedActuatorType.equals(x.getValue().toString())){
								IdOfActuatorToExecute = x.getKey().toString();
								accion = "OOOOOOOO EJECUCION: Las Valvulas del estanque " + ins.getElement() + " han sido abiertas OOOOOOOO";
								actuator.executeActuators(true, IdOfActuatorToExecute, AssociatedActuatorType, accion);
							}}}}}
		//dataaggregator.action(true); comentareado hasta que sea capaz de enviar la info a los actuadores
	}

	public void setData(HashMap<String, String> sensorInd, List<ContextInformation> pond, ActuatorAdmin actuator) {
		if(sensorInd.containsValue("Oximetro")){ // falta definir bien si se guarda como ahora, el tipo de device o se guarda es: el tipo de medicion
			//HashMap<String, String> t = new HashMap<String, String>();
			this.sensorInd = sensorInd;
			this.pond = pond;
			this.actuator = actuator;
			exe = true;
			//System.out.println("map " + map + "\ncontext " + context + "\nexe " + exe);
		}
		else
			exe = false;
	}
	
}