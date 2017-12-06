package Agente.Memoria;

import Agente.Percepciones;

public class Posicion {
    private int fila;
    private int columna;
    private boolean[] monstruo;
    private boolean[] precipicio;
    private int pasos;

    public Posicion(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
        monstruo = new boolean[]{false, false, false, false};
        precipicio = new boolean[]{false, false, false, false};
        pasos = 0;
    }

    public void detectarBrisa(Posicion celda, int percepcion){
        int pos = contiguo(celda);
        if (pos != - 1 && detectarPeligro(percepcion, Percepciones.BRISA_MASK)){
            precipicio[pos] = true;
        }
    }

    public void detectarHedor(Posicion celda, int percepcion){
        int pos = contiguo(celda);
        if (pos != - 1 && detectarPeligro(percepcion, Percepciones.HEDOR_MASK)){
            monstruo[pos] = true;
        }
    }

    public boolean monstruoEnPosicion(){
        return monstruo[0] && monstruo[1] && monstruo[2] && monstruo[3];
    }

    public boolean precipicioEnPosicion(){
        return precipicio[0] && precipicio[1] && precipicio[2] && precipicio[3];
    }

    private boolean detectarPeligro(int percepcion, int mascara){
        return (percepcion & mascara) != 0;
    }

    public int getFila(){
        return fila;
    }

    public int getColumna(){
        return columna;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Posicion posicion = (Posicion) o;

        if (fila != posicion.fila) return false;
        return columna == posicion.columna;
    }
    //Norte = 0, Sur = 1, Este = 2, Oeste = 3, No contiguo = -1
    public int contiguo(Posicion pos) {
        if (this.fila == pos.fila){
            if ((this.columna - 1) == pos.columna){
                return 3;
            } else if ((this.columna + 1) == pos.columna){
                return 2;
            }
        } else if (this.columna == pos.columna){
            if ((this.fila - 1) == pos.fila){
                return 0;
            } else if ((this.fila + 1) == pos.fila){
                return 1;
            }
        }
        return -1;
    }

}
