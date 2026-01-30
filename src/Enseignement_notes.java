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
import java.util.Comparator;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class Enseignement_notes extends JPanel {
    
    // Déclaration des variables globales : images, listes de données et composants d'état
    private Image backgroundImage;
    private ArrayList<String[]> cours = new ArrayList<>();
    private ArrayList<JLabel> choisses = new ArrayList<>();
    private JLabel cdr_1, cdr_2, info;
    private int switcher = 0;
    private ArrayList<String[]> students = new ArrayList<>();

    // Méthode utilitaire pour trouver l'index d'un cours à partir de son code unique
    public int findIndex(String code) {
        int index = -1;
        for (int i = 0; i < cours.size(); i++) {
            if (cours.get(i)[0].equals(code)) {
                index = i;
            }
        }
        return index;
    }

    public Enseignement_notes() {
        
        // Configuration initiale : chargement de l'image de fond et mise en mémoire de la page précédente
        Main.setLastClass(this.getClass());
        try {
            backgroundImage = ImageIO.read(new File("data/pg_Enseignement_notes.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load data/pg_Etudient_notes.png");
        }
        Color perpul = new Color(87, 107, 194);

        // Initialisation des données de test pour les cours disponibles
        if (cours.isEmpty()) {
            cours.add(new String[]{"BI15", "Sécurité des Systèmes Informatiques", "Master", "SIDI", "S1"});
            cours.add(new String[]{"GL054", "Théorie des Graphes et Optimisation", "Licence", "Génie Logiciel", "S2"});
            cours.add(new String[]{"IDS01", "Logique et Intelligence Artificielle", "Master", "IA & Data Science", "S1"});
            cours.add(new String[]{"FS11", "Développement Web Sémantique", "Licence", "Fullstack", "S2"});
            cours.add(new String[]{"SI09", "Administration des Bases de Données", "Licence", "Systèmes d'Information", "S1"});
        }

        // Initialisation et tri alphabétique de la liste des étudiants par nom
        if (students.isEmpty()) {
            students.add(new String[]{"Alami", "Yassine", "G135042189"});
            students.add(new String[]{"Bennani", "Salma", "R120934556"});
            students.add(new String[]{"Chraibi", "Omar", "S134098221"});
            students.add(new String[]{"Idrissi", "Laila", "K110763442"});
            students.add(new String[]{"Mansouri", "Hamza", "M145022339"});
            students.add(new String[]{"Tazi", "Kenza", "P120055441"});
            students.add(new String[]{"Zahiri", "Anas", "D139088772"});
            students.add(new String[]{"Fassi", "Meriem", "B110334455"});
            students.add(new String[]{"Jouhari", "Amine", "H142099881"});
            students.add(new String[]{"Radi", "Sanaa", "F130077662"});
            students.add(new String[]{"Belkhayat", "Walid", "J122044335"});
            students.add(new String[]{"Tahiri", "Nouzha", "E110022998"});
        }
        students.sort(Comparator.comparing(s -> s[0]));

        // Affichage des informations de profil de l'enseignant connecté
        JLabel profilIconMini = new JLabel(Etudient_profil.icon_2);
        profilIconMini.setBounds(920, 48, 40, 40);
        this.add(profilIconMini);

        JLabel myname = Functions.creetLabel(710, 60, Main.getUserName());
        myname.setHorizontalAlignment(JLabel.RIGHT);
        this.add(myname);
        this.setLayout(null);

        Color col = new Color(77, 149, 247);
        Color grey = new Color(122, 145, 176);

        // Configuration du bouton de sélection de cours avec gestion du menu déroulant
        JButton CourChoisse = new JButton("Cour");
        CourChoisse.setBounds(180, 170, 150, 25);
        CourChoisse.setHorizontalAlignment(JButton.CENTER);
        CourChoisse.setForeground(Color.white);
        CourChoisse.setOpaque(false);
        CourChoisse.setContentAreaFilled(false);
        CourChoisse.setBorderPainted(false);
        CourChoisse.setFocusPainted(false);
        CourChoisse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        CourChoisse.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (switcher == 0) {
                    cdr_1.setVisible(false);
                    cdr_2.setVisible(true);
                    switcher = 1;
                    for (JLabel choi : choisses) choi.setVisible(true);
                } else {
                    cdr_1.setVisible(true);
                    cdr_2.setVisible(false);
                    switcher = 0;
                    for (JLabel choi : choisses) choi.setVisible(false);
                }
            }
        });
        this.add(CourChoisse);

        // Gestion de l'apparence visuelle (bordures) du sélecteur de cours
        cdr_1 = Functions.createCustomLabelWithBorder("", 180, 170, 150, 25, 7, 7, 7, 7, perpul);
        this.add(cdr_1);
        cdr_2 = Functions.createCustomLabelWithBorder("", 180, 170, 150, 25, 7, 7, 0, 0, perpul);
        cdr_2.setVisible(false);
        this.add(cdr_2);

        // Génération dynamique de la liste déroulante des cours
        for (int i = 0; i < cours.size(); i++) {
            final int index = i;
            JLabel lib;
            if (i == cours.size() - 1) {
                lib = Functions.createCustomLabelWithBorder(cours.get(i)[0], 180, 193 + 23 * i, 150, 25, 0, 0, 7, 7, col);
            } else {
                lib = Functions.createCustomLabelWithBorder(cours.get(i)[0], 180, 193 + 23 * i, 150, 25, 0, 0, 0, 0, col);
            }

            lib.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { lib.setBackground(grey); }
                @Override
                public void mouseExited(MouseEvent e) { lib.setBackground(col); }
                @Override
                public void mouseClicked(MouseEvent e) {
                    CourChoisse.setText(cours.get(index)[0]);
                    cdr_1.setVisible(true);
                    cdr_2.setVisible(false);
                    switcher = 0;
                    for (JLabel choi : choisses) choi.setVisible(false);
                    int idx = findIndex(lib.getText());
                    info.setText(lib.getText() + " : " + cours.get(idx)[1] + "         " + cours.get(idx)[2] + " : " + cours.get(idx)[3] + "-" + cours.get(idx)[4]);
                }
            });
            lib.setVisible(false);
            choisses.add(lib);
            this.add(lib);
        }

        // Zone d'affichage des détails du cours sélectionné (Nom, Niveau, Semestre)
        info = new JLabel("");
        info.setBounds(360, 170, 600, 25);
        info.setForeground(new Color(27, 39, 206));
        info.setFont(new Font("Ariel", Font.BOLD, 12));
        this.add(info);

        // Création du conteneur pour la liste des étudiants
        JPanel conPan = new JPanel();
        conPan.setPreferredSize(new Dimension(784, students.size() * 30 + 140));
        conPan.setOpaque(false);
        conPan.setLayout(null);

        // Ajout des en-têtes de colonnes (Nom Complet, CNE, Note)
        conPan.add(Functions.createCustomLabelWithBorder("Nom Complet", 130, 15, 200, 25, 5, 5, 5, 5, col));
        conPan.add(Functions.createCustomLabelWithBorder("CNE", 360, 15, 150, 25, 5, 5, 5, 5, col));
        conPan.add(Functions.createCustomLabelWithBorder("La Note", 540, 15, 80, 25, 5, 5, 5, 5, col));

        // Génération de la liste des étudiants avec champs de saisie pour les notes
        for (int i = 0; i < students.size(); i++) {
            JLabel fullName = Functions.createCustomLabelWithBorder(students.get(i)[0] + " " + students.get(i)[1], 130, 45 + 30 * i, 200, 25, 5, 5, 5, 5, new Color(204, 255, 255));
            fullName.setForeground(new Color(0, 0, 102));

            JLabel cne = Functions.createCustomLabelWithBorder(students.get(i)[2], 360, 45 + 30 * i, 150, 25, 5, 5, 5, 5, new Color(204, 255, 255));
            cne.setForeground(new Color(0, 0, 102));

            JLabel cader = Functions.createCustomLabelWithBorder("", 540, 45 + 30 * i, 80, 25, 5, 5, 5, 5, new Color(204, 255, 255));

            JTextField mark = new JTextField("00.00");
            mark.setBounds(540, 45 + 30 * i, 80, 25);
            mark.setHorizontalAlignment(JTextField.CENTER);
            mark.setFont(new Font("Arial", Font.BOLD, 12));
            mark.setForeground(new Color(0, 0, 102));
            mark.setOpaque(false);
            mark.setBorder(null);

            // Application du filtre de sécurité sur la saisie des notes
            ((AbstractDocument) mark.getDocument()).setDocumentFilter(new MarkFilter());

            conPan.add(fullName);
            conPan.add(cne);
            conPan.add(mark);
            conPan.add(cader);
        }

        // Intégration de la liste des étudiants dans une zone de défilement (JScrollPane)
        JScrollPane sp = new JScrollPane(conPan);
        sp.setBorder(null);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setBounds(165, 223, 738, 392);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        if (students.size() * 30 + 140 > 392) {
            sp.getVerticalScrollBar().setUI(new ScrollBarUI((int) (153664 / (students.size() * 30 + 70))));
        }
        sp.getVerticalScrollBar().setUnitIncrement(16);
        this.add(sp);

        // Ajout des boutons de navigation latérale (Profil, Paramètres, Notifications)
        JButton Profil = Functions.createNavButton(55, 246, "profil_ens", this);
        JButton Settings = Functions.createNavButton(55, 406, "settings_ens", this);
        JButton Notification = Functions.createNavButton(55, 486, "notification_ens", this);

        this.add(Profil);
        this.add(Settings);
        this.add(Notification);
        this.add(Functions.LogOutIcon(this));

        // Ajout du menu supérieur de navigation
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Main.getLastClass(), this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);

        this.add(acceuille);
        this.add(contact);
        this.add(propos);
    }

    // Gestion du dessin de l'image de fond du panneau
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Filtre de saisie interne : accepte uniquement les nombres entre 0 et 20 avec deux décimales
    private static class MarkFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            replace(fb, offset, 0, text, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            Document doc = fb.getDocument();
            String oldText = doc.getText(0, doc.getLength());
            String newText = oldText.substring(0, offset) + (text == null ? "" : text) + oldText.substring(offset + length);

            if (newText.isEmpty()) {
                super.replace(fb, offset, length, text, attrs);
                return;
            }

            if (!newText.matches("\\d*(\\.\\d{0,2})?")) return;
            if (newText.equals(".")) return;

            try {
                double v = Double.parseDouble(newText);
                if (v < 0 || v > 20) return;
            } catch (NumberFormatException ex) {
                return;
            }
            super.replace(fb, offset, length, text, attrs);
        }
    }
}