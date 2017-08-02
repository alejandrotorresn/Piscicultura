package uis.actuator.api;

import java.util.Properties;
import com.google.common.eventbus.EventBus;

/**  Interface que deben implementar los futuros actuadores
 *   que se integraran al sistema embebido 
 */
public interface Actuator {

	public void configure(String id);

	// Establece el estado del actuador
	public void state(boolean state);
		
	// Activa la finalidad del actuador
	public void open(String mensajeAction); 

	// Detiene la finalidad del actuador
	public void close(String mensajeAction); 
	
	// Se ejecuta dependiendo del estado del actuador
	public void execute(boolean change, String mensajeAction);
	
	public String getId();
	public String getType();
}
