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
    
    static ImageIcon icon_1  = new ImageIcon(new ImageIcon("data/default_profil.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
    static ImageIcon icon_2  = new ImageIcon(new ImageIcon("data/default_profil.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    static ImageIcon vet_adm  = new ImageIcon(new ImageIcon("data/vector_admis.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    static ImageIcon vect_ddn  = new ImageIcon(new ImageIcon("data/vector_dateDeNaissance.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    static ImageIcon vect_niv  = new ImageIcon(new ImageIcon("data/vector_niveau.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    static ImageIcon vect_opt  = new ImageIcon(new ImageIcon("data/vector_option.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    static ImageIcon vect_sem  = new ImageIcon(new ImageIcon("data/vector_semester.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    static ImageIcon vect_not  = new ImageIcon(new ImageIcon("data/vector_note.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
    static String fullName , Cne , ddn , option , niveau , semester , note , admis;


    static JLabel creetLabel(int x , int y , String text ){
        JLabel myText = new JLabel(text);
        myText.setBounds(x,y,200,20);
        myText.setFont(Functions.getMyFont("", Font.BOLD, 14f));
        myText.setForeground(new Color(22, 31, 112));
        return myText;
	}

    JLabel EtudInfo(int x , int y , String text ){
        JLabel myText = new JLabel(text);
        myText.setBounds(x,y,200,20);
        myText.setFont(Functions.getMyFont("Raleway-Regular.fft", Font.BOLD, 17f));
        myText.setForeground(new Color(34, 161, 241));
        return myText;
	}

    JLabel creetVector(int x , int y , ImageIcon vect){
        JLabel vector = new JLabel(vect);
        vector.setBounds(x, y , 25 , 25);
        return vector;
    }
    
    
    public Etudient_profil() {
        Main.setLastClass(this.getClass());

        try {
            backgroundImage = ImageIO.read(new File("data/pg_Etudient_profil.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null); 

        fullName = "Yassine Er-rabbany";
        Cne = "R130000121";
        ddn = "01 / 01 / 2001";
        option = "SIDI";
        niveau = "MASTER";
        admis = "OUI";
        note = "00.00";
        semester = "S1";
        

        Color perpul = new Color(87, 107, 194);
        //Color ciel = new Color(166, 177, 235);
        
        JLabel profilIcon = new JLabel(icon_1);
        profilIcon.setBounds(250, 215, 150, 150);
        this.add(profilIcon);

        JLabel profilIconMini = new JLabel(icon_2);
        profilIconMini.setBounds(920,48 , 40, 40);
        this.add(profilIconMini);

        JLabel myname = creetLabel(710, 60, fullName);
        myname.setHorizontalAlignment(JLabel.RIGHT);
        this.add(myname);
      
        JLabel FullN =  creetLabel(230, 370, fullName);
        FullN.setHorizontalAlignment(JLabel.CENTER);
        this.add(FullN);

        JLabel CneLab =  EtudInfo(230, 390, Cne);
        CneLab.setHorizontalAlignment(JLabel.CENTER);
        this.add(CneLab);

        this.add(creetLabel(515, 245, "DATE DE NAISSANCE"));
        this.add(EtudInfo(515, 280, ddn));

        this.add(creetLabel(515, 345, "OPTION"));
        this.add(EtudInfo(515, 380, option));

        this.add(creetLabel(720, 245, "NIVEAU"));
        this.add(EtudInfo(720, 280, niveau));


        this.add(creetLabel(720, 345, "SEMESTER"));
        this.add(EtudInfo(720, 380, semester));


        this.add(creetVector(480, 240, vect_ddn));
        this.add(creetVector(480, 340, vect_opt));
        this.add(creetVector(685, 244, vect_niv));
        this.add(creetVector(685, 340, vect_sem));
    
        
        this.add(creetVector(300, 515, vet_adm));
        this.add(creetLabel(335, 520, "ADMIS DIPLOM : "));
        this.add(EtudInfo(460, 521, admis));

        this.add(creetVector(600, 515, vect_not));
        this.add(creetLabel(635, 520, "NOTE : "));
        this.add(EtudInfo(700, 521, note));
        
    

              
        this.add(Functions.LogOutIcon(this));
            
       
        JButton Notification =  Functions.createNavButton(55, 486, "notification", this);
        JButton Settings = Functions.createNavButton(55, 406, "settings", this);
        JButton Notes =  Functions.createNavButton(55, 326,  "notes", this);


        this.add(Notification);
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