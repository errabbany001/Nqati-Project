package panels;

import element.Enseignant;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import tools.Connexion;
import tools.Functions;
import tools.Navigation;
import tools.Session;

public final class LoginEnseignant extends JPanel{ 

    // Déclaration des images de fond, des icônes et des variables d'état (visibilité mot de passe)
    private Image backgroundImage;
    private JLabel line1, line2, erur;
    private char defaultEchoChar;
    private int switcher = 0;
    private JButton icon; 
    
    ImageIcon icon_1 = new ImageIcon(new ImageIcon("data/icon1.png").getImage().getScaledInstance(320, 30, Image.SCALE_SMOOTH));
    ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/icon2.png").getImage().getScaledInstance(320, 30, Image.SCALE_SMOOTH));
    
    ImageIcon show = new ImageIcon(new ImageIcon("data/show_icon.png").getImage().getScaledInstance(25, 18, Image.SCALE_SMOOTH));
    ImageIcon hide = new ImageIcon(new ImageIcon("data/hide_icon.png").getImage().getScaledInstance(25, 18, Image.SCALE_SMOOTH));
    


    // Méthode utilitaire pour créer les lignes décoratives sous les champs de saisie
    public JLabel creetLine(int x, int y, Color c){
        JLabel line = new JLabel();
        line.setBackground(c); line.setOpaque(true);
        line.setBounds(x, y, 320, 4); return line;
    }


    public Enseignant getTheTeacher(  String ens_mail){
        Connection con = Connexion.getConnexion();
        Enseignant enseignant = new Enseignant();
        enseignant.setEmail(ens_mail);

        String sql  = "select id_enseignant ,nom , prenom , dateDeNaissance , genre, d.nom_departement from enseignant e " +
                      "join departement d  on d.id_departement = e.id_departement "
                      +"where email = ? ;";



        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ens_mail);

            try (ResultSet rs = ps.executeQuery()){
               if(rs.next()) {
                    enseignant.setId_ens(rs.getString(1));
                    enseignant.setNom(rs.getString(2));
                    enseignant.setPrenom(rs.getString(3));
                    enseignant.setDateDeNaissance(rs.getString(4));
                    enseignant.setGener(rs.getString(5));
                    enseignant.setDepatement(rs.getString(5));
                    enseignant.setCours(getCours(rs.getString(1)));
                
               }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return enseignant;
    }


    public ArrayList<String[]> getCours(String id_ens){
        ArrayList<String[]> cours = new ArrayList<>();

        Connection con = Connexion.getConnexion();
        String sql = "select id_cour , nom_cour , nom_niveau , nom_filiere , nom_semester from cour co " +
                        "join semester sem on sem.id_semester = co.id_semester " +
                        "join filiere fi on fi.id_filiere = sem.id_filiere " +
                        "join niveau ni on ni.id_niveau = fi.id_niveau " +
                        "where id_enseignant = ? ;"; 

        try(PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, id_ens);
            try(ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    cours.add(new String[] { rs.getString(1) , rs.getString(2) , rs.getString(3) , rs.getString(4) , rs.getString(5)});
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return cours;
    }

    



    public LoginEnseignant() {
        
        // Initialisation du panneau : sauvegarde de l'historique et chargement du fond
        Navigation.lastClass = this.getClass();

        try {
            backgroundImage = ImageIO.read(new File("data/Login_Enseignant.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        //Connexion a la base des donnees
        Connection con =  Connexion.getConnexion();

        this.setLayout(null); 
        

        
        // Gestionnaire pour retirer le focus des champs de saisie lors d'un clic sur le panneau
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                requestFocusInWindow();
            }
        });

        Color perpul = new Color(87, 107, 194);
        Color bleu = new Color(19, 43, 149);
        Font font = new Font("Calibri", Font.BOLD, 15);

        // Ajout du bouton retour et des lignes d'interface
        this.add(Functions.cretBackBtn());
        line1 = creetLine(330, 368, perpul);
        line2 = creetLine(330, 447, perpul);
        this.add(line1);
        this.add(line2);

        // Configuration du champ Email avec gestion du texte par défaut (Placeholder)
        JTextField mail = new JTextField();
        mail.setBounds(360, 345, 270, 30);
        mail.setOpaque(false);
        mail.setBorder(null);
        mail.setHorizontalAlignment(JTextField.CENTER);
        mail.setForeground(perpul);
        mail.setFont(font);
        mail.setText("Email  ");

        mail.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                erur.setText("");
                if ("Email  ".equals(mail.getText())) {
                    mail.setText("");
                }
                mail.setBounds(360, 343, 270, 30);
                mail.setForeground(bleu);
                line1.setBackground(bleu);
            }

            @Override
            public void focusLost(FocusEvent e) {
                mail.setCaretPosition(0);
                if (mail.getText().replace(" ", "").equals("")) {
                    mail.setText("Email  ");
                    mail.setForeground(perpul);
                    line1.setBackground(perpul);
                    mail.setHorizontalAlignment(JTextField.CENTER); 
                    mail.setBounds(360, 345, 270, 30);
                }
            }
        });
        this.add(mail);

        // Configuration du champ Mot de passe avec gestion de l'écho des caractères (masquage)
        JPasswordField passWord = new JPasswordField();
        passWord.setBounds(370, 424, 250, 30);
        passWord.setOpaque(false);
        passWord.setBorder(null);
        passWord.setHorizontalAlignment(JTextField.CENTER);
        passWord.setForeground(perpul);
        passWord.setFont(font);
        defaultEchoChar = passWord.getEchoChar(); 
        passWord.setEchoChar((char) 0); 
        passWord.setText("Mot de passe");

        passWord.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                erur.setText("");
                if ("Mot de passe".equals(String.valueOf(passWord.getPassword()))) {
                    passWord.setText("");
                    passWord.setEchoChar(defaultEchoChar); 
                    passWord.setBounds(370, 422, 250, 30);
                }
                passWord.setForeground(bleu);
                line2.setBackground(bleu);
                icon.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passWord.getPassword()).trim().isEmpty()) {
                    passWord.setText("Mot de passe");
                    passWord.setForeground(perpul);
                    line2.setBackground(perpul);
                    passWord.setEchoChar((char) 0); 
                    icon.setVisible(false);
                    passWord.setBounds(370, 424, 250, 30);
                }
            }
        });
        this.add(passWord);

        // Bouton pour afficher ou masquer le mot de passe en clair
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
                    passWord.setEchoChar((char) 0);
                } else {
                    icon.setIcon(hide);
                    switcher = 0;
                    passWord.setEchoChar(defaultEchoChar);
                }
            }
        });
        icon.setVisible(false);
        this.add(icon);

        // Bouton de validation pour se connecter et accéder au profil étudiant
        JButton loginBtn = new JButton("se connecter" , icon_1);
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
            java.awt.Window window = SwingUtilities.getWindowAncestor(LoginEnseignant.this);
            if (window instanceof javax.swing.JFrame) {
                char[] input = passWord.getPassword();
                String passString = new String(input);

                if(Functions.checkUser(con, "enseignant",mail.getText().toLowerCase().trim() ,passString )){
                Enseignant ens = getTheTeacher(mail.getText().toLowerCase().trim());
                ens.setEmail(mail.getText().toLowerCase().trim());

                Session.setEnseignant(ens);
                

                javax.swing.JFrame frame = (javax.swing.JFrame) window;
                Session.isLoggedIn = 1;
                Navigation.addToHistory(LoginEnseignant.class); 
                Enseignant_profil panelMdp = new Enseignant_profil();
                frame.setContentPane(panelMdp);
                frame.revalidate();
                frame.repaint();
                }else{
                  erur.setText("Email ou mot de passe incorrect");  
                }

            }
        });
        this.add(loginBtn);

        // Label cliquable pour la redirection vers le formulaire de mot de passe oublié
        JLabel forgotLabel = new JLabel("<HTML><U>Mot de passe oublié</U></HTML>");
        forgotLabel.setBounds(427, 540, 132, 25);
        forgotLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        forgotLabel.setForeground(perpul);
        forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        forgotLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                java.awt.Window window = SwingUtilities.getWindowAncestor(LoginEnseignant.this);
                if (window instanceof javax.swing.JFrame) {
                    javax.swing.JFrame frame = (javax.swing.JFrame) window;
                    MotDePasseOublier panelMdp = new MotDePasseOublier();
                    Navigation.addToHistory(LoginEnseignant.class);
                    frame.setContentPane(panelMdp);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });
        this.add(forgotLabel);

        // Zone d'affichage des messages d'erreur de validation
        erur = new JLabel();
        erur.setForeground(Color.red);
        erur.setFont(new Font("Arial", Font.BOLD, 14));
        erur.setBounds(360, 565, 260, 30);
        erur.setHorizontalAlignment(JLabel.CENTER);
        this.add(erur);

        // Définition du bouton par défaut (Entrée) une fois le composant affiché à l'écran
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

        // Initialisation des éléments du menu de navigation supérieur
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Navigation.lastClass , this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);
          
        this.add(acceuille);
        this.add(contact);
        this.add(propos);
    }

    // Gestion du rendu graphique de l'image de fond du panneau
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}