
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;



public class Functions {



    public static JButton LogOutIcon(javax.swing.JPanel currentFrame){
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

        LogOut.addActionListener(e -> Functions.logoutWithConfirm(currentFrame));
        return LogOut;
    }


    public static boolean isValidEmail(String email) {
    if (email == null) {
        return false;
    }

   
        String emailRegex = "^[^@]+@[^@]+\\.[^@]+$";

    Pattern pattern = Pattern.compile(emailRegex);
    return pattern.matcher(email).matches();
    }
  
    public static JButton createNavButton(int x, int y, String target, JPanel currentPanel) {
        JButton btn = new JButton();
        btn.setBounds(x, y, 40, 40);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component)e.getSource());
            
            if (frame == null) {
                System.out.println("Error: Frame not found!");
                return;
            }

            JPanel nextPanel = null;
            System.out.println("Button clicked! Target: " + target); 

           
            switch (target.toLowerCase()) {
                case "notes": nextPanel = new Etudient_notes(); break;
                case "profil": nextPanel = new Etudient_profil(); break;
                case "settings": nextPanel = new Etudient_settings(); break;
                case "notification": nextPanel = new Etudient_notification();   break;
            }

            if (nextPanel != null) {
                frame.setContentPane(nextPanel);
                frame.revalidate(); 
                frame.repaint();   
            } else {
                System.out.println("Error: nextPanel is null. Check class name!");
            }
        });

        return btn;
    }


    public static JButton creerMenu(String text, int x , int y , Color co , Class<? extends JPanel> panelClass, JPanel parent){
        // had fonction back sawebt le menu dyal aceuille, contact w propos
    	ImageIcon icon_1  = new ImageIcon(new ImageIcon("data/shape_1.png").getImage().getScaledInstance(120, 20, Image.SCALE_SMOOTH));
        ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/shape_2.png").getImage().getScaledInstance(120, 20, Image.SCALE_SMOOTH));

        JButton button_shape_1 = new JButton(text, icon_1);

        button_shape_1.setHorizontalTextPosition(JButton.CENTER); 
        button_shape_1.setVerticalTextPosition(JButton.CENTER);  

        
        button_shape_1.setContentAreaFilled(false); 
        button_shape_1.setBorderPainted(false);     
        button_shape_1.setFocusPainted(false);
        button_shape_1.setForeground(co); 
        button_shape_1.setFont(new Font("Arial", Font.BOLD, 14)); 
        button_shape_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button_shape_1.setBounds(x, y, 120, 20);

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
            @Override      
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (panelClass != null) {
                    java.awt.Window window = SwingUtilities.getWindowAncestor(parent);
                    
                    if (window instanceof javax.swing.JFrame) {
                        javax.swing.JFrame frame = (javax.swing.JFrame) window;
                        
                        try {
                            JPanel target = panelClass.getDeclaredConstructor().newInstance();
                            
                            frame.setContentPane(target);
                            frame.revalidate();
                            frame.repaint();
                            Transition.showWithFade(frame, target);
                            
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
}
        });

        return button_shape_1;
    }
    


public static void logoutWithConfirm(JPanel currentPanel) {
    Color ciel = new Color(193, 221, 240);
    
    
    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBackground(ciel);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(2, 20, 20, 20)); 

    JLabel label = new JLabel("Êtes-vous sûr de vouloir vous déconnecter ?");
    label.setFont(new Font("Arial", Font.BOLD, 14));
    label.setHorizontalAlignment(SwingConstants.CENTER);
    
    mainPanel.add(label, BorderLayout.CENTER);

    UIManager.put("OptionPane.background", ciel);
    UIManager.put("Panel.background", ciel);

    int answer = JOptionPane.showConfirmDialog(
        currentPanel, 
        mainPanel, 
        "Confirmation", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.PLAIN_MESSAGE
    );

    if (answer == JOptionPane.YES_OPTION) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(currentPanel);
        if (frame != null) {
            frame.setContentPane(new Accueil());
            frame.revalidate();
            frame.repaint();
        }
    }
}
}