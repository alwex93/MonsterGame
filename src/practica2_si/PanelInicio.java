/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2_si;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Frame.HAND_CURSOR;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author yolan
 */
class PanelInicio extends JPanel implements ActionListener {
    
    private Boton BStart;
    private static final String path = "img/portada.jpg";
    private BufferedImage portada;
    private Ventana ventana;
    private JLabel LWelcome;
    
    public PanelInicio(Ventana v){
        ventana = v;
        this.setLayout(null);
        initApariencia();
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BStart){
            try {
                ventana.visualizarMenu();
            } catch (MalformedURLException ex) {
                Logger.getLogger(PanelInicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void initApariencia() {
        // Iniciar botón de inicio
        BStart = new Boton("Start", Color.LIGHT_GRAY, Color.GRAY);                
        this.add(BStart);
        BStart.setBounds((Ventana.ancho - 200)/2, (int) (0.6 * Ventana.alto) - 200 / 2, 180, 60);
        BStart.addActionListener(this);
        BStart.setCursor(new Cursor(HAND_CURSOR));
        
        LWelcome = new JLabel("<html>La cueva del mostruo</html>", SwingConstants.CENTER);
        this.add(LWelcome);   
        LWelcome.setBounds((Ventana.ancho - 800)/2, (int)(0.4*Ventana.alto) - 250 / 2, 800, 200);
        LWelcome.setFont(new Font("Calibri",0,75));
        LWelcome.setForeground(Color.decode("#2B193D"));
        
        try {
            portada = ImageIO.read(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(PanelInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(portada, 0, 0, null);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ventana.getWidth(), ventana.getHeight());
    };
}
