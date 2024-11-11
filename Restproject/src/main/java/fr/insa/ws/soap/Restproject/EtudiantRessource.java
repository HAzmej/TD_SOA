package fr.insa.ws.soap.Restproject;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("etudiant")
public class EtudiantRessource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Etudiant getetudiant(@Context UriInfo uri) {
		Etudiant etudiant =new Etudiant();
		etudiant.setnom("N");
		etudiant.setprenom("G");
		etudiant.setid(01);
		etudiant.addLink(getUriforSelf(uri,etudiant), "self","GET");
		etudiant.addLink(getUriforStage(uri),"Stage", "GET");
		return etudiant;
		
	}
	
	private String getUriforStage(UriInfo uri) {
		String url=uri.getBaseUriBuilder()
				.path(StageRessource.class)
				.build()
				.toString();
		return url;
	}
	
	private String getUriforSelf(UriInfo uri, Etudiant etudiant) {
		String url=uri.getBaseUriBuilder()
				.path(EtudiantRessource.class)
				.path(Long.toString(etudiant.getid()))
				.build()
				.toString();
		return url;
	}
	
	@GET
	@Path("/{idetud}")
	@Produces(MediaType.APPLICATION_JSON)
	public Etudiant getEtudiant(@PathParam("idetud") int id) {
		Etudiant etudiant =new Etudiant();
		etudiant.setnom("A");
		etudiant.setprenom("B");
		return etudiant;
	}
	
	@POST
	@Path("/{idetud}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addetudiant(Etudiant nouv) {
		System.out.println("Ajout de l'etudiant "+nouv.getnom()+" "+nouv.getprenom()+" résussie");
		System.out.println("Son binome est " +nouv.getbinome().getnom()+" "+nouv.getbinome().getprenom());
		
	}
}
