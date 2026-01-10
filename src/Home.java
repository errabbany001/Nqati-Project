import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Home extends JFrame {

    private Image backgroundImage;

    public Home() {
        try {
            // Load the image from the 'data' folder
            backgroundImage = ImageIO.read(new File("data/pg_home.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png from data folder.");
        }

        setTitle("Nqati Student Marks");
        setSize(1000, 697);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a custom panel to handle the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Use absolute positioning (null layout) on the PANEL, not the Frame
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);
        Color perpul = new Color(87, 107, 194);
        // Add the label
        JLabel seconnecter = new JLabel("Se connecter");
        seconnecter.setForeground(perpul);
        seconnecter.setFont(new Font("Arial", Font.BOLD, 18)); // Slightly larger font
        seconnecter.setBounds(640, 220, 230, 30); // Centered a bit better for 1000px width
        backgroundPanel.add(seconnecter);


        JLabel text1 = new JLabel("<html><body style=' text-align: center;'> Cette plateforme est un système de gestion des notes conçu pour faciliter le suivi académique.</body></html>");
        text1.setForeground(perpul);
        text1.setFont(new Font("Arial" , Font.BOLD, 14));
        text1.setBounds(90 , 320 , 350, 50);
        backgroundPanel.add(text1);

        JLabel text2 = new JLabel("<html><body style=' text-align: center;'> Les étudiants peuvent consulter leurs notes par module et par semestre en toute transparence, tandis que les enseignants disposent d’un espace dédié pour saisir, modifier et valider les notes.</body></html>");
        text2.setForeground(perpul);
        text2.setFont(new Font("Arial" , Font.BOLD , 14));
        text2.setBounds(90,370,350,100);
        backgroundPanel.add(text2);

        JLabel text3 = new JLabel("<html><body style='text-align: center;'>Le système garantit la sécurité des données, la fiabilité des calculs et une consultation rapide des résultats.</body></html>");
        text3.setForeground(perpul);
        text3.setFont(new Font("Arial", Font.BOLD , 14));
        text3.setBounds(90,470 , 350 , 70);
        backgroundPanel.add(text3);



        JLabel cader1 = new JLabel();
        cader1.setBackground(perpul);
        cader1.setOpaque(true);
        cader1.setBounds(580,290,10,50);
        backgroundPanel.add(cader1);


        JLabel cader2 = new JLabel();
        cader2.setBackground(perpul);
        cader2.setOpaque(true);
        cader2.setBounds(580,380,10,50);
        backgroundPanel.add(cader2);


        JLabel etudiant = new JLabel("etudiant");
        etudiant.setFont(new Font("Arial" , Font.BOLD , 18));
        etudiant.setForeground(perpul);
        etudiant.setBackground(Color.white);
        etudiant.setOpaque(true);
        etudiant.setBorder(new LineBorder(perpul, 2, true));
        etudiant.setBounds(580, 290, 250, 50);
        etudiant.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(etudiant);


        JLabel enseignement = new JLabel("enseignement");
        enseignement.setFont(new Font("Arial" , Font.BOLD , 18));
        enseignement.setForeground(perpul);
        enseignement.setBackground(Color.white);
        enseignement.setOpaque(true);
        enseignement.setBorder(new LineBorder(perpul, 2, true));
        enseignement.setBounds(580, 380, 250, 50);
        enseignement.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(enseignement);
    
    
    }
}