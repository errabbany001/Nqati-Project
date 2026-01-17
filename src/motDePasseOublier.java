import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder



;
public final class motDePasseOublier extends JPanel{
    
    private Image backgroundImage;

    private final JLabel line1 , line2 , line3 , line4;

    private JButton creerMenu(String text, int x , int y , Color co){
        // had fonction back sawebt le menu dyal aceuille, contact w propos
        ImageIcon icon_1  = new ImageIcon(new ImageIcon("data/shape_1.png").getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH));
        ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/shape_2.png").getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH));


        
        JButton button_shape_1 = new JButton(text, icon_1);

        button_shape_1.setHorizontalTextPosition(JButton.CENTER); 
        button_shape_1.setVerticalTextPosition(JButton.CENTER);  

        
        button_shape_1.setContentAreaFilled(false); 
        button_shape_1.setBorderPainted(false);     
        button_shape_1.setFocusPainted(false);
        button_shape_1.setForeground(co); 
        button_shape_1.setFont(new Font("Arial", Font.BOLD, 14)); 
        button_shape_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button_shape_1.setBounds(x, y, 120, 40);

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






public static boolean isValidEmail(String email) {
    if (email == null) {
        return false;
    }

   
        String emailRegex = "^[^@]+@[^@]+\\.[^@]+$";

    Pattern pattern = Pattern.compile(emailRegex);
    return pattern.matcher(email).matches();
}

    

    @SuppressWarnings("Convert2Lambda")
    public motDePasseOublier() {

        try {
            backgroundImage = ImageIO.read(new File("data/pg_PasseOublier.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null); 



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




        JButton Valider = new JButton("Valider");
        Valider.setFont(new Font("Arial", Font.BOLD, 16));
        Valider.setForeground(perpul);
        Valider.setBackground(Color.white);
        Valider.setOpaque(true);
        Valider.setBorder(new LineBorder(perpul, 2, true));
        Valider.setBounds(363, 520, 260, 30);
        Valider.setFocusPainted(false);
        Valider.setContentAreaFilled(true);
        Valider.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Valider.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Valider.setBackground(new Color(196, 205, 250));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Valider.setBackground(Color.white);
            }
        });
        
        Valider.addActionListener(new java.awt.event.ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String email = Email.getText();
                String nom = Nom.getText();
                String prenom = Prenom.getText();
                String cne = Cne.getText();
                erur.setText("");

                if(email.equals("Email") || nom.equals("Nom") || prenom.equals("Prenom") || cne.equals("CNE")){

                    erur.setText("Veuillez remplir tous les champs !");
                }else if(!isValidEmail(email)){
                    erur.setText("Format d'email invalide");
                }
             


            }
        });

        this.add(Valider);





this.addHierarchyListener(new java.awt.event.HierarchyListener() {
    @Override
    public void hierarchyChanged(java.awt.event.HierarchyEvent e) {
        if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0) {
            if (isShowing()) {
                // Now we are 100% sure the RootPane exists
                JRootPane root = SwingUtilities.getRootPane(Valider);
                if (root != null) {
                    root.setDefaultButton(Valider);
                }
            }
        }
    }
});
       

  






    

    




        JButton acceuille = creerMenu("Acceuille", 330, 50, perpul);
        JButton contact = creerMenu("Contact", 460, 50, perpul);
        JButton propos = creerMenu("A propos", 590, 50, perpul);

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
