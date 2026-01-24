
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

    private Image backgroundImage;
    private ImageIcon cdr1  = new ImageIcon(new ImageIcon("data/contact_cader_1.png").getImage().getScaledInstance(500, 450, Image.SCALE_SMOOTH));
    private ImageIcon inp1  = new ImageIcon(new ImageIcon("data/contact_input1.png").getImage().getScaledInstance(150, 31, Image.SCALE_SMOOTH));
    private ImageIcon inp2  = new ImageIcon(new ImageIcon("data/contact_input2.png").getImage().getScaledInstance(200, 31, Image.SCALE_SMOOTH));
    private ImageIcon inp3  = new ImageIcon(new ImageIcon("data/contact_input3.png").getImage().getScaledInstance(365, 31, Image.SCALE_SMOOTH));
    private ImageIcon inp4  = new ImageIcon(new ImageIcon("data/contact_input4.png").getImage().getScaledInstance(365, 156, Image.SCALE_SMOOTH));
    private ImageIcon env1  = new ImageIcon(new ImageIcon("data/envoyer_1.png").getImage().getScaledInstance(365, 31, Image.SCALE_SMOOTH));
    private ImageIcon env2  = new ImageIcon(new ImageIcon("data/envoyer_2.png").getImage().getScaledInstance(365, 31, Image.SCALE_SMOOTH));
    private ImageIcon mail  = new ImageIcon(new ImageIcon("data/mail.png").getImage().getScaledInstance(30, 18, Image.SCALE_SMOOTH));
    private ImageIcon phone  = new ImageIcon(new ImageIcon("data/phone.png").getImage().getScaledInstance(30, 18, Image.SCALE_SMOOTH));
    private ImageIcon localisation  = new ImageIcon(new ImageIcon("data/localisation.png").getImage().getScaledInstance(30, 18, Image.SCALE_SMOOTH));

    private JLabel cretInpCad(ImageIcon cader , int x , int y , int l , int h){
        JLabel inpCad1 = new JLabel(cader);
        inpCad1.setBounds(x,y, l,h);
        return inpCad1;
    }
    private JTextField cretInp(int x, int y , int l , int h){
        JTextField InPut = new JTextField();
        InPut.setBounds(x,y, l,h);
        InPut.setOpaque(false); 
        InPut.setBorder(null);
        InPut.setFont(new Font("Arial", Font.ITALIC, 11));
        InPut.setForeground(Color.black);
        return InPut;
        
    }
    private JLabel cretText(String text , int x , int y){
        JLabel myText = new JLabel(text);
        myText.setBounds(x,y,200,20);
        myText.setFont(Functions.getMyFont("", Font.BOLD, 12f));
        myText.setForeground(new Color(22, 31, 112));
        return myText;
    }
    private JLabel cretIcon( ImageIcon icon, int x , int y){
        JLabel myIcon = new JLabel(icon);
        myIcon.setBounds(x,y,30,18);
        return myIcon;
    }
    private JLabel cretTextCont(String text , int x, int y){
        JLabel lib = new JLabel(text);
        lib.setBounds(x,y,200,20);
        lib.setFont(Functions.getMyFont("data/Raleway-.ttf", Font.ITALIC, 14f));
        lib.setForeground(new Color(78,94,241));
        lib.setVerticalAlignment(JLabel.CENTER);
        lib.setHorizontalAlignment(JLabel.CENTER);
        return lib;
    }


    
    public Contact() {

        try {
            backgroundImage = ImageIO.read(new File("data/pg_contact.png"));
        } catch (IOException e) {
            System.err.println("Error: Could not load pg_home.png");
        }

        this.setLayout(null);



        Color perpul = new Color(87, 107, 194);

        JLabel Contactus = new JLabel("Contactez-Nous");
        Contactus.setForeground(new Color(78,94,241));
        Contactus.setFont(Functions.getMyFont("", Font.BOLD, 30f));
        Contactus.setBounds(206,200,400,40);
        this.add(Contactus);


        this.add(cretText("Nom Complet", 206, 260));
        this.add(cretText("Email", 371, 260));
        this.add(cretText("Suject", 206, 310));
        this.add(cretText("Message", 206, 360));
        
        this.add(cretInp(206, 282, 140, 22));
        this.add(cretInp(371, 282, 190, 22));
        this.add(cretInp(206, 332, 354, 22));



        


        JLabel countLabel = new JLabel("0 / 1000");
        countLabel.setBounds(495, 358, 66, 20); 
        countLabel.setForeground(Color.BLACK);
        countLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        countLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.add(countLabel);


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


        this.add(cretInpCad(inp1, 200,280, 150,31));
        this.add(cretInpCad(inp2, 365,280, 200,31));
        this.add(cretInpCad(inp3, 200,330, 365,31));
        this.add(cretInpCad(inp4, 200,380, 365,156));


        JLabel envoyer = new JLabel("Envoyer");
        envoyer.setBounds(200, 545, 365, 27);
        envoyer.setForeground(Color.WHITE);
        envoyer.setFont(new Font("Arial", Font.BOLD, 14));
        envoyer.setVerticalAlignment(JLabel.CENTER);
        envoyer.setHorizontalAlignment(JLabel.CENTER);
        this.add(envoyer);

        JButton vld = new JButton( env1);
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
        }});
        this.add(vld);


        JLabel cader1 = new JLabel(cdr1);
        cader1.setBounds(140,160, 500 , 450);
        this.add(cader1);



        this.add(cretIcon(mail,738, 280));
        this.add(cretTextCont("Contact@nqaty.com", 657, 302));
        this.add(cretIcon(phone , 738, 360));
        this.add(cretTextCont("06 00 10 01 45", 657, 382));
        this.add(cretIcon(localisation , 738, 440));
        this.add(cretTextCont("FSTE, Er-rachidia", 657, 462));




        
        
        JButton acceuille = Functions.creerMenu("Accueil", 300, 60, perpul, Main.getLastClass() , this);
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
}

