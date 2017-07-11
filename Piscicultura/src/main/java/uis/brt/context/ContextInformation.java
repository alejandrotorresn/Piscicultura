package uis.brt.context;

import java.util.HashMap;

public class ContextInformation {
	
	private String group; // id del grupo de estanques
	private String element; // id del estanque
	private String fish; // nombre del pez
	private String weather; // tipo de clima
	private String cycle; // ciclo en el que se encuentra
	private HashMap<String, String> devices;
	
	public ContextInformation() {
		}
	
	// metodo base para tener algun estanque de prueba almacenado al iniciar el sistema
	public void basic(){
		this.group = "1";
		this.element = "2";
		this.fish = "trucha";
		this.weather = "calido";
		this.cycle = "engorde";
		HashMap<String, String> example = new HashMap<String, String>();
		example.put("3","Valvula"); example.put("4","Oximetro");  example.put("5","Phmetro");
		devices = example;
	}
	
	public void testing(String group, String element, String fish, String weather, String cycle, HashMap<String, String> device){

		this.group = group;
		this.element = element;
		this.fish = fish;
		this.weather = weather;
		this.cycle = cycle;
		this.devices = device;
	}
	
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getFish() {
		return fish;
	}
	public void setFish(String fish) {
		this.fish = fish;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public HashMap<String, String> getDevices() {
		return devices;
	}
	public void setDevices(HashMap<String, String> devices) {
		this.devices = devices;
	}

}
