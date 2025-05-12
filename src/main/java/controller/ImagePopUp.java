/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 *
 * @author choue
 */
public class ImagePopUp extends JWindow {

    public ImagePopUp(JFrame parent, Image originalImage) {
        super(parent);

        // Taille max autorisée = 80% de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.width * 0.90);
        int maxHeight = (int) (screenSize.height * 0.90);
                //Dimension à voir 

        int imgWidth = originalImage.getWidth(this);
        int imgHeight = originalImage.getHeight(this);

        if (imgWidth <= 0 || imgHeight <= 0) {
            imgWidth = 200;
            imgHeight = 200;
        }

        // Ratio pour ne pas dépasser l’écran
        double ratioW = (double) maxWidth / imgWidth;
        double ratioH = (double) maxHeight / imgHeight;
        double ratio = Math.min(3.5, Math.min(ratioW, ratioH)); // zoom max ×3.5, ou limité par écran
        //Dimension à voir

        int finalWidth = (int) (imgWidth * ratio);
        int finalHeight = (int) (imgHeight * ratio);
        Image scaledImage = originalImage.getScaledInstance(finalWidth, finalHeight, Image.SCALE_SMOOTH);

        JLabel label = new JLabel(new ImageIcon(scaledImage));
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(parent);
        setAlwaysOnTop(true);
        setVisible(true);

        // Ferme si on clique à côté
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent e && e.getID() == MouseEvent.MOUSE_PRESSED) {
                    Component clicked = SwingUtilities.getDeepestComponentAt(e.getComponent(), e.getX(), e.getY());
                    if (clicked == null || !SwingUtilities.isDescendingFrom(clicked, ImagePopUp.this)) {
                        dispose();
                        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }
}