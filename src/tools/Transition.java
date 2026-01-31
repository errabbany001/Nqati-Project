package tools;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Transition {

    // Classe interne pour gérer l'affichage progressif (opacité) d'un panneau
    private static class FadePanel extends JPanel {
        private float opacity = 0f;

        public FadePanel(JPanel content) {
            setLayout(new BorderLayout());
            add(content, BorderLayout.CENTER);
            setOpaque(false);
        }

        public void setOpacity(float value) {
            opacity = Math.max(0f, Math.min(1f, value));
            repaint();
        }

        @Override
        protected void paintChildren(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Application de la transparence sur tous les composants enfants
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            super.paintChildren(g2);
            g2.dispose();
        }
    }

    // Méthode publique pour déclencher une transition en fondu (Fade In)
    public static void showWithFade(JFrame frame, JPanel newPanel) {
        FadePanel fadePanel = new FadePanel(newPanel);
        fadePanel.setOpacity(0f);

        frame.setContentPane(fadePanel);
        frame.revalidate();
        frame.repaint();

        final float[] op = {0f};

        // Animation de l'opacité par incréments via un Timer Swing
        Timer timer = new Timer(1, e -> {
            op[0] += 0.05f;
            if (op[0] >= 1f) {
                op[0] = 1f;
                fadePanel.setOpacity(op[0]);
                ((Timer) e.getSource()).stop();
            } else {
                fadePanel.setOpacity(op[0]);
            }
        });
        timer.start();
    }

    // Classe interne pour gérer simultanément le glissement (offset) et l'opacité
    private static class SlideFadePanel extends JPanel {
        private int offsetX = 0;
        private float opacity = 0f;

        public SlideFadePanel(JPanel content) {
            setLayout(new BorderLayout());
            add(content, BorderLayout.CENTER);
            setOpaque(false);
        }

        public void setOffsetX(int x) {
            offsetX = x;
            repaint();
        }

        public void setOpacity(float value) {
            opacity = Math.max(0f, Math.min(1f, value));
            repaint();
        }

        @Override
        protected void paintChildren(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Déplacement horizontal et application de la transparence
            g2.translate(offsetX, 0);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            super.paintChildren(g2);
            g2.dispose();
        }
    }

    // Logique interne commune pour animer un panneau avec un effet de glissement fluide
    private static void slideInternal(JFrame frame, JPanel newPanel, boolean fromRight) {
        SlideFadePanel slidePanel = new SlideFadePanel(newPanel);

        int w = frame.getContentPane().getWidth();
        if (w <= 0) w = frame.getWidth();
        if (w <= 0) w = 800;

        int startX = fromRight ? w : -w;
        slidePanel.setOffsetX(startX);
        slidePanel.setOpacity(0f);

        frame.setContentPane(slidePanel);
        frame.revalidate();
        frame.repaint();

        final long startTime = System.currentTimeMillis();
        final int duration = 350; // Durée de l'animation en millisecondes

        // Calcul de la trajectoire avec une fonction d'accélération (Easing)
        Timer timer = new Timer(16, e -> {
            long now = System.currentTimeMillis();
            float t = (now - startTime) / (float) duration;
            if (t > 1f) t = 1f;

            // Fonction "Smoothstep" pour une transition fluide et naturelle
            float eased = t * t * (3f - 2f * t);

            int x = (int) (startX * (1f - eased));
            float alpha = eased;

            slidePanel.setOffsetX(x);
            slidePanel.setOpacity(alpha);

            if (t >= 1f) {
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    // Méthode pour faire apparaître un panneau depuis la droite vers le centre
    public static void showSlideFadeFromRight(JFrame frame, JPanel newPanel) {
        slideInternal(frame, newPanel, true);
    }

    // Méthode pour faire apparaître un panneau depuis la gauche vers le centre
    public static void showSlideFadeFromLeft(JFrame frame, JPanel newPanel) {
        slideInternal(frame, newPanel, false);
    }
}