package fr.insa.micro.Mission_MicroService.controlleur;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.micro.Mission_MicroService.model.Mission;



@RestController
@RequestMapping("/miss")
public class MissionRessource {
	
	private static final String JDBC_URL ="jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_061";
	private static final String JDBC_USER="projet_gei_061";
	private static final String JDBC_PASSWORD ="woo2Phie";
	
	@GetMapping("/all")
	public ArrayList<Mission> getallmission() throws ClassNotFoundException{
		ArrayList<Mission>missions=new ArrayList<Mission>();
        try {
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
        } catch (ClassNotFoundException e) {
	        System.err.println("Driver MySQL non trouvé !");
	        e.printStackTrace();
	    }

		return missions;
}

	@GetMapping("/{id}")
	public ArrayList<Mission> getmissionWithuser(@PathVariable("id") int id) throws ClassNotFoundException {
		ArrayList<Mission>missions=new ArrayList<Mission>();
	    
	    
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

	
   
    
    @PutMapping("/{id}/{statut}")
    public void changestate(@PathVariable ("id") int id, @PathVariable("statut") String state) throws ClassNotFoundException {
    	try {
        	Class.forName("com.mysql.cj.jdbc.Driver"); 
        	
			try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
				
				// Ajouter Mission
			    String sql = "UPDATE Mission SET statut = ? WHERE mission_id=?";
			    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			        stmt.setInt(2, id);
			        stmt.setString(1, state);
			    

			        int rowsAffected = stmt.executeUpdate();

			        if (rowsAffected > 0) {
			            System.out.println("Chnagement state réussi !de "+id);
			        }
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

