package Interfaz;
import Agente.Agente;
import Agente.Memoria.Transicion;
import tablero.Celda;
import tablero.Cueva;
import tablero.Elementos;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;

public class Cuadricula extends JPanel implements MouseListener {

    private int n = 4;
    private int lado = 600;
    private Casilla[][] casillas;
    private Cueva cueva;
    private Menu menu;
    private ArrayList<Agente> buscadores;

    public Cuadricula (int n, Menu menu)  {
        buscadores = new ArrayList<Agente>();
        this.n = n;
        this.casillas = new Casilla[this.n][this.n];
        this.menu = menu;
        this.addMouseListener(this);
        initCuadricula();
    }

    public void initCuadricula() {
        casillas = new Casilla[this.n][this.n];
        cueva = new Cueva(n);
        for (int fila = 0; fila < this.n; fila++) {
            for (int columna = 0; columna < this.n; columna++) {
                casillas[fila][columna] = new Casilla(this.lado/this.n, fila, columna, cueva.getCelda(fila, columna));
            }
        }
    }

    public void setN (int nposiciones) {
        this.n = nposiciones;
        initCuadricula();
    }

    public Casilla getCasilla (int x, int y) {
        return casillas[(int) y / (this.lado / this.n)][(int) x / (this.lado / this.n)];
    }

    public Casilla[][] getCasillas() {
        return casillas;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int fila = 0; fila < this.n; fila++) {
            for (int columna = 0; columna < this.n; columna++) {
                this.casillas[fila][columna].paintComponent(g2d);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }

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
        // Si la partida no ha acabado ni ha sido cancelada.
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
                            if (c.getElemento() == (Elementos.MONSTRUO)) {
                                cueva.quitarElemento(c.getFila(), c.getColumna());
                                menu.decrementarMonstruos();
                                menu.repaint();
                            } else if (c.getVacia()) {
                                cueva.setMounstruo(c.getFila(), c.getColumna());
                                c.marcarVacia(false);
                                c.setElemento(Elementos.MONSTRUO);
                                menu.incrementarMonstruos();
                                menu.repaint();
                            }
                            break;
                        case 3:
                            if (c.getElemento() == Elementos.TESORO) {
                                cueva.quitarElemento(c.getFila(), c.getColumna());
                                menu.decrementarTesoros();
                                menu.repaint();
                            } else if (c.getVacia()) {
                                cueva.setTesoro(c.getFila(), c.getColumna());
                                c.marcarVacia(false);
                                c.setElemento(Elementos.TESORO);
                                menu.incrementarTesoros();
                                menu.repaint();
                            }
                            break;
                        case 4:
                            if (c.getElemento() == Elementos.PRECIPICIO) {
                                cueva.quitarElemento(c.getFila(), c.getColumna());
                                menu.decrementarPrecipicios();
                                menu.repaint();
                            } else if (c.getVacia()) {
                                cueva.setPrecipicio(c.getFila(), c.getColumna());
                                c.marcarVacia(false);
                                c.setElemento(Elementos.PRECIPICIO);
                                menu.incrementarPrecipicios();
                                menu.repaint();
                            }
                            break;
                    }
                }
            }
        }
        // Aquí se van imprimiendo el número de elementos que vas eligiendo.
        System.out.println("Monstruos :" + menu.getnMonstruos());
        System.out.println("Tesoros :" + menu.getnTesoros());
        System.out.println("Precipicios :" + menu.getnPrecipicios());
    }

    public char getElemento(int f, int c) {
        return casillas[f][c].getElemento();
    }

    public void marcarBrisaHedorResp() {
        for (int fila = 0; fila < this.n; fila++) {
            for (int columna = 0; columna < this.n; columna++) {
                if (casillas[fila][columna].getElemento() == Elementos.TESORO) {
                    marcarAdyacentes(fila, columna, Elementos.TESORO);
                }
                if (casillas[fila][columna].getElemento() == Elementos.MONSTRUO) {
                    marcarAdyacentes(fila, columna, Elementos.MONSTRUO);
                }
                if (casillas[fila][columna].getElemento() == Elementos.PRECIPICIO) {
                    marcarAdyacentes(fila, columna, Elementos.PRECIPICIO);
                }

            }
        }
    }

    private void marcarAdyacentes(int fila, int columna, char elemento) {
        if (fila != 0) {
            switch (elemento) {
                case Elementos.TESORO:
                    casillas[fila - 1][columna].marcarResplandor(true);
                    break;
                case Elementos.MONSTRUO:
                    casillas[fila - 1][columna].marcarHedor(true);
                    break;
                case Elementos.PRECIPICIO:
                    casillas[fila - 1][columna].marcarBrisa(true);
            }
        }

        if (fila != this.n - 1) {
            switch (elemento) {
                case Elementos.TESORO:
                    casillas[fila + 1][columna].marcarResplandor(true);
                    break;
                case Elementos.MONSTRUO:
                    casillas[fila + 1][columna].marcarHedor(true);
                    break;
                case Elementos.PRECIPICIO:
                    casillas[fila + 1][columna].marcarBrisa(true);
            }
        }

        if (columna != 0) {
            switch (elemento) {
                case Elementos.TESORO:
                    casillas[fila][columna - 1].marcarResplandor(true);
                    break;
                case Elementos.MONSTRUO:
                    casillas[fila][columna - 1].marcarHedor(true);
                    break;
                case Elementos.PRECIPICIO:
                    casillas[fila][columna - 1].marcarBrisa(true);
            }
        }

        if (columna != this.n - 1) {
            switch (elemento) {
                case Elementos.TESORO:
                    casillas[fila][columna + 1].marcarResplandor(true);
                    break;
                case Elementos.MONSTRUO:
                    casillas[fila][columna + 1].marcarHedor(true);
                    break;
                case Elementos.PRECIPICIO:
                    casillas[fila][columna + 1].marcarBrisa(true);
            }
        }
    }

    public void setAgente(int fila, int columna){
        buscadores.add(new Agente(cueva, fila, columna));
        cueva.setAgente(fila, columna);
        System.out.println(cueva.toString());
        casillas[fila][columna].marcarAgente(true);
        casillas[fila][columna].setElemento(Elementos.AGENTE);
        menu.repaint();
    }

    public void goBuscadores(Observer ob){
        ExecutorService executor = Executors.newCachedThreadPool();

        cueva.addObserver(ob);
        for(Agente ag : buscadores){
            executor.submit(ag);
        }

    }

    public void actualizarCueva(Transicion transicion){
        Casilla origen = casillas[transicion.getFilaOrigen()][transicion.getColumnaOrigen()];
        origen.setElemento(Elementos.SEGURO);
        Casilla destino = casillas[transicion.getFilaDestino()][transicion.getColumnaDestino()];
        destino.setElemento(Elementos.AGENTE);
    }
}
