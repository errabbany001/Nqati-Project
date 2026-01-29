import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.io.File;
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

    public static  JLabel createCustomLabelWithBorder(String text, int x, int y, int width, int height, 
                                           int topLeft, int topRight, int bottomRight, int bottomLeft, 
                                           Color color) {
    
    JLabel label = new JLabel(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Lissage des bords pour l'arrondi et la ligne
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Pour éviter que la bordure de 2px soit coupée, on réduit légèrement la zone de dessin
            // 1px de marge (moitié de l'épaisseur de 2px)
            int borderThickness = 1;
            int inset = 1; 
            int w = getWidth() - (inset * 2);
            int h = getHeight() - (inset * 2);

            // On déplace le crayon pour commencer à (1,1) au lieu de (0,0)
            g2.translate(inset, inset);

            // --- Création de la forme (Path) ---
            Path2D.Float path = new Path2D.Float();

            // 1. Coin Haut-Gauche
            path.moveTo(0, topLeft);
            if (topLeft > 0) path.quadTo(0, 0, topLeft, 0);
            else path.lineTo(0, 0);

            // 2. Coin Haut-Droit
            path.lineTo(w - topRight, 0);
            if (topRight > 0) path.quadTo(w, 0, w, topRight);
            else path.lineTo(w, 0);

            // 3. Coin Bas-Droit
            path.lineTo(w, h - bottomRight);
            if (bottomRight > 0) path.quadTo(w, h, w - bottomRight, h);
            else path.lineTo(w, h);

            // 4. Coin Bas-Gauche
            path.lineTo(bottomLeft, h);
            if (bottomLeft > 0) path.quadTo(0, h, 0, h - bottomLeft);
            else path.lineTo(0, h);

            path.closePath();

            // --- Remplissage (Fond) ---
            g2.setColor(getBackground());
            g2.fill(path);

            // --- Bordure (Contour Noir) ---
            g2.setColor(Color.BLACK); // Couleur de la bordure
            g2.setStroke(new BasicStroke(borderThickness)); // Épaisseur 2px
            g2.draw(path);

            g2.dispose();
            
            // On remet la translation à 0 pour que le texte soit bien centré par rapport au composant global
            super.paintComponent(g); 
        }
            };

        // Configuration
        label.setOpaque(false);
        label.setBackground(color);
        label.setForeground(Color.WHITE); // Couleur du texte
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));

    return label;
    }
    //==========================================================
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

    //==========================================================
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[^@]+@[^@]+\\.[^@]+$";

        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    //==========================================================
  
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

           
            switch (target.toLowerCase()) {
                case "notes_etd": nextPanel = new Etudient_notes(); break;
                case "notes_ens": nextPanel = new Enseignement_notes(); break;

                case "profil_etd": nextPanel = new Etudient_profil(); break;
                case "profil_ens": nextPanel = new Enseignement_profil(); break;

                case "settings_etd": nextPanel = new Etudient_settings(); break;
                case "settings_ens": nextPanel = new Enseignement_settings(); break;

                case "notification_etd": nextPanel = new Etudient_notification(); break;
                case "notification_ens": nextPanel = new Enseignement_notification(); break;
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

     //==========================================================

    public static JButton creerMenu(String text, int x , int y , Color co , Class<? extends JPanel> panelClass, JPanel parent){
        // had fonction back sawebt le menu dyal aceuille, contact w propos
    	ImageIcon icon_1  = new ImageIcon(new ImageIcon("data/shape_1.png").getImage().getScaledInstance(120, 20, Image.SCALE_SMOOTH));
        ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/shape_2.png").getImage().getScaledInstance(120, 20, Image.SCALE_SMOOTH));
        ImageIcon icon_3 = new ImageIcon(new ImageIcon("data/shape_3.png").getImage().getScaledInstance(120, 20, Image.SCALE_SMOOTH));

        boolean verf = (parent.getClass() != panelClass) ;
        Color col = (verf) ? co : Color.white;
        ImageIcon icn = (verf) ? icon_1 : icon_3;


        JButton button_shape_1 = new JButton(text, icn);

        button_shape_1.setHorizontalTextPosition(JButton.CENTER); 
        button_shape_1.setVerticalTextPosition(JButton.CENTER);  

        
        button_shape_1.setContentAreaFilled(false); 
        button_shape_1.setBorderPainted(false);     
        button_shape_1.setFocusPainted(false);
        button_shape_1.setForeground(col); 
        button_shape_1.setFont(new Font("Arial", Font.BOLD, 14)); 
        if (verf){
            button_shape_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        button_shape_1.setBounds(x, y, 120, 20);

        button_shape_1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e){
                if(verf)
                {button_shape_1.setIcon(icon_2);
                button_shape_1.setForeground(Color.white); }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e){
                if (verf)
                {button_shape_1.setIcon(icon_1);
                button_shape_1.setForeground(co);}
            }    
            @Override      
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (panelClass != null && verf) {
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
    
    //==========================================================

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
                Main.setLogedIn(0);
                Main.getLisOfCls().clear();
                frame.setContentPane(new Accueil());
                frame.revalidate();
                frame.repaint();
            }
        }
    }
    //================================================================
    public static JButton cretBackBtn(){
        ImageIcon icon_1 = new ImageIcon(new ImageIcon("data/icon_back_2.png").getImage().getScaledInstance(26, 20, Image.SCALE_SMOOTH));
        ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/icon_back_1.png").getImage().getScaledInstance(26, 20, Image.SCALE_SMOOTH));

        
        JButton btn = new JButton(icon_1);
        btn.setBounds(30,60,26,20);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
               
                btn.setIcon(icon_2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
               
                btn.setIcon(icon_1);
            }

            @Override

            public void mouseClicked(MouseEvent e) {
                if (!Main.getLisOfCls().isEmpty()) {
                    
                    java.awt.Window window = SwingUtilities.getWindowAncestor(btn);
                    
                    if (window instanceof javax.swing.JFrame) {
                        javax.swing.JFrame frame = (javax.swing.JFrame) window;

                        try {
                            Class<? extends JPanel> prevClass = Main.getLisOfCls().remove(Main.getLisOfCls().size() - 1);

                            JPanel panelBack = prevClass.getDeclaredConstructor().newInstance();

                            frame.setContentPane(panelBack);
                            frame.revalidate();
                            frame.repaint();
                            
                            

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            
        });
              

          
        return btn;
    }

    //====================================================================
    public static Font getMyFont(String path, int style, float size) {
        try {
            if (path == null || path.trim().isEmpty()) {
                return new Font("Arial", style, (int)size);
            }
            
            File fontFile = new File(path);
            if (!fontFile.exists()) {
                return new Font("Arial", style, (int)size);
            }
            
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            return customFont.deriveFont(style, size);
            
        } catch (Exception e) {
            return new Font("Arial", style, (int)size);
        }
    }
    
    //=========================================================================
    static JLabel creetLabel(int x , int y , String text ){
        JLabel myText = new JLabel(text);
        myText.setBounds(x,y,200,20);
        myText.setFont(Functions.getMyFont("", Font.BOLD, 14f));
        myText.setForeground(new Color(22, 31, 112));
        return myText;
	}

    static JLabel EtudInfo(int x , int y , String text ){
        JLabel myText = new JLabel(text);
        myText.setBounds(x,y,200,20);
        myText.setFont(Functions.getMyFont("Raleway-Regular.fft", Font.BOLD, 17f));
        myText.setForeground(new Color(34, 161, 241));
        return myText;
	}
}