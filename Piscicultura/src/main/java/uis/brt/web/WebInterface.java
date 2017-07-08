package uis.brt.web;

import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.google.common.eventbus.EventBus;

import uis.brt.context.ContextInformation;

public class WebInterface {
	

	public WebInterface(EventBus bus, ContextInformation context, List<ContextInformation> pond) throws Exception {// public static void main(String
											// url) throws Exception {
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
		Home homeResource = new Home(bus, context, pond);
		// Here, the resource (WS) is registered ont Jersey
		config.register(homeResource);
		
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
		// public static void lanzarpagina(String url){
		String URL = "http://localhost:8080/home";
		try {
			Runtime.getRuntime().exec(
					"rundll32 url.dll,FileProtocolHandler " + URL);
		} catch (Exception err) {
			System.out.println("Error: " + err);
		}
	}
}