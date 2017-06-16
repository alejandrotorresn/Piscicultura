package uis.brt.sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import uis.brt.aggregator.DataAggregator;

/**
 * Cliente RestFul que consume un servicio sea local o remoto y le envia un
 * contenido tipo JSON
 * 
 * @author
 *
 */
public class RestSender implements Runnable {

	DataAggregator cmj;
	private String uri;
	private static boolean recTerminado = false;
	private static int proxpar;

	public static int getProxpar() {
		return proxpar;
	}

	public static boolean isRecTerminado() {
		return recTerminado;
	}

	public RestSender(String uriServicio) {
		uri = uriServicio;

	}

	public void setProductorMensaje(DataAggregator mj) {
		cmj = mj;
	}

	/*
	 * Funcion de envio del contenido Json
	 * 
	 * @param jsonToSend Este es un String el cual se crea por el metodo
	 * toString de un jsonObject
	 */
	public void enviar(String jsonToSend) {
		
		System.out.println("----- Intentando enviar el siguiente mensaje ----");
		System.out.println(jsonToSend);
		System.out.println("-------------------------------------------------");
		
		try {

			URL url = new URL(uri);
			// "http://192.168.0.14:8080/cloudBRT/api/colector/buses");
			// "http://localhost:8080/cloudBRT/api/colector/buses");

			String input = jsonToSend;
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {

				System.out.println(output + "\n");

				JsonReader jsonReader = Json.createReader(new StringReader(
						output));
				JsonObject respuesta = jsonReader.readObject();
				jsonReader.close();
				proxpar = respuesta.getInt("ProximaParada");
				recTerminado = respuesta.getBoolean("Terminado");

			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (ConnectException conn) {
			System.out
					.println("El servidor apache esta fuera de alcance o no esta encendido");
		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public void run() {
		// TODO Auto-generated method stub

		
		String st = cmj.armarJson();
		System.out.println("JSON" + st);
		
		
		/*
		if (cmj != null) {

			String st = cmj.armarJson();
			System.out.println("JSON" + st);
			//System.out.println(st);
			if (!st.equals(""))
				enviar(st);

		}
		*/
	}

	/**
	 * Este método se invoca para iniciar el envío de los datos a través del API
	 * REST del servicio Cloud
	 */
	public void start() {
		// Crea el scheduler
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		// Programa la ejecución cada 2 segundos del hilo (servicio).
		// La ejecuión empieza a los 3 segundos
		executor.scheduleAtFixedRate(this, 2, 5, TimeUnit.SECONDS);
		System.out.println("Sending Message Scheduled ..... ");
	}

}
