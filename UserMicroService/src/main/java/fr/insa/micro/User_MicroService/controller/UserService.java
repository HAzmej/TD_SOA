package fr.insa.micro.User_MicroService.controller;

import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.micro.Mission_MicroService.model.Mission;




@RestController
@RequestMapping()
@CrossOrigin(origins = "http://frontend:8000")  // Où "frontend" est le nom de service dans Docker Compose
public class UserService {

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

	@GetMapping("/users")
	public ArrayList<User> getalluser() {
	    ArrayList<User> users = new ArrayList<>();
	    
//	        Class.forName("com.mysql.cj.jdbc.Driver"); // Charger le driver explicitement
	        try {
	        	Connection conn = DriverManager.getConnection(dbUri, dblogin, dbpwd);
	        	System.out.println("Connecte au Drive");
	            String sql = "SELECT * FROM User";
	            try (PreparedStatement stmt = conn.prepareStatement(sql);
	                 ResultSet rs = stmt.executeQuery()) {

	   
	                if (!rs.isBeforeFirst()) {
	                    System.out.println("Aucun utilisateur trouvé.");
	                    return users; 
	                }

	        
	                while (rs.next()) {
	                    String pseudo = rs.getString("pseudo");
	                    int type = rs.getInt("type");
	                    int idd=rs.getInt("id");

	                    User user = new User();
	                    user.setpseudo(pseudo);
	                    user.settype(type);
	                    user.setid(idd);
//	                    user.addLink(getUriforSelf(uri, user), "Self", "GET");
	                    users.add(user);
	                    System.out.println("Utilisateur récupéré : " + pseudo);
	                }
	            }
	        
	    } catch (SQLException e) {
	        System.err.println("Erreur lors de la connexion ou de l'exécution de la requête SQL.");
	        e.printStackTrace();
	    } 
	    //System.out.println("dbhost"+this.getDbHost());
	    return users;
	}
	
	//affichage de users par type
	@GetMapping("{type}")
	public ArrayList<User> getalluserbytype(@PathVariable("type") int type) {
	    ArrayList<User> users = new ArrayList<>();
//	        Class.forName("com.mysql.cj.jdbc.Driver"); // Charger le driver explicitement
	        try {
	        	Connection conn = DriverManager.getConnection(dbUri, dblogin, dbpwd);
	        	System.out.println("connecteeeeee");
	            String sql = "SELECT * FROM User WHERE type= ?";
	            try (PreparedStatement stmt = conn.prepareStatement(sql)){
	      
	            		stmt.setInt(1, type);
		                
		                try (ResultSet rs = stmt.executeQuery()) {
	            	

	   
			                if (!rs.isBeforeFirst()) {
			                    System.out.println("Aucun utilisateur trouvé.");
			                    return users; 
			                }
		
			        
			                while (rs.next()) {
			                    String pseudo = rs.getString("pseudo");
			                    int types = rs.getInt("type");
			                    int idd=rs.getInt("id");
		
			                    User user = new User();
			                    user.setpseudo(pseudo);
			                    user.settype(types);
			                    user.setid(idd); 
		//	                    user.addLink(getUriforSelf(uri, user), "Self", "GET");	                   
			                    users.add(user);
			                    System.out.println("Utilisateur récupéré : " + pseudo);
			                }
			            }
	            }
	        
	    } catch (SQLException e) {
	        System.err.println("Erreur lors de la connexion ou de l'exécution de la requête SQL.");
	        e.printStackTrace();
	    } 
	    //System.out.println("dbhost"+this.getDbHost());
	    return users;
	}
	
	@PostMapping
    public void addUser (@RequestBody User user) throws SQLException, ClassNotFoundException
    {
        System.out.println("User with the name " + user.getpseudo() );
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver"); 
			try (Connection conn = DriverManager.getConnection(dbUri, dblogin, dbpwd)) {

				// Ajouter User
			    String sql = "INSERT INTO User(pseudo, mdp,type) VALUES (?,?, ?)";
			    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			        stmt.setString(1, user.getpseudo());
			        stmt.setString(2, user.getmdp());
			        stmt.setInt(3, user.gettype());

			        int rowsAffected = stmt.executeUpdate();

			        if (rowsAffected > 0) {
			            System.out.println("Ajout réussi !");
			        }
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if (e.getErrorCode() == 1062) {  // Code d'erreur pour "Duplicate entry" en MySQL
	            System.out.println("Erreur : Le pseudo est déjà utilisé.");
	        } else {
	            System.out.println("Erreur SQL : " + e.getMessage());
	        }
		} 

    }

	
//	private String getUriforMission(UriInfo uri) {
//		String url=uri.getBaseUriBuilder()
//				.path(MissionService.class)
//				.build()
//				.toString();
//		return url;
//	}
//	
//	private String getUriforSelf(UriInfo uri, User user) {
//		String url=uri.getBaseUriBuilder()
//				.path(UserService.class)
//				.path(Long.toString(user.getid()))
//				.build()
//				.toString();
//		return url;
//	}
	
	@GetMapping("users/{id}")
	public User getUserWithID(@PathVariable("id") int id) {
	    User user = new User();
	    System.out.println("Cherche User: "+id);
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver"); 
	        System.out.println(id);
	        try (Connection conn = DriverManager.getConnection(dbUri, dblogin, dbpwd)) {
	            String sql = "SELECT * FROM User WHERE id = ?";
	            
	            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	                stmt.setInt(1, id);
	                
	                try (ResultSet rs = stmt.executeQuery()) {
	                    
	                    if (rs.next()) {
	                        String pseudo = rs.getString("pseudo");
	                        int type = rs.getInt("type");
	                    

	                        user.setpseudo(pseudo);
	                        user.settype(type);
	                        user.setid(id);
	                        //user.addLink(getUriforMission(uri),"Mission", "GET");
	                  
	                        System.out.println("Utilisateur trouvé avec Pseudo : " + pseudo);
	                    } else {
	                        System.out.println("Aucun utilisateur trouvé avec l'id : " + id);
	                        return null; 
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
	    return user;
	}
//	
	
	
	@PostMapping("/users/{id}")
    public void addMission (@PathVariable("id") int id ,@RequestBody Mission missions) throws SQLException, ClassNotFoundException
    {
        System.out.println("Mission with the name " + missions.gettext() );
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver"); 
			try (Connection conn = DriverManager.getConnection(dbUri, dblogin, dbpwd)) {

				// Ajouter Mission
			    String sql = "INSERT INTO Mission(titre,statut, responsable_id) VALUES (?,?, ?)";
			    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			        stmt.setString(1, missions.gettext());
			        stmt.setString(2, "En attente");
			        stmt.setInt(3, id);

			        int rowsAffected = stmt.executeUpdate();

			        if (rowsAffected > 0) {
			            System.out.println("Ajout réussi !");
			        }
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

    }
	
	
}

