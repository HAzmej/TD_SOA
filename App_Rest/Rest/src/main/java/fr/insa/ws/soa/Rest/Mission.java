package fr.insa.ws.soa.Rest;

public class Mission {
	
	private String text;
	private int id;
	private String status;
	
	public void settext(String text) {
		this.text=text;
	}
	
	public String gettext() {
		return text;
	}
	
	public String getstatus() {
		return status;
	}
	
	public void setstatus(String status) {
		this.status=status;
	}
		

}
