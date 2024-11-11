package fr.insa.ws.soap.Restproject;

public class Link {
	private String uri;
	private String rel;
	private String methode;
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri=uri;
	}
	
	public String getRel() {
		return rel;
	}
	
	public void setRel(String rel) {
		this.rel=rel;
	}
	
	public String getMeth() {
		return methode;
	}
	
	public void setMeth(String methode) {
		this.methode=methode;
	}

}
