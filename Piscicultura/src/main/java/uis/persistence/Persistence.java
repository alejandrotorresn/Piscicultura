package uis.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import uis.aggregator.DataAggregator;
import uis.util.Fecha;

public class Persistence implements Runnable {
	DataAggregator cmj;

	public void setProductorMensaje(DataAggregator mj) {
		cmj = mj;
	}

	public void run() {
		String st = cmj.armarJson();
		if (!st.equals("")) {
			guardar(st);
		}
	}

	/*
	 * almacena un linea en el archivo coordenadas.txt al final del archivo cada
	 * vez que se llama
	 * 
	 * @linea es el parametro de entrada del metodo, su contenido se almacena en
	 * una nueva linea de coordenadas.txt
	 */
	public void guardar(String linea) {
		String filename = ""; // String que contiene la ruta del archivo usado
								// en el metodo guardar
		File newDir = new File("coordenadasHistory");
		newDir.mkdir();

		filename = "coord-" + Fecha.getFecha() + ".txt";

		try {
			File file = new File(newDir, filename); // archivo que contiene un
													// historico de las mensajes
													// //de un bus

			// si no existe el archivo
			if (!file.exists()) {
				file.createNewFile();
			}
			// fw y bw son elementos para escritura de archivos
			FileWriter fw = new FileWriter("coordenadasHistory/" + filename,
					true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(linea); // guardo el string
			bw.newLine(); // creo un salto de linea en el archivo
			bw.close();
		} catch (IOException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Este método se invoca para iniciar el servicio de persistencia de los
	 * datos
	 */
	public void start() {
		// Crea el scheduler
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		// Programa la ejecución cada 2 segundos del hilo (servicio).
		// La ejecuión empieza a los 3 segundos
		executor.scheduleAtFixedRate(this, 3, 10, TimeUnit.SECONDS);
	}

}
