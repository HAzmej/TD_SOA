package fr.insa.ws.soap;
import javax.jws.WebService;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName="User")
public class Gestion {
	
	private static final String JDBC_URL ="jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_061";
	private static final String JDBC_USER="projet_gei_061";
	private static final String JDBC_PASSWORD ="woo2Phie";
			
	@WebMethod(operationName="Add")
	public int addUser(@WebParam String pseudo, @WebParam String mdp, @WebParam int n) throws SQLException {
	    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
	        // Ajouter l'utilisateur
	        String sql = "INSERT INTO User(pseudo, mdp, type) VALUES (?, ?, ?)";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, pseudo);
	            stmt.setString(2, mdp);
	            stmt.setInt(3, n);

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
	    return n;
	}
	
	@WebMethod(operationName="RemoveByPseudo")
	public int removeUserByPseudo(@WebParam String pseudo) throws SQLException {
	    int rowsAffected = 0;
	    
	    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
	        // Requête pour supprimer l'utilisateur avec le pseudo spécifié
	        String sql = "DELETE FROM User WHERE pseudo = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, pseudo);
	            
	            rowsAffected = stmt.executeUpdate();
	            
	            if (rowsAffected > 0) {
	                System.out.println("Utilisateur supprimé avec succès.");
	            } else {
	                System.out.println("Aucun utilisateur trouvé avec ce pseudo.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Erreur SQL : " + e.getMessage());
	    }    
	    return rowsAffected;
	}
	
	@WebMethod(operationName="ModifyPseudo")
	public int modifyPseudoById(@WebParam int id, @WebParam String new_pseudo) throws SQLException {
	    
	    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
	        // Requête pour supprimer l'utilisateur avec le pseudo spécifié
	        String sql = "SELECT id FROM User WHERE pseudo = ?";
	        try (PreparedStatement checkStmt = conn.prepareStatement(sql)) {
                checkStmt.setString(1, new_pseudo);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Erreur : Le pseudo '" + new_pseudo + "' est déjà utilisé.");
                        return -1; // Retourne false si le pseudo est déjà pris
                    }
                }
            }
	       
	       String sql1 ="UPDATE User SET pseudo=? WHERE id=?";
	       try (PreparedStatement stmt = conn.prepareStatement(sql1)) {
               stmt.setString(1, new_pseudo);
               stmt.setInt(2, id);

               int rowsAffected = stmt.executeUpdate();

               if (rowsAffected > 0) {
                   System.out.println("Mise à jour du pseudo réussie !");
                   return id; // Retourne true si la mise à jour est réussie
               } else {
                   System.out.println("Erreur : Utilisateur introuvable.");
                   return -1; // Retourne false si aucun utilisateur n'est trouvé avec cet ID
               }
           }
	    }
	       
	       
	}
}
