package panels;



import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import tools.Connexion;
import tools.Functions;
import tools.Navigation;
import tools.Session;

public class Enseignant_settings extends JPanel{

    private Image backgroundImage;
    JLabel Profil;
    Color perpul = new Color(87, 107, 194) , lightPerpul = new Color(78, 94, 241);
    ImageIcon icon_1 = new ImageIcon(new ImageIcon("data/mail_icon.png").getImage().getScaledInstance(50 , 50, Image.SCALE_SMOOTH));
    ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/luck_icon.png").getImage().getScaledInstance(50 , 50, Image.SCALE_SMOOTH));

    JLabel ErurMail,ErurPass;
    // Top of class
    public static BufferedImage control_picture;

    // Static load
    static {
        try {
            File f = new File("data/control_photo.png");
            if (f.exists()) {
                control_picture = ImageIO.read(f);
            } else {
                System.err.println("ERROR: not found: data/contorl_photo.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static BufferedImage applyPhotoFrame(BufferedImage sourceImage) {
        if (control_picture == null || sourceImage == null) return sourceImage;

        int w = control_picture.getWidth();
        int h = control_picture.getHeight();

        // Resize user photo to frame size (fast + quality)
        BufferedImage resizedSource = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedSource.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(sourceImage, 0, 0, w, h, null);
        g.dispose();

        BufferedImage finalImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // Tolerance for "white"
        final int WHITE_TOL = 10; // 0 = only 255; 10 means >=245

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                int controlARGB = control_picture.getRGB(x, y);
                int a = (controlARGB >>> 24) & 0xFF;

                // transparent in frame => transparent output
                if (a == 0) {
                    finalImage.setRGB(x, y, 0x00000000);
                    continue;
                }

                int r = (controlARGB >>> 16) & 0xFF;
                int gC = (controlARGB >>> 8) & 0xFF;
                int b = (controlARGB) & 0xFF;

                boolean isWhite = (r >= 255 - WHITE_TOL) && (gC >= 255 - WHITE_TOL) && (b >= 255 - WHITE_TOL);

                if (isWhite) {
                    finalImage.setRGB(x, y, resizedSource.getRGB(x, y)); // user photo
                } else {
                    finalImage.setRGB(x, y, controlARGB); // frame pixel
                }
            }
        }

        return finalImage;
    }

    public void saveMail(String mail1 , String mail2){
        Connection con = Connexion.getConnexion();
        String sql = "update enseignant set email = ? where email = ?";
        try(PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, mail1);
            pr.setString(2, mail2);
            int num = pr.executeUpdate();
            
        } catch (Exception e) {
             System.err.println(e.getMessage());
        }
    }
    public void savePass(String pass){
        Connection con = Connexion.getConnexion();
        String sql = "update enseignant set password = ? where id_enseignant = ?";
        try(PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, pass);
            pr.setString(2, Session.getEnseignant().getId_ens());
            int num = pr.executeUpdate();
            
        } catch (Exception e) {
             System.err.println(e.getMessage());
        }
    }

    public boolean checkMail(String mail){
        Connection con = Connexion.getConnexion();
        String sql = "Select * from enseignant where email = ?";
        try(PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, mail);
            try (ResultSet rs = pr.executeQuery()){
                if(rs.next()){
                    return true;
                }
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            
        } catch (Exception e) {
             System.err.println(e.getMessage());
        }
        return false;
    }   



    public void savePhoto(String cne, byte[] photoBytes) {
    

        // 2. The SQL Update Query
        String sql = "UPDATE enseignant SET photo_profil = ? WHERE id_enseignant = ?";

        try (Connection conn = Connexion.getConnexion();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 3. Set the parameters
            if (photoBytes != null) {
                pstmt.setBytes(1, photoBytes); // The photo data
            } else {
                pstmt.setNull(1, Types.BLOB);  // Handle null case
            }
            
            pstmt.setString(2, cne);       // The Student CNE

            // 4. Execute
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("Photo saved successfully for id_enseignant: " + cne);
            } else {
                System.err.println("Error: id_enseignatn not found: " + cne);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Import needed for this method
    // import java.io.ByteArrayOutputStream;
    // import java.sql.Types;

    public static void saveTheProfil(String cne, BufferedImage newPhoto) {
        String sql = "UPDATE enseignant SET photo_profil = ? WHERE id_enseignant = ?";
        
        try (Connection conn = Connexion.getConnexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (newPhoto != null) {
                // 1. Convert BufferedImage to Byte Array (PNG format)
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(newPhoto, "png", baos);
                byte[] imgBytes = baos.toByteArray();
                
                // 2. Set the Bytes in the Query
                pstmt.setBytes(1, imgBytes);
            } else {
                // Handle case where we want to remove the photo
                pstmt.setNull(1, java.sql.Types.BLOB);
            }
            
            pstmt.setString(2, cne);
            
            // 3. Execute Update
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                // 4. Update the Session variable immediately
                Session.photo = newPhoto;
                System.out.println("Photo saved successfully for id_enseignant: " + cne);
            } else {
                System.err.println("Error: CNE not found in database: " + cne);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error saving photo to DB: " + e.getMessage());
        }
    }


    public Enseignant_settings() {
        this.add(Functions.LogOutIcon(this));
        Navigation.lastClass = this.getClass();
        this.setLayout(null);

        try {
            backgroundImage = ImageIO.read(new File("data/pg_Etudient_settings.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_Etudient_settings.png");
        }

        JLabel profilIconMini = new JLabel(new ImageIcon(Session.photo.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        profilIconMini.setBounds(920, 48, 40, 40);
        this.add(profilIconMini);

        JLabel myname = Functions.creetLabel(710, 60, Session.userName);
        myname.setHorizontalAlignment(JLabel.RIGHT);
        this.add(myname);
        //---------------------------------------------------------------------------//

        JLabel parametre = new JLabel("Paramètres du compte");
        parametre.setForeground(new Color(78, 94, 241));
        parametre.setFont(Functions.getMyFont("", Font.BOLD, 25f));
        parametre.setBounds(410, 150, 400, 30);
        this.add(parametre);

        //===========
        Color grey = new Color(144 , 159 , 250);

        JLabel text1 =  Contact.cretText(" Changer Profil ", 180, 200);
        text1.setForeground(lightPerpul);
        this.add(text1);

        JLabel changeProfil = Functions.createCustomLabelWithBorder("Changer la photo de profil", 400, 255, 300, 30, 7, 7, 7, 7, perpul);
        changeProfil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changeProfil.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                changeProfil.setBackground(grey);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                changeProfil.setBackground(perpul);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                // 1. Crop
                CropTool.CropResult cropResult = CropTool.openAndCrop(changeProfil);

                if (cropResult != null) {
                    try {
                        // 2. Apply Frame (Using the static control_picture automatically)
                        BufferedImage finalImage = applyPhotoFrame(cropResult.image);
                        saveTheProfil(Session.getEnseignant().getId_ens(), finalImage);
                        // 3. Display
                        Image scaled = finalImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                        Profil.setIcon(new ImageIcon(scaled));
                        profilIconMini.setIcon(new ImageIcon(finalImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
                        
                        
                        
                        // updateDatabase(bytesForDB);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            
         });
       
        this.add(changeProfil);

        Profil = new JLabel(new ImageIcon(Session.photo.getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        Profil.setBounds(300, 230, 80, 80);
        this.add(Profil);

        //=============

        JLabel text2 =  Contact.cretText(" Changer Email ", 180, 350);
        text2.setForeground(lightPerpul);
        this.add(text2);

        JLabel Icon_mail = new JLabel(icon_1);
        Icon_mail.setBounds(190 , 380 , 50 , 50);
        this.add(Icon_mail);

        JTextField neuvelleMail = Contact.cretInp(505, 402, 190, 22);
        this.add(neuvelleMail);

        JLabel emailActeuil = new JLabel(Session.getEnseignant().getEmail());
        emailActeuil.setBounds(265 , 402 , 190 , 22);
        emailActeuil.setFont(new Font("Arial" , Font.ITALIC , 11));
        emailActeuil.setForeground(Color.BLACK);
        this.add(emailActeuil);

        JLabel ea =  Contact.cretText("Email Actuel" , 265 , 380);
        ea.setFont(new Font("Arial" , Font.BOLD , 12));
        this.add(ea);

        JLabel ne =  Contact.cretText("Nouvelle Email" , 505 , 380);
        ne.setFont(new Font("Arial" , Font.BOLD , 12));
        this.add(ne);

        this.add(Contact.cretInpCad(Contact.inp2, 260, 400, 200, 31));
        this.add(Contact.cretInpCad(Contact.inp2, 500, 400, 200, 31));

        

        JLabel confiremEmail = Functions.createCustomLabelWithBorder("Confirmer", 740, 400, 100, 25, 7, 7, 7,7, perpul);
        confiremEmail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confiremEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                confiremEmail.setBackground(grey);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                confiremEmail.setBackground(perpul);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String etud_mail = neuvelleMail.getText().trim();
                if (etud_mail.isEmpty()) {
                    ErurMail.setText("Remplire le neuveille email");
                } else if (!Functions.isValidEmail(etud_mail)) {
                    ErurMail.setText("donner un frome mail corecte");
                } else if (checkMail(etud_mail)) {
                    ErurMail.setText("Email deja utiliser");
                }else{
                    Color ciel = new Color(193, 221, 240);
                    JPanel mainPanel = new JPanel(new java.awt.BorderLayout(10, 10));
                    mainPanel.setBackground(ciel);
                    mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)); 

                    JLabel label = new JLabel("<html>Êtes-vous sûr de vouloir changer votre adresse e-mail pour :<br/><b>" + etud_mail + "</b> ?</html>");
                    label.setFont(new Font("Arial", Font.PLAIN, 14));
                    label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    mainPanel.add(label, java.awt.BorderLayout.CENTER);

                    // 3. Set Global UI Colors just for this Dialog
                    javax.swing.UIManager.put("OptionPane.background", ciel);
                    javax.swing.UIManager.put("Panel.background", ciel);

                    // 4. Show the Styled Dialog
                    int answer = JOptionPane.showConfirmDialog(
                        Enseignant_settings.this, // Use the panel instance as parent
                        mainPanel, 
                        "Confirmation changement", 
                        javax.swing.JOptionPane.YES_NO_OPTION, 
                        javax.swing.JOptionPane.PLAIN_MESSAGE
                    );

                    if(answer == JOptionPane.YES_OPTION){
                        Session.getEnseignant().setEmail(etud_mail);
                        saveMail(etud_mail, emailActeuil.getText());

                    }
                }
                
            }

        });
        this.add(confiremEmail);


        ErurMail = new JLabel();
        ErurMail.setForeground(Color.red);
        ErurMail.setFont(new Font("Arial", Font.BOLD, 12));
        ErurMail.setBounds(260, 440, 440, 25);
        ErurMail.setHorizontalAlignment(JLabel.CENTER);
        this.add(ErurMail);

        //===========================

        JLabel text3 =  Contact.cretText(" Changer Mot De Passe ", 0, 0);
        text3.setBounds(180 , 490 , 300 , 20);
        text3.setForeground(lightPerpul);
        this.add(text3);

        JLabel Icon_pass = new JLabel(icon_2);
        Icon_pass.setBounds(190 , 520 , 50 , 50);
        this.add(Icon_pass);


        JPasswordField passActuel = new JPasswordField();
        passActuel.setOpaque(false);
        passActuel.setBorder(null);
        passActuel.setBounds(265, 542, 140, 22);
        this.add(passActuel);

        JPasswordField passNeuvelle = new JPasswordField();
        passNeuvelle.setOpaque(false);
        passNeuvelle.setBorder(null);
        passNeuvelle.setBounds(435, 542, 140, 22);
        this.add(passNeuvelle);

        JPasswordField passConfirm = new JPasswordField();
        passConfirm.setOpaque(false);
        passConfirm.setBorder(null);
        passConfirm.setBounds(605, 542, 140, 22);
        this.add(passConfirm);


        JLabel pa =  Contact.cretText("Mot De Pass Actuel" , 265 , 520);
        pa.setFont(new Font("Arial" , Font.BOLD , 12));
        this.add(pa);

        JLabel np =  Contact.cretText("Nouvelle Mot de Pass" , 435 , 520);
        np.setFont(new Font("Arial" , Font.BOLD , 12));
        this.add(np);
        
        JLabel nc =  Contact.cretText("Confirme Mot de Pass" , 605 , 520);
        nc.setFont(new Font("Arial" , Font.BOLD , 12));
        this.add(nc);

        this.add(Contact.cretInpCad(Contact.inp1, 260, 540, 150, 31));
        this.add(Contact.cretInpCad(Contact.inp1, 430, 540, 150, 31));
        this.add(Contact.cretInpCad(Contact.inp1, 600, 540, 150, 31));

        

        // N'oublie pas l'import si ce n'est pas déjà fait :

// ...

        JLabel confirmPass = Functions.createCustomLabelWithBorder(
                "Confirmer", 780, 540, 100, 25, 7, 7, 7, 7, perpul
        );
        confirmPass.setCursor(new Cursor(Cursor.HAND_CURSOR));      // Couleur initiale

        confirmPass.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                confirmPass.setBackground(grey);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                confirmPass.setBackground(perpul);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                // 0) Vérifier session
                if (Session.getEnseignant() == null) {
                    JOptionPane.showMessageDialog(null, "Session expirée. Veuillez vous reconnecter.");
                    return;
                }

                String inputActuel = new String(passActuel.getPassword());
                String nouveau = new String(passNeuvelle.getPassword());
                String confirmation = new String(passConfirm.getPassword());

                // Reset erreur au début
                ErurPass.setText("");

                // 1) Champs vides
                if (inputActuel.isEmpty() || nouveau.isEmpty() || confirmation.isEmpty()) {
                    ErurPass.setText("Tous les champs doivent être remplis.");
                    return;
                }
                // 4) Vérifier mot de passe actuel (hash)
                String hashSaisieActuelle = Functions.hashPassword(inputActuel);
                String hashStocke = Session.getEnseignant().getPassword();

                if (hashStocke == null || !hashSaisieActuelle.equals(hashStocke)) {
                    ErurPass.setText("Le mot de passe actuel est incorrect.");
                    return;
                }

                // 2) Longueur min 8
                if (nouveau.length() < 8) {
                    ErurPass.setText("Le nouveau mot de passe doit contenir au moins 8 caractères.");
                    return;
                }

                // 3) Nouveau = ancien (interdit)
                if (nouveau.equals(inputActuel)) {
                    ErurPass.setText("Le nouveau mot de passe doit être différent de l'ancien.");
                    return;
                }

                
                

                // 5) Confirmation identique
                if (!nouveau.equals(confirmation)) {
                    ErurPass.setText("La confirmation ne correspond pas au nouveau mot de passe.");
                    return;
                }

                // 6) Demande de confirmation (JDialog)
                int choix = JOptionPane.showConfirmDialog(
                        null,
                        "Êtes-vous sûr de vouloir changer votre mot de passe ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (choix != JOptionPane.YES_OPTION) {
                    ErurPass.setText("Changement annulé.");
                    return;
                }

                // 7) Mise à jour BD
                try {
                    String hashNouveau = Functions.hashPassword(nouveau);

                    Session.getEnseignant().setPassword(hashNouveau);
                    savePass(hashNouveau);

                    JOptionPane.showMessageDialog(null, "Mot de passe modifié avec succès !");

                    // Reset champs + erreur
                    passActuel.setText("");
                    passNeuvelle.setText("");
                    passConfirm.setText("");
                    ErurPass.setText("");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
                }
            }
        });

        this.add(confirmPass);


        ErurPass = new JLabel();
        ErurPass.setForeground(Color.red);
        ErurPass.setFont(new Font("Arial", Font.BOLD, 12));
        ErurPass.setBounds(260, 580, 440, 25);
        ErurPass.setHorizontalAlignment(JLabel.CENTER);
        this.add(ErurPass);





        //---------------------------------------------------------------------------//



        this.add(Functions.createNavButton(55, 246, "profil_ens", this));
        this.add(Functions.createNavButton(55, 486, "notification_ens", this));
        this.add(Functions.createNavButton(55, 326,  "notes_ens", this));


        this.add(Functions.creerMenu("Accueil", 300, 60, perpul, Navigation.lastClass, this));
        this.add(Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this));
        this.add(Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}