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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField; 
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class LoginEtudient extends JPanel { 

    private Image backgroundImage;
    JLabel cader2,cader1;
    
    ImageIcon icon_1  = new ImageIcon(new ImageIcon("data/shape_1.png").getImage().getScaledInstance(350, 100, Image.SCALE_SMOOTH));
    ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/shape_2.png").getImage().getScaledInstance(350, 100, Image.SCALE_SMOOTH));

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
            public void mouseEntered(java.awt.event.MouseEvent e){
                button_shape_1.setIcon(icon_2);
                button_shape_1.setForeground(Color.white); 
            }
            public void mouseExited(java.awt.event.MouseEvent e){
                button_shape_1.setIcon(icon_1);
                button_shape_1.setForeground(co);
            }
        });

        return button_shape_1;
    }

    
    public LoginEtudient() {

        try {
            backgroundImage = ImageIO.read(new File("data/Login_Etudiant.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null); 
        
        Color perpul = new Color(87, 107, 194);
        
        JTextField cneEtud = new JTextField();
		cneEtud.setBounds(400, 345, 250, 30);
		cneEtud.setFont(new Font("Arial", Font.PLAIN, 14));
		cneEtud.setOpaque(false);
		cneEtud.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(120, 140, 255)));
		cneEtud.addFocusListener(new FocusListener() {
			@Override
            public void focusGained(FocusEvent e) { 
			   String text = "Entrez votre CNE";
			   Color befor = new Color(150, 150, 150);
			   Color after = new Color(87, 107, 194);
			   JLabel line = new JLabel();
			   line.setBackground(befor);
			   line.setOpaque(true);
			   line.setBounds(400,375,250,4);
			   if (cneEtud.getText().equals(text)){
				   cneEtud.setText("");
				   cneEtud.setForeground(after);
				line.setBackground(after);
			   }
			}

			@Override
			public void focusLost(FocusEvent e) {
							   String text = "Entrez votre CNE";
			   Color befor = new Color(150, 150, 150);
			   Color after = new Color(87, 107, 194);
			   JLabel line = new JLabel();
			   line.setBackground(befor);
			   line.setOpaque(true);
			   line.setBounds(400,375,250,4);
			   if (cneEtud.getText().isEmpty()){
				   cneEtud.setText(text);
				   cneEtud.setForeground(befor);
				line.setBackground(befor);
			   }
			}
        });

		this.add(cneEtud);
		
		JPasswordField passEtud = new JPasswordField();
		passEtud.setBounds(400, 420, 250, 30);
		passEtud.setFont(new Font("Arial", Font.PLAIN, 14));
		passEtud.setOpaque(false);
		passEtud.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(120, 140, 255)));
		this.add(passEtud);
		
		JButton loginBtn = new JButton("se connecter", icon_1);
		loginBtn.setBounds(340, 480, 340, 55);
		loginBtn.setHorizontalTextPosition(JButton.CENTER); 
		loginBtn.setVerticalTextPosition(JButton.CENTER);  
		loginBtn.setContentAreaFilled(false); 
		loginBtn.setBorderPainted(false);     
		loginBtn.setFocusPainted(false);
		loginBtn.setForeground(perpul); 
		loginBtn.setFont(new Font("Arial", Font.BOLD, 14)); 
		loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		loginBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent e){
				loginBtn.setIcon(icon_2);
				loginBtn.setForeground(Color.white); 
			}
			public void mouseExited(java.awt.event.MouseEvent e){
				loginBtn.setIcon(icon_1);
				loginBtn.setForeground(perpul); 
			}
		});
		this.add(loginBtn);
		
		
		JLabel forgotLabel = new JLabel("<HTML><U>Mot de passe oublié</U></HTML>");
		forgotLabel.setBounds(440, 550, 200, 25);
		forgotLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		forgotLabel.setForeground(perpul);
		forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		forgotLabel.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent e) { 
		        // this = panel actuel (login)
		        java.awt.Window window = SwingUtilities.getWindowAncestor(LoginEtudient.this); 
		        if (window instanceof javax.swing.JFrame) {
		            javax.swing.JFrame frame = (javax.swing.JFrame) window;

		            // changer le contenu de la fenêtre par le nouveau panel
		            motDePasseOublier panelMdp = new motDePasseOublier();
		            frame.setContentPane(panelMdp);
		            frame.revalidate();
		            frame.repaint();
		        }
		    }
		});
		this.add(forgotLabel);
		
       
        JButton acceuille = creerMenu("Acceuille", 280, 50, perpul);
        JButton contact = creerMenu("Contact", 480, 50, perpul);
        JButton propos = creerMenu("A propos", 680, 50, perpul);
        
        acceuille.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent e) { 
		        // this = panel actuel (login)
		        java.awt.Window window = SwingUtilities.getWindowAncestor(LoginEtudient.this); 
		        if (window instanceof javax.swing.JFrame) {
		            javax.swing.JFrame frame = (javax.swing.JFrame) window;

		            // changer le contenu de la fenêtre par le nouveau panel
		            Acceuille panelMdp = new Acceuille();
		            frame.setContentPane(panelMdp);
		            frame.revalidate();
		            frame.repaint();
		        }
		    }
		});

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