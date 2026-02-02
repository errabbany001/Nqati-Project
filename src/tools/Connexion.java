package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static Connection con = null;

    public static Connection getConnexion() {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/project_nqaty?serverTimezone=UTC";
                String user = "root";
                String password = "YASSine2002@";

                con = DriverManager.getConnection(url, user, password);
                System.out.println("Connexion réussie avec MySQL 9.x !");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Erreur de connexion : " + e.getMessage());
            }
        }
        
        return con;
    }

    public static void closeConnexion() {
        if (con != null) {
            try {
                con.close();
                con = null; 
                System.out.println("Connexion fermée avec succès.");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture : " + e.getMessage());
            }
        }
    }

    public static Connection getCon() {
        return con;
    }
}