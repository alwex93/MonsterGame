/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2_si;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author yolan
 */
public class PanelTablero extends JPanel implements MouseListener {

    private int nposiciones = 4;
    private int midaLado = 780;
    private Casilla[][] casillas;
    private PanelMenu menu;

    public PanelTablero(int n, PanelMenu m)  {
        nposiciones = n;
        casillas = new Casilla[nposiciones][nposiciones];
        menu = m;

        this.addMouseListener(this);

    }

    public void cambiarCursor() {
        String path = null;
        switch (menu.getEtapa()) {
            case 2:
                path = Casilla.pathMonstruo;
                break;
            case 3:
                path = Casilla.pathTesoro;
                break;
            case 4:
                path = Casilla.pathPrecipicio;
                break;
        }
        if (menu.getEtapa() != 5) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = new ImageIcon(path).getImage();
            Point hotspot = new Point(0, 0);
            Cursor cursor = toolkit.createCustomCursor(image, hotspot, "");
            setCursor(cursor);
        } else {
            setCursor(null);
        }
    }

    public void setNposiciones(int nposiciones) {
        this.nposiciones = nposiciones;
    }

    private void initCasillas() {
        casillas = new Casilla[nposiciones][nposiciones];
        for (int fila = 0; fila < nposiciones; fila++) {
            for (int columna = 0; columna < nposiciones; columna++) {
                casillas[fila][columna] = new Casilla(midaLado / nposiciones, fila, columna);
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (menu.getEtapa() == 1) {
            initCasillas();
        }

        for (int fila = 0; fila < nposiciones; fila++) {
            for (int columna = 0; columna < nposiciones; columna++) {
                this.casillas[fila][columna].paintComponent(g);
            }
        }

        //g2d.setStroke(new BasicStroke(3.0f));
        //g2d.drawRect(2, 2, midaLado-5, midaLado-5);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Si la partida no ha acabadoC ni ha sido cancelada.
        if (menu.getEtapa() != 1 && menu.getEtapa() != 5) {
            /* Obtener las coordenas del tablero donde se ha presionado 
             * el boton izquiero del ratón. */
            if (e.getButton() == MouseEvent.BUTTON1) {
                int x = e.getX();
                int y = e.getY();
                Casilla c = getCasilla(x, y);

                if (!(c.getColumna() == 0 && c.getFila() == casillas.length - 1)) {
                    // Miramos si la casilla no está ocupada por una ficha.
                    switch (menu.getEtapa()) {
                        case 2:
                            if (c.getOcupacion().equals(Ocupacion.MONSTRUO)) {
                                c.setOcupacion(Ocupacion.VACIA);
                                menu.decrementarMonstruos();
                                menu.repaint();
                            } else if (c.getOcupacion().equals(Ocupacion.VACIA)) {
                                c.setOcupacion(Ocupacion.MONSTRUO);
                                menu.incrementarMonstruos();
                                menu.repaint();
                            }
                            break;
                        case 3:
                            if (c.getOcupacion().equals(Ocupacion.TESORO)) {
                                c.setOcupacion(Ocupacion.VACIA);
                                menu.decrementarTesoros();
                                menu.repaint();
                            } else if (c.getOcupacion().equals(Ocupacion.VACIA)) {
                                EliminarTesoro();
                                c.setOcupacion(Ocupacion.TESORO);
                                menu.incrementarTesoros();
                                menu.repaint();
                            }
                            break;
                        case 4:
                            if (c.getOcupacion().equals(Ocupacion.PRECIPICIO)) {
                                c.setOcupacion(Ocupacion.VACIA);
                                menu.decrementarPrecipicios();
                                menu.repaint();
                            } else if (c.getOcupacion().equals(Ocupacion.VACIA)) {
                                c.setOcupacion(Ocupacion.PRECIPICIO);
                                menu.incrementarPrecipicios();
                                menu.repaint();
                            }
                            break;
                    }
                }
            }
        }
    }

    public void EliminarTesoro() {
        for (int fila = 0; fila < nposiciones; fila++) {
            for (int columna = 0; columna < nposiciones; columna++) {
                if (casillas[fila][columna].getOcupacion() == Ocupacion.TESORO) {
                    casillas[fila][columna].setOcupacion(Ocupacion.VACIA);
                    menu.decrementarTesoros();
                    break;
                }
            }
        }
    }

    /**
     * Devuelve la casilla que se encuentra en las coordenadas pasadas como
     * parámetro (x,y).
     */
    public Casilla getCasilla(int x, int y) {
        return casillas[(int) y / (midaLado / nposiciones)][(int) x / (midaLado / nposiciones)];
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public Casilla[][] getCasillas() {
        return casillas;
    }

    public Ocupacion getOcupacion(int f, int c) {
        return casillas[f][c].getOcupacion();
    }

    public void marcarBrisaHedorResp() {
        for (int fila = 0; fila < nposiciones; fila++) {
            for (int columna = 0; columna < nposiciones; columna++) {
                if (casillas[fila][columna].getOcupacion() == Ocupacion.TESORO) {
                    marcarAdyacentes(fila, columna, Ocupacion.TESORO);
                }
                if (casillas[fila][columna].getOcupacion() == Ocupacion.MONSTRUO) {
                    marcarAdyacentes(fila, columna, Ocupacion.MONSTRUO);
                }
                if (casillas[fila][columna].getOcupacion() == Ocupacion.PRECIPICIO) {
                    marcarAdyacentes(fila, columna, Ocupacion.PRECIPICIO);
                }
                
            }
        }
    }

    private void marcarAdyacentes(int fila, int columna, Ocupacion ocupacion) {
        if (fila != 0) {
            switch (ocupacion) {
                case TESORO:
                    casillas[fila - 1][columna].marcarResplandor();
                    break;
                case MONSTRUO:
                    casillas[fila - 1][columna].marcarHedor();
                    break;
                case PRECIPICIO:
                    casillas[fila - 1][columna].marcarBrisa();
            }
        }

        if (fila != nposiciones - 1) {
            switch (ocupacion) {
                case TESORO:
                    casillas[fila + 1][columna].marcarResplandor();
                    break;
                case MONSTRUO:
                    casillas[fila + 1][columna].marcarHedor();
                    break;
                case PRECIPICIO:
                    casillas[fila + 1][columna].marcarBrisa();
            }        
        }

        if (columna != 0) {
            switch (ocupacion) {
                case TESORO:
                    casillas[fila][columna - 1].marcarResplandor();
                    break;
                case MONSTRUO:
                    casillas[fila][columna - 1].marcarHedor();
                    break;
                case PRECIPICIO:
                    casillas[fila][columna - 1].marcarBrisa();
            }        
        }

        if (columna != nposiciones - 1) {
            switch (ocupacion) {
                case TESORO:
                    casillas[fila][columna + 1].marcarResplandor();
                    break;
                case MONSTRUO:
                    casillas[fila][columna + 1].marcarHedor();
                    break;
                case PRECIPICIO:
                    casillas[fila][columna + 1].marcarBrisa();
            }             
        }
    }

}
