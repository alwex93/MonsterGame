/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2_si;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author yolan
 */
public class PanelTableroSimulador extends JPanel {

    private int midaLado = 780;
    private Casilla[][] casillasOcupadas;
    public Casilla[][] casillas;
    private Simulador simulador;
    
    

    public PanelTableroSimulador(Casilla[][] casillas, Simulador s) {
        this.casillasOcupadas = casillas;
        this.casillas = new Casilla[casillasOcupadas.length][casillasOcupadas.length];
        simulador = s;

        initCasillas();
        casillas[casillas.length-1][0].setOcupacion(Ocupacion.INICIO);
        
    }

    private void initCasillas() {
        casillas = new Casilla[casillasOcupadas.length][casillasOcupadas.length];
        for (int fila = 0; fila < casillasOcupadas.length; fila++) {
            for (int columna = 0; columna < casillasOcupadas.length; columna++) {
                casillas[fila][columna] = new Casilla(midaLado / casillasOcupadas.length, fila, columna);
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int fila = 0; fila < casillas.length; fila++) {
            for (int columna = 0; columna < casillas.length; columna++) {
                this.casillas[fila][columna].paintComponent(g);
            }
        }
    }

    public Casilla getCasilla(int fila, int columna) {
        return casillas[fila][columna];
    }

    public Casilla initPosicionAgente() {
        casillas[casillas.length - 1][0].marcarAstronauta();
        return casillas[casillas.length - 1][0];
    }

    public Casilla getCasillaAgente() {
        for (int fila = 0; fila < casillas.length; fila++) {
            for (int columna = 0; columna < casillas.length; columna++) {
                if (casillas[fila][columna].isAstronauta()) {
                    return casillas[fila][columna];
                }
            }
        }
        return null;
    }

    public Casilla getCasillaPercepcion() {
        for (int fila = 0; fila < casillas.length; fila++) {
            for (int columna = 0; columna < casillas.length; columna++) {
                if (casillas[fila][columna].isAstronauta()) {
                    return casillasOcupadas[fila][columna];
                }
            }
        }
        return null;
    }

    public void desmarcarPosibleTesoro() {
        for (int fila = 0; fila < casillas.length; fila++) {
            for (int columna = 0; columna < casillas.length; columna++) {
                if (casillas[fila][columna].isPosibleTesoro()) {
                    casillas[fila][columna].desmarcarPosibleTesoro();
                }
            }
        }
    }

    public void marcarPosiblesAdyacentes(int fila, int columna, Ocupacion ocupacion) {
        if (fila != 0) {
            switch (ocupacion) {
                case TESORO:
                    if (!casillas[fila - 1][columna].isNoTesoro() && casillas[fila - 1][columna].getOcupacion() != Ocupacion.TESORO) {
                        casillas[fila - 1][columna].marcarPosibleTesoro();
                    }
                    break;
                case MONSTRUO:
                    if (!casillas[fila - 1][columna].isNoMonstruo() && casillas[fila - 1][columna].getOcupacion() != Ocupacion.MONSTRUO) {
                        casillas[fila - 1][columna].marcarPosibleMonstruo();
                        if (fila - 1 == 0 && columna == 0) {
                        }
                    }
                    break;
                case PRECIPICIO:
                    if (!casillas[fila - 1][columna].isNoPrecipicio() && casillas[fila - 1][columna].getOcupacion() != Ocupacion.PRECIPICIO) {
                        casillas[fila - 1][columna].marcarPosiblePrecipicio();
                    }
                    break;
            }
        }

        if (fila != casillas.length - 1) {
            switch (ocupacion) {
                case TESORO:
                    if (!casillas[fila + 1][columna].isNoTesoro() && casillas[fila + 1][columna].getOcupacion() != Ocupacion.TESORO) {
                        casillas[fila + 1][columna].marcarPosibleTesoro();
                    }
                    break;
                case MONSTRUO:
                    if (!casillas[fila + 1][columna].isNoMonstruo() && casillas[fila + 1][columna].getOcupacion() != Ocupacion.MONSTRUO) {
                        casillas[fila + 1][columna].marcarPosibleMonstruo();
                    }
                    break;
                case PRECIPICIO:
                    if (!casillas[fila + 1][columna].isNoPrecipicio() && casillas[fila + 1][columna].getOcupacion() != Ocupacion.PRECIPICIO) {
                        casillas[fila + 1][columna].marcarPosiblePrecipicio();
                    }
                    break;
            }
        }

        if (columna != 0) {
            switch (ocupacion) {
                case TESORO:
                    if (!casillas[fila][columna - 1].isNoTesoro() && casillas[fila][columna - 1].getOcupacion() != Ocupacion.TESORO) {
                        casillas[fila][columna - 1].marcarPosibleTesoro();
                    }
                    break;
                case MONSTRUO:
                    if (!casillas[fila][columna - 1].isNoMonstruo() && casillas[fila][columna - 1].getOcupacion() != Ocupacion.MONSTRUO) {
                        casillas[fila][columna - 1].marcarPosibleMonstruo();
                    }
                    break;
                case PRECIPICIO:
                    if (!casillas[fila][columna - 1].isNoPrecipicio() && casillas[fila][columna - 1].getOcupacion() != Ocupacion.PRECIPICIO) {
                        casillas[fila][columna - 1].marcarPosiblePrecipicio();
                    }
                    break;
            }
        }

        if (columna != casillas.length - 1) {
            switch (ocupacion) {
                case TESORO:
                    if (!casillas[fila][columna + 1].isNoTesoro()) {
                        casillas[fila][columna + 1].marcarPosibleTesoro();
                    }
                    break;
                case MONSTRUO:
                    if (!casillas[fila][columna + 1].isNoMonstruo()) {
                        casillas[fila][columna + 1].marcarPosibleMonstruo();
                    }
                    break;
                case PRECIPICIO:
                    if (!casillas[fila][columna + 1].isNoPrecipicio()) {
                        casillas[fila][columna + 1].marcarPosiblePrecipicio();
                    }
                    break;
            }
        }
    }

    public void marcarSegurosAdyacentes(int fila, int columna) {

        if (!casillas[fila][columna].isBrisa()) {
            if (fila != 0) {
                casillas[fila - 1][columna].marcarNoPrecipicio();
                casillas[fila - 1][columna].desmarcarPosiblePrecipicio();
            }

            if (fila != casillas.length - 1) {
                casillas[fila + 1][columna].marcarNoPrecipicio();
                casillas[fila + 1][columna].desmarcarPosiblePrecipicio();
            }

            if (columna != 0) {
                casillas[fila][columna - 1].marcarNoPrecipicio();
                casillas[fila][columna - 1].desmarcarPosiblePrecipicio();
            }

            if (columna != casillas.length - 1) {
                casillas[fila][columna + 1].marcarNoPrecipicio();
                casillas[fila][columna + 1].desmarcarPosiblePrecipicio();
            }
        }
        if (!casillas[fila][columna].isHedor()) {
            if (fila != 0) {
                casillas[fila - 1][columna].marcarNoMonstruo();
                casillas[fila - 1][columna].desmarcarPosibleMonstruo();
            }

            if (fila != casillas.length - 1) {
                casillas[fila + 1][columna].marcarNoMonstruo();
                casillas[fila + 1][columna].desmarcarPosibleMonstruo();
            }

            if (columna != 0) {
                casillas[fila][columna - 1].marcarNoMonstruo();
                casillas[fila][columna - 1].desmarcarPosibleMonstruo();
            }

            if (columna != casillas.length - 1) {
                casillas[fila][columna + 1].marcarNoMonstruo();
                casillas[fila][columna + 1].desmarcarPosibleMonstruo();
            }
        }
        if (!casillas[fila][columna].isResplandor()) {
            if (fila != 0) {
                casillas[fila - 1][columna].marcarNoTesoro();
                casillas[fila - 1][columna].desmarcarPosibleTesoro();
            }

            if (fila != casillas.length - 1) {
                casillas[fila + 1][columna].marcarNoTesoro();
                casillas[fila + 1][columna].desmarcarPosibleTesoro();
            }

            if (columna != 0) {
                casillas[fila][columna - 1].marcarNoTesoro();
                casillas[fila][columna - 1].desmarcarPosibleTesoro();
            }

            if (columna != casillas.length - 1) {
                casillas[fila][columna + 1].marcarNoTesoro();
                casillas[fila][columna + 1].desmarcarPosibleTesoro();
            }
        }

    }

    Casilla norteSeguro(Casilla cAgente) {
        if (cAgente.getFila() != 0) {
            Casilla c = casillas[cAgente.getFila() - 1][cAgente.getColumna()];
            if (c.isNoMonstruo()
                    && c.isNoPrecipicio()) {
                return c;
            }
        }
        return null;
    }

    Casilla esteSeguro(Casilla cAgente) {
        if (cAgente.getFila() != casillas.length - 1) {
            Casilla c = casillas[cAgente.getFila() + 1][cAgente.getColumna()];
            if (c.isNoMonstruo()
                    && c.isNoPrecipicio()) {
                return c;
            }
        }
        return null;
    }

    Casilla surSeguro(Casilla cAgente) {
        if (cAgente.getColumna() != 0) {
            Casilla c = casillas[cAgente.getFila()][cAgente.getColumna() - 1];
            if (c.isNoMonstruo()
                    && c.isNoPrecipicio()) {
                return c;
            }
        }
        return null;
    }

    Casilla oesteSeguro(Casilla cAgente) {
        if (cAgente.getColumna() != casillas.length - 1) {
            Casilla c = casillas[cAgente.getFila()][cAgente.getColumna() + 1];
            if (c.isNoMonstruo()
                    && c.isNoPrecipicio()) {
                return c;
            }
        }
        return null;
    }

    public void irA(Casilla cAnterior, Casilla cNueva) {
        cAnterior.desmarcarAstronauta();
        cNueva.marcarAstronauta();
        cAnterior.marcarVisitado();
        cNueva.incVisitas();
    }

    boolean encontrarMonstruo(int fila, int columna) {
        Casilla cNorte = getCasillaNorte(fila, columna);
        Casilla cEste = getCasillaEste(fila, columna);
        Casilla cSur = getCasillaSur(fila, columna);
        Casilla cOeste = getCasillaOeste(fila, columna);
        if ((cNorte == null || (cNorte.isPosibleMonstruo()))
                && (cEste == null || (cEste.isNoMonstruo()))
                && (cSur == null || (cSur.isNoMonstruo()))
                && (cOeste == null || (cOeste.isNoMonstruo()))) {
            if (cNorte != null) {
                cNorte.setOcupacion(Ocupacion.MONSTRUO);
                cNorte.desmarcarPosibleMonstruo();
                cNorte.desmarcarPosibleTesoro();
                cNorte.desmarcarPosiblePrecipicio();
                return true;
            }
        }
        if ((cNorte == null || (cNorte.isNoMonstruo()))
                && (cEste == null || (cEste.isPosibleMonstruo()))
                && (cSur == null || (cSur.isNoMonstruo()))
                && (cOeste == null || (cOeste.isNoMonstruo()))) {
            if (cEste != null) {
                cEste.setOcupacion(Ocupacion.MONSTRUO);
                cEste.desmarcarPosibleTesoro();
                cEste.desmarcarPosibleMonstruo();
                cEste.desmarcarPosiblePrecipicio();
                return true;
            }
        }
        if ((cNorte == null || (cNorte.isNoMonstruo()))
                && (cEste == null || (cEste.isNoMonstruo()))
                && (cSur == null || (cSur.isPosibleMonstruo()))
                && (cOeste == null || (cOeste.isNoMonstruo()))) {
            if (cSur != null) {
                cSur.setOcupacion(Ocupacion.MONSTRUO);
                cSur.desmarcarPosibleMonstruo();
                cSur.desmarcarPosibleTesoro();
                cSur.desmarcarPosiblePrecipicio();
                return true;
            }
        }
        if ((cNorte == null || (cNorte.isNoMonstruo()))
                && (cEste == null || (cEste.isNoMonstruo()))
                && (cSur == null || (cSur.isNoMonstruo()))
                && (cOeste == null || (cOeste.isPosibleMonstruo()))) {
            if (cOeste != null) {
                cOeste.setOcupacion(Ocupacion.MONSTRUO);
                cOeste.desmarcarPosibleMonstruo();
                cOeste.desmarcarPosibleTesoro();
                cOeste.desmarcarPosiblePrecipicio();
                return true;
            }
        }
        return false;
    }

    Casilla getCasillaNorte(int fila, int columna) {
        if (fila != 0) {
            return casillas[fila - 1][columna];
        }

        return null;
    }

    Casilla getCasillaEste(int fila, int columna) {
        if (columna != casillas.length - 1) {
            return casillas[fila][columna + 1];
        }

        return null;
    }

    Casilla getCasillaSur(int fila, int columna) {
        if (fila != casillas.length - 1) {
            return casillas[fila + 1][columna];
        }

        return null;
    }

    Casilla getCasillaOeste(int fila, int columna) {
        if (columna != 0) {
            return casillas[fila][columna - 1];
        }

        return null;
    }

    Casilla encontrarTesoro(int fila, int columna) {
        Casilla cNorte = getCasillaNorte(fila, columna);
        Casilla cEste = getCasillaEste(fila, columna);
        Casilla cSur = getCasillaSur(fila, columna);
        Casilla cOeste = getCasillaOeste(fila, columna);
        if ((cNorte == null || (cNorte.isPosibleTesoro()))
                && (cEste == null || (cEste.isNoTesoro()))
                && (cSur == null || (cSur.isNoTesoro()))
                && (cOeste == null || (cOeste.isNoTesoro()))) {
            if (cNorte != null) {
                cNorte.setOcupacion(Ocupacion.TESORO);
                cNorte.desmarcarPosibleTesoro();
                cNorte.desmarcarPosibleMonstruo();
                cNorte.desmarcarPosiblePrecipicio();
                return cNorte;
            }
        }
        if ((cNorte == null || (cNorte.isNoTesoro()))
                && (cEste == null || (cEste.isPosibleTesoro()))
                && (cSur == null || (cSur.isNoTesoro()))
                && (cOeste == null || (cOeste.isNoTesoro()))) {
            if (cEste != null) {
                cEste.setOcupacion(Ocupacion.TESORO);
                cEste.desmarcarPosibleTesoro();
                cEste.desmarcarPosibleMonstruo();
                cEste.desmarcarPosiblePrecipicio();
                return cEste;
            }

        }
        if ((cNorte == null || (cNorte.isNoTesoro()))
                && (cEste == null || (cEste.isNoTesoro()))
                && (cSur == null || (cSur.isPosibleTesoro()))
                && (cOeste == null || (cOeste.isNoTesoro()))) {
            if (cSur != null) {
                cSur.setOcupacion(Ocupacion.TESORO);
                cSur.desmarcarPosibleTesoro();
                cSur.desmarcarPosibleMonstruo();
                cSur.desmarcarPosiblePrecipicio();
                return cSur;
            }
        }
        if ((cNorte == null || (cNorte.isNoTesoro()))
                && (cEste == null || (cEste.isNoTesoro()))
                && (cSur == null || (cSur.isNoTesoro()))
                && (cOeste == null || (cOeste.isPosibleTesoro()))) {
            if (cOeste != null) {
                cOeste.setOcupacion(Ocupacion.TESORO);
                cOeste.desmarcarPosibleTesoro();
                cOeste.desmarcarPosibleMonstruo();
                cOeste.desmarcarPosiblePrecipicio();
                return cOeste;
            }

        }
        return null;
    }

    boolean encontrarPrecipicio(int fila, int columna) {
        Casilla cNorte = getCasillaNorte(fila, columna);
        Casilla cEste = getCasillaEste(fila, columna);
        Casilla cSur = getCasillaSur(fila, columna);
        Casilla cOeste = getCasillaOeste(fila, columna);
        if ((cNorte == null || (cNorte.isPosiblePrecipicio()))
                && (cEste == null || (cEste.isNoPrecipicio()))
                && (cSur == null || (cSur.isNoPrecipicio()))
                && (cOeste == null || (cOeste.isNoPrecipicio()))) {
            if (cNorte != null) {
                cNorte.setOcupacion(Ocupacion.PRECIPICIO);
                cNorte.desmarcarPosiblePrecipicio();
                cNorte.desmarcarPosibleMonstruo();
                cNorte.desmarcarPosibleTesoro();
                return true;
            }

        }
        if ((cNorte == null || (cNorte.isNoPrecipicio()))
                && (cEste == null || (cEste.isPosiblePrecipicio()))
                && (cSur == null || (cSur.isNoPrecipicio()))
                && (cOeste == null || (cOeste.isNoPrecipicio()))) {
            if (cEste != null) {
                cEste.setOcupacion(Ocupacion.PRECIPICIO);
                cEste.desmarcarPosiblePrecipicio();
                cEste.desmarcarPosibleMonstruo();
                cEste.desmarcarPosibleTesoro();
                return true;
            }

        }
        if ((cNorte == null || (cNorte.isNoPrecipicio()))
                && (cEste == null || (cEste.isNoPrecipicio()))
                && (cSur == null || (cSur.isPosiblePrecipicio()))
                && (cOeste == null || (cOeste.isNoPrecipicio()))) {
            if (cSur != null) {
                cSur.setOcupacion(Ocupacion.PRECIPICIO);
                cSur.desmarcarPosiblePrecipicio();
                cSur.desmarcarPosibleMonstruo();
                cSur.desmarcarPosibleTesoro();
                return true;
            }

        }
        if ((cNorte == null || (cNorte.isNoPrecipicio()))
                && (cEste == null || (cEste.isNoPrecipicio()))
                && (cSur == null || (cSur.isNoPrecipicio()))
                && (cOeste == null || (cOeste.isPosiblePrecipicio()))) {
            if (cOeste != null) {
                cOeste.setOcupacion(Ocupacion.PRECIPICIO);
                cOeste.desmarcarPosiblePrecipicio();
                cOeste.desmarcarPosibleMonstruo();
                cOeste.desmarcarPosibleTesoro();
                return true;
            }

        }
        return false;
    }

    Casilla unicaCasillaSegura(int fila, int columna) {
        Casilla cNorte = getCasillaNorte(fila, columna);
        Casilla cEste = getCasillaEste(fila, columna);
        Casilla cSur = getCasillaSur(fila, columna);
        Casilla cOeste = getCasillaOeste(fila, columna);

        if ((cNorte == null || (cNorte.isNoMonstruo() && cNorte.isNoPrecipicio()))
                && (cEste == null || (cEste.isPosibleMonstruo() || cEste.isPosiblePrecipicio()))
                && (cSur == null || (cSur.isPosibleMonstruo() || cSur.isPosiblePrecipicio()))
                && (cOeste == null || (cOeste.isPosibleMonstruo() || cOeste.isPosiblePrecipicio()))) {
            if (cNorte != null) {
                return cNorte;
            }
        }
        if ((cEste == null || (cEste.isNoMonstruo() && cEste.isNoPrecipicio()))
                && (cNorte == null || (cNorte.isPosibleMonstruo() || cNorte.isPosiblePrecipicio()))
                && (cSur == null || (cSur.isPosibleMonstruo() || cSur.isPosiblePrecipicio()))
                && (cOeste == null || (cOeste.isPosibleMonstruo() || cOeste.isPosiblePrecipicio()))) {
            if (cEste != null) {
                return cEste;
            }
        }
        if ((cSur == null || (cSur.isNoMonstruo() && cSur.isNoPrecipicio()))
                && (cEste == null || (cEste.isPosibleMonstruo() || cEste.isPosiblePrecipicio()))
                && (cNorte == null || (cNorte.isPosibleMonstruo() || cNorte.isPosiblePrecipicio()))
                && (cOeste == null || (cOeste.isPosibleMonstruo() || cOeste.isPosiblePrecipicio()))) {
            if (cSur != null) {
                return cSur;
            }
        }
        if ((cOeste == null || (cOeste.isNoMonstruo() && cOeste.isNoPrecipicio()))
                && (cEste == null || (cEste.isPosibleMonstruo() || cEste.isPosiblePrecipicio()))
                && (cSur == null || (cSur.isPosibleMonstruo() || cSur.isPosiblePrecipicio()))
                && (cNorte == null || (cNorte.isPosibleMonstruo() || cNorte.isPosiblePrecipicio()))) {
            if (cOeste != null) {
                return cOeste;
            }
        }

        return null;
    }

    boolean tieneCasillasSeguras(int fila, int columna, int filaAct, int colAct) {
        Casilla cNorte = getCasillaNorte(fila, columna);
        Casilla cEste = getCasillaEste(fila, columna);
        Casilla cSur = getCasillaSur(fila, columna);
        Casilla cOeste = getCasillaOeste(fila, columna);

        // Se comprueba si la casilla es segura y si es la casilla donde está el agente
        if (cNorte != null && !(filaAct == cNorte.getFila() && columna == cNorte.getColumna())) {
            if (cNorte.isNoPrecipicio() && cNorte.isNoMonstruo()) {
                return true;
            }
        }
        if (cEste != null && !(filaAct == cEste.getFila() && columna == cEste.getColumna())) {
            if (cEste.isNoPrecipicio() && cEste.isNoMonstruo()) {
                return true;
            }
        }
        if (cSur != null && !(filaAct == cSur.getFila() && columna == cSur.getColumna())) {
            if (cSur.isNoPrecipicio() && cSur.isNoMonstruo()) {
                return true;
            }
        }
        if (cOeste != null && !(filaAct == cOeste.getFila() && columna == cOeste.getColumna())) {
            if (cOeste.isNoPrecipicio() && cOeste.isNoMonstruo()) {
                return true;
            }
        }

        return false;
    }

    void desmarcarTesoro(int fila, int columna) {
        casillas[fila][columna].setOcupacion(Ocupacion.VACIA);
    }

    void desmarcarResplandores() {
        for (int fila = 0; fila < casillas.length; fila++) {
            for (int columna = 0; columna < casillas.length; columna++) {
                if (casillas[fila][columna].isResplandor()) {
                    casillas[fila][columna].desmarcarResplandor();
                }
            }
        }
    }
}
