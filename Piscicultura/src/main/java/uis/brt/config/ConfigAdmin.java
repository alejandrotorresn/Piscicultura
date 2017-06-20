package uis.brt.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * This class is responsible for providing configuration to different components
 * in the middleware
 */
public class ConfigAdmin extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public ConfigAdmin() {
		load();
	}

	/**
	 * Loads properties files (resources) found in all jars contained in the
	 * classpath
	 */
	public void load() {
		try {
			List<InputStream> streams = loadResources("config.properties", null);
			
			for (InputStream inputStream : streams) {
				/*
				System.out.println("encontrados " + inputStream);
				
				Iterator i = streams.iterator();
				
				while(i.hasNext()){
					 Object x = i.next();
					System.out.println("objetos " + x);
				}*/
				load(inputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a list with streams of files required in jars found in classpath  
	 * @param name name of resource to load
	 * @param classLoader classLoader to be used
	 * @return List of stream loaded
	 * @throws IOException if any stream cannot be load
	 */
	public static List<InputStream> loadResources(String name,
			ClassLoader classLoader) throws IOException {
		final List<InputStream> list = new ArrayList<InputStream>();
		final Enumeration<URL> systemResources = (classLoader == null ? ClassLoader
				.getSystemClassLoader() : classLoader).getResources(name);
		while (systemResources.hasMoreElements()) {
			URL url = systemResources.nextElement();
			System.out.println(url);
			list.add(url.openStream());
			//System.out.println("esta en loadResources " + list.toString());
		}
		//for (InputStream x : list) {System.out.println("lista " + x);}
		return list;
	}
}
