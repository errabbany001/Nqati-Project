import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;
import java.util.Date;

public class Test extends JPanel {

    // --- GLOBAL STYLES ---
    // The main "NATY" Blue/Purple color
    Color mainColor = new Color(87, 107, 194); 
    
    // Fonts
    Font placeholderFont = new Font("Arial", Font.BOLD, 16); // For Label
    Font inputFont = new Font("Arial", Font.PLAIN, 14);      // For Typing

    public Test() {
        this.setLayout(null);
        this.setBackground(new Color(200, 240, 255)); // Light blue background

        // --- 1. ENABLE BACKGROUND FOCUS ---
        // This allows the panel to "steal" focus when you click away from a text box
        this.setFocusable(true);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow(); // Stop typing
            }
        });

        // --- 2. ADD TEXT FIELDS ---
        this.add(creerChamp("Prenom", 100, 50));
        this.add(creerChamp("Nom", 100, 110));
        this.add(creerChamp("Email", 100, 170));
        this.add(creerChamp("CNE", 100, 230));

        // --- 3. ADD DATE SPINNER (The Default Java Date Tool) ---
        this.add(creerDateSpinner(100, 290));
    }

    // =========================================================
    // HELPER 1: CREATE TEXT FIELD
    // =========================================================
    private JTextField creerChamp(String placeholder, int x, int y) {
        JTextField field = new JTextField(placeholder);
        field.setBounds(x, y, 300, 40);

        // Visual Styles
        field.setBorder(null);      // No border
        field.setOpaque(false);     // Transparent
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setForeground(mainColor);
        field.setFont(placeholderFont);

        // Logic (Focus Listener)
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setFont(inputFont);
                    field.setForeground(Color.DARK_GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setFont(placeholderFont);
                    field.setForeground(mainColor);
                }
            }
        });

        return field;
    }

    // =========================================================
    // HELPER 2: CREATE DATE SPINNER
    // =========================================================
private JComponent creerDateSpinner(int x, int y) {
    // 1. Setup the Model
    SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
    JSpinner spinner = new JSpinner(model);
    spinner.setBounds(x, y, 300, 40);

    // 2. Format as dd/MM/yyyy
    JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
    spinner.setEditor(editor);

    // --- 3. REMOVE BACKGROUND (The Tricky Part) ---
    
    // A. Make the main container transparent
    spinner.setOpaque(false);
    spinner.setBorder(null);

    // B. Dig inside to find the text field and make it transparent too
    JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
    tf.setOpaque(false); // Make text field transparent
    tf.setBackground(new Color(0, 0, 0, 0)); // Ensure background color is clear
    
    // C. Style the text to match your design
    tf.setForeground(mainColor);
    tf.setFont(placeholderFont);
    tf.setHorizontalAlignment(JTextField.CENTER);

    return spinner;
}

    // =========================================================
    // MAIN METHOD
    // =========================================================
    public static void main(String[] args) {
        JFrame frame = new JFrame("Inscription NATY");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center on screen
        
        frame.setContentPane(new Test());
        
        frame.setVisible(true);
    }
}