package panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    
    private JLabel cdr_1, cdr_2, info , nor , rat, telecharger , importer, Confirm_cader;
    private int switcher = 0;
    private String Text = "";
    private JScrollPane sp;
    private JPanel conPan;
    private JButton CourChoisse;
    private Color col = new Color(77, 149, 247);
    private Color grey = new Color(122, 145, 176);
    private Color lightBleu = new Color(219,245,255);
    private Color ratColor = lightBleu , norColor = lightBleu;
    private Color perpul = new Color(87, 107, 194);
    private ArrayList<String[]> cours = new ArrayList<>();
    private ArrayList<JLabel> choisses = new ArrayList<>();
    private ArrayList<String[]> students = new ArrayList<>();
    private ArrayList<String[]> importedStudents = new ArrayList<>(); 
    private ArrayList<JTextField> listOfNotes = new ArrayList<>();

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
    //=====================================================================
    //=====================================================================
    public void saveToCSV(ArrayList<String[]> dataList, String filePath) {
    try (FileWriter writer = new FileWriter(filePath)) {
        
        writer.append("Nom Complet,CNE,Note\n");

        for (String[] row : dataList) {
            writer.append(row[0]+" " + row[1]).append(","); // Nom Complet
            writer.append(row[2]).append("\n"); // CNE
        }
        writer.flush();
        JOptionPane.showMessageDialog(null, "✅ C'est bon ! Le fichier est enregistré.");

    } catch (IOException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Erreur : " + ex.getMessage());
    }
    }

    // =====================================================================
    // =====================================================================
    public int getIndexByCne(String stu_cne){
        int indx = -1;
        for(int i = 0 ; i < students.size() ; i++){
            if (students.get(i)[2].equals(stu_cne)){
                indx = i;
                break;
            }
        }
        return indx;
    }
    //-----------------------
    public void changeMarks(){
        for (String[] elem : importedStudents) {
            int note_indx = getIndexByCne(elem[1]);
            if (note_indx != -1){
                listOfNotes.get(note_indx).setText(elem[2]);
            }
        }
    }
    // =====================================================================
    // =====================================================================
    public void saveMarks() {
        // 1. تحديد الكور (Matière)
        String idCour = CourChoisse.getText();
        String tableu = (Text.equals("Normal")) ? "note_normal" : "note_rattrapage";
        // 2. جملة SQL للتحديث (تعتمد على note_normal حسب طلبك)
        String sql = "UPDATE note SET " + tableu + " = ? WHERE id_etudiant = ? AND id_cour = ?";

        // استخدام try-with-resources لغلق الكونيكسيون تلقائياً
        try (Connection con = Connexion.getConnexion();
            PreparedStatement pst = con.prepareStatement(sql)) {

            // 3. الدوران على جميع التلاميذ
            for (int i = 0; i < students.size(); i++) {
                
                // أ) جلب الـ CNE من الليستة (الخانة الثالثة رقم 2)
                String idEtudiant = students.get(i)[2]; 
                
                // ب) جلب النقطة من JTextField الموافق لهذا التلميذ
                String noteText = listOfNotes.get(i).getText().trim();
                
                // ج) تحويل النقطة إلى رقم (Double)
                double noteVal = 0.0;
                try {
                    // إذا كانت الخانة فارغة أو فيها "00.00" نعتبرها 0
                    if (!noteText.isEmpty() && !noteText.equals("00.00")) {
                        noteVal = Double.parseDouble(noteText);
                    }
                } catch (NumberFormatException e) {
                    noteVal = 0.0; // حماية إضافية
                }

                // د) ملء الفراغات في الـ Query
                pst.setDouble(1, noteVal);   // مكان note_normal
                pst.setString(2, idEtudiant); // مكان id_etudiant
                pst.setString(3, idCour);     // مكان id_cour

                // هـ) إضافة الأمر إلى الحزمة (Batch) لتنفيذها دفعة واحدة
                pst.addBatch();
            }

            // 4. تنفيذ التحديثات دفعة واحدة (أسرع من تحديث واحد بواحد)
            pst.executeBatch();
            
            JOptionPane.showMessageDialog(null, "✅ تم حفظ جميع النقط بنجاح!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ خطأ أثناء الحفظ: " + e.getMessage());
        }
    }
    // =====================================================================
    // =====================================================================
    public String getMark(String id_etu, String id_cour) {
        String tab = (Text.equals("Normal")) ? "note_normal" : "note_rattrapage";
        System.out.println(tab);
        String sql = "SELECT "+ tab +" FROM note WHERE id_etudiant = ? AND id_cour = ?";
        String noteStr = "00.00"; // Default value (valeur par défaut)

        try (Connection con = Connexion.getConnexion();
            PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, id_etu);
            pst.setString(2, id_cour);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    double note = rs.getDouble(1);
                    
                    // Check if the value was actually NULL in database
                    if (!rs.wasNull()) {
                        noteStr = String.valueOf(note);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching mark: " + e.getMessage());
        }
        return noteStr;
    }
    // =====================================================================
    // =====================================================================

    public JPanel fillPanel(){
        JPanel myPan = new JPanel();
        myPan.setOpaque(false);
        myPan.setLayout(null);
        // ضعه في أول سطر في fillPanel
        try (Connection con = Connexion.getConnexion();
            java.sql.Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DATABASE()")) {
            if (rs.next()) {
                System.out.println("🚨 أنا متصل حالياً بقاعدة البيانات: " + rs.getString(1));
            }
        } catch (Exception e) { e.printStackTrace(); }
        if(!students.isEmpty()){
            myPan.setPreferredSize(new Dimension(784, students.size() * 30 + 140));
            

            JButton btnDownload = new JButton();
            btnDownload.setBounds(130 , 15 , 240 , 25);
            btnDownload.setOpaque(false);
            btnDownload.setContentAreaFilled(false);
            btnDownload.setBorderPainted(false);
            btnDownload.setFocusPainted(false);
            btnDownload.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnDownload.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    telecharger.setBackground(grey);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    telecharger.setBackground(perpul);
                }
                
            });
            btnDownload.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choisir l'emplacement de sauvegarde");
                
                fileChooser.setSelectedFile(new File((CourChoisse.getText() + cours.get(findIndex(CourChoisse.getText()))[2]+ cours.get(findIndex(CourChoisse.getText()))[3]).replace(" ", "") + Text +  ".csv"));

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    
                    File fileToSave = fileChooser.getSelectedFile();
                    String path = fileToSave.getAbsolutePath();

                    if (!path.toLowerCase().endsWith(".csv")) {
                        path += ".csv";
                    }

                    saveToCSV(students, path);
                }
            });
            myPan.add(btnDownload);
            //-------------------------------------------
            JButton btnUploadList = new JButton();
            btnUploadList.setBounds(380, 15, 240, 25); // بلاصة جديدة
            btnUploadList.setOpaque(false);
            btnUploadList.setContentAreaFilled(false);
            btnUploadList.setBorderPainted(false);
            btnUploadList.setFocusPainted(false);
            btnUploadList.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnUploadList.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    importer.setBackground(grey);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    importer.setBackground(perpul);
                }
                
            });
            btnUploadList.addActionListener(e -> {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Choisir la liste des étudiants (CSV)");
                
                int result = chooser.showOpenDialog(null);
                
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    
                    loadStudentsToArrayList(file);
                    
                    System.out.println("List Size: " + importedStudents.size());
                    for (String[] elem : importedStudents) {
                            System.err.println(elem[0] + " "+ elem[2]);
                    }
                    changeMarks();
                
                }
            });
            myPan.add(btnUploadList);


            telecharger =  Functions.createCustomLabelWithBorder("Telecharger la list", 130, 15, 240, 25, 5, 5, 5, 5, perpul);
            myPan.add(telecharger);
            importer =  Functions.createCustomLabelWithBorder("Import la list des note", 380, 15, 240, 25, 5, 5, 5, 5, perpul);
            myPan.add(importer);

            myPan.add(Functions.createCustomLabelWithBorder("Nom Complet", 130, 70, 200, 25, 5, 5, 5, 5, col));
            myPan.add(Functions.createCustomLabelWithBorder("CNE", 360, 70, 150, 25, 5, 5, 5, 5, col));
            myPan.add(Functions.createCustomLabelWithBorder("La Note", 540, 70, 80, 25, 5, 5, 5, 5, col));

            listOfNotes.clear();
            int i ;
            for (i = 0; i < students.size(); i++) {
                JLabel fullName = Functions.createCustomLabelWithBorder(students.get(i)[0] + " " + students.get(i)[1], 130, 100 + 30 * i, 200, 25, 5, 5, 5, 5, new Color(204, 255, 255));
                fullName.setForeground(new Color(0, 0, 102));

                JLabel cne = Functions.createCustomLabelWithBorder(students.get(i)[2], 360, 100 + 30 * i, 150, 25, 5, 5, 5, 5, new Color(204, 255, 255));
                cne.setForeground(new Color(0, 0, 102));

                JLabel cader = Functions.createCustomLabelWithBorder("", 540, 100 + 30 * i, 80, 25, 5, 5, 5, 5, new Color(204, 255, 255));


                JTextField mark = new JTextField(getMark(students.get(i)[2], CourChoisse.getText()));
                mark.setBounds(540, 100 + 30 * i, 80, 25);
                mark.setHorizontalAlignment(JTextField.CENTER);
                mark.setFont(new Font("Arial", Font.BOLD, 12));
                mark.setForeground(new Color(0, 0, 102)); 
                mark.setOpaque(false);
                mark.setBorder(null);

                Runnable checkColor = () -> {
                    String text = mark.getText().replace(".", "").replace("0", "").trim();
                    if (text.isEmpty()) {
                        mark.setForeground(Color.RED); 
                    } else {
                        mark.setForeground(new Color(0, 0, 102));
                    }
                };

                ((AbstractDocument) mark.getDocument()).setDocumentFilter(new MarkFilter());

                mark.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) { checkColor.run(); }
                    @Override
                    public void removeUpdate(DocumentEvent e) { checkColor.run(); }
                    @Override
                    public void changedUpdate(DocumentEvent e) { checkColor.run(); }
                });

                mark.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {
                        if (mark.getText().equals("00.00")) {
                            mark.setText("");
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        if (mark.getText().trim().isEmpty()) {
                            mark.setText("00.00");
                            checkColor.run();
                        }
                    }
                });
                checkColor.run();
                listOfNotes.add(mark);

                myPan.add(fullName);
                myPan.add(cne);
                myPan.add(mark);
                myPan.add(cader);
            }

            JButton Confirm = new JButton();
            Confirm.setBounds(130 ,110 + 30 * i , 490 , 25 );
            Confirm.setOpaque(false);
            Confirm.setContentAreaFilled(false);
            Confirm.setBorderPainted(false);
            Confirm.setFocusPainted(false);
            Confirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
            Confirm.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    Confirm_cader.setBackground(grey);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Confirm_cader.setBackground(perpul);
                }
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // إظهار نافذة التأكيد
                    int reponse = JOptionPane.showConfirmDialog(null, 
                            "Voulez-vous vraiment enregistrer les notes ?", 
                            "Confirmation", 
                            JOptionPane.YES_NO_OPTION, // أزرار نعم / لا
                            JOptionPane.QUESTION_MESSAGE);

                    // إذا ضغط المستخدم على Yes
                    if (reponse == JOptionPane.YES_OPTION) {
                        saveMarks(); // استدعاء دالة الحفظ التي أنشأناها سابقاً
                    }
                }
            });

            myPan.add(Confirm);

            Confirm_cader = Functions.createCustomLabelWithBorder("Confirmé", 130 , 110 + 30 * i , 490 , 25, 5, 5, 5,5, perpul);
            myPan.add(Confirm_cader);
        }else{
            JLabel text = Functions.creetLabel(200, 70, "Actuellement indisponible");
            text.setFont(new Font("Arieal" , Font.BOLD , 30));
            text.setBounds(210 , 70 ,  600 , 50);
            myPan.add(text);
        }


        return myPan;
    }

    public void changeContent(JPanel newPan) {
        sp.setViewportView(newPan);
        
        if (students.size() * 30 + 140 > 392) {
            int scrollValue = (int) (153664 / (students.size() * 30 + 70));
            sp.getVerticalScrollBar().setUI(new ScrollBarUI(scrollValue));
        }

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
   

    public void loadStudentsToArrayList(File file) {
    if (!file.getName().toLowerCase().endsWith(".csv")) {
        JOptionPane.showMessageDialog(null, "❌ Erreur : Veuillez sélectionner un fichier CSV (.csv).", "Format Invalide", JOptionPane.ERROR_MESSAGE);
        return; 
    }

    importedStudents.clear(); 

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        

        while ((line = br.readLine()) != null) {
            // Skip empty lines to avoid errors
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] data = line.split(",");
            
            if (data.length >= 3) {
                String col1 = data[0].trim(); // Nom
                String col2 = data[1].trim(); // CNE
                String col3 = data[2].trim(); // Note
                
                importedStudents.add(new String[]{col1, col2 , col3});
            } 
        }
        
        if (importedStudents.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Le fichier est vide ou le format est incorrect.", "Attention", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "✅ Importation réussie : " + importedStudents.size() + " étudiants chargés.");
            
        }

    } catch (Exception e) {
        // 3. We removed e.printStackTrace() so it doesn't clutter the terminal
        JOptionPane.showMessageDialog(null, "❌ Erreur lors de la lecture du fichier : " + e.getMessage());
    }
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
        CourChoisse = new JButton("Cour");
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
                  Text = "Normal";
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
                    Text = "Rattrappage";
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
        

        sp = new JScrollPane();
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