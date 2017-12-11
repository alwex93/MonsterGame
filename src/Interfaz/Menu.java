package Interfaz;

import Agente.Agente;
import tablero.Cueva;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import static java.awt.Frame.HAND_CURSOR;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
public class Menu extends JPanel implements Runnable {

    public Ventana ventana;
    private Cuadricula cuadricula;
    public JLabel Lposiciones;
    public JComboBox nposiciones;
    public JLabel Lmonstruos, Ltesoro, Lprecipicios, Letapa, Lconfiguracion;
    private JButton BSiguiente;
    public int Etapa = 1;
    public int nMonstruos = 0, nTesoros = 0, nPrecipicios = 0;
    private Thread hilorepintado;

    /*          1: Elegir nº de casillas
                2: Elegir posiciones monstruo
                3: Elegir posición tesoro
                4: Elegir posiciones precipicios
                5: Juego                                */

    public Menu(Ventana ventana) throws MalformedURLException {
        this.ventana = ventana;
        this.setLayout(null);
        this.cuadricula = new Cuadricula(4, this);
        this.add(this.cuadricula);
        initApariencia();
    }

    public void initApariencia() {
        if (Etapa == 1) {
            Lconfiguracion = new JLabel("Opciones", SwingConstants.CENTER);
            Lconfiguracion.setBounds(150 + 15 - 300 / 2, 100, 300, 75);
            Lconfiguracion.setFont(new Font("Haettenschweiler", 0, 30));
            Lconfiguracion.setForeground(Color.YELLOW);
            this.add(Lconfiguracion);

            Letapa = new JLabel("Paso 1 de 4", SwingConstants.CENTER);
            Letapa.setBounds(150 + 15 - 300 / 2, 100, 300, 190);
            Letapa.setFont(new Font("Haettenschweiler", 0, 15));
            Letapa.setForeground(Color.WHITE);
            Letapa.setBackground(new Color(79, 169, 166));
            this.add(Letapa);

            Lposiciones = new JLabel("<html> Nº de casillas <br> por lado </html>", SwingConstants.CENTER);
            this.add(Lposiciones);
            Lposiciones.setBounds(56, 300, 220, 50);
            Lposiciones.setFont(new Font("Gadugi", 0, 15));
            Lposiciones.setForeground(Color.yellow);

            nposiciones = new JComboBox();
            for (int i = 4; i <= 50; i++) {
                nposiciones.addItem(Integer.toString(i));
            }
            this.add(nposiciones);
            nposiciones.setBounds(150 + 15 - 200 / 2, 350, 200, 40);
            nposiciones.setFont(new Font("Calibri", 0, 15));
            nposiciones.setForeground(Color.BLACK);
            // Accion a realizar cuando el JComboBox cambia de item seleccionado.
            nposiciones.addActionListener(e -> {
                cuadricula.setN(Integer.parseInt(nposiciones.getSelectedItem().toString()));
                repaint();
            });

            BSiguiente = new JButton(">");
            //BSiguiente.setBackground(Color.);
            BSiguiente.setForeground(Color.yellow);
            BSiguiente.setBorder(null);
            BSiguiente.setFont(new Font("Calibri", 0, 30));
            BSiguiente.setBounds(150 - 45 + 15, 450, 90, 90);
            BSiguiente.setCursor(new Cursor(HAND_CURSOR));
            BSiguiente.addActionListener(new CuadroSimulacion(this));
            this.add(BSiguiente);
        }

        if (Etapa == 2) {
            Letapa.setText("Paso 2 de 4");

            Lmonstruos = new JLabel("<html> Nº de monstruos <br> y posiciones </html>", SwingConstants.CENTER);
            this.add(Lmonstruos);
            Lmonstruos.setBounds(56, 300, 220, 100);
            Lmonstruos.setFont(new Font("Gadugi", 0, 15));
            Lmonstruos.setForeground(Color.YELLOW);
        }

        if (Etapa == 3) {
            Letapa.setText("Paso 3 de 4");

            Ltesoro = new JLabel("<html> Posición del tesoro </html>", SwingConstants.CENTER);
            this.add(Ltesoro);
            Ltesoro.setBounds(56, 300, 220, 100);
            Ltesoro.setFont(new Font("Gadugi", 0, 15));
            Ltesoro.setForeground(Color.YELLOW);
        }

        if (Etapa == 4) {
            Letapa.setText("Paso 4 de 4");

            Lprecipicios = new JLabel("<html> Nº precipicios <br> y posiciones </html>", SwingConstants.CENTER);
            this.add(Lprecipicios);
            Lprecipicios.setBounds(56, 300, 220, 100);
            Lprecipicios.setFont(new Font("Gadugi", 0, 15));
            Lprecipicios.setForeground(Color.YELLOW);
        }

        this.cuadricula.setBounds(450, 15, 780, 780);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(2.0f));

        // OPCIONES
        g.setColor(Color.decode("#6D0BDE"));
        g2d.fillRect(15, 15, 300, 600);
        g.setColor(Color.decode("#6D0BDE"));
        g2d.drawRect(15, 15, 300, 600);
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

    public void initSimulador(Observer ob){
        cuadricula.goBuscadores(ob);
    }

    public Cuadricula getCuadricula() {
        return cuadricula;
    }

    @Override
    public void addNotify(){
        super.addNotify();
        hilorepintado = new Thread(this);
        hilorepintado.start();
    }

    @Override
    public void run() {
        boolean a = true;
        while (a){
            repintar();
            espera();
        }
    }

    private void espera(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}