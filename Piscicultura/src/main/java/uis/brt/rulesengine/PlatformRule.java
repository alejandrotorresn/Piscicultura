package uis.brt.rulesengine;

import java.util.HashMap;
import org.easyrules.api.Rule;

public interface PlatformRule extends Rule {

	//obliga a todas las reglas a tener este metodo
	public void setData(HashMap<String, Object> map);
}
