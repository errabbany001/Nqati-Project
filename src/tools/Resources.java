package tools;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Resources {
    
    // تحميل الصور مرة واحدة هنا
    public static final ImageIcon logo = new ImageIcon(new ImageIcon("data/logo.png").getImage().getScaledInstance(100, 87, Image.SCALE_SMOOTH));
    public static final ImageIcon ph_1 = new ImageIcon(new ImageIcon("data/photo_1.png").getImage().getScaledInstance(860, 416, Image.SCALE_SMOOTH));
    public static final ImageIcon ph_2 = new ImageIcon(new ImageIcon("data/photo_2.png").getImage().getScaledInstance(400, 320, Image.SCALE_SMOOTH));
    public static final ImageIcon ph_3 = new ImageIcon(new ImageIcon("data/photo_3.png").getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH));
    public static final ImageIcon ph_4 = new ImageIcon(new ImageIcon("data/photo_4.png").getImage().getScaledInstance(560, 400, Image.SCALE_SMOOTH));
    public static final ImageIcon ph_5 = new ImageIcon(new ImageIcon("data/photo_5.png").getImage().getScaledInstance(860, 400, Image.SCALE_SMOOTH));
    

}