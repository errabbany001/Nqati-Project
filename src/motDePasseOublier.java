
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
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public final class MotDePasseOublier extends JPanel{
    
    private Image backgroundImage;

    private final JLabel line1 , line2 , line3 , line4;



    ImageIcon icon_1  = new ImageIcon(new ImageIcon("data/icon1.png").getImage().getScaledInstance(260, 30, Image.SCALE_SMOOTH));
    ImageIcon icon_2  = new ImageIcon(new ImageIcon("data/icon2.png").getImage().getScaledInstance(260, 30, Image.SCALE_SMOOTH));

    
    public JLabel creetLine(int x , int y , Color c){
        JLabel line = new JLabel();
        line.setBackground(c);
        line.setOpaque(true);
        line.setBounds(x,y,260,4);
        return line;
    }



    public JTextField creeField(int x , int y , String text , JLabel line , Color befor , Color after , Font font){
        JTextField field = new JTextField(text);

        field.setBounds(x,y,260,18);
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
               if (field.getText().replace(" ","").equals("")){
                field.setText(text);
                field.setForeground(befor);
                line.setBackground(befor);
               }

            } 
        });

        return field;
    }






    

    @SuppressWarnings("Convert2Lambda")
    public MotDePasseOublier() {

        Main.setLastClass(this.getClass());
        try {
            backgroundImage = ImageIO.read(new File("data/pg_PasseOublier.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null); 
        Main.printHistory();

        this.add(Functions.cretBackBtn());



        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                requestFocusInWindow(); 
            }
        });
        

        Color perpul = new Color(87, 107, 194);
        Color bleu = new Color(19, 43, 149);
        Font font = new Font("Calibri", Font.BOLD, 15);

        line1 = creetLine(363, 330, perpul);
        line2 = creetLine(363, 380, perpul);
        line3 = creetLine(363, 430, perpul);
        line4 = creetLine(363, 480, perpul);


        this.add(line1);
        this.add(line2);
        this.add(line3);
        this.add(line4);


        

        JTextField Nom = creeField(363, 310, "Nom", line1, perpul, bleu, font);
        JTextField Prenom = creeField(363, 360, "Prenom", line2, perpul, bleu, font);
        JTextField Email = creeField(363, 410, "Email", line3, perpul, bleu, font);
        JTextField Cne = creeField(363, 460, "CNE", line4, perpul, bleu, font);

        this.add(Nom);
        this.add(Prenom);
        this.add(Email);
        this.add(Cne);

        JLabel erur = new JLabel();
        erur.setForeground(Color.red);
        erur.setFont(new Font("Arial", Font.BOLD, 12));
        erur.setBounds(369, 560, 260, 30);
        erur.setHorizontalAlignment(JLabel.CENTER);
        this.add(erur);


        // ================== Bouton se valider ==================
        JButton vld = new JButton("Valide" , icon_1);
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
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            vld.setIcon(icon_2);
            vld.setForeground(Color.white);
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            vld.setIcon(icon_1);
            vld.setForeground(perpul);
        }});
        vld.addActionListener(e -> {
            String email = Email.getText();
            String nom = Nom.getText();
            String prenom = Prenom.getText();
            String cne = Cne.getText();
            erur.setText("");

            if (email.trim().equalsIgnoreCase("Email") || 
                nom.trim().equalsIgnoreCase("Nom") || 
                prenom.trim().equalsIgnoreCase("Prenom") || 
                cne.trim().equalsIgnoreCase("CNE")) {

                erur.setText("Veuillez remplir tous les champs !");
            } else if (!Functions.isValidEmail(email)) {
                erur.setText("Format d'email invalide");
            } else {
                java.awt.Window window = SwingUtilities.getWindowAncestor(MotDePasseOublier.this); 
                if (window instanceof javax.swing.JFrame) {
                    javax.swing.JFrame frame = (javax.swing.JFrame) window;

                    // changer le contenu de la fenêtre par le nouveau panel
                    Etudient_profil panelMdp = new Etudient_profil(); 
                    frame.setContentPane(panelMdp);
                    frame.revalidate();
                    frame.repaint();
                    }
                }
             } );
        


        this.add(vld);




  




    



  
       

	    this.addHierarchyListener(new java.awt.event.HierarchyListener() {
		    @Override
		    public void hierarchyChanged(java.awt.event.HierarchyEvent e) {
		        if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0) {
		            if (isShowing()) {
		                // Now we are 100% sure the RootPane exists
		                JRootPane root = SwingUtilities.getRootPane(vld);
		                if (root != null) {
		                    root.setDefaultButton(vld);
		                }
		            }
		        }
		    }
		}); 
		       
		
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Main.getLastClass() , this);
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
