package fr.insa.ws.soap;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.net.MalformedURLException;

public class UserClient {
    public static void main(String[] args) throws MalformedURLException {
        // Spécifiez l'URL WSDL du service
        URL wsdlUrl = new URL("http://localhost:8080/?wsdl");

        // Nom du service et du port - doit correspondre à ceux définis dans Gestion.java
        QName serviceName = new QName("http://soap.ws.insa.fr/", "User");
        QName portName = new QName("http://soap.ws.insa.fr/", "GestionPort");

        // Création du service
        Service service = Service.create(wsdlUrl, serviceName);
        Gestion gestion = service.getPort(portName, Gestion.class);

        // Appel de la méthode addUser
        int type = 1;  
        try {
            int result = gestion.addUser("nouveauPseudo", "motDePasse123", type);
            System.out.println("Utilisateur ajouté avec type : " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
