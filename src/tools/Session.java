package tools;

import element.Enseignant;
import element.Etudiant;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;

public class Session {
    
    // Statut de connexion (0 = déconnecté, 1 = connecté)
    public static int isLoggedIn = 0;
    
    // Nom de l'utilisateur affiché dans l'interface
    public static String userName = "";

    // === FIX: Added 'static' keyword here ===
    public static BufferedImage photo ;
    // ========================================

    private static Etudiant etudiant ;
    private static Enseignant enseignant;


    public static void setTheProfil(String id_ , boolean table) {
        String sql1 = "SELECT photo_profil FROM etudiant WHERE cne = ?";
        String sql2 = "SELECT photo_profil FROM enseignant WHERE id_enseignant = ?";
        String sql = table ? sql1 : sql2;

        Session.photo = null; // Reset first

        // 1. Try to load from Database
        try (Connection conn = Connexion.getConnexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id_);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    byte[] imgBytes = rs.getBytes(1); // Column 1 is photo_profil
                    if (imgBytes != null && imgBytes.length > 0) {
                        // Convert byte[] to BufferedImage
                        ByteArrayInputStream bais = new ByteArrayInputStream(imgBytes);
                        Session.photo = ImageIO.read(bais);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading from DB for id_: " + id_);
        }

        // 2. FALLBACK: If photo is still null, load default
        if (Session.photo == null) {
            try {
                // Ensure this path is correct relative to your project root
                File defaultFile = new File("data/default_profil.png");
                if (defaultFile.exists()) {
                    Session.photo = ImageIO.read(defaultFile);
                } else {
                    System.err.println("Warning: Default photo not found at data/default_profil.png");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Etudiant getEtudiant() {
        return etudiant;
    }

    public static void setEtudiant(Etudiant etudiant) {
        Session.etudiant = etudiant;
    }

    public static Enseignant getEnseignant() {
        return enseignant;
    }

    public static void setEnseignant(Enseignant enseignant) {
        Session.enseignant = enseignant;
    }

   
    

    
}