/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2_si;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author yolan
 */
public class Simulador extends JPanel {

    private BufferedImage fondo;
    private static final String path = "img/portada.jpg";
    private Ventana ventana;
    private PanelTableroSimulador tablero;
    private final int tiempo = 400; // Para entrar y salir
    public boolean simulando = false;

    public Simulador(Ventana v, Casilla[][] casillas) throws InterruptedException {
        ventana = v;
        tablero = new PanelTableroSimulador(casillas, this);
        this.add(tablero);
        tablero.setBounds(1500 / 2 - 780 / 2, 15, 790, 790);
        this.setLayout(null);

        try {
            fondo = ImageIO.read(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(PanelInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void esperar() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(tiempo);
                } catch (Exception e) {
                }
            }
        }.run();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(fondo, 0, 0, null);
    }

    public PanelTableroSimulador getTablero() {
        return tablero;
    }
}

class SimuladorListener implements ActionListener {

    private Simulador simulador;
    private Ventana ventana;
    private boolean inicio = true;
    private boolean monstruoEncontrado = false;
    private boolean precipicioEncontrado = false;
    private boolean tieneTesoro = false;
    private boolean matarMonstruo = false;
    private Casilla cMonstruo = null;
    private int fase = 1;
    private boolean marcadasPercepciones = false;

    public SimuladorListener(Simulador simulador, Ventana ventana) {
        this.simulador = simulador;
        this.ventana = ventana;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!simulador.simulando) {
            simulador.simulando = true;
            if (inicio) {
                Casilla c = simulador.getTablero().initPosicionAgente();
                c.marcarNoMonstruo();
                c.marcarNoPrecipicio();
                c.marcarNoTesoro();
                c.incVisitas();
                inicio = false;
                simulador.repaint();
                simulador.esperar();
            } else if (tieneTesoro) {
                Casilla cAgente = simulador.getTablero().getCasillaAgente();
                simulador.getTablero().desmarcarTesoro(cAgente.getFila(), cAgente.getColumna());
                simulador.getTablero().desmarcarResplandores();
                simulador.repaint();
                simulador.esperar();
                ((Timer) e.getSource()).stop();
            } else {
                Casilla cAgente = simulador.getTablero().getCasillaAgente();
                Casilla cPercepcion = simulador.getTablero().getCasillaPercepcion();

                // Comprobamos si hemos encontrado el tesoro
                if (cPercepcion.getOcupacion() == Ocupacion.TESORO) {
                    cAgente.setOcupacion(Ocupacion.TESORO);
                    simulador.getTablero().desmarcarPosibleTesoro();
                    simulador.repaint();
                    simulador.esperar();
                    tieneTesoro = true; // Obtenemos las percepciones
                } else if (cPercepcion.isBrisa() && !cAgente.isBrisa()) {
                        cAgente.marcarBrisa();
                        simulador.getTablero().marcarPosiblesAdyacentes(cAgente.getFila(), cAgente.getColumna(), Ocupacion.PRECIPICIO);
                        simulador.repaint();
                        simulador.esperar();
                    } else if (cPercepcion.isHedor() && !cAgente.isHedor()) {
                        cAgente.marcarHedor();
                        simulador.getTablero().marcarPosiblesAdyacentes(cAgente.getFila(), cAgente.getColumna(), Ocupacion.MONSTRUO);
                        simulador.repaint();
                        simulador.esperar();
                    } else if (cPercepcion.isResplandor() && !cAgente.isResplandor()) {
                        cAgente.marcarResplandor();
                        simulador.getTablero().marcarPosiblesAdyacentes(cAgente.getFila(), cAgente.getColumna(), Ocupacion.TESORO);
                        simulador.repaint();
                        simulador.esperar();
                    } else if (!marcadasPercepciones) {
                        simulador.getTablero().marcarSegurosAdyacentes(cAgente.getFila(), cAgente.getColumna());
                        marcadasPercepciones = true;
                        simulador.repaint();
                        simulador.esperar();// Puede que el monstruo solo pueda estar en un sitio, igual con el precipio y el tesoro
                    } else if (!monstruoEncontrado && cAgente.isHedor() && simulador.getTablero().encontrarMonstruo(cAgente.getFila(), cAgente.getColumna())) {
                            monstruoEncontrado = true;
                            simulador.repaint();
                            simulador.esperar();
                        } else if (!precipicioEncontrado && cAgente.isBrisa() && simulador.getTablero().encontrarPrecipicio(cAgente.getFila(), cAgente.getColumna())) {
                            simulador.repaint();
                            simulador.esperar();
                            precipicioEncontrado = true;
                        } else {
                            Casilla casillaTesoro = simulador.getTablero().encontrarTesoro(cAgente.getFila(), cAgente.getColumna());
                            if (casillaTesoro != null) {
                                simulador.repaint();
                                simulador.esperar();
                                System.out.println("1");
                                simulador.getTablero().irA(cAgente, casillaTesoro);
                                monstruoEncontrado = false;
                                precipicioEncontrado = false;
                                marcadasPercepciones = false;
                                simulador.repaint();
                                simulador.esperar();
                            } else {
                                // Si solo hay una casilla segura, ir a esa
                                simulador.repaint();
                                simulador.esperar();
                                Casilla unicaCasillaSegura = simulador.getTablero().unicaCasillaSegura(cAgente.getFila(), cAgente.getColumna());

                                if (unicaCasillaSegura != null) {
                                    System.out.println("2");
                                    simulador.getTablero().irA(cAgente, unicaCasillaSegura);
                                    monstruoEncontrado = false;
                                    precipicioEncontrado = false;
                                    marcadasPercepciones = false;
                                    simulador.repaint();
                                    simulador.esperar();
                                } else {
                                    Casilla cNorte = simulador.getTablero().norteSeguro(cAgente);
                                    if (cNorte != null && cNorte.isVisitado() && cNorte.isResplandor() && simulador.getTablero().tieneCasillasSeguras(cNorte.getFila(), cNorte.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                        System.out.println("3");
                                        simulador.getTablero().irA(cAgente, cNorte);
                                        monstruoEncontrado = false;
                                        precipicioEncontrado = false;
                                        marcadasPercepciones = false;
                                        simulador.repaint();
                                        simulador.esperar();
                                    } else {
                                        Casilla cEste = simulador.getTablero().esteSeguro(cAgente);
                                        if (cEste != null && cEste.isVisitado() && cEste.isResplandor() && simulador.getTablero().tieneCasillasSeguras(cEste.getFila(), cEste.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                            System.out.println("4");
                                            simulador.getTablero().irA(cAgente, cEste);
                                            marcadasPercepciones = false;
                                            monstruoEncontrado = false;
                                            precipicioEncontrado = false;
                                            simulador.repaint();
                                            simulador.esperar();
                                        } else {
                                            Casilla cSur = simulador.getTablero().surSeguro(cAgente);
                                            if (cSur != null && cSur.isVisitado() && cSur.isResplandor() && simulador.getTablero().tieneCasillasSeguras(cSur.getFila(), cSur.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                                System.out.println("5");
                                                simulador.getTablero().irA(cAgente, cSur);
                                                marcadasPercepciones = false;
                                                monstruoEncontrado = false;
                                                precipicioEncontrado = false;
                                                simulador.repaint();
                                                simulador.esperar();
                                            } else {
                                                Casilla cOeste = simulador.getTablero().oesteSeguro(cAgente);
                                                if (cOeste != null && cOeste.isVisitado() && cOeste.isResplandor() && simulador.getTablero().tieneCasillasSeguras(cOeste.getFila(), cOeste.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                                    System.out.println("6");
                                                    simulador.getTablero().irA(cAgente, cOeste);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                    // Si no ha sido visitado pero es una casilla segura
                                                } else if (cNorte != null && !cNorte.isVisitado() && cNorte.isNoMonstruo() && cNorte.isNoPrecipicio()) {
                                                    simulador.getTablero().irA(cAgente, cNorte);
                                                    marcadasPercepciones = false;
                                                    System.out.println("7");
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                } else if (cEste != null && !cEste.isVisitado() && cEste.isNoMonstruo() && cEste.isNoPrecipicio()) {
                                                    System.out.println("8");
                                                    simulador.getTablero().irA(cAgente, cEste);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                } else if (cSur != null && !cSur.isVisitado() && cSur.isNoMonstruo() && cSur.isNoPrecipicio()) {
                                                    System.out.println("9");
                                                    simulador.getTablero().irA(cAgente, cSur);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                } else if (cOeste != null && !cOeste.isVisitado() && cOeste.isNoMonstruo() && cOeste.isNoPrecipicio()) {
                                                    System.out.println("10");
                                                    simulador.getTablero().irA(cAgente, cOeste);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                    // Si no ha sido visitado y tiene casillas seguras que no son esta
                                                } else if (cNorte != null && !cNorte.isVisitado() && simulador.getTablero().tieneCasillasSeguras(cNorte.getFila(), cNorte.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                                    System.out.println("11");
                                                    simulador.getTablero().irA(cAgente, cNorte);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                } else if (cEste != null && !cEste.isVisitado() && simulador.getTablero().tieneCasillasSeguras(cEste.getFila(), cEste.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                                    System.out.println("12");
                                                    simulador.getTablero().irA(cAgente, cEste);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                } else if (cSur != null && !cSur.isVisitado() && simulador.getTablero().tieneCasillasSeguras(cSur.getFila(), cSur.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                                    System.out.println("13");
                                                    simulador.getTablero().irA(cAgente, cSur);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                } else if (cOeste != null && !cOeste.isVisitado() && simulador.getTablero().tieneCasillasSeguras(cOeste.getFila(), cOeste.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                                    System.out.println("14");
                                                    simulador.getTablero().irA(cAgente, cOeste);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                    // Si ha sido visitada y tiene casillas seguras que no son esta
                                                } else if (cNorte != null && simulador.getTablero().tieneCasillasSeguras(cNorte.getFila(), cNorte.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                                    System.out.println("15");
                                                    simulador.getTablero().irA(cAgente, cNorte);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                } else if (cEste != null && simulador.getTablero().tieneCasillasSeguras(cEste.getFila(), cEste.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                                    System.out.println("16");
                                                    simulador.getTablero().irA(cAgente, cEste);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                } else if (cSur != null && simulador.getTablero().tieneCasillasSeguras(cSur.getFila(), cSur.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                                    System.out.println("17");
                                                    simulador.getTablero().irA(cAgente, cSur);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                } else if (cOeste != null && simulador.getTablero().tieneCasillasSeguras(cOeste.getFila(), cOeste.getColumna(), cAgente.getFila(), cAgente.getColumna())) {
                                                    System.out.println("18");
                                                    simulador.getTablero().irA(cAgente, cOeste);
                                                    marcadasPercepciones = false;
                                                    monstruoEncontrado = false;
                                                    precipicioEncontrado = false;
                                                    simulador.repaint();
                                                    simulador.esperar();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

            }
            simulador.simulando = false;
        }
    }
