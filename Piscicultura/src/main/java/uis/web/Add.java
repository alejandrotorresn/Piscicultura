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
@Path("/agrega") 
public class Add {
	
	private EventBus bus;
	private ContextInformation context;
	private List<ContextInformation> pond;
	
	String POST ="POST", pez ="pez", text="text", submit ="submit"; 
	String estanque = "estanque", group = "group", element = "element", fish = "fish", weather = "weather", cycle = "cycle";
	String id1="id1", id2="id2", id3="id3", id4="id4", type1="type1", type2="type2", type3="type3", type4="type4";
	String result, linkhome, linkagre, linkmodi, linkcons, linkelim;
	
	public Add(EventBus bus, ContextInformation context, List<ContextInformation> pond, Home home) {
		this.bus = bus;
		this.context = context;
		this.pond = pond;
		linkhome = home.getLinkhome();
		linkagre = home.getLinkagre();
		linkmodi = home.getLinkmodi();
		linkcons = home.getLinkcons();
		linkelim = home.getLinkelim();
	}

	@GET // pagina que recibira los datos de nuevos estanques para el sistema
	//@Path("/agrega")
	@Produces(MediaType.TEXT_HTML)//({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON}) //@Produces(MediaType.TEXT_PLAIN) // (MediaType.APPLICATION_JSON)
	public String agregar(){
		
		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY>"
				+ "<form action="+ linkagre +" method="+POST+">" // el form action es donde debe ir a buscar el POST
				+ "<blockquote></br></br>AGREGUE LOS DATOS QUE SE SOLICITAN: </br></br></br>"
				+ "NUMERO DE GRUPO : <input name="+ group   +" type="+text+" /></br></br>"
				+ "NUMERO ESTANQUE : <input name="+ element +" type="+text+" /></br></br>"
				+ "NOMBRE DEL PEZ  : <input name="+ fish    +" type="+text+" /></br></br>"
				+ "TIPO DE CLIMA   : <input name="+ weather +" type="+text+" /></br></br>"
				+ "CICLO PRODUCTIVO: <input name="+ cycle   +" type="+text+" /></br></br>"
				+ "SENSORES Y ACCIONADORES DEL ESTANQUE </br></br>"
				+ "ID: <input name="+ id1 +" type="+text+" /> TYPE: <input name="+ type1 +" type="+text+" /> </br></br>"
				+ "ID: <input name="+ id2 +" type="+text+" /> TYPE: <input name="+ type2 +" type="+text+" /> </br></br>"
				+ "ID: <input name="+ id3 +" type="+text+" /> TYPE: <input name="+ type3 +" type="+text+" /> </br></br>"
				+ "ID: <input name="+ id4 +" type="+text+" /> TYPE: <input name="+ type4 +" type="+text+" /> </br></br>"
				+ "(se enviaran los datos por metodo post) </br></br>"
				+ "<input type="+submit+" value="+submit+" /></br></br>"
				+ "<A href="+ linkhome +">DEVOLVERSE A 'HOME'</A></br></br>"
				+ "</blockquote></form></BODY></HTML>";
		return result ;
	}
	
	@POST // metodo que AGREGARA al sistema los nuevos estanques que el usuario digite
    @Produces(MediaType.TEXT_HTML)//({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String AgregarPorPost(@FormParam("group") String group, @FormParam("element") String element,
    							 @FormParam("fish") String fish, @FormParam("weather") String weather, @FormParam("cycle") String cycle,
    							 @FormParam("id1") String id1, @FormParam("id2") String id2, @FormParam("id3") String id3, @FormParam("id4") String id4,
    							 @FormParam("type1") String type1, @FormParam("type2") String type2, @FormParam("type3") String type3, @FormParam("type4") String type4){
		
		context = new ContextInformation();
		HashMap<String, String> ex1 = new HashMap<String, String>();
		ex1.put(id1, type1); ex1.put(id2, type2); ex1.put(id3, type3); ex1.put(id4, type4);
		context.AddPond(group, element, fish, weather, cycle, ex1);
		pond.add(context);
		
		System.out.println("\nEstanque #" + element + " AGREGADO con exito\n" + context.information() + "\n");

		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY><blockquote>"
				+ "</br></br>Estanque " + element +" agregado con exito en el grupo: " + group +"</br></br>"
				+ "utilizando el metodo POST</br></br>"
				+ "<A href="+ linkhome +">DEVOLVERSE A 'HOME'</A></br></br>"
				+ "</blockquote></BODY></HTML>";
		//System.out.println("instancias guardadas desde post " + pond);
		return  result ;
	}
	
}