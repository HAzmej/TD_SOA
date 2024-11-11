package fr.insa.ws.soap;
import javax.jws.WebService;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName="Mission")
public class GestionMission {
	
	private static final String JDBC_URL ="jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_061";
	private static final String JDBC_USER="projet_gei_061";
	private static final String JDBC_PASSWORD ="woo2Phie";
			
	@WebMethod(operationName="AddMission")
	public int addMission(@WebParam String text, @WebParam int resp_id) throws SQLException {
		
	    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
	    	
	    	//Check Id Utilisateur
	    	String sql1 = "SELECT * FROM User WHERE id = ?";
	    	try (PreparedStatement checkStmt = conn.prepareStatement(sql1)) {
                checkStmt.setInt(1, resp_id);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Erreur : L'utilisateur avec l'ID " + resp_id + " n'existe pas.");
                        return -1; // Retourne -1 si l'utilisateur n'existe pas
                    }
                }
            }
	    	
	    	
	    	
	    	// Ajouter Mission
	        String sql = "INSERT INTO Mission(titre, responsable_id) VALUES (?, ?)";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, text);
	            stmt.setInt(2, resp_id);

	            int rowsAffected = stmt.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Ajout réussi !");
	            }
	        }
	    } catch (SQLException e) {
	        // Vérifie si l'erreur est liée à une violation de l'unicité du pseudo
	        if (e.getErrorCode() == 1062) {  // Code d'erreur pour "Duplicate entry" en MySQL
	            System.out.println("Erreur : Le pseudo est déjà utilisé.");
	        } else {
	            System.out.println("Erreur SQL : " + e.getMessage());
	        }
	    }
	    return resp_id;
	}
	
	@WebMethod(operationName="RemoveMissionById")
	public int removeMissionById(@WebParam int mission_id) throws SQLException {
	    
	    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
	        // Requête pour supprimer la mission avec l'id spécifié
	        String sql = "DELETE FROM Mission WHERE mission_id = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, mission_id);
	            
	            int rowsAffected = stmt.executeUpdate();
	            
	            if (rowsAffected > 0) {
	                System.out.println("Mission supprimée avec succès.");
	            } else {
	                System.out.println("Aucune Mission trouvée avec cet id.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Erreur SQL : " + e.getMessage());
	    }    
	    return mission_id;
	}
	
	
	@WebMethod(operationName="Change_Statut_Mission")
	public int changesstatut(@WebParam int mission_id, String statut) throws SQLException {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
			String sql1 ="UPDATE Mission SET statut=? WHERE mission_id=?";
		    try (PreparedStatement stmt = conn.prepareStatement(sql1)) {
		    	stmt.setString(1, statut);
	            stmt.setInt(2, mission_id);
	
	            int rowsAffected = stmt.executeUpdate();
	
	            if (rowsAffected > 0) {
	                System.out.println("Mise à jour de la mission réussie !");
	                return mission_id; // Retourne true si la mise à jour est réussie
	            } else {
	                System.out.println("Erreur : Mission introuvable.");
	                return -1; // Retourne false si aucune Mission n'est trouvée avec cet ID
	            }
	        }
		}
	}
	
	
}