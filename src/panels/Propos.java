package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import tools.Functions;
import tools.Navigation;
import tools.Resources;
import tools.ScrollBarUI;
import tools.Session;

public final class Propos extends JPanel {

    private Image backgroundImage;

    public Propos() {

        // Initialisation du panneau : chargement de l'image de fond et configuration du layout
        try {
            backgroundImage = ImageIO.read(new File("data/pg_propos.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }
        this.setLayout(null);

        // Affichage conditionnel des informations de profil utilisateur (icône et nom)
        JLabel profilIconMini = new JLabel(new ImageIcon(Session.photo.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        profilIconMini.setBounds(920, 48, 40, 40);
        this.add(profilIconMini);
    
        JLabel myname = Functions.creetLabel(710, 60, Session.userName);
        myname.setHorizontalAlignment(JLabel.RIGHT);
        this.add(myname);
        
        if(Session.isLoggedIn == 0){
            profilIconMini.setVisible(false);
            myname.setVisible(false);
        } else {
            profilIconMini.setVisible(true);
            myname.setVisible(true);
        }
        
        Color perpul = new Color(87, 107, 194);
        Color bleu = new Color(22, 31, 112);

        // Ajout des boutons de navigation du menu supérieur
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Navigation.lastClass, this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);
        
        this.add(acceuille);
        this.add(contact);
        this.add(propos);

        // Configuration du panneau de contenu principal pour le défilement (Scrollable)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false);
        contentPanel.setPreferredSize(new Dimension(876, 2200));

        // Ajout du logo et des titres d'introduction de la section "À Propos"
        JLabel lg = new JLabel(Resources.logo);
        lg.setBounds(380, 20, 100, 87);
        contentPanel.add(lg);

        JLabel text1 = new JLabel("À Propos de Nqati");
        text1.setFont(Functions.getMyFont("data/Raleway-Regular.ttf", Font.BOLD, 60f));
        text1.setBounds(0, 225, 874, 100);
        text1.setForeground(Color.white);
        text1.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(text1);

        JLabel text2 = new JLabel("<html><div style='text-align: center;'>" 
            + "Découvrez comment Nqati transforme la gestion académique en une expérience fluide "
            + "et sécurisée pour les établissements d'enseignement supérieur."
            + "</div></html>");
        text2.setFont(Functions.getMyFont("", Font.PLAIN, 20f));
        text2.setBounds(40, 290, 840, 100);
        text2.setForeground(Color.WHITE);
        text2.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(text2);

        // Section Vision : Présentation des objectifs et avantages de la plateforme
        JLabel text5 = new JLabel("Notre vision");
        text5.setBounds(440, 695, 400, 25);
        text5.setFont(Functions.getMyFont("", Font.ITALIC, 16f));
        text5.setForeground(bleu);
        contentPanel.add(text5);

        JLabel text3 = new JLabel("<html><div>Une plateforme moderne pour la gestion des notes </div></html>");
        text3.setForeground(perpul);
        text3.setFont(Functions.getMyFont("", Font.BOLD, 32f));
        text3.setBounds(440, 710, 400, 110);
        contentPanel.add(text3);

        JLabel text4 = new JLabel("<html><body> <div> Nqati simplifie la transition des établissements vers une gestion numérique... </div> </body></html>");
        text4.setForeground(Color.BLACK);
        text4.setFont(Functions.getMyFont("data/Raleway-Regular.ttf", Font.BOLD, 14f));
        text4.setBounds(440, 793, 400, 200);
        contentPanel.add(text4);

        JLabel photo1 = new JLabel(Resources.ph_1);
        photo1.setBounds(4, 150, 874, 416);
        contentPanel.add(photo1);

        JLabel photo2 = new JLabel(Resources.ph_2);
        photo2.setBounds(0, 668, 400, 320);
        contentPanel.add(photo2);

        // Section Rôles : Explication des interfaces adaptées aux Étudiants et Enseignants
        JLabel roles = new JLabel("Rôles");
        roles.setBounds(0, 1062, 876, 25);
        roles.setForeground(new Color(28, 175, 244));
        roles.setFont(Functions.getMyFont("", Font.BOLD, 22f));
        roles.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(roles);

        JLabel text6 = new JLabel("Deux expériences adaptées");
        text6.setBounds(0, 1087, 876, 35);
        text6.setFont(Functions.getMyFont("", Font.PLAIN, 30f));
        text6.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(text6);

        // Détails spécifiques pour le rôle "Étudiants"
        JLabel etudiants = new JLabel("Étudiants");
        etudiants.setBounds(30, 1175, 280, 25);
        etudiants.setFont(Functions.getMyFont("", Font.ITALIC, 12f));
        etudiants.setForeground(bleu);
        contentPanel.add(etudiants);

        JLabel text8 = new JLabel("<html><div>Suivi transparent de vos résultats</div></html>");
        text8.setForeground(new Color(28, 175, 244));
        text8.setFont(Functions.getMyFont("", Font.BOLD, 25f));
        text8.setBounds(30, 1190, 240, 80);
        contentPanel.add(text8);

        JLabel photo3 = new JLabel(Resources.ph_3);
        photo3.setBounds(4, 1160, 300, 400);
        contentPanel.add(photo3);

        // Détails spécifiques pour le rôle "Enseignants"
        JLabel ensg = new JLabel("Enseignants");
        ensg.setBounds(340, 1270, 280, 25);
        ensg.setFont(Functions.getMyFont("", Font.ITALIC, 12f));
        ensg.setForeground(bleu);
        contentPanel.add(ensg);

        JLabel text10 = new JLabel("<html><div>Gestion simplifiée des évaluations</div></html>");
        text10.setForeground(new Color(28, 175, 244));
        text10.setFont(Functions.getMyFont("", Font.BOLD, 25f));
        text10.setBounds(340, 1285, 240, 80);
        contentPanel.add(text10);

        JLabel photo4 = new JLabel(Resources.ph_4);
        photo4.setBounds(310, 1160, 560, 400);
        contentPanel.add(photo4);

        // Section Excellence : Mise en avant de l'expertise technique et de l'équipe
        JLabel excellence = new JLabel("Excellence");
        excellence.setBounds(0, 1630, 876, 25);
        excellence.setForeground(new Color(28, 175, 244));
        excellence.setFont(Functions.getMyFont("", Font.BOLD, 22f));
        excellence.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(excellence);

        JLabel text12 = new JLabel("Innovation et expertise technique");
        text12.setBounds(0, 1655, 876, 35);
        text12.setFont(Functions.getMyFont("", Font.PLAIN, 30f));
        text12.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(text12);

        JLabel photo5 = new JLabel(Resources.ph_5);
        photo5.setBounds(4, 1780, 874, 400);
        contentPanel.add(photo5);

        // Configuration et ajout du JScrollPane pour permettre la navigation verticale
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBounds(56, 140, 890, 481);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.add(scrollPane);
    }

    // Rendu graphique pour dessiner l'image de fond du panneau
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}