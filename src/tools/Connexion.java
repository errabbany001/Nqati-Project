package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static Connection con = null;

    public static Connection getConnexion() {
    try {
        if (con == null || con.isClosed()) { 
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/project_nqaty?serverTimezone=UTC";
            String user = "Kaddouri";
            String password = "MOHAtonic@2";

            con = DriverManager.getConnection(url, user, password);
        }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(" Erreur de connexion : " + e.getMessage());
        }
        
        return con;
    }

    public static void closeConnexion() {
        if (con != null) {
            try {
                con.close();
                con = null; 
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture : " + e.getMessage());
            }
        }
    }

    public static Connection getCon() {
        return con;
    }
}