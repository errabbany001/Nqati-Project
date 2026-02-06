import java.sql.Connection;
import javax.swing.JFrame;
import panels.Accueil;
import panels.Contact;
import panels.Propos;
import tools.Connexion;
import tools.Session;

public class Main {

    public static void main(String[] args) {
 
        Session s = new Session();
        Session.setTheProfil("");
        Propos p = new Propos();
        Contact c = new Contact();
        Connection con = Connexion.getConnexion();
        // Création de la fenêtre principale
        JFrame frame = new JFrame("Nqati Student Marks");
        frame.setSize(1000, 697);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Lancement de la page d'accueil (située dans le package panels)
        Accueil accueil = new Accueil();
        frame.setContentPane(accueil);
        frame.setVisible(true);
        
        System.out.println("Application Démarrée avec succès !");
    }
}