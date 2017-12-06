package Agente;

import java.util.Random;
import Agente.Acciones.Accion;
import Agente.Memoria.MemoriaCueva;
import Agente.Memoria.Posicion;

public class Razonamiento {

    Random gen;


    public Razonamiento(){
        gen = new Random(2017);
    }

    public Accion obtenerAccion(Percepciones percepciones, MemoriaCueva memoria){
        boolean hedor = percepciones.get(Percepciones.HEDOR);
        boolean brisa = percepciones.get(Percepciones.BRISA);
        boolean golpe = percepciones.get(Percepciones.GOLPE);
        boolean resplandor = percepciones.get(Percepciones.RESPLANDOR);
        boolean gemido = percepciones.get(Percepciones.GEMIDO);
        boolean muerto = percepciones.get(Percepciones.MUERTO);

        if(muerto){
            return Accion.MORIR;
        }else if (resplandor){
            return Accion.TOMAR;
        }else if (hedor || brisa){
            memoria.guardarPosicionDudosa(hedor, brisa);
            return Acciones.invertirMovimiento(memoria.recordarUltimoMovimiento());
        } else {
            memoria.guardarPosicionSegura();
            return randomDirecction(memoria);
        }
    }



    private Accion randomDirecction(MemoriaCueva memoria){
        int movimiento = getProp();
        Accion ultimoMovimiento = Acciones.invertirMovimiento(memoria.recordarUltimoMovimiento());

        while (movimiento == ultimoMovimiento.ordinal() || memoria.movimientoBloqueado(movimiento)){
            movimiento = getProp();
        }

        switch (movimiento){
            case 1:
                return Accion.SUR;
            case 2:
                return Accion.ESTE;
            case 3:
                return Accion.OESTE;
            default:
                return Accion.NORTE;
        }
    }

    private int getProp(){
        return gen.nextInt(4);
    }

    public Accion buscarCaminoSeguro(MemoriaCueva memoria){
        return Accion.values()[calcularPosicionAcercamiento(memoria, memoria.getEntradaSalida(), memoria.getPosition())];

    }

    private int calcularPosicionAcercamiento(MemoriaCueva memoria, Posicion salida, Posicion actual){
        Posicion siguiente = null;
        while (!memoria.esSeguro(siguiente)){
            if (salida.getFila() > actual.getFila()){
                siguiente =  new Posicion(actual.getFila() + 1, actual.getColumna());
            } else if (salida.getFila() < actual.getFila()){
                siguiente =  new Posicion(actual.getFila() - 1, actual.getColumna());
            } else if (salida.getColumna() < actual.getColumna()){
                siguiente =  new Posicion(actual.getFila(), actual.getColumna() - 1);
            } else if (salida.getColumna() > actual.getColumna()){
                siguiente =  new Posicion(actual.getFila(), actual.getColumna() + 1);
            }
        }
        return actual.contiguo(siguiente);

    }


}
