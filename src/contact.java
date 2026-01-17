import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public final class contact extends JPanel {

    private Image backgroundImage;
    JLabel cader2,cader1;



    
    public contact() {

        try {
            backgroundImage = ImageIO.read(new File("data/pg_home.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null);
        

        Color perpul = new Color(87, 107, 194);
        

        JButton acceuille = Functions.creerMenu("Acceuille", 280, 50, perpul, Acceuille.class, this);
        JButton contact = Functions.creerMenu("Contact", 480, 50, perpul, contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 680, 50, perpul, propos.class, this);

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

