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

import com.google.common.eventbus.EventBus;

import uis.context.ContextInformation;

@Path("/elimina") 
public class Delete {

	private EventBus bus;
	private ContextInformation context;
	private List<ContextInformation> pond;
	
	String POST ="POST", pez ="pez", text="text", submit ="submit"; 
	String estanque = "estanque", group = "group", element = "element", fish = "fish", weather = "weather", cycle = "cycle";
	String id1="id1", id2="id2", id3="id3", id4="id4", type1="type1", type2="type2", type3="type3", type4="type4";
	String result, linkhome, linkagre, linkmodi, linkcons, linkelim;
	
	public Delete(EventBus bus, ContextInformation context, List<ContextInformation> pond, Home home) {
		this.bus = bus;
		this.context = context;
		this.pond = pond;
		linkhome = home.getLinkhome();
		linkagre = home.getLinkagre();
		linkmodi = home.getLinkmodi();
		linkcons = home.getLinkcons();
		linkelim = home.getLinkelim();
	}
	
	
	
	@GET // pagina que SELECCIONARA UN estanque del sistema PARA eliminarlo
	//@Path("/elimina")
	@Produces(MediaType.TEXT_HTML)//({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON}) //@Produces(MediaType.TEXT_PLAIN) // (MediaType.APPLICATION_JSON)
	public String SeleccModificar(){
		
		String link;
		
		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY>"
				+ "<blockquote></br></br>SELECCIONE EL ESTANQUE QUE DESEA ELIMINAR: </br></br></br>";
		
		for(ContextInformation und : pond){
			link = linkelim;
			result += "<A href="+ link.concat(und.toString()) +">Grupo # " + und.getGroup() + " Estanque # " + und.getElement() + "</A></br></br>";
		}

		result += "<A href="+ linkhome +">DEVOLVERSE A 'HOME'</A></br></br>"
				+ "</blockquote></BODY></HTML>";
		return result ;
	}
	
	@GET // pagina que ELIMINARA UN estanque del sistema
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
					if(count == 4){ i4= x.getKey(); t4= x.getValue();}
					 count+=1;
				}
				break;
			}
		}
		
		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY>"
				+ "<form action="+ linkelim +" method="+POST+">" // el form action es donde debe ir a buscar el POST
				+ "<blockquote></br></br>El estanque que desea ELIMINAR contiene la siguiente informacion: </br></br></br>"
				+ "ESTANQUE        : <input name="+ estanque+" type="+text+" value="+ estan +" readonly/></br></br>"
				+ "NUMERO DE GRUPO : <input name="+ group   +" type="+text+" value="+ g +" readonly/></br></br>"
				+ "NUMERO ESTANQUE : <input name="+ element +" type="+text+" value="+ e +" readonly/></br></br>"
				+ "NOMBRE DEL PEZ  : <input name="+ fish    +" type="+text+" value="+ f +" readonly/></br></br>"
				+ "TIPO DE CLIMA   : <input name="+ weather +" type="+text+" value="+ w +" readonly/></br></br>"
				+ "CICLO PRODUCTIVO: <input name="+ cycle   +" type="+text+" value="+ c +" readonly/></br></br>"
				+ "SENSORES Y ACCIONADORES DEL ESTANQUE </br></br>"
				+ "ID: <input name="+ id1 +" type="+text+" value="+ i1 +" readonly/> TYPE: <input name="+ type1 +" type="+text+" value="+ t1 +" readonly/> </br></br>"
				+ "ID: <input name="+ id2 +" type="+text+" value="+ i2 +" readonly/> TYPE: <input name="+ type2 +" type="+text+" value="+ t2 +" readonly/> </br></br>"
				+ "ID: <input name="+ id3 +" type="+text+" value="+ i3 +" readonly/> TYPE: <input name="+ type3 +" type="+text+" value="+ t3 +" readonly/> </br></br>"
				+ "ID: <input name="+ id4 +" type="+text+" value="+ i4 +" readonly/> TYPE: <input name="+ type4 +" type="+text+" value="+ t4 +" readonly/> </br></br>"
				+ "Si esta seguro que desea ELIMINARLO pulse el boton de abajo</br></br>"
				//+ "(se enviaran los datos por metodo post) </br></br>"
				+ "<input type="+submit+" value="+submit+" /></br></br>"
				+ "<A href="+ linkhome +">DEVOLVERSE A 'HOME'</A></br></br>"
				+ "</blockquote></form></BODY></HTML>";
		return result ;
	}
	
	@POST // metodo que MODIFICARA EN EL sistema UN estanque EXISTENTE
    @Produces(MediaType.TEXT_HTML)//({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
	//@Consumes(MediaType.APPLICATION_JSON)//(MediaType.APPLICATION_FORM_URLENCODED)
    public String ModificarPorPost(@FormParam("estanque") String cadConf, @FormParam("group") String group, @FormParam("element") String element,
    							 @FormParam("fish") String fish, @FormParam("weather") String weather, @FormParam("cycle") String cycle,
    							 @FormParam("id1") String id1, @FormParam("id2") String id2, @FormParam("id3") String id3, @FormParam("id4") String id4,
    							 @FormParam("type1") String type1, @FormParam("type2") String type2, @FormParam("type3") String type3, @FormParam("type4") String type4){
		System.out.println("entro a metodo post");
		
		for(ContextInformation und : pond){
			if(cadConf.equals(und.toString())){
				pond.remove(und);
				System.out.println("\nEl estanque #" + element + " fue ELIMINADO exitosamente de la plataforma" );
				break;
		}}
		
		result=	"<HTML><HEAD><TITLE>PISCICULTURA</TITLE></HEAD><BODY><blockquote>"
				+ "</br></br>Estanque " + element + " ELIMINADO con exito en el grupo: " + group +"</br></br>"
				+ "utilizando el metodo POST</br></br>"
				+ "<A href="+ linkhome +">DEVOLVERSE A 'HOME'</A></br></br>"
				+ "</blockquote></BODY></HTML>";
		//System.out.println("instancias guardadas desde post " + pond);
		return  result ;
	}
}
