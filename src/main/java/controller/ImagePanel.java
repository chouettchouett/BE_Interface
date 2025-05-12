package controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ImagePanel extends javax.swing.JPanel {
    private Image image;
    private ImagePopUp popUp = null;

    public Image getImage() {
        return image;
    }

    public ImagePanel(){
        addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e) && image != null) {
                if (popUp != null) popUp.dispose();

                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(ImagePanel.this);
                int w = image.getWidth(ImagePanel.this);
                int h = image.getHeight(ImagePanel.this);
                if (w <= 0 || h <= 0) { w = 100; h = 100; }

                Image scaled = image.getScaledInstance(w * 2, h * 2, Image.SCALE_SMOOTH);
                popUp = new ImagePopUp(parentFrame, scaled);
            }
        }
    });
    }


    public void setImage(String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        image = icon.getImage();
        repaint(); // Force la redessination quand une image est chargée
    }
    
    public void setImageV2(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath); 
        image = icon.getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Redimensionne proprement à la taille actuelle du panel
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

