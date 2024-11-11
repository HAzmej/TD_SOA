package fr.insa.ws.soap.Restproject;

import java.util.ArrayList;

public class Etudiant {
	private int id;
	private String nom;
	private String prenom;
	private Binome binome;
	private Stage stage;
	private ArrayList<Link> links=new ArrayList<Link>();
	
	public int getid() {
		return id;
	}
	
	public void setid(int id) {
		this.id=id;
	}
	public String getnom() {
		return nom;
	}
	
	public void setnom(String nom) {
		this.nom=nom;
	}
	
	public String getprenom() {
		return prenom;
	}
	
	public void setprenom(String prenom) {
		this.prenom=prenom;
	}
	
	public Binome getbinome() {
		return binome;
	}
	
	public void setBinome(Binome binome ) {
		this.binome=binome;
	}
	
	public void addLink(String uri, String rel, String methode) {
		Link newlink=new Link();
		newlink.setUri(uri);
		newlink.setRel(rel);
		newlink.setMeth(methode);
		links.add(newlink);
	}
	
	public ArrayList<Link> getLinks(){
		return links;
	}

}
