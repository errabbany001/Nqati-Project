package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    // Instance unique de la connexion (Pattern Singleton)
    private static Connection con = null;

    
    public static Connection getConnexion() {
        if (con == null) {
            try {
                // Paramètres de connexion
                // Remplace 'nqati_db' par le nom exact de ta base de données
                String url = "jdbc:mysql://localhost:3306/nqati_db?serverTimezone=UTC";
                String user = "root";
                String password = "YASSine2002@";

                // Établissement de la connexion
                con = DriverManager.getConnection(url, user, password);
                System.out.println("Connexion réussie avec MySQL 9.x !");
                
            } catch (SQLException e) {
                // Affichage de l'erreur en cas de problème
                System.out.println("Erreur de connexion : " + e.getMessage());
            }
        }
        return con;
    }


  
    //Fermer la connexion proprement avant de quitter l'application
 
    public static void closeConnexion() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connexion fermée avec succès.");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture : " + e.getMessage());
            }
        }
}
}