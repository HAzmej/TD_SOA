package fr.insa.ws.soa.Rest;

import fr.insa.ws.soa.Rest.User;
import fr.insa.ws.soa.Rest.Mission;
import fr.insa.ws.soa.Rest.UserService;
import fr.insa.ws.soa.Rest.MissionService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("user")
public class UserService {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserWithID (@Context UriInfo uri)
    {
		User user = new User();
        user.setpseudo("Artus");
        user.setid(5);
        Mission mission = new Mission();
        mission.settext("TestMission22");
        mission.setstatus("Status: En cours 234");
        user.setMission(mission);
        user.addLink(getUriforSelf(uri,user), "self","GET");
        user.addLink(getUriforMission(uri),"Mission", "GET");
        return user;
    }
	private String getUriforMission(UriInfo uri) {
		String url=uri.getBaseUriBuilder()
				.path(MissionService.class)
				.build()
				.toString();
		return url;
	}
	
	private String getUriforSelf(UriInfo uri, User user) {
		String url=uri.getBaseUriBuilder()
				.path(UserService.class)
				.path(Long.toString(user.getid()))
				.build()
				.toString();
		return url;
	}
	
	@Path("/{id}")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserWithID (@PathParam("id") int id)
    {
		User user = new User();
        user.setid(id);
        user.setpseudo("Hazem");
        Mission mission = new Mission();
        mission.settext("TestMission");
        mission.setstatus("Status: En cours");
        user.setMission(mission);
        return user;
    }
}
