package style;

/**
 *
 * @author ember
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;

public class CustomJTextField extends JTextField {
    private Shape shape;
    private String placeholder;
    private Color borderColor;
    private int radius;
    
    public CustomJTextField() {
        super();
        this.borderColor = getForeground(); // par défaut
        this.radius = 15;
        setOpaque(false);
    }

    public void setBorderColor(Color newColor) {
        this.borderColor = newColor;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setBorderRadius(int radius) {
        this.radius = radius;
    }

    public int getBorderRadius() {
        return radius;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String s) {
        placeholder = s;
    }

    // mettre le placeholder de l'input
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getBorderRadius(), getBorderRadius());
        super.paintComponent(g);

        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g1 = (Graphics2D) g;
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g1.setColor(Color.DARK_GRAY);
        g1.drawString(placeholder, getInsets().left, g1.getFontMetrics().getMaxAscent() + getInsets().top);
    }

    // installer les bords arrondis
    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(this.getBorderColor());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getBorderRadius(), getBorderRadius());
    }

    // éviter clic possible en dehors de l'arrondi
    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, getBorderRadius(),
                    getBorderRadius());
        }

        return shape.contains(x, y);
    }
}
