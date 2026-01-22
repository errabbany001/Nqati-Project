import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main {

    private static Etudient etudient;
    private static Class<? extends JPanel> lastClass = Accueil.class;
    private  static ArrayList<Class<? extends JPanel>> LisOfCls = new ArrayList<>();
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Nqati Student Marks");
        frame.setSize(1000, 697);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Accueil lge = new Accueil();
        //motDePasseOublier lge = new motDePasseOublier();
        //Etudient_notes lge = new Etudient_notes();
        //LoginEtudient lge = new LoginEtudient();
        //Etudient_notes lge = new Etudient_notes();

        


 
        


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




        //========================================
        public static void printHistory() {
    System.out.println("---------- History List ----------");
    if (LisOfCls.isEmpty()) {
        System.out.println("History is empty!");
    } else {
        for (int i = 0; i < LisOfCls.size(); i++) {
            // كنجبدو السمية ديال الكلاص باش تبان مقروءة
            String className = LisOfCls.get(i).getSimpleName();
            System.out.println((i + 1) + " : " + className);
        }
    }
    System.out.println("----------------------------------");
}

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

    
    
}