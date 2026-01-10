import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Use invokeLater to ensure GUI updates happen on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Create an instance of your Home class
            Home homeFrame = new Home();
            
            // Make the frame visible
            homeFrame.setVisible(true);
        });
    }
}