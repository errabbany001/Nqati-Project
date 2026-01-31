package tools;

import java.util.ArrayList;
import javax.swing.JPanel;
import panels.Accueil;

public class Navigation {

    // Liste pour suivre l'historique de navigation
    public static ArrayList<Class<? extends JPanel>> historyList = new ArrayList<>();
    
    // Référence à la dernière classe visitée (par défaut Accueil)
    public static Class<? extends JPanel> lastClass = Accueil.class;

    // Méthode pour ajouter une page à l'historique
    public static void addToHistory(Class<? extends JPanel> cls) {
        String name = cls.getSimpleName();
        
        // Exclusion des pages qui ne doivent pas être dans l'historique (Contact, Propos)
        if (name.equals("Contact") || name.equals("Propos")) {
            return; 
        }
        
        // Éviter les doublons consécutifs dans l'historique
        if (!historyList.isEmpty()) {
            Class<? extends JPanel> lastSaved = historyList.get(historyList.size() - 1);
            if (lastSaved.equals(cls)) {
                return; 
            }
        }
        
        historyList.add(cls);
        lastClass = cls; 
    }
}