package uis.brt.web;

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

import uis.brt.context.ContextInformation;

//el nombre utilizado dentro de path es el que se colocara en el navegador
@Path("/home") 
public class Home {
	
	private EventBus bus;
	private ContextInformation context;
	private List<ContextInformation> pond;
	
	String POST ="POST", pez ="pez", text="text", submit ="submit"; 
	String estanque = "estanque", group = "group", element = "element", fish = "fish", weather = "weather", cycle = "cycle";
	String id1="id1", id2="id2", id3="id3", type1="type1", type2="type2", type3="type3";
	String linkhome = "http://localhost:8080/home";
	String linkagre = "http://localhost:8080/home/agregar";
	String linkmodi = "http://localhost:8080/modificar";
	String linkelim = "http://localhost:8080/home/eliminar";
	String result;
	
	public Home(EventBus bus, ContextInformation context, List<ContextInformation> pond) {
		this.bus = bus;
		this.context = context;
		this.pond = pond;
	}

	@GET // pagina inicial donde el usuario se comunicara con el sistema
	@Produces(MediaType.TEXT_HTML)//({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON}) //@Produces(MediaType.TEXT_PLAIN) // (MediaType.APPLICATION_JSON)
	public String seleccion(){
	
		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY>"
				+ "<form action="+ linkhome +" method="+POST+">"
				+ "<blockquote></br></br>ESTAS EN 'HOME', ESCOGE LA OPCION QUE DESEA REALIZAR: </br></br></br>"
				+ "(los siguientes links se realizaran por metodo get) </br></br>"
				+ "<A href="+ linkagre +">AGREGAR un estanque</A></br></br>"
				+ "<A href="+ linkmodi +">MODIFICAR un estanque</A></br></br>"
				+ "<A href="+ linkelim +">ELIMINAR un estanque</A></br></br>"
				//+ "(si se utiliza el boton submit, llena datos y se realizara por metodo post)</br></br>"
				//+ "tipo de pez: <input name="+pez+" type="+text+" /></br></br>"
				//+ "<input type="+submit+" value="+submit+" />"
				+ "</blockquote></form></BODY></HTML>";
		return result ;
	}

	
	
	@GET // pagina que recibira los datos de nuevos estanques para el sistema
	@Path("/agregar")
	@Produces(MediaType.TEXT_HTML)//({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON}) //@Produces(MediaType.TEXT_PLAIN) // (MediaType.APPLICATION_JSON)
	public String agregar(){
		
		
		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY>"
				+ "<form action="+ linkhome +" method="+POST+">" // el form action es donde debe ir a buscar el POST
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
    							 @FormParam("id1") String id1, @FormParam("id2") String id2, @FormParam("id3") String id3,
    							 @FormParam("type1") String type1, @FormParam("type2") String type2, @FormParam("type3") String type3){
		//bus.post(pez);
		context = new ContextInformation();
		HashMap<String, String> ex1 = new HashMap<String, String>();
		ex1.put(id1, type1); ex1.put(id2, type2); ex1.put(id3, type3);
		context.testing(group, element, fish, weather, cycle, ex1);//configadmin.getRoute());
		pond.add(context);

		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY><blockquote>"
				+ "</br></br>Estanque agregado con exito: " + element +"</br></br>"
				+ "utilizando el metodo POST</br></br>"
				+ "<A href="+ linkhome +">DEVOLVERSE A 'HOME'</A></br></br>"
				+ "</blockquote></BODY></HTML>";
		//System.out.println("instancias guardadas desde post " + pond);
		return  result ;
	}
	
	
	
	

	
	
	
/*
	@Path("{pez}")
	@GET
	@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON}) //(MediaType.APPLICATION_JSON) 
	public String resultado(@PathParam("pez") String pez) {
		bus.post(pez);
		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY><blockquote>"
				+ "</br></br>El tipo de pez que has escogido fue: " + pez +"</br></br>"
				+ "utilizando el metodo GET</br></br>"
				+ "<A href="+ linkhome +">DEVOLVERSE A 'HOME'</A></br></br>"
				+ "</blockquote></BODY></HTML>";
		return  result ;
	}
	
	@POST
    @Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String registropez(@FormParam("pez") String pez){
		bus.post(pez);
		
		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY><blockquote>"
				+ "</br></br>El tipo de pez que has escogido fue: " + pez +"</br></br>"
				+ "utilizando el metodo POST</br></br>"
				+ "<A href="+ linkhome +">DEVOLVERSE A 'HOME'</A></br></br>"
				+ "</blockquote></BODY></HTML>";
		return  result ;
	}
*/
}