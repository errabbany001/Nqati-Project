import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public final class Etudient_notification extends JPanel {

    private Image backgroundImage;
    private ImageIcon cader0 = new ImageIcon(new ImageIcon("data/netification_cader0.png").getImage().getScaledInstance(784, 97, Image.SCALE_SMOOTH));
    private ImageIcon cader1 = new ImageIcon(new ImageIcon("data/netification_cader.png").getImage().getScaledInstance(784, 97, Image.SCALE_SMOOTH));
    private ImageIcon cader2 = new ImageIcon(new ImageIcon("data/netification_cader2.png").getImage().getScaledInstance(784, 447, Image.SCALE_SMOOTH));
    private ImageIcon net_new = new ImageIcon(new ImageIcon("data/netification_new.png").getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH));
    private ImageIcon delt = new ImageIcon(new ImageIcon("data/delete.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    
    private static ArrayList<String[]> notificationList = new ArrayList<>();
    private ArrayList<JLabel> readCheck = new ArrayList<>();
    private ArrayList<JLabel> caders = new ArrayList<>();
    private JLabel netifica_text , cdr;
    private JScrollPane sp;
    private JButton backIcon;
    private int  numNet ;
    



    private JLabel addCader(ImageIcon cad , int x , int y){
        JLabel mycad =  new JLabel(cad);
        mycad.setBounds(x, y , 784 , 97);
        caders.add(mycad);
        return mycad;
    }

    private JLabel addTitre(String titre , int x , int y , int l , int h){
        JLabel myText = new JLabel(titre);
        myText.setFont(Functions.getMyFont("Raleway-Regular.ttf", Font.BOLD, 17f));
        myText.setForeground(new Color(22, 31, 112));
        myText.setBounds(x,y,l,h);
        myText.setVerticalAlignment(JLabel.CENTER);
        return myText;
    }

    private JLabel addNewNet(int x , int y , int i){
        JLabel icon = new JLabel(net_new);
        icon.setBounds(x , y , 12 , 12);
        if(!notificationList.get(i)[2].equals("new")){
            icon.setVisible(false);
        }
        readCheck.add(icon);
        return icon;
    }

    private void rebuildNotifications(JPanel conPan) {
        conPan.removeAll();
        caders.clear();
        readCheck.clear();

        for (int i = 0; i < notificationList.size(); i++) {
            conPan.add(addDelet(710, 47 + i * 90 , i , conPan) , 0);
            conPan.add(addButton(7, 32 + i * 90  , i));
            JLabel check = (addNewNet(10, 38 + i * 90 , i));
            if(!notificationList.get(i)[2].equals("new")){
                check.setVisible(false);
            }
            conPan.add(check);

            conPan.add(addTitre(notificationList.get(i)[0], 50 , 35 + i * 90  , 715 , 50));
            conPan.add(addCader(cader1, 0, 20 + i * 90));
        }
        conPan.setPreferredSize(new Dimension(784, notificationList.size() * 92));
    }


    private JButton addDelet(int x , int y , int index , JPanel pan){
        JButton btn = new JButton(delt);
        btn.setBounds(x , y, 25, 25);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
            // 1) remove notification
            if (index >= 0 && index < notificationList.size()) {
                notificationList.remove(index);
            }
            rebuildNotifications(pan);

            pan.revalidate();
            pan.repaint();
        });
        return btn;
    }


    private JButton addButton(int x , int y , int index){
        JButton btn = new JButton();
        btn.setBounds(x , y, 748, 60);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (index >= 0 && index < notificationList.size()) {
                    notificationList.get(index)[2] = "readed";
                    if (index >= 0 && index < readCheck.size()) {
                        readCheck.get(index).setVisible(false);
                    }
                    sp.setVisible(false);
                    cdr.setVisible(true);
                    netifica_text.setVisible(true);
                    netifica_text.setText("<html><div>" + notificationList.get(index)[1] + "</div></html>");

                    backIcon.setVisible(true);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (index >= 0 && index < caders.size()) {
                    caders.get(index).setIcon(cader0);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (index >= 0 && index < caders.size()) {
                    caders.get(index).setIcon(cader1);
                }
            }
            
        });

        return btn;
    }



    public Etudient_notification() {
        Main.setLastClass(this.getClass());

        try {
            backgroundImage = ImageIO.read(new File("data/pg_Etudient_notification.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_Etudient_notification.png");
        }

        this.setLayout(null); 


        if(notificationList.isEmpty())
        { 
            notificationList.add(new String[]{
                "Emploi du temps", 
                "Le planning détaillé des examens de la session d'automne a été publié. Veuillez vérifier vos horaires sur le portail étudiant. En cas de chevauchement d'horaires ou d'erreur de groupe, vous devez contacter l'administration avant le vendredi 30 janvier à 16h00.", 
                "new"
            });

            notificationList.add(new String[]{
                "Bibliothèque", 
                "Rappel : Le livre 'Java Programming for Beginners' que vous avez emprunté le 12 janvier arrive à échéance demain. Veuillez le rendre ou prolonger votre emprunt via l'application pour éviter les pénalités de retard de 5 DH par jour.", 
                "new"
            });

            notificationList.add(new String[]{
                "Administration", 
                "Votre demande de relevé de notes certifié a bien été traitée par le service de scolarité. Le document est disponible au guichet 3 (Bloc B). N'oubliez pas de vous munir de votre carte d'étudiant ou d'une pièce d'identité pour le retrait.", 
                "new"
            });

            notificationList.add(new String[]{
                "Absence Professeur", 
                "Nous vous informons que le cours de M. Alami (Analyse Mathématique II) prévu cet après-midi à 14h00 en Amphi A est annulé pour des raisons médicales. Une séance de rattrapage sera programmée ultérieurement, la date vous sera communiquée par email.", 
                "new"
            });

            notificationList.add(new String[]{
                "Bourse Universitaire", 
                "Le virement de la bourse 'Minhaty' concernant le premier trimestre a été effectué sur votre compte bancaire. Si vous n'avez pas reçu le montant sous 48h, veuillez vérifier votre RIB auprès du service économique ou contacter votre agence bancaire.", 
                "new"
            });

            notificationList.add(new String[]{
                "Club Informatique", 
                "Grande réunion de lancement pour le projet 'Nqati' ! Nous cherchons des développeurs Java Swing, des designers UI/UX et des testeurs. Rejoignez-nous ce mercredi à 15h00 au Bloc C, salle 12. Des pizzas et des boissons seront offertes à tous les participants.", 
                "new"
            });

            notificationList.add(new String[]{
                "Maintenance Serveur", 
                "Attention : Le site web de l'université et la plateforme e-learning seront inaccessibles ce soir entre 22h00 et 02h00 du matin pour une mise à jour de sécurité majeure. Veuillez sauvegarder votre travail et ne pas commencer de quiz en ligne durant cette période.", 
                "new"
            });

            notificationList.add(new String[]{
                "Note Disponible", 
                "La note finale du module 'Structures de Données et Algorithmes' est désormais affichée sur votre espace personnel. Vous disposez de 48 heures pour déposer une réclamation via le formulaire en ligne si vous constatez une erreur de comptage.", 
                "new"
            });
        }
        numNet = notificationList.size();

        Color perpul = new Color(87, 107, 194);


        netifica_text = addTitre("", 185, 190, 730, 360);
        netifica_text.setVisible(false);
        netifica_text.setVerticalAlignment(JLabel.TOP);
        this.add(netifica_text);

        cdr = new JLabel(cader2);
        cdr.setBounds(170 , 170 , 784 , 447);
        cdr.setVisible(false);
        this.add(cdr);


        ImageIcon icon_1 = new ImageIcon(new ImageIcon("data/icon_back_2.png").getImage().getScaledInstance(26, 20, Image.SCALE_SMOOTH));
        ImageIcon icon_2 = new ImageIcon(new ImageIcon("data/icon_back_1.png").getImage().getScaledInstance(26, 20, Image.SCALE_SMOOTH));

        
        backIcon = new JButton(icon_1);
        backIcon.setBounds(135,145,26,20);
        backIcon.setOpaque(false);
        backIcon.setContentAreaFilled(false);
        backIcon.setBorderPainted(false);
        backIcon.setFocusPainted(false);
        backIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
               
                backIcon.setIcon(icon_2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
               
                backIcon.setIcon(icon_1);
            }

            @Override

            public void mouseClicked(MouseEvent e) {
                sp.setVisible(true);
                cdr.setVisible(false);
                netifica_text.setVisible(false);
                backIcon.setVisible(false);
            }
            
        });
        backIcon.setVisible(false);
        this.add(backIcon);
        
         

        JPanel conPan = new JPanel();
        conPan.setLayout(null);
        conPan.setOpaque(false);
        for (int i = 0 ; i < numNet ; i++){
            conPan.add(addDelet(710, 47 + i * 90 , i , conPan) , 0);
            conPan.add(addButton(7, 32 + i * 90  , i));
            JLabel check = (addNewNet(10, 38 + i * 90 , i));
            if(!notificationList.get(i)[2].equals("new")){
                check.setVisible(false);
            }
            conPan.add(check);

            conPan.add(addTitre(notificationList.get(i)[0], 50 , 35 + i * 90  , 715 , 50));
            conPan.add(addCader(cader1, 0, 20 + i * 90));
        }
        conPan.setPreferredSize(new Dimension(784 , numNet* 92));



        sp = new JScrollPane(conPan);
        sp.setBorder(null);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setBounds(145, 138, 800, 481);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.getVerticalScrollBar().setOpaque(false);
        sp.getVerticalScrollBar().setUI(new ScrollBarUI());
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.setVisible(true);
        this.add(sp);

              
        this.add(Functions.LogOutIcon(this));
            
       

        JButton Profil =  Functions.createNavButton(55, 246, "profil", this);
        JButton Settings = Functions.createNavButton(55, 406, "settings", this);
        JButton Notes =  Functions.createNavButton(55, 326,  "notes", this);


        this.add(Profil);
        this.add(Settings);
        this.add(Notes);
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