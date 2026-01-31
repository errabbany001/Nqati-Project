package panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.Functions;
import tools.Navigation;
import tools.Session;
import tools.Resources;

public final class Etudient_notes extends JPanel {

    // Déclaration des variables globales pour l'arrière-plan et le stockage des données académiques
    private Image backgroundImage;
    private int nb_sem;
    private JPanel caderPan;
    private final ArrayList<String[]> semsInfo = new ArrayList<>();
    private final ArrayList<String[]> codes = new ArrayList<>();
    private final ArrayList<JLabel> BleuLines = new ArrayList<>();
    private final ArrayList<JLabel> semesters = new ArrayList<>();

    // Initialisation des données des modules et des codes pour chaque semestre (S1 à S4)
    public void FillTherSemsInfo() {

        String[] s1 = {
            "Réseaux Informatiques",
            "Modélisation UML et Programmation Avancée sous Java",
            "Théorie des graphes et Techniques d’Optimisation",
            "Système d’Information d’Aide à la Décision",
            "Acquisition et Traitement du Signal Numérique",
            "Gestion de Projet de Recherche et Anglais Scientifique"
        };

        String[] s2 = {
            "Modélisation Stochastique",
            "Système d’Information Géographique",
            "Infographie 2D/3D et Reconnaissance de Formes",
            "Sécurité des Systèmes Informatiques",
            "Traitement Numérique d’Image",
            "Analyse de données"
        };

        String[] s3 = {
            "Bases de Données Réparties",
            "Logique et Intelligence Artificielle",
            "Indexation et Recherche d’Image par le Contenu",
            "Machine Learning et Data Mining",
            "Web Sémantique",
            "Systèmes d’Informations pour le Big Data"
        };

        String[] s4 = {
            "Projet de Fin d’Études (PFE)"
        };

        semsInfo.add(s1);
        semsInfo.add(s2);
        semsInfo.add(s3);
        semsInfo.add(s4);

        String[] s1_codes = {"BI01", "BI02", "BI03", "BI04", "BI05", "BI06"};
        String[] s2_codes = {"BI07", "BI08", "BI09", "BI10", "BI11", "BI12"};
        String[] s3_codes = {"BI13", "BI14", "BI15", "BI16", "BI17", "BI18"};
        String[] s4_codes = {"BI19"};

        codes.add(s1_codes);
        codes.add(s2_codes);
        codes.add(s3_codes);
        codes.add(s4_codes);
    }

    // Méthode utilitaire pour charger et redimensionner des images (depuis JAR ou disque)
    public ImageIcon readImage(String path, int l, int h) {
        try {
            java.net.URL imgURL = getClass().getClassLoader().getResource(path);
            Image img;
            if (imgURL != null) {
                img = ImageIO.read(imgURL);
            } else {
                File file = new File(path);
                if (!file.exists()) {
                    System.err.println("CRITICAL: Path not found: " + path);
                    return null;
                }
                img = ImageIO.read(file);
            }
            Image scaled = img.getScaledInstance(l, h, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (IOException e) {
            System.err.println("Erreur de chargement " + path);
            return null;
        }
    }

    // Création des étiquettes de semestre avec gestion du clic pour filtrer les notes
    private JLabel createSemester(int i, int x) {
        int num = i + 1;
        JLabel semester = new JLabel("Semester " + num);
        semester.setBounds(x, 195, 95, 20);
        semester.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17)); 
        semester.setForeground(new Color(87, 107, 194));
        semester.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (i == 0) semester.setForeground(new Color(78, 94, 241));

        semester.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                semester.setForeground(new Color(78, 94, 241));
                if (i < BleuLines.size()) BleuLines.get(i).setVisible(true);

                for (int j = 0; j < semesters.size(); j++) {
                    if (j != i) { 
                        semesters.get(j).setForeground(new Color(87, 107, 194));
                        if (j < BleuLines.size()) BleuLines.get(j).setVisible(false);
                    }
                }

                caderPan.removeAll();
                caderPan.revalidate();
                caderPan.repaint();

                String text = semester.getText();
                int number = Integer.parseInt(text.substring(text.length() - 1));
                caderPan = creatMarksTable(caderPan, number - 1);
            }
        });

        semesters.add(semester);
        return semester;
    }

    // Création de la ligne bleue indicative sous le semestre sélectionné
    public JLabel createLine(int i) {
        JLabel line = new JLabel();
        line.setOpaque(true);
        line.setBackground(new Color(78, 94, 241));
        line.setBounds(156 + i * 127, 225, 126, 5);
        line.setVisible(false);
        BleuLines.add(line);
        return line;
    }

    // Méthodes de dessin pour les séparateurs verticaux et le texte des cellules du tableau
    private JLabel verticalLine(int x, int y, int h) {
        JLabel leb = new JLabel();
        leb.setBounds(x, y, 1, h);
        leb.setOpaque(true);
        leb.setBackground(Color.black);
        return leb;
    }

    private JLabel WriteText(String text, int x, int y, int l, int h, Color col) {
        JLabel lab = new JLabel(text);
        lab.setBounds(x, y, l, h);
        lab.setHorizontalAlignment(JLabel.CENTER);
        lab.setForeground(col);
        lab.setFont(Functions.getMyFont("", Font.BOLD, 12f));
        return lab;
    }

    // Construction d'une ligne de données pour le tableau des notes
    private JPanel WriteALine(JPanel pan, String cd, String nam, String nn, String nr, String nf, String valid, int i) {
        Color myCol = (i == 0) ? Color.white : new Color(49, 54, 154);
        pan.add(WriteText(cd, 12, 12 + i * 36, 54, 34, myCol));
        pan.add(WriteText(nam, 72, 12 + i * 36, 394, 34, myCol));
        pan.add(WriteText(nn, 472, 12 + i * 36, 54, 34, myCol));
        pan.add(WriteText(nr, 532, 12 + i * 36, 54, 34, myCol));
        pan.add(WriteText(nf, 592, 12 + i * 36, 54, 34, myCol));
        pan.add(WriteText(valid, 652, 12 + i * 36, 54, 34, myCol));
        return pan;
    }

    // Génération complète du tableau des notes pour un semestre donné
    public JPanel creatMarksTable(JPanel pan, int semes) {
        pan = WriteALine(pan, "Code", "Module", "NN", "NR", "NF", "Statu", 0);
        pan.add(Functions.createCustomLabelWithBorder("", 10, 10, 700, 38, 10, 10, 0, 0, new Color(87, 107, 194)));

        int modulesCount = semsInfo.get(semes).length;
        for (int j = 1; j <= modulesCount; j++) {
            pan = WriteALine(pan, codes.get(semes)[j-1], semsInfo.get(semes)[j-1], "00.00", "00.00", "00.00", "V", j);
            if (j != modulesCount) {
                pan.add(Functions.createCustomLabelWithBorder("", 10, 10 + j * 36, 700, 38, 0, 0, 0, 0, Color.white));
            } else {
                pan.add(Functions.createCustomLabelWithBorder("", 10, 10 + j * 36, 700, 38, 0, 0, 10, 10, Color.white));
            }
        }
        
        int rows = modulesCount + 1;
        pan.add(verticalLine(70, 11, rows * 36), 0);
        pan.add(verticalLine(470, 11, rows * 36), 0);
        pan.add(verticalLine(530, 11, rows * 36), 0);
        pan.add(verticalLine(590, 11, rows * 36), 0);
        pan.add(verticalLine(650, 11, rows * 36), 0);
        
        return pan;
    }

    public Etudient_notes() {

        // Chargement initial des données et de l'arrière-plan du panneau
        if (semsInfo.isEmpty()) FillTherSemsInfo();
        
        // FIXED: Main.setLastClass -> Navigation.lastClass
        Navigation.lastClass = this.getClass();
        nb_sem = semsInfo.size();

        try {
            backgroundImage = ImageIO.read(new File("data/pg_Etudient_notes.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load data/pg_Etudient_notes.png");
        }

        // Affichage des informations de profil et configuration du layout
        JLabel profilIconMini = new JLabel(Etudient_profil.icon_2);
        profilIconMini.setBounds(920, 48, 40, 40);
        this.add(profilIconMini);
    
        // FIXED: Main.getUserName() -> Session.userName
        JLabel myname = Functions.creetLabel(710, 60, Session.userName);
        myname.setHorizontalAlignment(JLabel.RIGHT);
        this.add(myname);
        this.setLayout(null);

        // Ajout des boutons de navigation latérale et du menu supérieur
        Color perpul = new Color(87, 107, 194);
        this.add(Functions.createNavButton(55, 246, "profil_etd", this));
        this.add(Functions.createNavButton(55, 406, "settings_etd", this));
        this.add(Functions.createNavButton(55, 486, "notification_etd", this));
        this.add(Functions.LogOutIcon(this));

        // FIXED: Main.getLastClass() -> Navigation.lastClass
        this.add(Functions.creerMenu("Accueil", 300, 60, perpul, Navigation.lastClass, this));
        this.add(Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this));
        this.add(Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this));

        // Initialisation des onglets de semestres et du tableau des notes par défaut (S1)
        for (int i = 0; i < semsInfo.size(); i++) {
            this.add(createSemester(i, 167 + 128 * i));
            this.add(createLine(i));
        }
        BleuLines.get(0).setVisible(true);

        caderPan = new JPanel();
        caderPan.setLayout(null);
        caderPan.setOpaque(false);
        caderPan = creatMarksTable(caderPan, 0);
        caderPan.setBounds(170, 250, 740, 285);
        this.add(caderPan);
    }

    // Gestion du rendu graphique pour l'image de fond
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}