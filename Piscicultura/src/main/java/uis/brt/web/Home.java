package uis.brt.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.eventbus.EventBus;

//el nombre utilizado dentro de path es el que se colocara en el navegador
@Path("/home") 
public class Home {
	
	private EventBus bus;
	
	public Home(EventBus bus) {
		this.bus = bus;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML) //@Produces(MediaType.TEXT_PLAIN) // (MediaType.APPLICATION_JSON)
	public String seleccion(){
		String link1 = "http://localhost:8080/home/frio";
		String link2 = "http://localhost:8080/home/calido";
		
		String result =	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY><blockquote>"
				+ "</br></br>Escoge el tipo de pez en el estanque"
				+ "</br></br></br> <A href="+ link1 +">tipo de pez frio</A></br></br>"
				+ "<A href="+ link2 +">tipo de pez calido</A></br></br>"
				+ "</blockquote></BODY></HTML>";
		return result ;
	}
	
	@Path("{pez}")
	@GET
	@Produces(MediaType.TEXT_HTML) //(MediaType.APPLICATION_JSON) 
	public String resultado(@PathParam("pez") String pez) {

		bus.post(pez);
		String atras = "http://localhost:8080/home";
		
		String result =	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY><blockquote>"
				+ "</br></br>El tipo de pez que has escogido fue: " + pez +"</br></br>"
				+ "<A href="+ atras +">devolverse y escoger otro tipo de pez</A></br></br>"
				+ "</blockquote></BODY></HTML>";
		return  result ;
	}
	
	@POST
    @Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response sendEmail(@FormParam("email") String email) {
        System.out.println(email);
        return Response.ok("email=" + email).build();
    }
	
}