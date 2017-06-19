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
		String POST ="POST", pez ="pez", text="text", submit ="submit" ;
		String link0 = "http://localhost:8080/home";
		String link1 = "http://localhost:8080/home/frio";
		String link2 = "http://localhost:8080/home/calido";
		
		String result =	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY>"
				+ "<form action="+ link0 +" method="+POST+">"
				+ "<blockquote></br></br>ESCOGE EL TIPO DE PEZ EN EL ESTANQUE: </br></br></br>"
				+ "(los siguientes links se realizaran por metodo get) </br></br>"
				+ "<A href="+ link1 +">tipo de pez frio</A></br></br>"
				+ "<A href="+ link2 +">tipo de pez calido</A></br></br></br>"
				+ "(si se utiliza el boton submit, llena datos y se realizara por metodo post)</br></br>"
				+ "tipo de pez: <input name="+pez+" type="+text+" /></br></br>"
				+ "<input type="+submit+" value="+submit+" />"
				+ "</blockquote></form></BODY></HTML>";
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
				+ "utilizando el metodo GET</br></br>"
				+ "<A href="+ atras +">devolverse y escoger otro tipo de pez</A></br></br>"
				+ "</blockquote></BODY></HTML>";
		return  result ;
	}
	
	@POST
    @Produces(MediaType.TEXT_HTML)
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String registropez(@FormParam("pez") String pez){
		bus.post(pez);
		String atras = "http://localhost:8080/home";
		
		String result =	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY><blockquote>"
				+ "</br></br>El tipo de pez que has escogido fue: " + pez +"</br></br>"
				+ "utilizando el metodo POST</br></br>"
				+ "<A href="+ atras +">devolverse y escoger otro tipo de pez</A></br></br>"
				+ "</blockquote></BODY></HTML>";
		return  result ;
	}
}