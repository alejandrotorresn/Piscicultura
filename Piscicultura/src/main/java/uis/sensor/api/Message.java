package uis.sensor.api;

import java.util.HashMap;

public class Message {

	private String id; // identificador
	private String type; //tipo de sensor
	private Object value;//valor medido
	//private HashMap<String, Object> map; // tipo de sensor (String) y medicion (Object)
/*
	public Message(String id, HashMap<String, Object> map) {
		super();
		this.id = id;
		this.map = map;
	}
*/
	public Message(String id, String type, Object value) {
		super();
		this.id = id;
		this.type = type;
		this.value = value;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
/*
	public HashMap<String, Object> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}
*/
}
