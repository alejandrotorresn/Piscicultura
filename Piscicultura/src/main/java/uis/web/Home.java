package uis.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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

import uis.context.ContextInformation;

//el nombre utilizado dentro de path es el que se colocara en el navegador
@Path("/home") 
public class Home {
	
	private EventBus bus;
	private ContextInformation context;
	private List<ContextInformation> pond;
	private String result, ip, linkhome, linkagre, linkmodi, linkcons, linkelim;
	
	public Home(EventBus bus, ContextInformation context, List<ContextInformation> pond, String ip) {
		this.bus = bus;
		this.context = context;
		this.pond = pond;
		this.ip = ip;
		linkhome = "http://"+ip+":8080/home/";
		linkagre = "http://"+ip+":8080/agrega/";
		linkmodi = "http://"+ip+":8080/modifica/";
		linkcons = "http://"+ip+":8080/consulta/";
		linkelim = "http://"+ip+":8080/elimina/";
	}

	@GET // pagina inicial donde el usuario se comunicara con el sistema
	@Produces(MediaType.TEXT_HTML)//({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON}) //@Produces(MediaType.TEXT_PLAIN) // (MediaType.APPLICATION_JSON)
	public String seleccion(){
	
		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY>"
				//+ "<form action="+ linkhome +" method="+POST+">"
				+ "<blockquote></br></br>ESTAS EN 'HOME', ESCOGE LA OPCION QUE DESEA REALIZAR: </br></br></br>"
				+ "(los siguientes links se realizaran por metodo get) </br></br>"
				+ "<A href="+ linkcons +">CONSULTAR un estanque</A></br></br>"
				+ "<A href="+ linkagre +">AGREGAR un estanque</A></br></br>"
				+ "<A href="+ linkmodi +">MODIFICAR un estanque</A></br></br>"
				+ "<A href="+ linkelim +">ELIMINAR un estanque</A></br></br>"
				//+ "(si se utiliza el boton submit, llena datos y se realizara por metodo post)</br></br>"
				//+ "tipo de pez: <input name="+pez+" type="+text+" /></br></br>"
				//+ "<input type="+submit+" value="+submit+" />"
				+ "</blockquote>"
				//+ "</form>"
				+ "</BODY></HTML>";
		return result ;
	}

	//getters and setters
	public String getLinkhome() {
		return linkhome;
	}
	public void setLinkhome(String linkhome) {
		this.linkhome = linkhome;
	}

	public String getLinkagre() {
		return linkagre;
	}
	public void setLinkagre(String linkagre) {
		this.linkagre = linkagre;
	}

	public String getLinkmodi() {
		return linkmodi;
	}
	public void setLinkmodi(String linkmodi) {
		this.linkmodi = linkmodi;
	}

	public String getLinkcons() {
		return linkcons;
	}
	public void setLinkcons(String linkcons) {
		this.linkcons = linkcons;
	}

	public String getLinkelim() {
		return linkelim;
	}
	public void setLinkelim(String linkelim) {
		this.linkelim = linkelim;
	}
}