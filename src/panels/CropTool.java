package panels;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;

public class CropTool {

    // ====== CONFIGURATION ======
    private static final int MIN_SELECTION_SIZE = 120; // Minimum square size allowed

    // ====== DATA OBJECT TO RETURN RESULTS ======
    public static class CropResult {
        public final BufferedImage image;  // Use this to show on screen
        public final byte[] imageBytes;    // Use this to save to DB

        public CropResult(BufferedImage image, byte[] imageBytes) {
            this.image = image;
            this.imageBytes = imageBytes;
        }
    }

    /**
     * MAIN ENTRY POINT: Opens File Chooser -> Opens Crop Dialog -> Returns Result
     * @param parent The UI component calling this (e.g., your button or frame)
     * @return CropResult containing image and bytes, or null if cancelled.
     */
    public static CropResult openAndCrop(Component parent) {
        // 1. Open File Chooser
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choisir une image");
        chooser.setFileFilter(new FileNameExtensionFilter("Images (JPG, PNG)", "jpg", "jpeg", "png", "gif"));

        if (chooser.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return null; // User cancelled file selection
        }

        try {
            File file = chooser.getSelectedFile();
            BufferedImage originalImage = ImageIO.read(file);

            if (originalImage == null) {
                JOptionPane.showMessageDialog(parent, "Fichier invalide ou non supporté.");
                return null;
            }

            // 2. Open the Crop Dialog
            return showCropDialog(parent, originalImage);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Erreur lors de l'ouverture: " + ex.getMessage());
            return null;
        }
    }

    // ====== INTERNAL: SHOWS THE MODAL DIALOG ======
    private static CropResult showCropDialog(Component parent, BufferedImage image) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog(window, "Rogner la photo", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());

        CropPanel cropPanel = new CropPanel(image);
        JScrollPane scroll = new JScrollPane(cropPanel);

        // --- DYNAMIC SIZING LOGIC ---
        // 1. Get Screen Size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // 2. Define Maximum Size (90% of screen to ensure it stays inside the "cader")
        int maxWidth = (int) (screenSize.width * 0.9);
        int maxHeight = (int) (screenSize.height * 0.9);

        // 3. Define Dialog Size (Image size + padding, but capped at Max Screen Size)
        int desiredWidth = image.getWidth() + 25;  // +25 for scrollbars/borders
        int desiredHeight = image.getHeight() + 50;

        int finalWidth = Math.min(desiredWidth, maxWidth);
        int finalHeight = Math.min(desiredHeight, maxHeight);

        scroll.setPreferredSize(new Dimension(finalWidth, finalHeight));

        // Clean look: remove border if image fits completely
        if (desiredWidth <= maxWidth && desiredHeight <= maxHeight) {
            scroll.setBorder(BorderFactory.createEmptyBorder());
        }

        // Speed up scrolling
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getHorizontalScrollBar().setUnitIncrement(16);

        dialog.add(scroll, BorderLayout.CENTER);

        // Instructions Label
        JLabel info = new JLabel("<html>Instructions: Glissez pour sélectionner. (Min " + MIN_SELECTION_SIZE + "px)</html>");
        info.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.add(info, BorderLayout.NORTH);

        // Buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancel = new JButton("Annuler");
        JButton btnConfirm = new JButton("Valider");

        // Style the confirm button
        btnConfirm.setBackground(new Color(50, 120, 200));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFocusPainted(false);

        bottom.add(btnCancel);
        bottom.add(btnConfirm);
        dialog.add(bottom, BorderLayout.SOUTH);

        // Wrapper to hold the result
        final CropResult[] resultHolder = {null};

        btnCancel.addActionListener(e -> dialog.dispose());

        btnConfirm.addActionListener(e -> {
            Rectangle r = cropPanel.getSelection();
            if (r.width <= 0) return;

            // Safety: Ensure coordinates are inside the image
            int x = Math.max(0, Math.min(r.x, image.getWidth() - r.width));
            int y = Math.max(0, Math.min(r.y, image.getHeight() - r.height));

            try {
                // 1. Crop
                BufferedImage cropped = image.getSubimage(x, y, r.width, r.height);

                // 2. Convert to Bytes (PNG format)
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(cropped, "png", baos);
                byte[] bytes = baos.toByteArray();

                // 3. Save Result
                resultHolder[0] = new CropResult(cropped, bytes);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            dialog.dispose();
        });

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true); // Code pauses here until dialog is closed

        return resultHolder[0];
    }

    // ====== INNER CLASS: THE VISUAL CROPPER ======
    private static class CropPanel extends JPanel {
        private final BufferedImage image;
        private final Rectangle selection;

        // State for dragging
        private Point anchorPoint = null;
        private Point lastMovePoint = null;
        private boolean isMoving = false;

        CropPanel(BufferedImage image) {
            this.image = image;

            // Initial selection centered (Standard size or Image min dim if smaller)
            int initSize = Math.min(200, Math.min(image.getWidth(), image.getHeight()));
            
            // Safety check for very small images
            if (initSize < MIN_SELECTION_SIZE) initSize = Math.min(image.getWidth(), image.getHeight());

            int cx = (image.getWidth() - initSize) / 2;
            int cy = (image.getHeight() - initSize) / 2;
            this.selection = new Rectangle(cx, cy, initSize, initSize);

            setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

            MouseAdapter ma = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    anchorPoint = e.getPoint();
                    // If clicked inside box -> Move Mode
                    if (selection.contains(anchorPoint)) {
                        isMoving = true;
                        lastMovePoint = anchorPoint;
                        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    } else {
                        // If clicked outside -> New Selection Mode
                        isMoving = false;
                        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                        selection.setBounds(anchorPoint.x, anchorPoint.y, 0, 0);
                        repaint();
                    }
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (isMoving) {
                        handleMove(e.getPoint());
                    } else {
                        handleResize(e.getPoint());
                    }
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // Enforce Minimum Size
                    if (selection.width < MIN_SELECTION_SIZE) {
                        // If the image itself is smaller than MIN, allow full image, else enforce MIN
                        int effectiveMin = Math.min(MIN_SELECTION_SIZE, Math.min(image.getWidth(), image.getHeight()));
                        
                        int centerX = selection.x + selection.width / 2;
                        int centerY = selection.y + selection.height / 2;
                        selection.setBounds(centerX - effectiveMin / 2, centerY - effectiveMin / 2, effectiveMin, effectiveMin);
                        clamp();
                        repaint();
                    }
                    anchorPoint = null;
                }
            };
            addMouseListener(ma);
            addMouseMotionListener(ma);
        }

        private void handleMove(Point p) {
            int dx = p.x - lastMovePoint.x;
            int dy = p.y - lastMovePoint.y;
            selection.translate(dx, dy);
            clamp();
            lastMovePoint = p;
        }

        private void handleResize(Point p) {
            if (anchorPoint == null) return;

            int dx = p.x - anchorPoint.x;
            int dy = p.y - anchorPoint.y;

            // Keep aspect ratio 1:1 (Square)
            int size = Math.max(Math.abs(dx), Math.abs(dy));

            // === UPDATED LOGIC HERE ===
            // Max size is the smallest dimension of the image (Height or Width)
            int maxPossibleSize = Math.min(image.getWidth(), image.getHeight());

            // Limit Size
            if (size > maxPossibleSize) size = maxPossibleSize;

            // Calculate new Top-Left based on drag direction
            int newX = (dx < 0) ? anchorPoint.x - size : anchorPoint.x;
            int newY = (dy < 0) ? anchorPoint.y - size : anchorPoint.y;

            selection.setBounds(newX, newY, size, size);
            clamp();
        }

        private void clamp() {
            // Prevent selection from going outside the image
            if (selection.x < 0) selection.x = 0;
            if (selection.y < 0) selection.y = 0;
            if (selection.x + selection.width > image.getWidth()) selection.x = image.getWidth() - selection.width;
            if (selection.y + selection.height > image.getHeight()) selection.y = image.getHeight() - selection.height;
        }

        public Rectangle getSelection() {
            return new Rectangle(selection);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            // 1. Draw Image
            g2.drawImage(image, 0, 0, null);

            // 2. Draw Dark Overlay with "Hole"
            Area overlay = new Area(new Rectangle(0, 0, getWidth(), getHeight()));
            overlay.subtract(new Area(selection));

            g2.setColor(new Color(0, 0, 0, 120)); // Transparent black
            g2.fill(overlay);

            // 3. Draw Selection Border
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRect(selection.x, selection.y, selection.width, selection.height);

            // 4. Draw Size Text
            g2.drawString(selection.width + "x" + selection.height, selection.x, selection.y - 5);
        }
    }
}