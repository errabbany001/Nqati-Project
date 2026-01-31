package panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import tools.Functions;
import tools.Navigation;

public final class Accueil extends JPanel {

    // Variables pour l'image d'arrière-plan et les indicateurs visuels (cadres)
    private Image backgroundImage;
    private JLabel cader2, cader1;

    public Accueil() {
        // --- 1. Chargement des ressources et initialisation ---
        try {
            backgroundImage = ImageIO.read(new File("data/pg_home.png"));
        } catch (IOException e) {
            System.err.println("Erreur : Impossible de charger l'image 'data/pg_home.png'");
        }

        // Enregistre cette page comme la dernière visitée pour la navigation (bouton retour)
        Navigation.lastClass = Accueil.class;

        // Configuration de la mise en page (Layout absolu pour un positionnement précis)
        this.setLayout(null);

        // Définition de la palette de couleurs de l'application
        Color perpul = new Color(87, 107, 194); // Couleur principale
        Color ciel = new Color(144, 159, 250);   // Couleur au survol

        // --- 2. Création des composants textuels (Labels) ---
        
        // Titre de la section de connexion
        JLabel seconnecter = new JLabel("Se connecter");
        seconnecter.setForeground(perpul);
        seconnecter.setFont(new Font("Arial", Font.BOLD, 18));
        seconnecter.setBounds(640, 220, 230, 30);
        this.add(seconnecter);

        // Texte descriptif 1 : Présentation générale
        JLabel text1 = new JLabel("<html><body style=' text-align: center;'> Cette plateforme est un système de gestion des notes conçu pour faciliter le suivi académique.</body></html>");
        text1.setForeground(perpul);
        text1.setFont(new Font("Arial", Font.BOLD, 14));
        text1.setBounds(90, 320, 350, 50);
        this.add(text1);

        // Texte descriptif 2 : Rôles (Étudiants vs Enseignants)
        JLabel text2 = new JLabel("<html><body style=' text-align: center;'> Les étudiants peuvent consulter leurs notes par module et par semestre en toute transparence, tandis que les enseignants disposent d’un espace dédié pour saisir, modifier et valider les notes.</body></html>");
        text2.setForeground(perpul);
        text2.setFont(new Font("Arial", Font.BOLD, 14));
        text2.setBounds(90, 370, 350, 100);
        this.add(text2);

        // Texte descriptif 3 : Sécurité et fiabilité
        JLabel text3 = new JLabel("<html><body style='text-align: center;'>Le système garantit la sécurité des données, la fiabilité des calculs et une consultation rapide des résultats.</body></html>");
        text3.setForeground(perpul);
        text3.setFont(new Font("Arial", Font.BOLD, 14));
        text3.setBounds(90, 470, 350, 70);
        this.add(text3);

        // --- 3. Création des éléments décoratifs ---
        
        // Indicateur visuel pour le bouton Étudiant
        cader1 = new JLabel();
        cader1.setBackground(perpul);
        cader1.setOpaque(true);
        cader1.setBounds(580, 290, 10, 50);
        this.add(cader1);

        // Indicateur visuel pour le bouton Enseignant
        cader2 = new JLabel();
        cader2.setBackground(perpul);
        cader2.setOpaque(true);
        cader2.setBounds(580, 380, 10, 50);
        this.add(cader2);

        // --- 4. Configuration des Boutons d'Action ---

        // === BOUTON : ESPACE ÉTUDIANT ===
        JButton etudiant = new JButton("etudiant");
        etudiant.setFont(new Font("Arial", Font.BOLD, 18));
        etudiant.setForeground(perpul);
        etudiant.setBackground(Color.white);
        etudiant.setOpaque(true);
        etudiant.setBorder(new LineBorder(perpul, 2, true));
        etudiant.setBounds(580, 290, 250, 50);
        etudiant.setFocusPainted(false);
        etudiant.setContentAreaFilled(true);
        etudiant.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Événements souris pour le bouton Étudiant
        etudiant.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Effet de survol : changement de couleur et agrandissement du cadre
                etudiant.setBackground(ciel);
                etudiant.setForeground(Color.white);
                cader1.setBounds(580, 290, 30, 50);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Retour à l'état normal
                etudiant.setBackground(Color.white);
                etudiant.setForeground(perpul);
                cader1.setBounds(580, 290, 10, 50);
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Navigation vers la page Login Étudiant
                java.awt.Window window = SwingUtilities.getWindowAncestor(Accueil.this);
                if (window instanceof javax.swing.JFrame) {
                    javax.swing.JFrame frame = (javax.swing.JFrame) window;
                    
                    // Ajout de la page actuelle à l'historique avant de changer
                    Navigation.addToHistory(Accueil.class);
                    
                    LoginEtudient panelMdp = new LoginEtudient();
                    frame.setContentPane(panelMdp);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
        this.add(etudiant);

        // === BOUTON : ESPACE ENSEIGNEMENT ===
        JButton enseignement = new JButton("enseignement");
        enseignement.setFont(new Font("Arial", Font.BOLD, 18));
        enseignement.setForeground(perpul);
        enseignement.setBackground(Color.white);
        enseignement.setOpaque(true);
        enseignement.setBorder(new LineBorder(perpul, 2, true));
        enseignement.setBounds(580, 380, 250, 50);
        enseignement.setFocusPainted(false);
        enseignement.setContentAreaFilled(true);
        enseignement.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Événements souris pour le bouton Enseignement
        enseignement.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                enseignement.setBackground(ciel);
                enseignement.setForeground(Color.white);
                cader2.setBounds(580, 380, 30, 50);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                enseignement.setBackground(Color.white);
                enseignement.setForeground(perpul);
                cader2.setBounds(580, 380, 10, 50);
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Navigation vers la page Login Enseignement
                java.awt.Window window = SwingUtilities.getWindowAncestor(Accueil.this);
                if (window instanceof javax.swing.JFrame) {
                    javax.swing.JFrame frame = (javax.swing.JFrame) window;
                    
                    Navigation.addToHistory(Accueil.class);
                    
                    LoginEnseignement panelMdp = new LoginEnseignement();
                    frame.setContentPane(panelMdp);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
        this.add(enseignement);

        // --- 5. Menu de Navigation Supérieur ---
        // Utilisation de la classe utilitaire Functions pour créer les boutons de menu standardisés
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Navigation.lastClass, this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);

        this.add(acceuille);
        this.add(contact);
        this.add(propos);
    }

    // Dessin de l'image d'arrière-plan
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}