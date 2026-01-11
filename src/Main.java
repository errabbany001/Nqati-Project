import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Nqati Student Marks");
        frame.setSize(1000, 697);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        
        Acceuille homePanel = new Acceuille();
        frame.setContentPane(homePanel); 

        frame.setVisible(true);

    }
}