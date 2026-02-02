package tools;
import element.Etudiant;

public class Session {
    
    // Statut de connexion (0 = déconnecté, 1 = connecté)
    public static int isLoggedIn = 0;
    
    // Nom de l'utilisateur affiché dans l'interface
    public static String userName = "";

    private static Etudiant etudiant;

    public static Etudiant getEtudiant() {
        return etudiant; 
    }

    public static void setEtudiant(Etudiant etudiant) {
        Session.etudiant = etudiant;
    }
    
}