import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollBarUI extends BasicScrollBarUI {
    
    // Définition de la taille fixe pour le curseur (thumb) de la barre de défilement
    private final int THUMB_SIZE;

    public ScrollBarUI() {
        this.THUMB_SIZE = 60;
    }

    public ScrollBarUI(int THUMB_SIZE) {
        this.THUMB_SIZE = THUMB_SIZE;
    }

    // Configuration de la taille maximale du curseur selon l'orientation (verticale ou horizontale)
    @Override
    protected Dimension getMaximumThumbSize() {
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(0, THUMB_SIZE);
        }
        return new Dimension(THUMB_SIZE, 0);
    }

    // Configuration de la taille minimale du curseur pour garantir sa visibilité
    @Override
    protected Dimension getMinimumThumbSize() {
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            return new Dimension(0, THUMB_SIZE);
        }
        return new Dimension(THUMB_SIZE, 0);
    }

    // Suppression visuelle du bouton fléché supérieur (diminution) en lui donnant une taille nulle
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(0, 0);
            }
        };
    }

    // Suppression visuelle du bouton fléché inférieur (augmentation) en lui donnant une taille nulle
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(0, 0);
            }
        };
    }

    // Gestion du rendu de la piste (track) de la barre de défilement (laissée vide ici)
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    }

    // Personnalisation graphique du curseur avec des coins arrondis et lissage (anti-aliasing)
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(87, 107, 194)); 
        g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, thumbBounds.width - 4, thumbBounds.height - 4, 10, 10);
    } 
}