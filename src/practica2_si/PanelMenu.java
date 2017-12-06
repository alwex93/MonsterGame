/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2_si;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author yolan
 */
class PanelMenu extends JPanel implements ActionListener {

    private Ventana ventana;
    private static final String path = "img/portada.jpg";
    private BufferedImage fondo;
    private PanelTablero tablero;
    private JLabel Lposiciones;
    private JComboBox nposiciones;
    private JLabel Lmonstruos, Ltesoro, Lprecipicios, Letapa;
    private JLabel Lconfiguracion;
    private String icono = "img/icon-next.png";
    private JButton BSiguiente;
    private int Etapa = 1;
    private int nMonstruos = 0, nTesoros = 0, nPrecipicios = 0;

    /*          1: Elegir nº de casillas
                2: Elegir posiciones monstruo
                3: Elegir posicio tesoro
                4: Elegir posiciones precipicios 
                5: Juego                                */

    public PanelMenu(Ventana ventana) throws MalformedURLException {
        this.ventana = ventana;
        this.setLayout(null);
        tablero = new PanelTablero(4, this);
        this.add(tablero);
        initApariencia();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BSiguiente) {
            switch (Etapa) {
                case 1:
                    this.Lposiciones.setVisible(false);
                    this.nposiciones.setVisible(false);
                    Etapa = 2;
                    tablero.cambiarCursor();
                    initApariencia();
                    break;
                case 2:
                    if (this.nMonstruos != 0) {
                        this.Lmonstruos.setVisible(false);
                        Etapa = 3;
                        tablero.cambiarCursor();
                        initApariencia();
                    }
                    break;
                case 3:
                    if (this.nTesoros != 0) {
                        this.Ltesoro.setVisible(false);
                        Etapa = 4;
                        tablero.cambiarCursor();
                        initApariencia();
                    }
                    break;
                case 4:
                    if (this.nPrecipicios != 0) {
                        this.Lprecipicios.setVisible(false);
                        Etapa = 5;
                        tablero.marcarBrisaHedorResp();
                        try {
                            ventana.Empezar(tablero.getCasillas());
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
            }
        }
    }

    private void initApariencia() {

        if (Etapa == 1) {
            Lconfiguracion = new JLabel("Configuración", SwingConstants.CENTER);
            Lconfiguracion.setBounds(150 + 15 - 300 / 2, 100, 300, 75);
            Lconfiguracion.setFont(new Font("Haettenschweiler", 0, 50));
            Lconfiguracion.setForeground(new Color(79, 169, 166));
            this.add(Lconfiguracion);

            Letapa = new JLabel("Paso 1 de 4", SwingConstants.CENTER);
            Letapa.setBounds(150 + 15 - 300 / 2, 100, 300, 190);
            Letapa.setFont(new Font("Haettenschweiler", 0, 35));
            Letapa.setForeground(Color.WHITE);
            Letapa.setBackground(new Color(79, 169, 166));
            this.add(Letapa);

            Lposiciones = new JLabel("Nº de casillas por lado", SwingConstants.CENTER);
            this.add(Lposiciones);
            Lposiciones.setBounds(56, 300, 220, 50);
            Lposiciones.setFont(new Font("Gadugi", 0, 22));
            Lposiciones.setForeground(Color.BLACK);

            nposiciones = new JComboBox();
            for (int i = 4; i <= 50; i++) {
                nposiciones.addItem(Integer.toString(i));
            }
            this.add(nposiciones);
            nposiciones.setBounds(150 + 15 - 200 / 2, 350, 200, 40);
            nposiciones.setFont(new Font("Calibri", 0, 20));
            nposiciones.setForeground(Color.BLACK);
            // Accion a realizar cuando el JComboBox cambia de item seleccionado.
            nposiciones.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tablero.setNposiciones(Integer.parseInt(nposiciones.getSelectedItem().toString()));
                    repaint();
                }
            });

            BSiguiente = new JButton(">");
            BSiguiente.setBackground(new Color(79, 169, 166));
            BSiguiente.setForeground(Color.white);
            BSiguiente.setBorder(null);
            BSiguiente.setFont(new Font("Calibri", 0, 100));
            BSiguiente.setBounds(150 - 45 + 15, 450, 90, 90);
            BSiguiente.setCursor(new Cursor(HAND_CURSOR));
            BSiguiente.addActionListener(this);
            this.add(BSiguiente);
        }

        if (Etapa == 2) {
            Letapa.setText("Paso 2 de 4");

            Lmonstruos = new JLabel("<html> Elegir el nº de monstruos <br> y sus posiciones </html>", SwingConstants.CENTER);
            this.add(Lmonstruos);
            Lmonstruos.setBounds(56, 300, 220, 100);
            Lmonstruos.setFont(new Font("Gadugi", 0, 22));
            Lmonstruos.setForeground(Color.BLACK);
        }

        if (Etapa == 3) {
            Letapa.setText("Paso 3 de 4");

            Ltesoro = new JLabel("<html> Elegir la posición <br> del tesoro </html>", SwingConstants.CENTER);
            this.add(Ltesoro);
            Ltesoro.setBounds(56, 300, 220, 100);
            Ltesoro.setFont(new Font("Gadugi", 0, 22));
            Ltesoro.setForeground(Color.BLACK);
        }

        if (Etapa == 4) {
            Letapa.setText("Paso 4 de 4");

            Lprecipicios = new JLabel("<html> Elegir el nº de precipicios <br> y sus posiciones </html>", SwingConstants.CENTER);
            this.add(Lprecipicios);
            Lprecipicios.setBounds(56, 300, 220, 100);
            Lprecipicios.setFont(new Font("Gadugi", 0, 22));
            Lprecipicios.setForeground(Color.BLACK);
        }

        try {
            fondo = ImageIO.read(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(PanelInicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        tablero.setBounds(450, 15, 780, 780);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(fondo, 0, 0, null);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(2.0f));

        // OPCIONES
        g.setColor(Color.decode("#E4DFDA"));
        g2d.fillRect(15, 15, 300, 770);
        g.setColor(Color.decode("#484D6D"));
        g2d.drawRect(15, 15, 300, 770);
    }

    public void repintar() {
        repaint();
    }

    public int getEtapa() {
        return Etapa;
    }

    public void incrementarMonstruos() {
        this.nMonstruos++;
    }

    public void incrementarTesoros() {
        this.nTesoros++;
    }

    public void decrementarTesoros() {
        this.nTesoros--;
    }

    public void decrementarMonstruos() {
        this.nMonstruos--;
    }

    public void decrementarPrecipicios() {
        this.nPrecipicios--;
    }

    public void incrementarPrecipicios() {
        this.nPrecipicios++;
    }

    public int getnMonstruos() {
        return nMonstruos;
    }

    public int getnTesoros() {
        return nTesoros;
    }

    public int getnPrecipicios() {
        return nPrecipicios;
    }
}
