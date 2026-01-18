import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public final class Etudient_notification extends JPanel {
        private Image backgroundImage;

    public Etudient_notification() {

        try {
            backgroundImage = ImageIO.read(new File("data/pg_Etudient_notification.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null); 
        

        Color perpul = new Color(87, 107, 194);
        //Color ciel = new Color(166, 177, 235);



              
        this.add(Functions.LogOutIcon(this));
            
       

        JButton Profil =  Functions.createNavButton(55, 246, "profil", this);
        JButton Settings = Functions.createNavButton(55, 406, "settings", this);
        JButton Notes =  Functions.createNavButton(55, 326,  "notes", this);


        this.add(Profil);
        this.add(Settings);
        this.add(Notes);

        JButton acceuille = Functions.creerMenu("Acceuille", 300, 60, perpul, Acceuille.class, this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, propos.class, this);
        
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
