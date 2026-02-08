package panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import element.Note;
import tools.Connexion;
import tools.Functions;
import tools.Navigation;
import tools.Session;

public final class Etudient_notes extends JPanel {

    // Déclaration des variables globales pour l'arrière-plan et le stockage des données académiques
    private Image backgroundImage;
    private int nb_sem,  thisSemester;
    private JPanel caderPan;
    //stock the cours there codes
    private ArrayList<String[]> semsInfo = new ArrayList<>();
    private ArrayList<String[]> codes = new ArrayList<>();
    // lines for telling what semester the user chosse
    private ArrayList<JLabel> BleuLines = new ArrayList<>();
    // list of the semesters
    private ArrayList<JLabel> semesters = new ArrayList<>();






    public void addMark(){

    }

    //==================================================================================================================================================
    //==================================================================================================================================================


    // Initialisation des données des modules et des codes pour chaque semestre (S1 à S4)
    public void FillTherSemsInfo(String cne, String filier) {
        semsInfo.clear();
        codes.clear();
        Session.getEtudiant().getNotes().clear();
        Connection con = Connexion.getConnexion();

        String sql1 = "SELECT nom_semester FROM etudiant e " +
                    "JOIN class s ON s.id_class = e.id_class " +
                    "JOIN semester sem ON sem.id_filiere = s.id_filiere " +
                    "WHERE cne = ?";

        // On prépare la requête COURS une seule fois (Performance)
        String sql2 = "SELECT nom_cour, id_cour FROM filiere f " +
                    "JOIN semester sem ON sem.id_filiere = f.id_filiere " +
                    "JOIN cour co ON co.id_semester = sem.id_semester " +
                    "WHERE nom_filiere = ? AND nom_semester = ?";

        try (PreparedStatement ps1 = con.prepareStatement(sql1);
            PreparedStatement ps2 = con.prepareStatement(sql2)) { // On prépare ps2 ici
            
            ps1.setString(1, cne);

            try (ResultSet rs1 = ps1.executeQuery()) {
                while (rs1.next()) {
                    // Création des listes neuves pour ce semestre
                    ArrayList<String> tempList = new ArrayList<>();
                    ArrayList<String> tempList2 = new ArrayList<>();
                    ArrayList<Note> tempList3 = new ArrayList<>();

                    String currentSem = rs1.getString("nom_semester");
                    
                    // Paramétrage de la requête cours
                    ps2.setString(1, filier);
                    ps2.setString(2, currentSem);

                    try (ResultSet rs2 = ps2.executeQuery()) {
                        while (rs2.next()) {
                            String nomCour = rs2.getString("nom_cour"); // Colonne 1
                            String idCour  = rs2.getString("id_cour");  // Colonne 2

                            tempList.add(nomCour);
                            tempList2.add(idCour);
                            
                            // CORRECTION ICI : On passe l'ID (idCour), pas le nom !
                            tempList3.add(getMark(cne, idCour)); 
                        }
                        
                        // Ajout aux listes globales
                        semsInfo.add(tempList.toArray(new String[0]));
                        codes.add(tempList2.toArray(new String[0]));
                        Session.getEtudiant().getNotes().add(tempList3);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==================================================================================================================================================
    //==================================================================================================================================================

    public  Note getMark(String cne, String idCour) {
        Note no = new Note(); 
        Connection con = Connexion.getConnexion();
        
        String sql = "SELECT note_normal, note_rattrapage FROM note " +
                    "WHERE id_etudiant = ? AND id_cour = ?";

        try (PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, cne);
            pr.setString(2, idCour);

            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    // CORRECTION ICI : On récupère en BigDecimal d'abord
                    java.math.BigDecimal bd1 = rs.getBigDecimal("note_normal");
                    java.math.BigDecimal bd2 = rs.getBigDecimal("note_rattrapage");

                    // On convertit en double. Si c'est null, on met 0.0
                    double val1 = (bd1 == null) ? -1 : bd1.doubleValue();
                    double val2 = (bd2 == null) ? -1 : bd2.doubleValue();
                    no.setNormal(val1);
                    no.setRattrapage(val2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return no;
    }

    //==================================================================================================================================================
    //==================================================================================================================================================

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

    //==================================================================================================================================================
    //==================================================================================================================================================
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
                thisSemester = number;
                caderPan = creatMarksTable(caderPan, number - 1);
            }
        });

        semesters.add(semester);
        return semester;
    }

    //==================================================================================================================================================
    //==================================================================================================================================================

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

    //==================================================================================================================================================
    //==================================================================================================================================================

    // Méthodes de dessin pour les séparateurs verticaux et le texte des cellules du tableau
    private JLabel verticalLine(int x, int y, int h) {
        JLabel leb = new JLabel();
        leb.setBounds(x, y, 1, h);
        leb.setOpaque(true);
        leb.setBackground(Color.black);
        return leb;
    }
    //==================================================================================================================================================
    //==================================================================================================================================================

    private JLabel WriteText(String text, int x, int y, int l, int h, Color col) {
        JLabel lab = new JLabel(text);
        lab.setBounds(x, y, l, h);
        lab.setHorizontalAlignment(JLabel.CENTER);
        lab.setForeground(col);
        lab.setFont(Functions.getMyFont("", Font.BOLD, 12f));
        return lab;
    }
    //==================================================================================================================================================
    //==================================================================================================================================================

    // Construction d'une ligne de données pour le tableau des notes

    private JPanel WriteALine(JPanel pan, String cd, String nam, double n, double r, double fo, String valid, int i) {
    String nn, nr, nf;
    if (n == -2) {
        nn = "NN";
        nr = "NR";
        nf = "NF";
    } else {
        nn = (n == -1) ? "-" : "" + n;
        nr = (r == -1) ? "-" : "" + r;
        nf = (fo == -1) ? "-" : "" + fo;
    }
    
    Color myCol = (i == 0) ? Color.white : new Color(49, 54, 154);
    pan.add(WriteText(cd, 12, 12 + i * 36, 54, 34, myCol));
    pan.add(WriteText(nam, 72, 12 + i * 36, 394, 34, myCol));
    pan.add(WriteText(nn, 472, 12 + i * 36, 54, 34, myCol));
    pan.add(WriteText(nr, 532, 12 + i * 36, 54, 34, myCol));
    pan.add(WriteText(nf, 592, 12 + i * 36, 54, 34, myCol));
    pan.add(WriteText(valid, 652, 12 + i * 36, 54, 34, myCol));
    return pan;
    }
    //==================================================================================================================================================
    //==================================================================================================================================================}

    // Génération complète du tableau des notes pour un semestre donné
    public JPanel creatMarksTable(JPanel pan, int semes) {
        pan = WriteALine(pan, "Code", "Module", -2, -2, -2, "Statu", 0);
        pan.add(Functions.createCustomLabelWithBorder("", 10, 10, 700, 38, 10, 10, 0, 0, new Color(87, 107, 194)));

        int modulesCount = semsInfo.get(semes).length;
        for (int j = 1; j <= modulesCount; j++) {
            pan = WriteALine(pan, codes.get(semes)[j-1], semsInfo.get(semes)[j-1], 
            Session.getEtudiant().getNotes().get(semes).get(j-1).getNormal(),  Session.getEtudiant().getNotes().get(semes).get(j-1).getRattrapage(), Session.getEtudiant().getNotes().get(semes).get(j-1).getFinalle()
            , Session.getEtudiant().getNotes().get(semes).get(j-1).getStatu(), j);

            if (j != modulesCount ) {
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
        //note de semester
        pan.add(Functions.creetLabel(100, 315 , "NOTE DE SEMESTER:"));

       
        Session.getEtudiant().notesCheck();

        double mark = Session.getEtudiant().getSemsterNotes().get(semes);
        String markText =(mark == -1) ?  "--.--" : mark + "";


        JLabel note = Functions.EtudInfo(260 , 315 , markText);
        pan.add(note);

        pan.add(Functions.creetLabel(400, 315 , "STATU DE SEMESTER:"));
        String result;
        if (mark == -1){
            result = "-----------";
        }else if (true) {
            int check = 1;
            for (String elem : codes.get(semes)) {
                if (getMark( Session.getEtudiant().getCne(), elem).getFinalle() < 10){
                    check = 0;
                    break;
                }
            }  
            result = (check == 1)? "Valider" : "Non Valider"; 
        }
        else{
            result = (mark >= 10) ? "Varlider" : "Non Valider";
        }
        JLabel statu = Functions.EtudInfo(565 , 315 , result);
        pan.add(statu);
        
        return pan;
    }
    //==================================================================================================================================================
    //==================================================================================================================================================

    public Etudient_notes() {

        // Chargement initial des données et de l'arrière-plan du panneau
        if (semsInfo.isEmpty()) FillTherSemsInfo(Session.getEtudiant().getCne() , Session.getEtudiant().getFilier());
        
        Navigation.lastClass = this.getClass();
        nb_sem = semsInfo.size();

        try {
            backgroundImage = ImageIO.read(new File("data/pg_Etudient_notes.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load data/pg_Etudient_notes.png");
        }

        
        // Affichage des informations de profil et configuration du layout
        JLabel profilIconMini = new JLabel(new ImageIcon(Session.photo.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        profilIconMini.setBounds(920, 48, 40, 40);
        this.add(profilIconMini);
    
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

        this.add(Functions.creerMenu("Accueil", 300, 60, perpul, Navigation.lastClass, this));
        this.add(Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this));
        this.add(Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this));

        // Initialisation des onglets de semestres et du tableau des notes par défaut (S1)
        for (int i = 0; i < semsInfo.size(); i++) {
            this.add(createSemester(i, 167 + 128 * i));
            this.add(createLine(i));
        }
        BleuLines.get(0).setVisible(true);

        //Creation de pannel de tableu des notes
        caderPan = new JPanel();
        caderPan.setLayout(null);
        caderPan.setOpaque(false);
        caderPan = creatMarksTable(caderPan, 0);
        caderPan.setBounds(170, 240, 740, 400);
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