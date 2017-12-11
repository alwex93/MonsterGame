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
        boolean resplandor = percepciones.get(Percepciones.RESPLANDOR);
        boolean muerto = percepciones.get(Percepciones.MUERTO);
        boolean monstruo = memoria.hayMounstruoATiro();
        boolean colindantesSeguras = memoria.colindantesSeguras(memoria.getPosition());

        if(muerto){
            return Accion.MORIR;
        }else if (resplandor){
            return Accion.TOMAR;
        } else if(monstruo){
            return Accion.DISPARAR;
        } else if (hedor && colindantesSeguras){
            memoria.guardarPosicionSegura();
            return randomDirecction(memoria);
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
        while (movimiento == ultimoMovimiento.ordinal() ||
                memoria.movimientoBloqueado(movimiento) || memoria.siguienteEsSalida(movimiento)){
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
        Posicion siguiente = new Posicion(Integer.MAX_VALUE, Integer.MAX_VALUE);
        boolean probado1 = false, probado2 = false, probado3 = false;
        while (!memoria.esSeguro(siguiente) && !siguiente.isPasado()){
            if (salida.getFila() > actual.getFila() && !probado1){
                siguiente =  new Posicion(actual.getFila() + 1, actual.getColumna());
                probado1 = true;
            } else if (salida.getFila() < actual.getFila() && !probado2){
                siguiente =  new Posicion(actual.getFila() - 1, actual.getColumna());
                probado2 = true;
            } else if (salida.getColumna() < actual.getColumna() && !probado3){
                siguiente =  new Posicion(actual.getFila(), actual.getColumna() - 1);
                probado3 = true;
            } else if (salida.getColumna() > actual.getColumna()){
                siguiente =  new Posicion(actual.getFila(), actual.getColumna() + 1);
            }
            if (memoria.esSeguro(siguiente)){
                siguiente = memoria.getCeldaSegura(siguiente);
            }
        }
        return actual.contiguo(siguiente);

    }


}
