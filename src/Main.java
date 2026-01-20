import javax.swing.JFrame;


public class Main {

    private static Etudient etudient;
    
    public static void main(String[] args) {

        JFrame frame = new JFrame("Nqati Student Marks");
        frame.setSize(1000, 697);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //Accueil lge = new Accueil();
        //motDePasseOublier lge = new motDePasseOublier();
        Etudient_notes lge = new Etudient_notes();
        //LoginEtudient lge = new LoginEtudient();
        //Etudient_notes lge = new Etudient_notes();

        


 
        


        frame.setContentPane(lge); 

        frame.setVisible(true);

    }

    public static Etudient getEtudient() {
        return etudient;
    }

    public static void setEtudient(Etudient etudient) {
        Main.etudient = etudient;
    }
}