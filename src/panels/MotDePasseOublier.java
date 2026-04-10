package panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import tools.Functions;
import tools.Navigation;

public final class MotDePasseOublier extends JPanel {

    // ── Image de fond ────────────────────────────────────────────
    private Image backgroundImage;

    // ── Lignes décoratives champs vérification ───────────────────
    private final JLabel line1, line2, line3, line4;

    // ── Lignes décoratives champs mot de passe ───────────────────
    private JLabel lineP1, lineP2;

    // ── Champs vérification ──────────────────────────────────────
    private JTextField Nom, Prenom, Email, Cne;

    // ── Champs nouveau mot de passe ──────────────────────────────
    private JPasswordField pass1, pass2;

    // ── Boutons ──────────────────────────────────────────────────
    private JButton vld, btnConfirm;

    // ── Label erreur ─────────────────────────────────────────────
    private JLabel erur;

    // ── Icônes boutons ───────────────────────────────────────────
    ImageIcon icon_1 = new ImageIcon(new ImageIcon("data/icon1.png").getImage().getScaledInstance(260, 30, Image.SCALE_SMOOTH));
    ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/icon2.png").getImage().getScaledInstance(260, 30, Image.SCALE_SMOOTH));

    // ─────────────────────────────────────────────────────────────
    // Méthode utilitaire : ligne décorative sous un champ
    // ─────────────────────────────────────────────────────────────
    public JLabel creetLine(int x, int y, Color c) {
        JLabel line = new JLabel();
        line.setBackground(c);
        line.setOpaque(true);
        line.setBounds(x, y, 260, 4);
        return line;
    }

    // ─────────────────────────────────────────────────────────────
    // Méthode utilitaire : champ texte avec placeholder + focus
    // ─────────────────────────────────────────────────────────────
    public JTextField creeField(int x, int y, String text, JLabel line, Color befor, Color after, Font font) {
        JTextField field = new JTextField(text);
        field.setBounds(x, y, 260, 18);
        field.setOpaque(false);
        field.setBorder(null);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setForeground(befor);
        field.setFont(font);

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (text.equals(field.getText())) {
                    field.setText("");
                }
                field.setForeground(after);
                line.setBackground(after);
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().replace(" ", "").equals("")) {
                    field.setText(text);
                    field.setForeground(befor);
                    line.setBackground(befor);
                }
            }
        });
        return field;
    }

    // ─────────────────────────────────────────────────────────────
    // CONSTRUCTEUR
    // ─────────────────────────────────────────────────────────────
    public MotDePasseOublier() {

        Navigation.lastClass = this.getClass();

        // Chargement image de fond
        try {
            backgroundImage = ImageIO.read(new File("data/pg_PasseOublier.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_PasseOublier.png");
        }

        this.setLayout(null);

        // Bouton retour + clic sur fond pour perdre le focus
        this.add(Functions.cretBackBtn());
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                requestFocusInWindow();
            }
        });

        // ── Couleurs & police ────────────────────────────────────
        Color perpul = new Color(87, 107, 194);
        Color bleu   = new Color(19, 43, 149);
        Font  font   = new Font("Calibri", Font.BOLD, 15);

        // ── Lignes vérification ──────────────────────────────────
        line1 = creetLine(363, 330, perpul);
        line2 = creetLine(363, 380, perpul);
        line3 = creetLine(363, 430, perpul);
        line4 = creetLine(363, 480, perpul);
        this.add(line1);
        this.add(line2);
        this.add(line3);
        this.add(line4);

        // ── Champs vérification ──────────────────────────────────
        Nom    = creeField(363, 310, "Nom",    line1, perpul, bleu, font);
        Prenom = creeField(363, 360, "Prenom", line2, perpul, bleu, font);
        Email  = creeField(363, 410, "Email",  line3, perpul, bleu, font);
        Cne    = creeField(363, 460, "CNE",    line4, perpul, bleu, font);
        this.add(Nom);
        this.add(Prenom);
        this.add(Email);
        this.add(Cne);

        // ── Label erreur ─────────────────────────────────────────
        erur = new JLabel();
        erur.setForeground(Color.red);
        erur.setFont(new Font("Arial", Font.BOLD, 12));
        erur.setBounds(369, 560, 260, 30);
        erur.setHorizontalAlignment(JLabel.CENTER);
        this.add(erur);

        // ── Bouton Valider ───────────────────────────────────────
        vld = new JButton("Valide", icon_1);
        vld.setBounds(363, 520, 260, 30);
        vld.setHorizontalTextPosition(JButton.CENTER);
        vld.setVerticalTextPosition(JButton.CENTER);
        vld.setContentAreaFilled(false);
        vld.setBorderPainted(false);
        vld.setFocusPainted(false);
        vld.setForeground(perpul);
        vld.setFont(new Font("Arial", Font.BOLD, 14));
        vld.setCursor(new Cursor(Cursor.HAND_CURSOR));
        vld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { vld.setIcon(icon_2); vld.setForeground(Color.white); }
            public void mouseExited (java.awt.event.MouseEvent e) { vld.setIcon(icon_1); vld.setForeground(perpul); }
        });

        // ── ActionListener Valider ───────────────────────────────
        vld.addActionListener(e -> {
            String email  = Email.getText();
            String nom    = Nom.getText();
            String prenom = Prenom.getText();
            String cne    = Cne.getText();
            erur.setText("");

            if (email.trim().equalsIgnoreCase("Email")   ||
                nom.trim().equalsIgnoreCase("Nom")        ||
                prenom.trim().equalsIgnoreCase("Prenom")  ||
                cne.trim().equalsIgnoreCase("CNE")) {
                erur.setText("Veuillez remplir tous les champs !");

            } else if (!Functions.isValidEmail(email)) {
                erur.setText("Format d'email invalide");

            } else {
                String type = Functions.verifierUtilisateur(nom, prenom, email, cne);

                if (type != null) {

                    // ✅ Cacher les champs de vérification
                    Nom.setVisible(false);
                    Prenom.setVisible(false);
                    Email.setVisible(false);
                    Cne.setVisible(false);
                    line1.setVisible(false);
                    line2.setVisible(false);
                    line3.setVisible(false);
                    line4.setVisible(false);
                    vld.setVisible(false);
                    erur.setText("");

                    // ✅ Afficher les champs nouveau mot de passe
                    pass1.setVisible(true);
                    pass2.setVisible(true);
                    lineP1.setVisible(true);
                    lineP2.setVisible(true);
                    btnConfirm.setVisible(true);

                    // ✅ ActionListener Confirmer (ajouté une seule fois ici)
                    btnConfirm.addActionListener(ev -> {
                        String p1 = new String(pass1.getPassword()).trim();
                        String p2 = new String(pass2.getPassword()).trim();
                        erur.setText("");

                        if (p1.equals("Nouveau mot de passe") || p1.isEmpty()) {
                            erur.setVisible(true);
                            erur.setText("Entrez un mot de passe !");
                        } else if (p1.length() < 6) {
                            erur.setVisible(true);
                            erur.setText("Au moins 6 caractères !");
                        } else if (!p1.equals(p2)) {
                            erur.setVisible(true);
                            erur.setText("Les mots de passe ne correspondent pas !");
                        } else {
                            String hash = Functions.hashPassword(p1); 
                            if (Functions.mettreAJourMotDePasse(cne, hash, type)) {
                                JOptionPane.showMessageDialog(
                                    MotDePasseOublier.this,
                                    "✅ Mot de passe mis à jour avec succès !",
                                    "Succès", JOptionPane.INFORMATION_MESSAGE
                                );
                                // Retourner au Login
                                java.awt.Window window = SwingUtilities.getWindowAncestor(MotDePasseOublier.this);
                                if (window instanceof javax.swing.JFrame) {
                                    javax.swing.JFrame frame = (javax.swing.JFrame) window;
                                    frame.setContentPane(new LoginEtudient()); 
                                    frame.revalidate();
                                    frame.repaint();
                                }
                            } else {
                                erur.setVisible(true);
                                erur.setText("Erreur lors de la mise à jour !");
                            }
                        }
                    });

                } else {
                    erur.setText("Informations incorrectes !");
                }
            }
        });
        this.add(vld);

        // ── Lignes mot de passe (cachées au début) ───────────────
        lineP1 = creetLine(363, 355, perpul);
        lineP2 = creetLine(363, 405, perpul);
        lineP1.setVisible(false);
        lineP2.setVisible(false);
        this.add(lineP1);
        this.add(lineP2);

        // ── Champ pass1 (caché au début) ─────────────────────────
        pass1 = new JPasswordField("Nouveau mot de passe");
        pass1.setEchoChar((char) 0);
        pass1.setBounds(363, 335, 260, 18);
        pass1.setOpaque(false);
        pass1.setBorder(null);
        pass1.setHorizontalAlignment(JTextField.CENTER);
        pass1.setForeground(perpul);
        pass1.setFont(font);
        pass1.setVisible(false);
        pass1.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (new String(pass1.getPassword()).equals("Nouveau mot de passe")) {
                    pass1.setText("");
                    pass1.setEchoChar('●');
                    pass1.setForeground(bleu);
                    lineP1.setBackground(bleu);
                }
            }
            public void focusLost(FocusEvent e) {
                if (new String(pass1.getPassword()).isEmpty()) {
                    pass1.setEchoChar((char) 0);
                    pass1.setText("Nouveau mot de passe");
                    pass1.setForeground(perpul);
                    lineP1.setBackground(perpul);
                }
            }
        });
        this.add(pass1);

        // ── Champ pass2 (caché au début) ─────────────────────────
        pass2 = new JPasswordField("Confirmer mot de passe");
        pass2.setEchoChar((char) 0);
        pass2.setBounds(363, 385, 260, 18);
        pass2.setOpaque(false);
        pass2.setBorder(null);
        pass2.setHorizontalAlignment(JTextField.CENTER);
        pass2.setForeground(perpul);
        pass2.setFont(font);
        pass2.setVisible(false);
        pass2.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (new String(pass2.getPassword()).equals("Confirmer mot de passe")) {
                    pass2.setText("");
                    pass2.setEchoChar('●');
                    pass2.setForeground(bleu);
                    lineP2.setBackground(bleu);
                }
            }
            public void focusLost(FocusEvent e) {
                if (new String(pass2.getPassword()).isEmpty()) {
                    pass2.setEchoChar((char) 0);
                    pass2.setText("Confirmer mot de passe");
                    pass2.setForeground(perpul);
                    lineP2.setBackground(perpul);
                }
            }
        });
        this.add(pass2);

        // ── Bouton Confirmer (caché au début) ────────────────────
        btnConfirm = new JButton("Confirmer", icon_1);
        btnConfirm.setBounds(363, 520, 260, 30);
        btnConfirm.setHorizontalTextPosition(JButton.CENTER);
        btnConfirm.setVerticalTextPosition(JButton.CENTER);
        btnConfirm.setContentAreaFilled(false);
        btnConfirm.setBorderPainted(false);
        btnConfirm.setFocusPainted(false);
        btnConfirm.setForeground(perpul);
        btnConfirm.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirm.setVisible(false);
        btnConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btnConfirm.setIcon(icon_2); btnConfirm.setForeground(Color.white); }
            public void mouseExited (java.awt.event.MouseEvent e) { btnConfirm.setIcon(icon_1); btnConfirm.setForeground(perpul); }
        });
        this.add(btnConfirm);

        // ── Bouton Enter par défaut ──────────────────────────────
        this.addHierarchyListener(new java.awt.event.HierarchyListener() {
            @Override
            public void hierarchyChanged(java.awt.event.HierarchyEvent e) {
                if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0) {
                    if (isShowing()) {
                        JRootPane root = SwingUtilities.getRootPane(vld);
                        if (root != null) root.setDefaultButton(vld);
                    }
                }
            }
        });

        // ── Menu navigation ──────────────────────────────────────
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Navigation.lastClass, this);
        JButton contact   = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos    = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);
        this.add(acceuille);
        this.add(contact);
        this.add(propos);
    }

    // ── Rendu de l'image de fond ─────────────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}