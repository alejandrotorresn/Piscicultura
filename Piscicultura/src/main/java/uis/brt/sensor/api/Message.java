package uis.brt.sensor.api;

import java.util.HashMap;

public class Message {

	private String id;
	private String name;
	private HashMap<String, Object> map;

	public Message(String id, String name, HashMap<String, Object> map) {
		super();
		this.id = id;
		this.name = name;
		this.map = map;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Object> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}

}
