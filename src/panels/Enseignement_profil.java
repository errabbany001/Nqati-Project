package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tools.Functions;
import tools.Navigation;
import tools.Session;

public class Enseignement_profil extends JPanel {
    
    // Déclaration des attributs pour l'image de fond et les données personnelles du professeur
    private Image backgroundImage;
    private String fullName, Id, ddn, departement, sexe;
    
    // FIXED: Added <> to fix [unchecked] warnings
    private ArrayList<String> listCours = new ArrayList<>();
    private ArrayList<String> codes = new ArrayList<>();

    // Initialisation des icônes par défaut pour le profil (grande et petite taille)
    static ImageIcon icon_1 = new ImageIcon(new ImageIcon("data/default_profil.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
    static ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/default_profil.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

    public Enseignement_profil() {

        // Chargement de l'image de fond et enregistrement de l'historique de navigation
        Navigation.lastClass = this.getClass();
        try {
            backgroundImage = ImageIO.read(new File("data/pg_Enseignement_profil.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        // Configuration du layout et définition des données académiques (cours et codes)
        this.setLayout(null); 
        Color perpul = new Color(87, 107, 194);

        if (listCours.isEmpty()){
            listCours.add("Sécurité des Systèmes Informatiques");
            listCours.add("Théorie des graphes et Techniques d’Optimisation");
            listCours.add("Logique et Intelligence Artificielle");
            listCours.add("Web Semantique");

            codes.add("BI10");
            codes.add("BI03");
            codes.add("BI14");
            codes.add("BI17");
        }

        // Initialisation des informations personnelles de l'enseignant
        fullName = "Yassine Er-rabbany";
        Id = "X000000";
        ddn = "01 / 01 / 1975";
        departement = "Informatique";
        sexe = "Homme";
        Session.userName = fullName;

        // Affichage des images de profil (avatar principal et icône de la barre supérieure)
        JLabel profilIcon = new JLabel(icon_1);
        profilIcon.setBounds(274, 215, 150, 150);
        this.add(profilIcon);

        JLabel profilIconMini = new JLabel(icon_2);
        profilIconMini.setBounds(920, 48, 40, 40);
        this.add(profilIconMini);

        // Affichage du nom d'utilisateur dans la barre d'en-tête
        JLabel myname = Functions.creetLabel(710, 60, Session.userName);
        myname.setHorizontalAlignment(JLabel.RIGHT);
        this.add(myname);

        // Affichage des détails d'identité sous la photo de profil (Nom, ID, Date de naissance, Sexe)
        JLabel name = Functions.creetLabel(245, 400, "Prof. " + fullName);
        name.setHorizontalAlignment(JLabel.CENTER);
        this.add(name);

        JLabel myId = Functions.EtudInfo(245, 422, Id);
        myId.setHorizontalAlignment(JLabel.CENTER);
        this.add(myId);

        JLabel datDN = Functions.EtudInfo(245, 444, ddn);
        datDN.setHorizontalAlignment(JLabel.CENTER);
        this.add(datDN);

        JLabel sex = Functions.EtudInfo(245, 466, sexe);
        sex.setHorizontalAlignment(JLabel.CENTER);
        this.add(sex);

        // Affichage des informations professionnelles (Département et liste des cours assignés)
        this.add(Functions.creetLabel(520, 220, "Departement  :"));
        this.add(Functions.EtudInfo(640, 220, departement));

        this.add(Functions.creetLabel(520, 250, "Les Cours  :"));
        for (int i = 0; i < listCours.size(); i++){
            JLabel line = Functions.EtudInfo(560, 270 + i * 20, "- " + codes.get(i) + " : " + listCours.get(i));
            line.setBounds(540, 270 + i * 20, 500, 20);
            line.setFont(new Font("Ariel", Font.BOLD, 13));
            this.add(line);
        }

        // Ajout des boutons de navigation latérale et de déconnexion
        this.add(Functions.LogOutIcon(this));
        
        JButton Notification = Functions.createNavButton(55, 486, "notification_ens", this);
        JButton Settings = Functions.createNavButton(55, 406, "settings_ens", this);
        JButton Notes = Functions.createNavButton(55, 326, "notes_ens", this);

        this.add(Notification);
        this.add(Settings);
        this.add(Notes);

        // Ajout du menu de navigation horizontal supérieur
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Navigation.lastClass, this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);
        
        this.add(acceuille);
        this.add(contact);
        this.add(propos);
    }

    // Gestion du rendu visuel pour dessiner l'image d'arrière-plan du profil
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}