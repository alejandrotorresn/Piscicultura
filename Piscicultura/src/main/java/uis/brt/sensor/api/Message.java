package uis.brt.sensor.api;

import java.util.HashMap;

public class Message {

	private String id; // identificador
	private String grupo; // numero del cultivo
	private String elemento; // numero del estanque dentro del cultivo
	private HashMap<String, Object> map; // tipo de sensor y medicion

	public Message(String id, String grupo, String elemento, HashMap<String, Object> map) {
		super();
		this.id = id;
		this.grupo = grupo;
		this.elemento = elemento;
		this.map = map;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getElemento() {
		return elemento;
	}
	public void setElemento(String elemento) {
		this.elemento = elemento;
	}

	public HashMap<String, Object> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}

}
