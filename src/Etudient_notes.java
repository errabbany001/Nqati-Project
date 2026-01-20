import static java.awt.Cursor.HAND_CURSOR;

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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class Etudient_notes extends JPanel {

    private Image backgroundImage;
    private ImageIcon def, click, light, dark, light2, dark2, mousIN1, mousIN2, conf1, conf2, tab1, tab4, tab6;
    private JButton sems;
    private int nb_sem , swt = 0;
    private ArrayList<String[]> semsInfo = new ArrayList<>();
    private ArrayList<String[]> codes = new ArrayList<>();
    private final ArrayList<JButton> myBtns = new ArrayList<>();

    public void FillTherSemsInfo(){
        // Each String[] represents: {Module Name, Grade, Semester, Status}
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

        String[] s1_codes = {
        "BI01", "BI02", "BI03", "BI04", "BI05", "BI06"
        };

        String[] s2_codes = {
            "BI07", "BI08", "BI09", "BI10", "BI11", "BI12"
        };

        String[] s3_codes = {
            "BI13", "BI14", "BI15", "BI16", "BI17", "BI18"
        };

        String[] s4_codes = {
            "BI19"
        };

        codes.add(s1_codes);
        codes.add(s2_codes);
        codes.add(s3_codes);
        codes.add(s4_codes);
    }



         
    
    
    //==============================================//
    public ImageIcon readImage(String path, int l, int h) {
        try {
            Image img = ImageIO.read(new File(path));
            Image scaled = img.getScaledInstance(l, h, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (IOException e) {
            System.err.println("Erreur de uploading " + path);
            return null;
        }
    }



    //================================================//
    public JButton CreerBTN(int x, int y, int l, int h, ImageIcon icon, String text, int act) {
        JButton btn = new JButton(text, icon);

        btn.setBounds(x, y, l, h);
        btn.setHorizontalTextPosition(JButton.CENTER);
        btn.setVerticalTextPosition(JButton.CENTER);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.white);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setCursor(new Cursor(HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(act==0){
                    if (swt == 0){
                        sems.setIcon(click);
                        swt = 1;
                        for(JButton bt : myBtns){
                            bt.setVisible(true);
                        }
                    }else{
                        sems.setIcon(def);
                        swt = 0;
                        for(JButton bt : myBtns){
                            bt.setVisible(false);
                        }
                    }
                }else{
                    JButton clickedBtn = (JButton) e.getSource();
                    sems.setText(clickedBtn.getText());
                    for (JButton bt : myBtns){
                        bt.setVisible(false);
                    }
                    sems.setIcon(def);
                    swt = 0;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(act != 0){
                    JButton clickedBtn = (JButton) e.getSource();
                    int indx = myBtns.indexOf(clickedBtn);
                    if (indx > 0 ){
                        ImageIcon icn = (indx != myBtns.size() - 1) ? mousIN1 : mousIN2;
                        clickedBtn.setIcon(icn);
                    } 
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(act != 0){
                    JButton clickedBtn = (JButton) e.getSource();
                    int indx = myBtns.indexOf(clickedBtn);
                    if (indx >= 0){
                        ImageIcon icn;
                        if(indx != myBtns.size() - 1){
                            if((indx+2) % 2 == 0){
                                icn = light;
                            }else{
                                icn = dark;
                            }
                        }else{
                            if((indx+2) % 2 == 0){
                                icn = light2;
                            }else{
                                icn = dark2;
                            }

                        }
                        clickedBtn.setIcon(icn);
                    }
                }
            }  
        });
        return btn;
    }

    //=========================================//

    public Etudient_notes() {
        FillTherSemsInfo();
        nb_sem = semsInfo.size();
        try {
            backgroundImage = ImageIO.read(new File("data/pg_Etudient_notes.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load data/pg_Etudient_notes.png");
        }

        def = readImage("data/sem_icon_def.png", 200, 31);

       
        click = readImage("data/sem_icon_click.png", 200, 31);
        light = readImage("data/sem_icon_lighter.png", 200, 31);
        dark = readImage("data/sem_icon_darker.png", 200, 31);
        light2 = readImage("data/sem_icon_lighter2.png", 200, 31);
        dark2 = readImage("data/sem_icon_darker2.png", 200, 31);
        mousIN1 = readImage("data/sem_icon_mousIN1.png", 200, 31);
        mousIN2 = readImage("data/sem_icon_mousIN2.png", 200, 31);
        conf1 = readImage("data/icon_confirm_def.png", 131, 31);
        conf2 = readImage("data/icon_confirm_mous.png", 131, 31);
        tab1 = readImage("data/tableu_1.png", 650, 60);
        tab4 = readImage("data/tableu_4.png", 650, 148);
        tab6 = readImage("data/tableu_6.png", 650, 207);
 

        this.setLayout(null);

        Color perpul = new Color(87, 107, 194);


        JButton Profil = Functions.createNavButton(55, 246, "profil", this);
        JButton Settings = Functions.createNavButton(55, 406, "settings", this);
        JButton Notification = Functions.createNavButton(55, 486, "notification", this);

        this.add(Profil);
        this.add(Settings);
        this.add(Notification);

        this.add(Functions.LogOutIcon(this));

        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Accueil.class, this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);

        this.add(acceuille);
        this.add(contact);
        this.add(propos);

        sems = CreerBTN(300, 160, 200, 31, def, "Semester", 0);
        this.add(sems);
        //=======================================================
        int firstY = 160;
        for(int i =1 ; i <= nb_sem ; i++){
            firstY += 29;
            String name = "Semester " + i;
            ImageIcon L = (i == nb_sem) ? light2 : light;
            ImageIcon D = (i == nb_sem) ? dark2 : dark;
            JButton btn;
            if(i%2 == 0){
                 btn = CreerBTN(300, firstY, 200, 31, L, name, 2);
            }else{
                 btn = CreerBTN(300, firstY, 200, 31, D, name, 2);
            }
            btn.setVisible(false);
            this.add(btn);
            myBtns.add(btn);

        }


        tab1 = readImage("data/tableu_1.png", 650, 60);
        tab4 = readImage("data/tableu_4.png", 650, 148);
        tab6 = readImage("data/tableu_6.png", 650, 207);
 

        JLabel tab_1 = new JLabel(tab1);
        tab_1.setBounds(200,250,650,60);
        tab_1.setVisible(false);
        this.add(tab_1);

        JLabel tab_4 = new JLabel(tab4);
        tab_4.setBounds(200,250,650,147);
        tab_4.setVisible(false);
        this.add(tab_4);

        
        JLabel tab_6 = new JLabel(tab6);
        tab_6.setBounds(200,250,650,207);
        tab_6.setVisible(false);
        this.add(tab_6);






        JButton confirm = new JButton("Confirm", conf1);
        confirm.setBounds(550, 160, 131, 31);
        confirm.setHorizontalTextPosition(JButton.CENTER);
        confirm.setVerticalTextPosition(JButton.CENTER);
        confirm.setContentAreaFilled(false);
        confirm.setBorderPainted(false);
        confirm.setFocusPainted(false);
        confirm.setForeground(Color.white);
        confirm.setFont(new Font("Arial", Font.BOLD, 14));
        confirm.setCursor(new Cursor(HAND_CURSOR));
        
        confirm.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                confirm.setIcon(conf2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                confirm.setIcon(conf1);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                String str = sems.getText();   // "Semester 1" ... "Semester 4"

                // If still "Semester" (default), do nothing
                if (!str.startsWith("Semester ")) return;

                int semNumber;
                try {
                    semNumber = Integer.parseInt(str.substring("Semester ".length()).trim());
                } catch (NumberFormatException ex) {
                    return;
                }

                int idx = semNumber - 1; // ArrayList is 0-based
                if (idx < 0 || idx >= semsInfo.size()) return;

                String[] modules = semsInfo.get(idx);
                String[] cds  = codes.get(idx);

                int nb_mod = modules.length;

                tab_1.setVisible(false);
                tab_4.setVisible(false);
                tab_6.setVisible(false);

                switch (nb_mod) {
                    case 1: tab_1.setVisible(true); break;
                    case 4: tab_4.setVisible(true); break;
                    case 6: tab_6.setVisible(true); break;
                    default: break;
                }

                // Now you can use modules[i] and cds[i] to fill labels if you want
            }

            
            
          });
        this.add(confirm);
















    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
