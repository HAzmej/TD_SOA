package fr.insa.ws.soap;
import javax.xml.ws.Endpoint;
import java.net.MalformedURLException;


public class UserService {

	public static String host="localhost";
	public static short port=8080;
	
	public void demarrerService() {
		String url="http://"+host+":"+port+"/";
		Endpoint.publish(url, new Gestion());
		
		
	}
	
	public static void main (String [] args) throws MalformedURLException{
		new UserService().demarrerService();	
		System.out.println("User Service") ;
		
	}
}
