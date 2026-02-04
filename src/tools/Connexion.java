package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static Connection con = null;

    public static Connection getConnexion() {
    // 1. هاد try كتحمي الكود كامل
    try {
        // con.isClosed() كتقدر دير Error، ولكن بما أنها وسط try، فهي محمية
        if (con == null || con.isClosed()) { 
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/project_nqaty?serverTimezone=UTC";
            String user = "root";
            String password = "YASSine2002@";

            con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connexion (ré)ouverte avec succès !");
        }
        } catch (ClassNotFoundException | SQLException e) {
            // 2. هاد catch هي اللي كتشد الخطأ إلا وقع (سواء فـ isClosed أو getConnection)
            System.out.println("❌ Erreur de connexion : " + e.getMessage());
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