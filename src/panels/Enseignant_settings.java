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

public class Enseignant_settings extends JPanel{

    private Image backgroundImage;
    
    public Enseignant_settings() {
        Navigation.lastClass = this.getClass();

        try {
            backgroundImage = ImageIO.read(new File("data/pg_Etudient_settings.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null); 

        JLabel profilIconMini = new JLabel(new ImageIcon(Session.photo.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        profilIconMini.setBounds(920,48 , 40, 40);
        this.add(profilIconMini);
    
        JLabel myname = Functions.creetLabel(710, 60, Session.userName);
        myname.setHorizontalAlignment(JLabel.RIGHT);
        this.add(myname);
        
        Color perpul = new Color(87, 107, 194);

        this.add(Functions.LogOutIcon(this));
            
        JButton Profil =  Functions.createNavButton(55, 246, "profil_etd", this);
        JButton Notification = Functions.createNavButton(55, 486, "notification_etd", this);
        JButton Notes =  Functions.createNavButton(55, 326,  "notes_etd", this);

        this.add(Profil);
        this.add(Notification);
        this.add(Notes);

        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Navigation.lastClass , this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);
        
        this.add(acceuille);
        this.add(contact);
        this.add(propos);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}