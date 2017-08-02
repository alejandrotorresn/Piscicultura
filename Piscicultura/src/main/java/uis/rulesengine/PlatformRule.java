package uis.rulesengine;

import java.util.HashMap;
import java.util.List;

import org.easyrules.api.Rule;

import uis.actuator.admin.ActuatorAdmin;
import uis.context.ContextInformation;

public interface PlatformRule extends Rule {

	//obliga a todas las reglas a tener este metodo
	public void setData(HashMap<String, String> hashMap, List<ContextInformation> context, ActuatorAdmin actuator);
}
