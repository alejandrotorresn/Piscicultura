/**
 * Esta clase se encarga de construir una fecha y hora de acuerdo al formato indicado año/mes/dia hora/minuto/segundo
 * y devuelve la misma como un String
 */

package uis.brt.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Fecha {

	
	/**
	 * Devuelve la fecha y la hora
	 * 
	 * @return fecha Este es un String que esta formado por "Año/Mes/Dia Hora:Minuto:Segundo" del sistema
	 */
	public static String getFechaAndTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	/**
	 * Devuelve la fecha	 * 
	 * @return fecha  Este es un String que esta formado por "Año-Mes-Dia" del sistema
	 */
	public static String getFecha() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}