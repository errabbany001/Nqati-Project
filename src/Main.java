import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Nqati Student Marks");
        frame.setSize(1000, 697);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Acceuille lge = new Acceuille();
        //motDePasseOublier lge = new motDePasseOublier();
        //Etudient_notes lge = new Etudient_notes();
        //LoginEtudient lge = new LoginEtudient();
        //Etudient_notes lge = new Etudient_notes();


 
        


        frame.setContentPane(lge); 

        frame.setVisible(true);

    }
}