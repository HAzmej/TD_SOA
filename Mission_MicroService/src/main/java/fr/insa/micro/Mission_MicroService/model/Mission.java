package fr.insa.micro.Mission_MicroService.model;

public class Mission {
	
	private int idm;
	private String text;
	private int responsable_id;
	private String status;
	
	public int getidm() {
		return idm;
	}
	
	public void setidm(int idm) {
		this.idm=idm;
	}
	public int getid() {
		return responsable_id;
	}
	
	public void setid(int id) {
		this.responsable_id=id;
	}	
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

