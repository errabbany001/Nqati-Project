import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main {

    private static Etudient etudient;
    private static Class<? extends JPanel> lastClass = Accueil.class;
    private  static ArrayList<Class<? extends JPanel>> LisOfCls = new ArrayList<>();
    private static ImageIcon logo  = new ImageIcon(new ImageIcon("data/logo.png").getImage().getScaledInstance(100, 87, Image.SCALE_SMOOTH));
    private static ImageIcon ph_1  = new ImageIcon(new ImageIcon("data/photo_1.png").getImage().getScaledInstance(860, 416, Image.SCALE_SMOOTH));
    private static ImageIcon ph_2  = new ImageIcon(new ImageIcon("data/photo_2.png").getImage().getScaledInstance(400, 320, Image.SCALE_SMOOTH));
    private static ImageIcon ph_3  = new ImageIcon(new ImageIcon("data/photo_3.png").getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH));
    private static ImageIcon ph_4  = new ImageIcon(new ImageIcon("data/photo_4.png").getImage().getScaledInstance(560, 400, Image.SCALE_SMOOTH));
    private static ImageIcon ph_5  = new ImageIcon(new ImageIcon("data/photo_5.png").getImage().getScaledInstance(860, 400, Image.SCALE_SMOOTH));
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Nqati Student Marks");
        frame.setSize(1000, 697);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //Accueil lge = new Accueil();
        //motDePasseOublier lge = new motDePasseOublier();
        //Etudient_notes lge = new Etudient_notes();
        //LoginEtudient lge = new LoginEtudient();
        Etudient_profil lge = new Etudient_profil();

        


 
        


        frame.setContentPane(lge); 

        frame.setVisible(true);

    }

        //======================
            public static void addPageToHistory(Class<? extends JPanel> cls) {
            String name = cls.getSimpleName();
            if (name.equals("Contact") || name.equals("Propos")) {
                return; 
            }
            if (!LisOfCls.isEmpty()) {
                Class<? extends JPanel> lastSaved = LisOfCls.get(LisOfCls.size() - 1);
                if (lastSaved.equals(cls)) {
                    return; 
                }
            }
            LisOfCls.add(cls);
        }


//============================================






    public static Etudient getEtudient() {
        return etudient;
    }

    public static void setEtudient(Etudient etudient) {
        Main.etudient = etudient;
    }

    public static Class<? extends JPanel> getLastClass() {
        return lastClass;
    }

    public static void setLastClass(Class<? extends JPanel> lastClass) {
        Main.lastClass = lastClass;
    }

    public static ArrayList<Class<? extends JPanel>> getLisOfCls() {
        return LisOfCls;
    }

    public static void setLisOfCls(ArrayList<Class<? extends JPanel>> LisOfCls) {
        Main.LisOfCls = LisOfCls;
    }

    public static ImageIcon getLogo() {
        return logo;
    }

    public static void setLogo(ImageIcon logo) {
        Main.logo = logo;
    }

    public static ImageIcon getPh_1() {
        return ph_1;
    }

    public static void setPh_1(ImageIcon ph_1) {
        Main.ph_1 = ph_1;
    }

    public static ImageIcon getPh_2() {
        return ph_2;
    }

    public static void setPh_2(ImageIcon ph_2) {
        Main.ph_2 = ph_2;
    }

    public static ImageIcon getPh_3() {
        return ph_3;
    }

    public static void setPh_3(ImageIcon ph_3) {
        Main.ph_3 = ph_3;
    }

    public static ImageIcon getPh_4() {
        return ph_4;
    }

    public static void setPh_4(ImageIcon ph_4) {
        Main.ph_4 = ph_4;
    }

    public static ImageIcon getPh_5() {
        return ph_5;
    }

    public static void setPh_5(ImageIcon ph_5) {
        Main.ph_5 = ph_5;
    }
    

    
    
}