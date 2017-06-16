package uis.brt.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.eventbus.EventBus;

//el nombre utilizado dentro de path es el que se colocara en el navegador
@Path("/home") 
public class Home {
	
	private EventBus bus;
	
	public Home(EventBus bus) {
		this.bus = bus;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN) //@Produces("application/xml")
	public String seleccion() {
 
		String result = "\n\n\n\n\t copie y pegue en la barra de direccion el tipo de pez"
				+ "\n\n\t /frio \n\n \to \n\n\t /calido";
		
		result = result + "\n\n\n\n\t el bus es null : " + (bus == null); 
		
		return result ;
	}
	
	
	@Path("{pez}")
	@GET
	@Produces(MediaType.TEXT_PLAIN) //@Produces("application/xml")
	public String resultado(@PathParam("pez") String pez) {
		
		String result = " \n\n\n\n\t el tipo de pez seleccionado fue: \n\n \t" + pez;
		
		result = result + "\n\n\n\n\t el bus es null " + (bus == null); 
		
		System.out.println(result);
		bus.post(pez);
		return  result ;
	}
	/*
	@POST
    @Path("/sendemail")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendEmail(@FormParam("email") String email) {
        System.out.println(email);
        return Response.ok("email=" + email).build();
    }
	*/
	
}