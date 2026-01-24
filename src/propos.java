
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



public final class Propos extends JPanel {

    private Image backgroundImage;

    private ImageIcon logo  = new ImageIcon(new ImageIcon("data/logo.png").getImage().getScaledInstance(100, 87, Image.SCALE_SMOOTH));
    private ImageIcon ph_1  = new ImageIcon(new ImageIcon("data/photo_1.png").getImage().getScaledInstance(860, 416, Image.SCALE_SMOOTH));
    private ImageIcon ph_2  = new ImageIcon(new ImageIcon("data/photo_2.png").getImage().getScaledInstance(400, 320, Image.SCALE_SMOOTH));
    private ImageIcon ph_3  = new ImageIcon(new ImageIcon("data/photo_3.png").getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH));
    private ImageIcon ph_4  = new ImageIcon(new ImageIcon("data/photo_4.png").getImage().getScaledInstance(560, 400, Image.SCALE_SMOOTH));
    private ImageIcon ph_5  = new ImageIcon(new ImageIcon("data/photo_5.png").getImage().getScaledInstance(860, 400, Image.SCALE_SMOOTH));
   
    
    public Propos() {

        try {
            backgroundImage = ImageIO.read(new File("data/pg_propos.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null);
        

        Color perpul = new Color(87, 107, 194);
        Color bleu = new Color(22, 31, 112);
       


        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Main.getLastClass() , this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);
        
        this.add(acceuille);
        this.add(contact);
        this.add(propos);
        //===========================================
     
        //test.setBounds(64,140,874,481);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false);
        contentPanel.setPreferredSize(new Dimension(876,2200));


        JLabel lg = new JLabel(logo);
        lg.setBounds(380,20,100,87);
        contentPanel.add(lg);


        JLabel text1 = new JLabel("À Propos de Nqati");
        text1.setFont(Functions.getMyFont("data/Raleway-Regular.ttf", Font.BOLD , 60f));
        text1.setBounds(0,225,874,100);
        text1.setForeground(Color.white);
        text1.setHorizontalAlignment(JLabel.CENTER);
        text1.setVerticalAlignment(JLabel.CENTER);
        contentPanel.add(text1);


        JLabel text2 = new JLabel("<html><div style='text-align: center;'>" 
            + "Découvrez comment Nqati transforme la gestion académique en une expérience fluide "
            + "et sécurisée pour les établissements d'enseignement supérieur."
            + "</div></html>");

        text2.setFont(Functions.getMyFont("", Font.PLAIN, 20f));
        text2.setBounds(40, 290, 840, 100);
        text2.setForeground(Color.WHITE);
        text2.setHorizontalAlignment(JLabel.CENTER);
        text2.setVerticalAlignment(JLabel.CENTER);
        contentPanel.add(text2);


        JLabel text3 = new JLabel("<html><div>Une plateforme moderne pour la gestion des notes </div></html>");
        text3.setForeground(perpul);
        text3.setFont(Functions.getMyFont("", Font.BOLD , 32f));
        text3.setBounds(440, 710, 400,110);
        contentPanel.add(text3);


        JLabel text4 = new JLabel("<html><body> <div> Nqati simplifie la transition des établissements vers une gestion numérique complète des évaluations académiques. Nous offrons une solution robuste et intuitive qui respecte les standards universitaires."
        +     "<br><br> &nbsp;&nbsp;&nbsp;&nbsp;  -Sécurité des données garantie" 
        +     "<br> &nbsp;&nbsp;&nbsp;&nbsp;  -Interface accessible et ergonomique"
        +     "<br> &nbsp;&nbsp;&nbsp;&nbsp; -Conformité aux normes éducatives"
        +" </div> </body></html>");
        text4.setForeground(Color.BLACK);
        text4.setFont(Functions.getMyFont("data/Raleway-Regular.ttf", Font.BOLD, 14f));
        text4.setBounds(440, 793, 400, 200);
        contentPanel.add(text4);

        JLabel text5 = new JLabel("Notre vision");
        text5.setBounds(440 , 695 , 400, 25);
        text5.setFont(Functions.getMyFont("", Font.ITALIC, 16f));
        text5.setForeground(bleu);
        contentPanel.add(text5);

        



        JLabel photo1 = new JLabel(ph_1);
        photo1.setBounds(4,150,874,416);
        contentPanel.add(photo1);


        JLabel photo2 = new JLabel(ph_2);
        photo2.setBounds(0,668 , 400,320);
        contentPanel.add(photo2);

  




        JLabel roles = new JLabel("Rôles");
        roles.setBounds(0,1062 , 876 , 25 );
        roles.setForeground(new Color(28,175,244));
        roles.setFont(Functions.getMyFont("", Font.BOLD, 22f));
        roles.setHorizontalAlignment(JLabel.CENTER);
        roles.setVerticalAlignment(JLabel.CENTER);
        contentPanel.add(roles);


        JLabel text6 = new JLabel("Deux expériences adaptées");
        text6.setBounds(0,1087 , 876 , 35 );
        text6.setForeground(Color.black);
        text6.setFont(Functions.getMyFont("", Font.LAYOUT_NO_LIMIT_CONTEXT, 30f));
        text6.setHorizontalAlignment(JLabel.CENTER);
        text6.setVerticalAlignment(JLabel.CENTER);
        contentPanel.add(text6);


        JLabel text7 = new JLabel("Chaque utilisateur bénéficie d'une interface pensée pour ses besoins spécifiques");
        text7.setBounds(0,1120 , 876 , 35 );
        text7.setForeground(Color.black);
        text7.setFont(Functions.getMyFont("data/Raleway-Regular.ttf", Font.BOLD, 14f));
        text7.setHorizontalAlignment(JLabel.CENTER);
        text7.setVerticalAlignment(JLabel.CENTER);
        contentPanel.add(text7);





        JLabel etudiants  = new JLabel("Étudiants");
        etudiants .setBounds(30 , 1175 , 280, 25);
        etudiants .setFont(Functions.getMyFont("", Font.ITALIC, 12f));
        etudiants .setForeground(bleu);
        contentPanel.add(etudiants );

        JLabel text8 = new JLabel("<html><div>Suivi transparent de vos résultats</div></html>");
        text8.setForeground(new Color(28,175,244));
        text8.setFont(Functions.getMyFont("", Font.BOLD , 25f));
        text8.setBounds(30, 1190, 240,80);
        contentPanel.add(text8);

        JLabel text9 = new JLabel("<html><div>Consultez vos notes en temps réel et recevez des notifications instantanées des mises à jour académiques</div></html>");
        text9.setForeground(Color.BLACK);
        text9.setFont(Functions.getMyFont("data/Raleway-Regular.ttf", Font.BOLD , 12f));
        text9.setBounds(30, 1250, 240,80);
        contentPanel.add(text9);

        JLabel photo3 = new JLabel(ph_3);
        photo3.setBounds(4, 1160, 300,400);
        contentPanel.add(photo3);





        JLabel ensg  = new JLabel("Enseignants");
        ensg .setBounds(340 , 1270 , 280, 25);
        ensg .setFont(Functions.getMyFont("", Font.ITALIC, 12f));
        ensg .setForeground(bleu);
        contentPanel.add(ensg );

        JLabel text10 = new JLabel("<html><div>Gestion simplifiée des évaluations</div></html>");
        text10.setForeground(new Color(28,175,244));
        text10.setFont(Functions.getMyFont("", Font.BOLD , 25f));
        text10.setBounds(340, 1285, 240,80);
        contentPanel.add(text10);

        JLabel text11 = new JLabel("<html><div>Saisissez les notes de manière sécurisée avec des outils de gestion intuitifs et des rapports détaillés</div></html>");
        text11.setForeground(Color.BLACK);
        text11.setFont(Functions.getMyFont("data/Raleway-Regular.ttf", Font.BOLD , 12f));
        text11.setBounds(340, 1345, 240,80);
        contentPanel.add(text11);

        JLabel photo4 = new JLabel(ph_4);
        photo4.setBounds(310, 1160, 560,400);
        contentPanel.add(photo4);




        JLabel excellence = new JLabel("Excellence");
        excellence.setBounds(0,1630 , 876 , 25 );
        excellence.setForeground(new Color(28,175,244));
        excellence.setFont(Functions.getMyFont("", Font.BOLD, 22f));
        excellence.setHorizontalAlignment(JLabel.CENTER);
        excellence.setVerticalAlignment(JLabel.CENTER);
        contentPanel.add(excellence);


        JLabel text12 = new JLabel("Innovation et expertise technique");
        text12.setBounds(0,1655 , 876 , 35 );
        text12.setForeground(Color.black);
        text12.setFont(Functions.getMyFont("", Font.LAYOUT_NO_LIMIT_CONTEXT, 30f));
        text12.setHorizontalAlignment(JLabel.CENTER);
        text12.setVerticalAlignment(JLabel.CENTER);
        contentPanel.add(text12);


        JLabel text13 = new JLabel("<html><div style='text-align: center;'>" 
            + "Notre équipe de développeurs passionnés construit une plateforme fiable et performante. Chaque ligne de code reflète notre engagement envers la qualité académique et l'innovation technologique."
            + "</div></html>");
        text13.setBounds(90,1689 , 710 , 50 );
        text13.setForeground(Color.black);
        text13.setFont(Functions.getMyFont("data/Raleway-Regular.ttf", Font.BOLD, 14f));
        text13.setHorizontalAlignment(JLabel.CENTER);
        text13.setVerticalAlignment(JLabel.CENTER);
        contentPanel.add(text13);

        JLabel photo5 = new JLabel(ph_5);
        photo5.setBounds(4,1780,874,400);
        contentPanel.add(photo5);













        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBounds(56,140,890,481);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);



        this.add(scrollPane);








   
        

    }

        @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}



