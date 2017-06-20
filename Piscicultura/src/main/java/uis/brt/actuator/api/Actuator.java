package uis.brt.actuator.api;

import java.util.Properties;
import com.google.common.eventbus.EventBus;

/**  Interface que deben implementar los futuros actuadores
 *   que se integraran al sistema embebido 
 */
public interface Actuator {

	public void configure(Properties props);

	// Contiene el estado actual del actuador
	public boolean state();
		
	// Activa la finalidad del actuador
	public void open(); 

	// Detiene la finalidad del actuador
	public void close(); 
	
	// Se ejecuta dependiendo del estado del actuador
	public void execute(boolean change);
}
