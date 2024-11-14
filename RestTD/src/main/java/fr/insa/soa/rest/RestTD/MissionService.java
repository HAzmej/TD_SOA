package fr.insa.soa.rest.RestTD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.insa.soa.rest.RestTD.Mission;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("mission")
public class MissionService {
	
	private static final String JDBC_URL ="jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_061";
	private static final String JDBC_USER="projet_gei_061";
	private static final String JDBC_PASSWORD ="woo2Phie";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Mission> getallmission(@Context UriInfo uri) throws ClassNotFoundException{
		ArrayList<Mission>missions=new ArrayList<Mission>();
        Class.forName("com.mysql.cj.jdbc.Driver"); // Charger le driver explicitement
	    try {
	    	Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
	    	System.out.println("connecteeeeee");
	        String sql = "SELECT * FROM Mission";
	        try (PreparedStatement stmt = conn.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {
	
	
	            if (!rs.isBeforeFirst()) {
	                System.out.println("Aucun utilisateur trouvé.");
	                return missions; 
	            }
	
	    
	            while (rs.next()) {
	                String text = rs.getString("titre");
	                int idd = rs.getInt("responsable_id");
	                String statut=rs.getString("statut");
	                int idm =rs.getInt("mission_id");
	                
	
	                Mission mission= new Mission();
	                mission.setidm(idm);
	                mission.settext(text);
	                mission.setstatus(statut);
	                mission.setid(idd);
	
	                
	
	              
	                //user.addLink(getUriforSelf(uri, user), "Self", "GET");
	
	               
	                missions.add(mission);
	
	                System.out.println("Mission récupéré : " + text);
	            }
	        }
	    
		} catch (SQLException e) {
		    System.err.println("Erreur lors de la connexion ou de l'exécution de la requête SQL.");
		    e.printStackTrace();
		} 
		
		return missions;
}
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Mission> getmissionWithuser(@PathParam("id") int id) throws ClassNotFoundException {
		ArrayList<Mission>missions=new ArrayList<Mission>();
	    User user = new User();
	    
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver"); 
	        
	        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
	            String sql = "SELECT * FROM Mission WHERE responsable_id = ?";
	            
	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                stmt.setInt(1, id);
	                
	                try (ResultSet rs = stmt.executeQuery()) {
	                    
	                	while (rs.next()) {
	    	                String text = rs.getString("titre");
	    	                int idd = rs.getInt("responsable_id");
	    	                String statut=rs.getString("statut");
	    	                int idm=rs.getInt("mission_id");
	    	
	    	                Mission mission= new Mission();
	    	                mission.setidm(idm);
	    	                mission.settext(text);
	    	                mission.setstatus(statut);
	    	                mission.setid(idd);
	    	
	    	                
	    	
	    	              
	    	                //user.addLink(getUriforSelf(uri, user), "Self", "GET");
	    	
	    	               
	    	                missions.add(mission);
	    	
	    	                System.out.println("Mission récupéré : " + text);
	    	            }
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        System.err.println("Driver MySQL non trouvé !");
	        e.printStackTrace();
	    }
	    return missions;
	}

	
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addMission (Mission missions)
    {
        System.out.println("mission with the name " + missions.gettext() );

    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void changestate(Mission mission) throws ClassNotFoundException {
    	try {
        	Class.forName("com.mysql.cj.jdbc.Driver"); 
        	
			try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
				System.out.println("mission with the name " + mission.getstatus() );
				// Ajouter Mission
			    String sql = "UPDATE Mission SET statut = ? WHERE mission_id=?";
			    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			        stmt.setInt(2, mission.getidm());
			        stmt.setString(1, mission.getstatus());
			    

			        int rowsAffected = stmt.executeUpdate();

			        if (rowsAffected > 0) {
			            System.out.println("Chnagement state réussi !de "+mission.getidm());
			        }
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
