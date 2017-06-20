package uis.brt.aggregator;

import java.util.HashMap;
import java.util.Iterator;

/*
 * Clase en la que se ensambla el mensaje Json que se va a enviar a la plataforma CloudBRT,
 *  Tambien contiene los escuchadores del eventBus que le envian los datos del bus y de los sensores
 */
import javax.json.Json;
import javax.json.JsonObject;
import uis.brt.config.ConfigAdmin;
import uis.brt.sender.RestSender;
import uis.brt.sensor.api.Coordinate;
import uis.brt.sensor.api.Message;
import uis.brt.util.Fecha;
//import uis.brt.actuator.valvulas;

import com.google.common.eventbus.Subscribe;

public class DataAggregator {
	private ConfigAdmin esteDisp; // Almacena los datos del vehiculo y
									// dispositivo
	private Coordinate coorToSend; // Contine la ultima coordenada recibida

	private int proximaParada = 1; // contiene un entero que representa la
		// siguiente parada de la ruta, es de utilidad para la
		// plataforma cloud

	private HashMap<String, Object> map = new HashMap<String, Object>();
	
	//ActuatorValvulas actuatorvalvulas = new ActuatorValvulas();
	
	
	public void setState(HashMap<String, Object> map) {
		//this.map.put(map.get(this), map.)
	}
	public HashMap<String, Object> getState() {
		return map;
	}
	
	@Subscribe 
	public void TipoPez(String tipopez) {
		map.put("tipo",(String) tipopez); // info del pez que se almacena en el map local
		System.out.println("\n *+*+*+* Se determina que los peces son de clima: " + map.get("tipo") + " *+*+*+* \n");
	}

	@Subscribe //System.out.println("tamaño del mapa " + map.size());
	public void receiveData(Message message) {
		String k = message.getMap().keySet().toString();
		k = k.substring(1); // quita el primer caracter [
		k = k.substring(0, k.length()-1); // quita el ultimo caracter ]
		//System.out.println("esto es lo que se almaceno en k = " + k);
		//if(k.equalsIgnoreCase("Oximetro")){
				//map.put("tipo", (String) "calido"); // quitar si el webservice esta enviando correctamente
			map.put(k, message.getMap().get(k));// info que se almacena en el map local
			//if(!map.containsKey("tipo"))
				//map.put("tipo", (String)message.getMap().get("tipo"));
		//}
		System.out.println("\n ----- \n "
				+ "Receiving a data, "
				+ "fron sensor id: " + message.getId() + "," // identificador
				+ " from crop: " + message.getGrupo() + "," // cultivo
				+ " pond # " + message.getElemento() + "," // estanque #
				+ "\n type: " + k + "," // tipo de sensor
				+ "\n value= " + message.getMap().get(k).toString() // valor medido
				//map.get(k) + "\n " +
				//map.get("tipo") + "\n " +
				+ "\n ----- \n " );

	}
	
	public void action(boolean actionexecute){
		
		//ActuatorValvulas.execute(actionexecute);
		
	}
	
	
	
	
	
	

	@Subscribe
	public void envDisp(ConfigAdmin dbus) {	
		/*
		 * Metodo que se activa cuando el eventBus usa el metodo post con un DispBus
		 * de parametro
		 * 
		 * @param dbus Este es el DispBus, enviado desde el launcher
		 */
		esteDisp = dbus;
	}
	
	public String armarJson() {
		/*
		 * Metodo encargado de generar un String apartir de un JsonObject armado con
		 * la informacion del sistema
		 */
		String input = "";
		//proximaParada = EnvioRestClient.getProxpar();
		proximaParada = 10;
		String placa = esteDisp.getProperty("placa");
		String codigo = esteDisp.getProperty("codigo");
		if (coorToSend != null) {
			JsonObject Entrada = Json
					.createObjectBuilder()
					.add("Placa", placa)
					.add("Tde", Fecha.getFechaAndTime())
					.add("ProximaParada", proximaParada)
					.add("Coordenada",
							Json.createObjectBuilder()
							.add("Latitud",
									"" + coorToSend.getLatitud())
							.add("Longitud",
									"" + coorToSend.getLongitud())
							.add("CodigoDispo", codigo)
							.add("Temperatura", "50")) // Cambiar aqui
					.build();
			input = Entrada.toString();
		}
		return input;
	}
	
	@Subscribe
	public void envCoordenadas(Coordinate c) {
		/*
		 * Metodo que se activa cuando el eventBus usa el metodo post con una
		 * Coordenada de parametro
		 * 
		 * @param c Esta es la Coordenadas, enviada desde tcpServer
		 */
		System.out.println("coordenadas tcpserver(" + c.getLatitud() + ","
				+ c.getLongitud() + ")");
		coorToSend = c;
	}
}
