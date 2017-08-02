package uis.web;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.google.common.eventbus.EventBus;

import uis.context.ContextInformation;

public class WebInterface {
	
	String ip = "";

	public WebInterface(EventBus bus, ContextInformation context, List<ContextInformation> pond) throws Exception {
		// public static void main(String  // url) throws Exception {
		ServletContextHandler servletcontexthandler = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		servletcontexthandler.setContextPath("/");

		Server jettyServer = new Server(8080);
		jettyServer.setHandler(servletcontexthandler);

		
		//////  Begin Jersey Code /////
		
		// Jersey Configurator Class (Resource are registered on it)
		ResourceConfig config = new ResourceConfig();
		// This is the Jersey Servlet
		ServletContainer servletContainer = new ServletContainer(config);
		// Instance of the resource (Web Service), it will use the EventBus instance
		
		//System.out.println(System.getProperty("os.name"));
		//System.out.println(InetAddress.getLocalHost().getHostAddress().toString());
		//System.out.println(InetAddress.getLocalHost().getHostName().toString());
		
		if((System.getProperty("os.name")).startsWith("Windows"))
			ip = InetAddress.getLocalHost().getHostAddress().toString();
		else
			ip = buscarIP();
		
		System.out.println("IP asignada al dispositivo " + ip);
		
		Home home = new Home(bus, context, pond, ip);
		Add add =new Add(bus, context, pond, home);
		Consult consult = new Consult(bus, context, pond, home);
		Modify modify = new Modify(bus, context, pond, home);
		Delete delete = new Delete(bus, context, pond, home);
		// Here, the resource (WebService) is registered on Jersey
		config.register(home);
		config.register(add);
		config.register(consult);
		config.register(modify);
		config.register(delete);
		//////  End Jersey Code //////
		
		
 		// Jersey Servlet added to Jetty 
		ServletHolder jerseyServletHolder = new ServletHolder(servletContainer);
		servletcontexthandler.addServlet(jerseyServletHolder, "/*");
		jerseyServletHolder.setInitOrder(0);



		lanzarpagina(); // lanzarpagina(url);
		/*
		 * jerseyServlet.setInitParameter(
		 * "jersey.config.server.provider.classnames",
		 * FtoCService.class.getCanonicalName());
		 */
		try {
			jettyServer.start();
			jettyServer.join();
		} finally {
			jettyServer.destroy();
		}
	}

	public void lanzarpagina() {
		String URL = "http://"+ip+":8080/home";
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + URL); //PARA WINDOWS
		} catch (Exception err) {
			System.out.println("No se pudo lanzar pagina: " + err);
			System.out.println("COPIE Y PEGUE EN EL NAVEGADOR LA SIGUIENTE DIRECCION PARA INGRESAR AL MODULO DE PISCICULTURA:");
			System.out.println(URL);
		}
	}
	
	public String buscarIP() {
		NetworkInterface iface = null;
		String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +	"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		InetAddress ia = null;
		try
		{
			for(Enumeration ifaces = NetworkInterface.getNetworkInterfaces();ifaces.hasMoreElements();)
			{
				iface = (NetworkInterface)ifaces.nextElement();
				//ethr = iface.getDisplayName();
				//if (Pattern.matches("eth[0-9]", ethr)) //funciona solo si la interfaz de red se llama eth en el SO que se ejecute 
				//{
					for(Enumeration ips = iface.getInetAddresses();ips.hasMoreElements();)
					{
						ia = (InetAddress)ips.nextElement();
						if (Pattern.matches(regex, ia.getCanonicalHostName()))
						{
							System.out.println("Interfaz de acceso a red " + iface.getDisplayName());
							System.out.println("IP " + ia.getCanonicalHostName());
							return ia.getCanonicalHostName();
						}
					}
				//}
			}
		}
		catch (SocketException e){
			System.out.println("No encontro ip : " + e);}
		return ia.getCanonicalHostName();
	}
}