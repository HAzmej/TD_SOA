package fr.insa.micro.Mission_MicroService.controlleur;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.micro.Mission_MicroService.model.Mission;



@RestController
@RequestMapping("/missions")
public class MissionRessource {
	@Value("${server.port}")
	private String serverPort;
	
	@Value("${db.connection}")
	private String typeConnectionDB;
	
	@Value("${db.host}")
	private String dbHost;
	
	@Value("${db.port}")
	private String dbPort;
	
	@Value("${db.uri}")
	private String dbUri;
	
	@Value("${db.name}")
	private String dbname;
	
	@Value("${db.login}")
	private String dblogin;
	
	@Value("${db.pwd}")
	private String dbpwd;
	
	@GetMapping("/serverport")
	public String getServerPort() {
		return serverPort;
	}
	
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	
	@GetMapping("/typecnx")
	public String getTypeConnectionDB() {
		return typeConnectionDB;
	}
	
	public void setTypeConnectionDB(String typeConnectionDB) {
		this.typeConnectionDB = typeConnectionDB;
	}
	
	@GetMapping("/dbhost")
	public String getDbHost() {
		return dbHost;
	}
	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}
	
	@GetMapping("/dbport")
	public String getDbPort() {
		return dbPort;
	}
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	
	@GetMapping("/dburi")
	public String getDbUri() {
		return dbUri;
	}
	public void setDbUri(String dbUri) {
		this.dbUri = dbUri;
	}
	
	@GetMapping("/dbname")
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	
	@GetMapping("/dblogin")
	public String getDblogin() {
		return dblogin;
	}
	public void setDblogin(String dblogin) {
		this.dblogin = dblogin;
	}
	
	@GetMapping("/dbpwd")
	public String getDbpwd() {
		return dbpwd;
	}
	public void setDbpwd(String dbpwd) {
		this.dbpwd = dbpwd;
	}
//	private static final String JDBC_URL ="jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_061";
//	private static final String JDBC_USER="projet_gei_061";
//	private static final String JDBC_PASSWORD ="woo2Phie";
	
	@GetMapping()
	public ArrayList<Mission> getallmission() throws ClassNotFoundException{
		ArrayList<Mission>missions=new ArrayList<Mission>();
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver"); // Charger le driver explicitement
		    try {
		    	Connection conn = DriverManager.getConnection(dbUri, dblogin, dbpwd);
		    	System.out.println("Connecté au Driver SQL");
		        String sql = "SELECT * FROM Mission";
		        try (PreparedStatement stmt = conn.prepareStatement(sql);
		             ResultSet rs = stmt.executeQuery()) {
		            if (!rs.isBeforeFirst()) {
		                System.out.println("Aucune Mission trouvé.");
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

	@GetMapping("/user/{id}")
	public ArrayList<Mission> getmissionsWithuser(@PathVariable("id") int id) throws ClassNotFoundException {
		ArrayList<Mission>missions=new ArrayList<Mission>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver"); 
	        
	        try (Connection conn = DriverManager.getConnection(dbUri, dblogin, dbpwd)) {
	            String sql = "SELECT * FROM Mission WHERE responsable_id = ?";
	            
	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                stmt.setInt(1, id);
	                
	                try (ResultSet rs = stmt.executeQuery()) {
	                    
	                	while (rs.next()) {
	    	                String text = rs.getString("titre");
	    	                //int idd = rs.getInt("responsable_id");
	    	                
	    	                String statut=rs.getString("statut");
	    	                int idm=rs.getInt("mission_id");
	    	
	    	                Mission mission= new Mission();
	    	                mission.setidm(idm);
	    	                mission.settext(text);
	    	                mission.setstatus(statut);
	    	                mission.setid(id);
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
	
	@GetMapping("/{id}")
	public ArrayList<Mission> getmissionsWithID(@PathVariable("id") int id) throws ClassNotFoundException {
		ArrayList<Mission>missions=new ArrayList<Mission>();
	    	    
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver"); 	        
	        try (Connection conn = DriverManager.getConnection(dbUri, dblogin, dbpwd)) {
	            String sql = "SELECT * FROM Mission WHERE mission_id = ?";	            
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
			try (Connection conn = DriverManager.getConnection(dbUri, dblogin, dbpwd)) {
				
				// Update state Mission
			    String sql = "UPDATE Mission SET statut = ? WHERE mission_id=?";
			    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			        stmt.setInt(2, id);
			        stmt.setString(1, state);
			    
			        int rowsAffected = stmt.executeUpdate();
			        if (rowsAffected > 0) {
			            System.out.println("Changement state réussi de la mission: "+id);
			        }
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @PutMapping("/user/{id}/{mission_id}")
    public int AffecterBenevMission(@PathVariable ("id") int id, @PathVariable("mission_id") int mission_id) throws ClassNotFoundException {
    	try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(dbUri, dblogin, dbpwd)) {
				
				//check si id existe et correspond a un benev
				String sql1 = "SELECT id FROM User WHERE id = ? AND type = ?";
		        try (PreparedStatement checkStmt = conn.prepareStatement(sql1)) {
	                checkStmt.setInt(1, id);
	                checkStmt.setInt(2, 2);
	                try (ResultSet rs = checkStmt.executeQuery()) {
	                    if (rs.next()) {
	                        System.out.println("L'id '" + id + "' est un Benevole.");
	                        ; // Retourne false si le pseudo est déjà pris
	                    } else {
	                    	System.out.println("Erreur : L'id '" + id + "' n'est pas un Benevole.");
	                    	return -1;
	                    }
	                }
	            }
		        
		        
		        String sql2 = "SELECT * FROM Mission WHERE mission_id = ? ";
		        try (PreparedStatement checkStmt = conn.prepareStatement(sql2)) {
	                checkStmt.setInt(1, id);
	                try (ResultSet rs = checkStmt.executeQuery()) {
	                    if (rs.next()) {
	                        System.out.println("L'id mission '" + mission_id + "' existe.");
	                        ; 
	                    } else {
	                    	System.out.println("Erreur : L'id mission'" + mission_id + "' n'existe pas.");
	                    	return -1;
	                    }
	                }
	            }
				// Update state Mission
			    String sql = "UPDATE Mission SET Benev_id = ? WHERE mission_id=?";
			    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			    	stmt.setInt(1, id);
			    	stmt.setInt(2, mission_id);
			    	
			        int rowsAffected = stmt.executeUpdate();
			        if (rowsAffected > 0) {
			            System.out.println("Affectation du Benev "+id+" réussi de la mission"+mission_id);
			            return 0;
			        }
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0;
    }

}

