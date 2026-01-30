import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public final class LoginEnseignement extends JPanel {

    // Déclaration des composants graphiques et des ressources d'image
    private Image backgroundImage;
    private JLabel line1, line2;
    private char defaultEchoChar;
    private int switcher = 0;
    private JButton icon;
    
    ImageIcon icon_1 = new ImageIcon(new ImageIcon("data/icon1.png").getImage().getScaledInstance(320, 30, Image.SCALE_SMOOTH));
    ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/icon2.png").getImage().getScaledInstance(320, 30, Image.SCALE_SMOOTH));
    
    // Méthode utilitaire pour créer les lignes décoratives sous les champs de saisie
    public JLabel creetLine(int x, int y, Color c) {
        JLabel line = new JLabel();
        line.setBackground(c); 
        line.setOpaque(true);
        line.setBounds(x, y, 320, 4); 
        return line;
    }

    public LoginEnseignement() {
        
        // Initialisation du panneau : historique et chargement de l'image de fond
        Main.setLastClass(this.getClass());
        try {
            backgroundImage = ImageIO.read(new File("data/Login_Enseignement.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null); 
        
        // Gestion du focus global pour désélectionner les champs au clic sur le panneau
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                requestFocusInWindow();
            }
        });

        Color perpul = new Color(87, 107, 194);
        Color bleu = new Color(19, 43, 149);
        Font font = new Font("Calibri", Font.BOLD, 15);

        // Ajout du bouton de retour et des lignes d'interface
        this.add(Functions.cretBackBtn());
        line1 = creetLine(330, 368, perpul);
        line2 = creetLine(330, 447, perpul);
        this.add(line1);
        this.add(line2);

        // Configuration du champ de saisie Email avec gestion du texte par défaut (Placeholder)
        JTextField cneEtud = new JTextField();
        cneEtud.setBounds(360, 345, 270, 30);
        cneEtud.setOpaque(false);
        cneEtud.setBorder(null);
        cneEtud.setHorizontalAlignment(JTextField.CENTER);
        cneEtud.setForeground(perpul);
        cneEtud.setFont(font);
        cneEtud.setText("Email  ");

        cneEtud.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("Email  ".equals(cneEtud.getText())) {
                    cneEtud.setText("");
                }
                cneEtud.setBounds(360, 343, 270, 30);
                cneEtud.setForeground(bleu);
                line1.setBackground(bleu);
            }

            @Override
            public void focusLost(FocusEvent e) {
                cneEtud.setCaretPosition(0);
                if (cneEtud.getText().replace(" ", "").equals("")) {
                    cneEtud.setText("Email  ");
                    cneEtud.setForeground(perpul);
                    line1.setBackground(perpul);
                    cneEtud.setHorizontalAlignment(JTextField.CENTER); 
                    cneEtud.setBounds(360, 345, 270, 30);
                }
            }
        });
        this.add(cneEtud);

        // Configuration du champ Mot de passe avec gestion de la visibilité et du placeholder
        JPasswordField passEtud = new JPasswordField();
        passEtud.setBounds(370, 424, 250, 30);
        passEtud.setOpaque(false);
        passEtud.setBorder(null);
        passEtud.setHorizontalAlignment(JTextField.CENTER);
        passEtud.setForeground(perpul);
        passEtud.setFont(font);
        defaultEchoChar = passEtud.getEchoChar(); 
        passEtud.setEchoChar((char) 0); // Texte en clair pour le placeholder
        passEtud.setText("Mot de passe");

        passEtud.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("Mot de passe".equals(String.valueOf(passEtud.getPassword()))) {
                    passEtud.setText("");
                    passEtud.setEchoChar(defaultEchoChar); // Active les points de sécurité
                    passEtud.setBounds(370, 422, 250, 30);
                }
                passEtud.setForeground(bleu);
                line2.setBackground(bleu);
                icon.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passEtud.getPassword()).trim().isEmpty()) {
                    passEtud.setText("Mot de passe");
                    passEtud.setForeground(perpul);
                    line2.setBackground(perpul);
                    passEtud.setEchoChar((char) 0); 
                    icon.setVisible(false);
                    passEtud.setBounds(370, 424, 250, 30);
                }
            }
        });
        this.add(passEtud);

        // Création du bouton pour afficher/masquer le mot de passe
        ImageIcon show = new ImageIcon(new ImageIcon("data/show_icon.png").getImage().getScaledInstance(25, 18, Image.SCALE_SMOOTH));
        ImageIcon hide = new ImageIcon(new ImageIcon("data/hide_icon.png").getImage().getScaledInstance(25, 18, Image.SCALE_SMOOTH));

        icon = new JButton(hide);
        icon.setBounds(623, 426, 25, 18); 
        icon.setContentAreaFilled(false);
        icon.setBorderPainted(false);
        icon.setFocusPainted(false);
        icon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        icon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (switcher == 0){
                    icon.setIcon(show);
                    switcher = 1;
                    passEtud.setEchoChar((char) 0);
                } else {
                    icon.setIcon(hide);
                    switcher = 0;
                    passEtud.setEchoChar(defaultEchoChar);
                }
            }
        });
        icon.setVisible(false);
        this.add(icon);

        // Configuration du bouton de connexion et redirection vers le profil enseignant
        JButton loginBtn = new JButton("se connecter", icon_1);
        loginBtn.setBounds(330, 490, 320, 40);
        loginBtn.setHorizontalTextPosition(JButton.CENTER);
        loginBtn.setVerticalTextPosition(JButton.CENTER);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setForeground(perpul);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                loginBtn.setIcon(icon_2);
                loginBtn.setForeground(Color.white);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                loginBtn.setIcon(icon_1);
                loginBtn.setForeground(perpul);
            }
        });

        loginBtn.addActionListener(e -> {
            java.awt.Window window = SwingUtilities.getWindowAncestor(LoginEnseignement.this);
            if (window instanceof javax.swing.JFrame) {
                javax.swing.JFrame frame = (javax.swing.JFrame) window;
                Main.addPageToHistory(LoginEnseignement.class);
                Main.setLogedIn(1);
                Enseignement_profil panelMdp = new Enseignement_profil();
                frame.setContentPane(panelMdp);
                frame.revalidate();
                frame.repaint();
            }
        });
        this.add(loginBtn);

        // Label interactif pour la récupération du mot de passe
        JLabel forgotLabel = new JLabel("<HTML><U>Mot de passe oublié</U></HTML>");
        forgotLabel.setBounds(427, 540, 132, 25);
        forgotLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        forgotLabel.setForeground(perpul);
        forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        forgotLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                java.awt.Window window = SwingUtilities.getWindowAncestor(LoginEnseignement.this);
                if (window instanceof javax.swing.JFrame) {
                    javax.swing.JFrame frame = (javax.swing.JFrame) window;
                    MotDePasseOublier panelMdp = new MotDePasseOublier();
                    Main.addPageToHistory(LoginEnseignement.class);
                    frame.setContentPane(panelMdp);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
        this.add(forgotLabel);

        // Liaison de la touche "Entrée" au bouton de connexion une fois le panneau affiché
        this.addHierarchyListener(new java.awt.event.HierarchyListener() {
            @Override
            public void hierarchyChanged(java.awt.event.HierarchyEvent e) {
                if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0) {
                    if (isShowing()) {
                        JRootPane root = SwingUtilities.getRootPane(loginBtn);
                        if (root != null) {
                            root.setDefaultButton(loginBtn);
                        }
                    }
                }
            }
        }); 

        // Ajout du menu supérieur de navigation
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Main.getLastClass(), this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);

        this.add(acceuille);
        this.add(contact);
        this.add(propos);
    }

    // Rendu graphique de l'image d'arrière-plan du formulaire de connexion
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}