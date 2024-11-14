package fr.insa.soa.rest.RestTD;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.Response;

public class ClientRest {
	public static void main(String [] args) {
		Client client =ClientBuilder.newClient();
		
		Response response=client.target("http://localhost:8080/RestTD/webapi/comparator/longueur/Toulouse").request().get();
				
		System.out.println(response.readEntity(String.class));
	}
}
