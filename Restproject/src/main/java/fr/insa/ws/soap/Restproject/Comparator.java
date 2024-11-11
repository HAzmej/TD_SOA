package fr.insa.ws.soap.Restproject;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;;

@Path("comparator")
public class Comparator {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getlongueur() {
		return "Bonjouuur";
	}
	@GET 
	@Path("longueur/{chaine}")
	@Produces(MediaType.TEXT_PLAIN)
	public int getlongueur(@PathParam("chaine") String chaine) {
		System.out.println("je suis ici");
		return chaine.length();
	}
	
	@GET
	@Path("longueurdouble/{chaine}")
	@Produces(MediaType.TEXT_PLAIN)
	public int getlongueurdouble(@PathParam("chaine") String chaine) {
		return chaine.length()*2;
	}
	
	
	@PUT
	@Path("/{idetud}")
	@Produces(MediaType.TEXT_PLAIN)
	public int updateetud(@PathParam("idetud") int id) {
		System.out.println("mise a jour reussie !!!!");
		return id;
	}
}
