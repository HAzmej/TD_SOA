package fr.insa.ws.soa.Rest;

import fr.insa.ws.soa.Rest.Mission;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("mission")
public class MissionService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Mission getmission(int id) {
		Mission mission=new Mission();
		mission.settext("1st Mission");
		mission.setstatus("En cours//Acceptée");
		return mission;
	}
	
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUser (User user)
    {
        System.out.println("User with the name " + user.getpseudo() );
        int i=0;
        for (Mission mission : user.getMissions()) {
        	System.out.println(" and mission number" + i + "with text: "+ mission.gettext() + " with status: "+ mission.getstatus()+" /n");
        	i++;
        }
    }

}
