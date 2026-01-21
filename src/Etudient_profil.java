import java.awt.Color;
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

public final class Etudient_profil extends JPanel {
    private Image backgroundImage;
    
    ImageIcon icon_1  = new ImageIcon(new ImageIcon("data/default_profil.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));

    JLabel creetLabel(int x , int y , String text , Color color , Font font){
		JLabel label = new JLabel(text);
		label.setBounds(x,y,450,50);
		label.setForeground(color);
		label.setFont(font);
		return label;
	}
    
    
    public Etudient_profil() {

        try {
            backgroundImage = ImageIO.read(new File("data/pg_Etudient_profil.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null); 
        

        Color perpul = new Color(87, 107, 194);
        Color bleu = new Color(19, 43, 149);
        Font font1 = new Font("Arial", Font.BOLD, 34);
        Font font2 = new Font("Arial", Font.BOLD, 14);
        //Color ciel = new Color(166, 177, 235);
        
        JLabel profilIcon = new JLabel(icon_1);
        profilIcon.setBounds(300, 170, 150, 150);
        this.add(profilIcon);
      
        
        JLabel nameLabel = creetLabel(495, 215, "Kaddouri Mohammed ", bleu, font1);
        
        String name = "Kaddouri Mohammed";
        String cne = "J165376378";
        String dateNaiss = "01/01/2003";
        
        JLabel nomLabel = creetLabel(180, 350,     "NOM                               :  "+name, bleu, font2);
        JLabel cneLabel = creetLabel(180, 400,     "CNE                                :  "+cne, bleu, font2);
        JLabel datenessLabel = creetLabel(180, 450,"DATE DE NAISSANCE   :  "+dateNaiss, bleu, font2); 

        this.add(nameLabel); 
        this.add(nomLabel);   
        this.add(cneLabel);  
        this.add(datenessLabel);
        
        String niveau = "Master";
        String option = "SIDI";
        String semester = "S1";
        
        JLabel niveauLabel = creetLabel(680, 350, "NIVEAU         :  "+niveau, bleu, font2);
        JLabel optionLabel = creetLabel(680, 400, "OPTION        :  "+option, bleu, font2);
        JLabel semesterLabel = creetLabel(680, 450,"SEMESTER  :  "+semester, bleu, font2); 
        
        this.add(niveauLabel);
        this.add(optionLabel);
        this.add(semesterLabel);
        
        String admitDiplom = "______";
        String moyGeneral = "__,__";
        
        JLabel admitDiplomLabel = creetLabel(480, 515, "ADMIT DIPLOM  :  "+admitDiplom, bleu, font2);
        JLabel moyGeneralLabel = creetLabel(470, 545, "MOYENNE GENERAL  :  "+moyGeneral, bleu, font2);

        this.add(admitDiplomLabel);
        this.add(moyGeneralLabel);
              
        this.add(Functions.LogOutIcon(this));
            
       
        JButton Notification =  Functions.createNavButton(55, 486, "notification", this);
        JButton Settings = Functions.createNavButton(55, 406, "settings", this);
        JButton Notes =  Functions.createNavButton(55, 326,  "notes", this);


        this.add(Notification);
        this.add(Settings);
        this.add(Notes);


       

        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Accueil.class, this);
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