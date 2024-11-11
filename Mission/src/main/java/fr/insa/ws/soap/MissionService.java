package fr.insa.ws.soap;
import javax.xml.ws.Endpoint;
import java.net.MalformedURLException;


public class MissionService {

	public static String host="localhost";
	public static short port=8081;
	
	public void demarrerService() {
		String url="http://"+host+":"+port+"/";
		Endpoint.publish(url, new GestionMission());
		
		
	}
	
	public static void main (String [] args) throws MalformedURLException{
		new MissionService().demarrerService();	
		System.out.println("Mission Service") ;
		
	}
}
