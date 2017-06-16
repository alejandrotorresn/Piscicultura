package uis.brt.sensor.gnss.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import uis.brt.sensor.api.Coordinate;

import com.google.common.eventbus.EventBus;

public class MyTCPLocalServer implements Runnable {
	
	private EventBus eb;
	private Coordinate currentCoord;
	private int puertoTCP;

	public MyTCPLocalServer(int puerto) {
		puertoTCP= puerto;
	}
	public Coordinate getCurrentCoord() {
		return currentCoord;
	}
	public void setBus(EventBus bus) {
		eb = bus;
	}

	public void startTcpServer() throws IOException {
		System.out.println("iniciando el server ...");

		// Define que el socket escuchara en el puerto que esta disponible para comunicacion TCP
		ServerSocket listener = new ServerSocket(puertoTCP);
		System.out.println("Servidor iniciado\n");

		try {
			while (true) {
				// Abre el socket y acepta las conexiones
				Socket socket = listener.accept(); // verifica si la conexion
													// fue exitosa
				try {

					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					while (true) {
						// Lee la linea de texto que llega del cliente
						String input = in.readLine();
						if (input == null || input.equals(".")) {
							break;
						}
						// Hace el tratamiento al texto recibido
						currentCoord = NmeatoDatos.separarTokenAndGetCoor(input);
						if(currentCoord!=null)eb.post(currentCoord);
					}
				} finally {
					socket.close();
				}
			}
		} finally {
			listener.close();
		}
	}
	
	public void run() {
		try {
			startTcpServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
