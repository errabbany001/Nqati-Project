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

public final class Accueil extends JPanel {

    // Déclaration des variables d'arrière-plan et des composants graphiques
    private Image backgroundImage;
    JLabel cader2, cader1;

    public Accueil() {

        // Chargement de l'image de fond et initialisation de l'historique
        try {
            backgroundImage = ImageIO.read(new File("data/pg_home.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }
        Main.setLastClass(Accueil.class);

        // Configuration du layout et définition de la palette de couleurs
        this.setLayout(null);
        Color perpul = new Color(87, 107, 194);
        Color ciel = new Color(144, 159, 250);

        // Création et positionnement des textes descriptifs de la plateforme
        JLabel seconnecter = new JLabel("Se connecter");
        seconnecter.setForeground(perpul);
        seconnecter.setFont(new Font("Arial", Font.BOLD, 18));
        seconnecter.setBounds(640, 220, 230, 30);
        this.add(seconnecter);

        JLabel text1 = new JLabel("<html><body style=' text-align: center;'> Cette plateforme est un système de gestion des notes conçu pour faciliter le suivi académique.</body></html>");
        text1.setForeground(perpul);
        text1.setFont(new Font("Arial", Font.BOLD, 14));
        text1.setBounds(90, 320, 350, 50);
        this.add(text1);

        JLabel text2 = new JLabel("<html><body style=' text-align: center;'> Les étudiants peuvent consulter leurs notes par module et par semestre en toute transparence, tandis que les enseignants disposent d’un espace dédié pour saisir, modifier et valider les notes.</body></html>");
        text2.setForeground(perpul);
        text2.setFont(new Font("Arial", Font.BOLD, 14));
        text2.setBounds(90, 370, 350, 100);
        this.add(text2);

        JLabel text3 = new JLabel("<html><body style='text-align: center;'>Le système garantit la sécurité des données, la fiabilité des calculs et une consultation rapide des résultats.</body></html>");
        text3.setForeground(perpul);
        text3.setFont(new Font("Arial", Font.BOLD, 14));
        text3.setBounds(90, 470, 350, 70);
        this.add(text3);

        // Initialisation des indicateurs visuels (cadres) pour les boutons
        cader1 = new JLabel();
        cader1.setBackground(perpul);
        cader1.setOpaque(true);
        cader1.setBounds(580, 290, 10, 50);
        this.add(cader1);

        cader2 = new JLabel();
        cader2.setBackground(perpul);
        cader2.setOpaque(true);
        cader2.setBounds(580, 380, 10, 50);
        this.add(cader2);

        // Configuration du bouton Étudiant avec ses effets de survol et de clic
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

        etudiant.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                etudiant.setBackground(ciel);
                etudiant.setForeground(Color.white);
                cader1.setBounds(580, 290, 30, 50);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                etudiant.setBackground(Color.white);
                etudiant.setForeground(perpul);
                cader1.setBounds(580, 290, 10, 50);
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                java.awt.Window window = SwingUtilities.getWindowAncestor(Accueil.this);
                if (window instanceof javax.swing.JFrame) {
                    javax.swing.JFrame frame = (javax.swing.JFrame) window;
                    Main.addPageToHistory(Accueil.class);
                    LoginEtudient panelMdp = new LoginEtudient();
                    frame.setContentPane(panelMdp);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
        this.add(etudiant);

        // Configuration du bouton Enseignement avec ses effets de survol et de clic
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
                java.awt.Window window = SwingUtilities.getWindowAncestor(Accueil.this);
                if (window instanceof javax.swing.JFrame) {
                    javax.swing.JFrame frame = (javax.swing.JFrame) window;
                    Main.addPageToHistory(Accueil.class);
                    LoginEnseignement panelMdp = new LoginEnseignement();
                    frame.setContentPane(panelMdp);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
        this.add(enseignement);

        // Ajout des boutons de navigation du menu supérieur
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Main.getLastClass(), this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);

        this.add(acceuille);
        this.add(contact);
        this.add(propos);
    }

    // Gestion du rendu graphique pour dessiner l'image d'arrière-plan
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}