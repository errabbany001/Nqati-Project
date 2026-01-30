import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public final class Contact extends JPanel {

    // Déclaration et redimensionnement des icônes et des ressources d'interface
    private Image backgroundImage;
    private ImageIcon cdr1 = new ImageIcon(new ImageIcon("data/contact_cader_1.png").getImage().getScaledInstance(200, 75, Image.SCALE_SMOOTH));
    private ImageIcon inp1 = new ImageIcon(new ImageIcon("data/contact_input1.png").getImage().getScaledInstance(150, 31, Image.SCALE_SMOOTH));
    private ImageIcon inp2 = new ImageIcon(new ImageIcon("data/contact_input2.png").getImage().getScaledInstance(200, 31, Image.SCALE_SMOOTH));
    private ImageIcon inp3 = new ImageIcon(new ImageIcon("data/contact_input3.png").getImage().getScaledInstance(365, 31, Image.SCALE_SMOOTH));
    private ImageIcon inp4 = new ImageIcon(new ImageIcon("data/contact_input4.png").getImage().getScaledInstance(365, 156, Image.SCALE_SMOOTH));
    private ImageIcon env1 = new ImageIcon(new ImageIcon("data/envoyer_1.png").getImage().getScaledInstance(365, 31, Image.SCALE_SMOOTH));
    private ImageIcon env2 = new ImageIcon(new ImageIcon("data/envoyer_2.png").getImage().getScaledInstance(365, 31, Image.SCALE_SMOOTH));
    private ImageIcon mail = new ImageIcon(new ImageIcon("data/mail.png").getImage().getScaledInstance(30, 18, Image.SCALE_SMOOTH));
    private ImageIcon phone = new ImageIcon(new ImageIcon("data/phone.png").getImage().getScaledInstance(30, 18, Image.SCALE_SMOOTH));
    private ImageIcon localisation = new ImageIcon(new ImageIcon("data/localisation.png").getImage().getScaledInstance(30, 18, Image.SCALE_SMOOTH));

    // Méthodes utilitaires pour la création rapide de composants graphiques personnalisés
    private JLabel cretInpCad(ImageIcon cader, int x, int y, int l, int h) {
        JLabel inpCad1 = new JLabel(cader);
        inpCad1.setBounds(x, y, l, h);
        return inpCad1;
    }

    private JTextField cretInp(int x, int y, int l, int h) {
        JTextField InPut = new JTextField();
        InPut.setBounds(x, y, l, h);
        InPut.setOpaque(false);
        InPut.setBorder(null);
        InPut.setFont(new Font("Arial", Font.ITALIC, 11));
        InPut.setForeground(Color.black);
        return InPut;
    }

    private JLabel cretText(String text, int x, int y) {
        JLabel myText = new JLabel(text);
        myText.setBounds(x, y, 200, 20);
        myText.setFont(Functions.getMyFont("", Font.BOLD, 15f));
        myText.setForeground(new Color(22, 31, 112));
        return myText;
    }

    private JLabel cretIcon(ImageIcon icon, int x, int y) {
        JLabel myIcon = new JLabel(icon);
        myIcon.setBounds(x, y, 30, 18);
        return myIcon;
    }

    private JLabel cretTextCont(String text, int x, int y) {
        JLabel lib = new JLabel(text);
        lib.setBounds(x, y, 200, 20);
        lib.setFont(Functions.getMyFont("", Font.ITALIC, 14f));
        lib.setForeground(new Color(78, 94, 241));
        lib.setVerticalAlignment(JLabel.CENTER);
        lib.setHorizontalAlignment(JLabel.CENTER);
        return lib;
    }

    public Contact() {

        // Chargement de l'image de fond du panneau contact
        try {
            backgroundImage = ImageIO.read(new File("data/pg_contact.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }
        this.setLayout(null);

        // Affichage conditionnel des informations de profil utilisateur si connecté
        JLabel profilIconMini = new JLabel(Etudient_profil.icon_2);
        profilIconMini.setBounds(920, 48, 40, 40);
        this.add(profilIconMini);

        JLabel myname = Functions.creetLabel(710, 60, Main.getUserName());
        myname.setHorizontalAlignment(JLabel.RIGHT);
        this.add(myname);

        if (Main.getLogedIn() == 0) {
            profilIconMini.setVisible(false);
            myname.setVisible(false);
        } else {
            profilIconMini.setVisible(true);
            myname.setVisible(true);
        }

        // Configuration du titre principal de la page
        Color perpul = new Color(87, 107, 194);
        JLabel Contactus = new JLabel("Contactez-Nous");
        Contactus.setForeground(new Color(78, 94, 241));
        Contactus.setFont(Functions.getMyFont("", Font.BOLD, 30f));
        Contactus.setBounds(206, 200, 400, 40);
        this.add(Contactus);

        // Ajout des libellés et des champs de saisie pour le formulaire
        this.add(cretText("Nom Complet", 206, 260));
        this.add(cretText("Email", 371, 260));
        this.add(cretText("Suject", 206, 310));
        this.add(cretText("Message", 206, 360));

        this.add(cretInp(206, 282, 140, 22));
        this.add(cretInp(371, 282, 190, 22));
        this.add(cretInp(206, 332, 354, 22));

        // Mise en place du compteur de caractères pour le champ message
        JLabel countLabel = new JLabel("0 / 1000");
        countLabel.setBounds(495, 358, 66, 20);
        countLabel.setBackground(Color.red);
        countLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        countLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.add(countLabel);

        // Configuration de la zone de texte défilante pour le message
        JTextArea text = new JTextArea();
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setOpaque(false);
        text.setBorder(null);
        text.setFont(new Font("Arial", Font.ITALIC, 11));
        text.setForeground(Color.black);

        JScrollPane scroll = new JScrollPane(text);
        scroll.setBounds(206, 382, 353, 147);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUI(new ScrollBarUI());
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scroll);

        // Application d'un filtre pour limiter la saisie à 1000 caractères
        AbstractDocument doc = (AbstractDocument) text.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            final int maxChars = 1000;

            private void updateLabel(int length) {
                countLabel.setText(length + " / 1000");
                if (length >= maxChars) {
                    countLabel.setForeground(Color.RED);
                } else {
                    countLabel.setForeground(Color.BLACK);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException {
                if (str == null) return;
                int currentLength = fb.getDocument().getLength();
                int newLength = currentLength - length + str.length();
                if (newLength <= maxChars) {
                    super.replace(fb, offset, length, str, attrs);
                    updateLabel(newLength);
                } else {
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length);
                updateLabel(fb.getDocument().getLength());
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {
                replace(fb, offset, 0, str, attr);
            }
        });

        // Ajout des cadres visuels en arrière-plan des champs de saisie
        this.add(cretInpCad(inp1, 200, 280, 150, 31));
        this.add(cretInpCad(inp2, 365, 280, 200, 31));
        this.add(cretInpCad(inp3, 200, 330, 365, 31));
        this.add(cretInpCad(inp4, 200, 380, 365, 156));

        // Création du bouton d'envoi avec gestion des effets de survol
        JLabel envoyer = new JLabel("Envoyer");
        envoyer.setBounds(200, 545, 365, 27);
        envoyer.setForeground(Color.WHITE);
        envoyer.setFont(new Font("Arial", Font.BOLD, 14));
        envoyer.setVerticalAlignment(JLabel.CENTER);
        envoyer.setHorizontalAlignment(JLabel.CENTER);
        this.add(envoyer);

        JButton vld = new JButton(env1);
        vld.setBounds(200, 545, 365, 31);
        vld.setHorizontalTextPosition(JButton.CENTER);
        vld.setVerticalTextPosition(JButton.CENTER);
        vld.setContentAreaFilled(false);
        vld.setBorderPainted(false);
        vld.setFocusPainted(false);
        vld.setCursor(new Cursor(Cursor.HAND_CURSOR));

        vld.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                vld.setIcon(env2);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                vld.setIcon(env1);
            }
        });
        this.add(vld);

        // Ajout des informations de contact direct (Mail, Téléphone, Localisation)
        this.add(cretIcon(mail, 738, 310));
        this.add(cretTextCont("Contact@nqaty.com", 657, 332));
        this.add(cretIcon(phone, 738, 390));
        this.add(cretTextCont("06 00 10 01 45", 657, 412));
        this.add(cretIcon(localisation, 738, 470));
        this.add(cretTextCont("FSTE, Er-rachidia", 657, 492));

        // Ajout des cadres décoratifs pour la section des coordonnées
        JLabel cader1 = new JLabel(cdr1);
        cader1.setBounds(658, 290, 200, 75);
        this.add(cader1);

        JLabel cader2 = new JLabel(cdr1);
        cader2.setBounds(658, 370, 200, 75);
        this.add(cader2);

        JLabel cader3 = new JLabel(cdr1);
        cader3.setBounds(658, 450, 200, 75);
        this.add(cader3);

        // Initialisation des éléments du menu de navigation supérieur
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Main.getLastClass(), this);
        JButton contact = Functions.creerMenu("Contact", 440, 60, perpul, Contact.class, this);
        JButton propos = Functions.creerMenu("A propos", 580, 60, perpul, Propos.class, this);

        this.add(acceuille);
        this.add(contact);
        this.add(propos);
    }

    // Rendu de l'image d'arrière-plan personnalisée
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}