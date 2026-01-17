import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Transition {

    // ================== FADE SIMPLE ==================
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
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            super.paintChildren(g2);
            g2.dispose();
        }
    }

    // FADE Public
    public static void showWithFade(JFrame frame, JPanel newPanel) {
        FadePanel fadePanel = new FadePanel(newPanel);
        fadePanel.setOpacity(0f);

        frame.setContentPane(fadePanel);
        frame.revalidate();
        frame.repaint();

        final float[] op = {0f};

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

    // ================== SLIDE + FADE + SMOOTH ==================
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
            g2.translate(offsetX, 0);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            super.paintChildren(g2);
            g2.dispose();
        }
    }

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
        final int duration = 350; // smooth 350ms

        Timer timer = new Timer(16, e -> {
            long now = System.currentTimeMillis();
            float t = (now - startTime) / (float) duration;
            if (t > 1f) t = 1f;

            // Easing smoothstep
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

    public static void showSlideFadeFromRight(JFrame frame, JPanel newPanel) {
        slideInternal(frame, newPanel, true);
    }

    public static void showSlideFadeFromLeft(JFrame frame, JPanel newPanel) {
        slideInternal(frame, newPanel, false);
    }
}
