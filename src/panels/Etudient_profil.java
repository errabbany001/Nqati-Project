package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tools.Functions;
import tools.Navigation;  
import tools.Session;

public final class Etudient_profil extends JPanel {

    // Déclaration de l'image de fond et des icônes statiques (profil et vecteurs)
    private Image backgroundImage;
    static ImageIcon icon_1 = new ImageIcon(new ImageIcon("data/default_profil.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
    static ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/default_profil.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    static ImageIcon vet_adm = new ImageIcon(new ImageIcon("data/vector_admis.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    static ImageIcon vect_ddn = new ImageIcon(new ImageIcon("data/vector_dateDeNaissance.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    static ImageIcon vect_niv = new ImageIcon(new ImageIcon("data/vector_niveau.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    static ImageIcon vect_opt = new ImageIcon(new ImageIcon("data/vector_option.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    static ImageIcon vect_sem = new ImageIcon(new ImageIcon("data/vector_semester.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    static ImageIcon vect_not = new ImageIcon(new ImageIcon("data/vector_note.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    
    // Variables pour stocker les informations textuelles de l'étudiant
    static String fullName, Cne, ddn, option, niveau, semester, note, admis;

    // Méthode utilitaire pour créer une étiquette d'icône vectorielle
    JLabel creetVector(int x, int y, ImageIcon vect) {
        JLabel vector = new JLabel(vect);
        vector.setBounds(x, y, 25, 25);
        return vector;
    }

    public Etudient_profil() {
        
        // Initialisation du panneau : historique et chargement de l'image de fond
        Navigation.lastClass = this.getClass();
        try {
            backgroundImage = ImageIO.read(new File("data/pg_Etudient_profil.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }
        this.setLayout(null);

        // Simulation des données de l'étudiant (base de données fictive)
        fullName = Session.getEtudiant().getNom() + " " + Session.getEtudiant().getPrenom();
        Cne = Session.getEtudiant().getCne();
        ddn = Session.getEtudiant().getDateDeNaissance();
        option = Session.getEtudiant().getFilier();
        niveau = Session.getEtudiant().getNiveau();
        admis = "---";
        note = "--.--";
        semester = Session.getEtudiant().getSemester();
        
        Session.userName = fullName;

        Color perpul = new Color(87, 107, 194);

        // Ajout des photos de profil (principale et miniature de la barre supérieure)
        JLabel profilIcon = new JLabel(new ImageIcon(Session.photo.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        profilIcon.setBounds(250, 215, 150, 150);
        this.add(profilIcon);

        JLabel profilIconMini = new JLabel(new ImageIcon(Session.photo.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        profilIconMini.setBounds(920, 48, 40, 40);
        this.add(profilIconMini);

        // Affichage du nom d'utilisateur et des identifiants (CNE)
        JLabel myname = Functions.creetLabel(710, 60, Session.userName);
        myname.setHorizontalAlignment(JLabel.RIGHT);
        this.add(myname);

        JLabel FullN = Functions.creetLabel(230, 370, fullName);
        FullN.setHorizontalAlignment(JLabel.CENTER);
        this.add(FullN);

        JLabel CneLab = Functions.EtudInfo(230, 390, Cne);
        CneLab.setHorizontalAlignment(JLabel.CENTER);
        this.add(CneLab);

        // Affichage des informations académiques (Date de naissance, Option, Niveau, Semestre)
        this.add(Functions.creetLabel(515, 245, "DATE DE NAISSANCE"));
        this.add(Functions.EtudInfo(515, 280, ddn));

        this.add(Functions.creetLabel(515, 345, "OPTION"));
        this.add(Functions.EtudInfo(500, 365, 200 , 50 ,14f, "<html> " + option + "</html>"));

        this.add(Functions.creetLabel(720, 245, "NIVEAU"));
        this.add(Functions.EtudInfo(720, 280, niveau));

        this.add(Functions.creetLabel(720, 345, "SEMESTER"));
        this.add(Functions.EtudInfo(720, 380, semester));

        // Ajout des icônes vectorielles décoratives pour chaque champ d'information
        this.add(creetVector(480, 240, vect_ddn));
        this.add(creetVector(480, 340, vect_opt));
        this.add(creetVector(685, 244, vect_niv));
        this.add(creetVector(685, 340, vect_sem));

        // Affichage du statut d'admission au diplôme et de la note globale
        this.add(creetVector(300, 515, vet_adm));
        this.add(Functions.creetLabel(335, 520, "ADMIS DIPLOM : "));
        this.add(Functions.EtudInfo(460, 521, admis));

        this.add(creetVector(600, 515, vect_not));
        this.add(Functions.creetLabel(635, 520, "NOTE : "));
        this.add(Functions.EtudInfo(700, 521, note));

        // Ajout des boutons de navigation latérale et de déconnexion
        this.add(Functions.LogOutIcon(this));
        
        JButton Notification = Functions.createNavButton(55, 486, "notification_etd", this);
        JButton Settings = Functions.createNavButton(55, 406, "settings_etd", this);
        JButton Notes = Functions.createNavButton(55, 326, "notes_etd", this);

        this.add(Notification);
        this.add(Settings);
        this.add(Notes);

        // Ajout du menu supérieur (Accueil, Contact, À propos)
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Navigation.lastClass, this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);

        this.add(acceuille);
        this.add(contact);
        this.add(propos);
    }

    // Gestion de l'affichage de l'image d'arrière-plan du profil étudiant
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}