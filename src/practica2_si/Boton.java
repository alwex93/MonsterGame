/*
 * Clase BotonPersonalizado
 * 
 * Hereda los atributos y métodos de la clase JButton.
 * Se cambia la apariencia del boton.
 */

package practica2_si;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import static java.awt.Frame.HAND_CURSOR;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

/**
 *
 * @author Yolanda Alemany
 */
public class Boton extends JButton {
    
    private Color colorFondo, colorPresionado;
    private Shape figura;
    
    // Constructor para crear un botón redondo.
    public Boton(String texto, Color colorFondo, Color colorPresionado) {
        super(texto);
        this.colorFondo = colorFondo;
        this.colorPresionado = colorPresionado;
        setContentAreaFilled(false);
        this.setOpaque(false);
        this.setFont(new Font("Britannic Bold",0,35));
        this.setForeground(Color.WHITE);
        this.setCursor(new Cursor(HAND_CURSOR));
        this.setContentAreaFilled(false);
    }

    /**
     * Dibuja el botón
     */
    @Override
    public void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(colorPresionado);
        } else {
            g.setColor(colorFondo);
        }
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 1, 1);
        super.paintComponent(g);
    }

    @Override
    public void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 1,1);
        
    }
    
    /**
     * Cambia la forma del botón.
     */
    public boolean constains(int x, int y) {
        figura = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 1, 1);
        return (figura.contains(x, y));
    }
}
