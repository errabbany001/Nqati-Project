import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class LoginEtudient extends JPanel { 

    private Image backgroundImage;
    


    private JButton creerMenu(String text, int x , int y , Color co){
        // had fonction back sawebt le menu dyal aceuille, contact w propos
    	ImageIcon icon_1  = new ImageIcon(new ImageIcon("data/shape_1.png").getImage().getScaledInstance(160, 40, Image.SCALE_SMOOTH));
        ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/shape_2.png").getImage().getScaledInstance(160, 40, Image.SCALE_SMOOTH));

        JButton button_shape_1 = new JButton(text, icon_1);

        button_shape_1.setHorizontalTextPosition(JButton.CENTER); 
        button_shape_1.setVerticalTextPosition(JButton.CENTER);  

        
        button_shape_1.setContentAreaFilled(false); 
        button_shape_1.setBorderPainted(false);     
        button_shape_1.setFocusPainted(false);
        button_shape_1.setForeground(co); 
        button_shape_1.setFont(new Font("Arial", Font.BOLD, 14)); 
        button_shape_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button_shape_1.setBounds(x, y, 160, 40);

        button_shape_1.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
            public void mouseEntered(java.awt.event.MouseEvent e){
                button_shape_1.setIcon(icon_2);
                button_shape_1.setForeground(Color.white); 
            }
			@Override
            public void mouseExited(java.awt.event.MouseEvent e){
                button_shape_1.setIcon(icon_1);
                button_shape_1.setForeground(co);
            }
        });

        return button_shape_1;
    }

    
    public LoginEtudient() {

        try {
            backgroundImage = ImageIO.read(new File("data/Pg_LoginEtudient.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null); 
        

        Color perpul = new Color(87, 107, 194);
        //Color ciel = new Color(166, 177, 235);
       



       


    
  



        JButton acceuille = creerMenu("Acceuille", 400, 50, perpul);
        JButton contact = creerMenu("Contact", 600, 50, perpul);
        JButton propos = creerMenu("A propos", 800, 50, perpul);

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

