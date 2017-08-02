package uis.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.eventbus.EventBus;

import uis.context.ContextInformation;

@Path("/consulta") 
public class Consult {

	private EventBus bus;
	private ContextInformation context;
	private List<ContextInformation> pond;
	
	String result, linkhome, linkagre, linkmodi, linkcons, linkelim;
	
	public Consult(EventBus bus, ContextInformation context, List<ContextInformation> pond, Home home) {
		this.bus = bus;
		this.context = context;
		this.pond = pond;
		linkhome = home.getLinkhome();
		linkagre = home.getLinkagre();
		linkmodi = home.getLinkmodi();
		linkcons = home.getLinkcons();
		linkelim = home.getLinkelim();
	}
	
	
	
	@GET // pagina que SELECCIONARA PARA consultar UN estanque del sistema
	//@Path("/modifica")
	@Produces(MediaType.TEXT_HTML)//({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON}) //@Produces(MediaType.TEXT_PLAIN) // (MediaType.APPLICATION_JSON)
	public String SeleccConsult(){
		//String so = System.getProperty("os.name");
		//JsonObject result2 = Json.createObjectBuilder().add("so", so).build();
		
		String link;
		
		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY>"
				+ "<blockquote></br></br>SELECCIONE EL ESTANQUE QUE DESEA CONSULTAR: </br></br></br>";
		
		for(ContextInformation und : pond){
			link = linkcons;
			result += "<A href="+ link.concat(und.toString()) +">Grupo # " + und.getGroup() + " Estanque # " + und.getElement() + "</A></br></br>";
		}

		result += "<A href="+ linkhome +">DEVOLVERSE A 'HOME'</A></br></br>"
				+ "</blockquote></BODY></HTML>";
		return result.toString() ;
	}
	
	@GET // pagina que MODIFICARA los datos de LOS estanques del sistema
	@Path("/{estanque}")
	@Produces(MediaType.TEXT_HTML) //@Produces(MediaType.TEXT_PLAIN) // (MediaType.APPLICATION_JSON)
	public String modificar(@PathParam("estanque") String cadAmodif){
		
		String estan = cadAmodif, g ="", e ="", f ="", w ="", c ="", i1="", i2="", i3="", i4="", t1="", t2="", t3="", t4="";
		for(ContextInformation und : pond){
			if(cadAmodif.equals(und.toString())){
				g = und.getGroup(); e = und.getElement(); f = und.getFish(); w = und.getWeather(); c = und.getCycle();
				int count = 1;
				for(Entry<String, String> x : und.getDevices().entrySet()){
					if(count == 1){ i1= x.getKey(); t1 = x.getValue();}
					if(count == 2){ i2= x.getKey(); t2 = x.getValue();}
					if(count == 3){ i3= x.getKey(); t3 = x.getValue();}
					if(count == 4){ i4= x.getKey(); t4 = x.getValue();}
					 count+=1;
				}
			}
		}
		
		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY>"
				//+ "<form action="+ linkmodi +" method="+POST+">" // el form action es donde debe ir a buscar el POST
				+ "<blockquote></br></br>INFORMACION DEL ESTANQUE: </br></br></br>"
				+ "ESTANQUE        : "+ estan +" </br></br>"
				+ "NUMERO DE GRUPO : "+ g +" </br></br>"
				+ "NUMERO ESTANQUE : "+ e +" </br></br>"
				+ "NOMBRE DEL PEZ  : "+ f +" </br></br>"
				+ "TIPO DE CLIMA   : "+ w +" </br></br>"
				+ "CICLO PRODUCTIVO: "+ c +" </br></br>"
				+ "SENSORES Y ACCIONADORES DEL ESTANQUE </br></br>"
				+ "ID: "+ i1 +": "+ t1 +"  </br></br>"
				+ "ID: "+ i2 +": "+ t2 +"  </br></br>"
				+ "ID: "+ i3 +": "+ t3 +"  </br></br>"
				+ "ID: "+ i4 +": "+ t4 +"  </br></br>"
				//+ "(se enviaran los datos por metodo post) </br></br>"
				//+ "<input type="+submit+" value="+submit+" /></br></br>"
				+ "<A href="+ linkhome +">DEVOLVERSE A 'HOME'</A></br></br>"
				+ "</blockquote>"
				//+ "</form>"
				+ "</BODY></HTML>";
		return result ;
	}
	
}
