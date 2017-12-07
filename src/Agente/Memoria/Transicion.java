package Agente.Memoria;

public class Transicion {

    private Posicion origen, destino;

    public Transicion(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino){
        origen = new Posicion(filaOrigen, columnaOrigen);
        destino = new Posicion(filaDestino, columnaDestino);
    }

    public int getFilaOrigen(){
        return origen.getFila();
    }

    public int getColumnaOrigen(){
        return origen.getColumna();
    }

    public int getFilaDestino(){
        return destino.getFila();
    }

    public int getColumnaDestino(){
        return destino.getColumna();
    }
}
