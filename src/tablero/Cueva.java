package tablero;

import Agente.Memoria.Transicion;

import java.util.Observable;

public class Cueva extends Observable{
    private Celda[][] cueva;
    private int tesoros;

    public Cueva(int n){
        cueva = new Celda[n][n];
        for (int fila = 0; fila < n; fila++){
            for (int columna = 0; columna < n; columna++){
                cueva[fila][columna] = new Celda();
            }
        }
        tesoros = 0;
    }

    public void setMounstruo(int fila, int columna){
        cueva[fila][columna].setElemento(Elementos.MONSTRUO);
        extenderEfecto(fila, columna, Celda.HEDOR);
    }

    public void quitarElemento(int fila, int columna){
        if (cueva[fila][columna].getElemento() == Elementos.TESORO){
            tesoros -= 1;
        }
        cueva[fila][columna].setElemento(Elementos.SEGURO);
        quitarEfecto(fila, columna, Celda.HEDOR);
    }

    private void quitarEfecto(int fila, int columna, int efecto){
        if (fila > 0) {
            cueva[fila - 1][columna].quitarPercepcion(efecto);
        }
        if (columna < (cueva.length - 1)) {
            cueva[fila + 1][columna].quitarPercepcion(efecto);
        }
        if (columna > 0) {
            cueva[fila][columna - 1].quitarPercepcion(efecto);
        }
        if (columna < (cueva.length - 1)) {
            cueva[fila][columna + 1].quitarPercepcion(efecto);
        }
    }

    public void setTesoro(int fila, int columna){
        cueva[fila][columna].setElemento(Elementos.TESORO);
        tesoros += 1;
    }

    public void setAgente(int fila, int columna){
        cueva[fila][columna].setElemento(Elementos.AGENTE);
    }

    public void setPrecipicio(int fila, int columna){
        cueva[fila][columna].setElemento(Elementos.PRECIPICIO);
        extenderEfecto(fila, columna, Celda.BRISA);
    }

    private void extenderEfecto(int fila, int columna, int efecto){
        if (fila > 0) {
            cueva[fila - 1][columna].setPerception(efecto);
        }
        if (columna < (cueva.length - 1)) {
            cueva[fila + 1][columna].setPerception(efecto);
        }
        if (columna > 0) {
            cueva[fila][columna - 1].setPerception(efecto);
        }
        if (columna < (cueva.length - 1)) {
            cueva[fila][columna + 1].setPerception(efecto);
        }
    }

    public boolean moveAgente(Transicion transicion){
        int borderPosLimitSup = cueva.length - 1, preBorderPosLimitSup = cueva.length - 2;
        int borderPosLimitInf = 0, preBorderPosLimitInf = 1;


        cueva[transicion.getFilaOrigen()][transicion.getColumnaOrigen()].setElemento(Elementos.SEGURO);
        cueva[transicion.getFilaDestino()][transicion.getColumnaDestino()].setElemento(Elementos.AGENTE);
        System.out.println(this.toString());
        setChanged();
        notifyObservers(transicion);
        return (transicion.getFilaOrigen() == preBorderPosLimitSup && transicion.getFilaDestino() == borderPosLimitSup) ||
                (transicion.getColumnaOrigen() == preBorderPosLimitSup && transicion.getColumnaDestino() == borderPosLimitSup) ||
                (transicion.getFilaOrigen() == preBorderPosLimitInf && transicion.getFilaDestino() == borderPosLimitInf) ||
                (transicion.getColumnaOrigen() == preBorderPosLimitInf && transicion.getColumnaDestino() == borderPosLimitInf);
    }

    public boolean disparo(int fila, int columna){
        return true;//TODO
    }

    public Celda getCelda(int fila, int columna){
        return cueva[fila][columna];
    }

    private String ENTER = System.getProperty("line.separator");
    private String TAB = "  ";
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("Cueva:" + ENTER);

        for (Celda[] aCueva : cueva) {
            for (int columna = 0; columna < cueva.length; columna++) {
                buffer.append(aCueva[columna].getElemento()).append(aCueva[columna].getPercepciones()).append(TAB);
            }
            buffer.append(ENTER);
        }

        return buffer.toString();
    }

    public int getLenght(){
        return cueva.length;
    }

    public int getTesoros(){
        return tesoros;
    }

    public void takeTesoro(){
        tesoros -= 1;
    }
}
