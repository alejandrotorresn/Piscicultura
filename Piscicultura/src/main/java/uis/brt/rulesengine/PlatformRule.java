package uis.brt.rulesengine;

import java.util.HashMap;
import java.util.List;

import org.easyrules.api.Rule;

import uis.brt.context.ContextInformation;

public interface PlatformRule extends Rule {

	//obliga a todas las reglas a tener este metodo
	public void setData(HashMap<String, Object> map, List<ContextInformation> context);
}
