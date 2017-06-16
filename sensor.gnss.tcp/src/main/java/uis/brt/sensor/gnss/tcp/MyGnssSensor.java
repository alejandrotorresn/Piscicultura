package uis.brt.sensor.gnss.tcp;

import java.util.Properties;

import uis.brt.sensor.api.Sensor;

import com.google.common.eventbus.EventBus;

public class MyGnssSensor implements Sensor {

	private EventBus eventBus;
	private MyTCPLocalServer tcpServer;
	private int puerto;

	public MyGnssSensor() {
		puerto= 9091;
	}

	public void setBus(EventBus bus) {
		eventBus = bus;
	}

	public void start() {
		tcpServer = new MyTCPLocalServer(puerto);
		tcpServer.setBus(eventBus);
		
		Thread thrTCPServer = new Thread(tcpServer);
		thrTCPServer.start();
	}

	public void stop() {
		// TODO Auto-generated method stub

	}

	public void configure(Properties props) {
		puerto = Integer.parseInt(props.getProperty("puertoTcp"));
		
	}

}
