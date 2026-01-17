import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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



              
        ImageIcon logOutIcon1 = new ImageIcon(new ImageIcon("data/logOut_icon_1.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon logOutIcon2 = new ImageIcon(new ImageIcon("data/logOut_icon_2.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

        
        JButton LogOut = new JButton(logOutIcon1);
        LogOut.setBounds(58, 170, 30, 30); 
        LogOut.setContentAreaFilled(false);
        LogOut.setBorderPainted(false);
        LogOut.setFocusPainted(false);
        LogOut.setCursor(new Cursor(Cursor.HAND_CURSOR));

       
        LogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                LogOut.setIcon(logOutIcon2);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                LogOut.setIcon(logOutIcon1);
            }
        });

        LogOut.addActionListener(e -> Functions.logoutWithConfirm(this));

        this.add(LogOut);
            
       

        JButton Profil =  Functions.createNavButton(55, 246, "profil", this);
        JButton Settings = Functions.createNavButton(55, 406, "settings", this);
        JButton Notes =  Functions.createNavButton(55, 326,  "notes", this);


        this.add(Profil);
        this.add(Settings);
        this.add(Notes);

        JButton contact = Functions.creerMenu("Contact", 330, 50, perpul, contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 460, 50, perpul, propos.class, this);


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
