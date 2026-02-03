package panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import tools.Connexion;
import tools.Functions;
import tools.Navigation;
import tools.ScrollBarUI;
import tools.Session;

public class Enseignant_notes extends JPanel {
    
    // Déclaration des variables globales
    private Image backgroundImage;
    private ArrayList<String[]> cours = new ArrayList<>();
    private ArrayList<JLabel> choisses = new ArrayList<>();
    private JLabel cdr_1, cdr_2, info , nor , rat;
    private int switcher = 0;
    JScrollPane sp;
    JPanel conPan;
    private ArrayList<String[]> students = new ArrayList<>();
    Color col = new Color(77, 149, 247);
    Color grey = new Color(122, 145, 176);
    Color lightBleu = new Color(219,245,255);
    Color ratColor = lightBleu , norColor = lightBleu;

    // Méthode utilitaire pour trouver l'index d'un cours
    public int findIndex(String code) {
        int index = -1;
        for (int i = 0; i < cours.size(); i++) {
            if (cours.get(i)[0].equals(code)) {
                index = i;
            }
        }
        return index;
    }

    // =====================================================================
    // =====================================================================

    public JPanel fillPanel(){
        JPanel myPan = new JPanel();

        myPan.setPreferredSize(new Dimension(784, students.size() * 30 + 140));
        myPan.setOpaque(false);
        myPan.setLayout(null);

        myPan.add(Functions.createCustomLabelWithBorder("Nom Complet", 130, 40, 200, 25, 5, 5, 5, 5, col));
        myPan.add(Functions.createCustomLabelWithBorder("CNE", 360, 40, 150, 25, 5, 5, 5, 5, col));
        myPan.add(Functions.createCustomLabelWithBorder("La Note", 540, 40, 80, 25, 5, 5, 5, 5, col));

        for (int i = 0; i < students.size(); i++) {
            JLabel fullName = Functions.createCustomLabelWithBorder(students.get(i)[0] + " " + students.get(i)[1], 130, 70 + 30 * i, 200, 25, 5, 5, 5, 5, new Color(204, 255, 255));
            fullName.setForeground(new Color(0, 0, 102));

            JLabel cne = Functions.createCustomLabelWithBorder(students.get(i)[2], 360, 70 + 30 * i, 150, 25, 5, 5, 5, 5, new Color(204, 255, 255));
            cne.setForeground(new Color(0, 0, 102));

            JLabel cader = Functions.createCustomLabelWithBorder("", 540, 70 + 30 * i, 80, 25, 5, 5, 5, 5, new Color(204, 255, 255));

            JTextField mark = new JTextField("00.00");
            mark.setBounds(540, 70 + 30 * i, 80, 25);
            mark.setHorizontalAlignment(JTextField.CENTER);
            mark.setFont(new Font("Arial", Font.BOLD, 12));
            mark.setForeground(new Color(0, 0, 102));
            mark.setOpaque(false);
            mark.setBorder(null);

            Runnable checkColor = () -> {
                if (mark.getText().equals("00.00")) {
                    mark.setForeground(Color.RED); 
                } else {
                    mark.setForeground(new Color(0, 0, 102));
                }
            };
            ((AbstractDocument) mark.getDocument()).setDocumentFilter(new MarkFilter());
            checkColor.run();
            mark.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) { checkColor.run(); }
                public void removeUpdate(DocumentEvent e) { checkColor.run(); }
                public void changedUpdate(DocumentEvent e) { checkColor.run(); }
            });

            myPan.add(fullName);
            myPan.add(cne);
            myPan.add(mark);
            myPan.add(cader);
        }
        return myPan;
    }

  public void changeContent(JPanel newPan) {
    // 1. Set the new view
    // 'sp' is accessed directly from the class
    sp.setViewportView(newPan);
    
    // 2. Recalculate ScrollBar based on the global 'students' list
    // We access 'students.size()' directly
    if (students.size() * 30 + 140 > 392) {
        int scrollValue = (int) (153664 / (students.size() * 30 + 70));
        sp.getVerticalScrollBar().setUI(new ScrollBarUI(scrollValue));
    }

    // 3. Refresh
    sp.revalidate();
    sp.repaint();
    }
     //=======================================================================
     //=======================================================================

    public JButton getNewButton(int x  , int y){
        JButton btn = new JButton();
        btn.setBounds(x, y , 90 , 25);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // =====================================================================
    // =====================================================================
    public ArrayList<String[]> getListOfStudent(String id_co , boolean statu){
        ArrayList<String[]> LiStu  = new ArrayList<>();
        String sql1  = "SELECT nom , prenom , Cne FROM etudiant e " + 
                        "join class cl on cl.id_class = e.id_class " + 
                        "join filiere fi on fi.id_filiere = cl.id_filiere " + 
                        "join semester sem on fi.id_filiere = sem.id_filiere " + 
                        "join cour co on co.id_semester = sem.id_semester " + 
                        "where id_cour = ? ;";
        String sql2 = "SELECT * FROM etudiant e " + 
                        "join class cl on cl.id_class = e.id_class " + 
                        "join filiere fi on fi.id_filiere = cl.id_filiere " + 
                        "join semester sem on fi.id_filiere = sem.id_filiere " + 
                        "join cour co on co.id_semester = sem.id_semester " + 
                        "join note nn on nn.id_etudiant = e.Cne and nn.id_cour = co.id_cour " + 
                        "where co.id_cour = ? and note_normal < 10 ;";

        String sql = statu ? sql1 : sql2;

        Connection con = Connexion.getConnexion();

        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id_co);

            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    LiStu.add(new String[] { rs.getString(1) , rs.getString( 2), rs.getString(3)});
               }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return LiStu;
    }
    
    // =====================================================================
    // =====================================================================

    public Enseignant_notes() {
        
        Navigation.lastClass = this.getClass();
        
        try {
            backgroundImage = ImageIO.read(new File("data/pg_Enseignant_notes.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load data/pg_Etudient_notes.png");
        }
        Color perpul = new Color(87, 107, 194);

        // Initialisation des données de test
        if (cours.isEmpty()) {
            cours = Session.getEnseignant().getCours();
        }

        // Affichage du profil
        JLabel profilIconMini = new JLabel(Etudient_profil.icon_2);
        profilIconMini.setBounds(920, 48, 40, 40);
        this.add(profilIconMini);

        JLabel myname = Functions.creetLabel(710, 60, Session.userName);
        myname.setHorizontalAlignment(JLabel.RIGHT);
        this.add(myname);
        this.setLayout(null);



        // Configuration du bouton de sélection de cours
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

        cdr_1 = Functions.createCustomLabelWithBorder("", 180, 170, 150, 25, 7, 7, 7, 7, perpul);
        this.add(cdr_1);
        cdr_2 = Functions.createCustomLabelWithBorder("", 180, 170, 150, 25, 7, 7, 0, 0, perpul);
        cdr_2.setVisible(false);
        this.add(cdr_2);

  


        nor = Functions.createCustomLabelWithBorder("Normal", 700, 170, 90, 25, 7, 7, 7, 7, norColor);
        nor.setFont(new Font("Arial" , Font.BOLD , 12));
        nor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nor.setForeground(Color.black);
        nor.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (norColor.equals(lightBleu)){
                    nor.setBackground(grey);
                }
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (norColor.equals(lightBleu)){
                    nor.setBackground(lightBleu);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                    students.clear();
                    students = getListOfStudent(CourChoisse.getText() , true);
                    changeContent(fillPanel());
                    nor.setForeground(Color.white);
                    rat.setForeground(Color.black);
                    nor.setBackground(perpul);
                    rat.setBackground(lightBleu);
                    norColor = perpul;
                    ratColor = lightBleu;
            }
        });
        nor.setVisible(false);
        this.add(nor);




        rat = Functions.createCustomLabelWithBorder("Rattrapage", 800, 170, 90, 25, 7, 7, 7, 7, ratColor);
        rat.setFont(new Font("Arial" , Font.BOLD , 12));
        rat.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rat.setForeground(Color.black);
        rat.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(ratColor.equals(lightBleu)){
                    rat.setBackground(grey);
                }
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(ratColor.equals(lightBleu)){
                    rat.setBackground(lightBleu);
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                    students.clear();
                    students = getListOfStudent(CourChoisse.getText() , false);
                    changeContent(fillPanel());
                    rat.setForeground(Color.white);
                    nor.setForeground(Color.black);
                    rat.setBackground(perpul);
                    nor.setBackground(lightBleu);
                    ratColor = perpul;
                    norColor = lightBleu;
            }
        });
        rat.setVisible(false);
        this.add(rat);

        // Génération dynamique de la liste déroulante
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
                    info.setText( "<html><div>"+lib.getText() + " : " + cours.get(idx)[1] + "<br>" + cours.get(idx)[2] + " : " + cours.get(idx)[3] + "-" + cours.get(idx)[4] + "</html></div>");
                    rat.setVisible(true);
                    nor.setVisible(true);
                }
            });
            lib.setVisible(false);
            choisses.add(lib);
            this.add(lib);
        }

        info = new JLabel("");
        info.setBounds(360, 170, 600, 25);
        info.setForeground(new Color(27, 39, 206));
        info.setFont(new Font("Ariel", Font.BOLD, 12));
        this.add(info);

        // Création du conteneur pour la liste des étudiants
        conPan = fillPanel();

        sp = new JScrollPane(conPan);
        sp.setBorder(null);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setBounds(165, 223, 738, 392);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        if (students.size() * 30 + 140 > 392) {
            // Using ScrollBarUI from tools package
            sp.getVerticalScrollBar().setUI(new ScrollBarUI((int) (153664 / (students.size() * 30 + 70))));
        }
        sp.getVerticalScrollBar().setUnitIncrement(16);
        this.add(sp);

        JButton Profil = Functions.createNavButton(55, 246, "profil_ens", this);
        JButton Settings = Functions.createNavButton(55, 406, "settings_ens", this);
        JButton Notification = Functions.createNavButton(55, 486, "notification_ens", this);

        this.add(Profil);
        this.add(Settings);
        this.add(Notification);
        this.add(Functions.LogOutIcon(this));

        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Navigation.lastClass, this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);

        this.add(acceuille);
        this.add(contact);
        this.add(propos);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

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