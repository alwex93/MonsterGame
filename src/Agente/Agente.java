package Agente;

import Agente.Acciones.Accion;
import Agente.Memoria.MemoriaCueva;
import Agente.Memoria.Posicion;
import Agente.Memoria.Transicion;
import tablero.Cueva;

public class Agente implements Runnable{

    private Cueva cueva;
    private Posicion entradaSalida;
    private Posicion posicionCueva;
    private MemoriaCueva memoria;
    private Razonamiento cerebro;
    private Percepciones percepciones;
    private boolean vida, sinTesoros;
    private int tesoros;
    private int municion;

    public Agente(Cueva cueva, int fila, int columna){
        this.cueva = cueva;
        entradaSalida = new Posicion(fila, columna);
        memoria = new MemoriaCueva(entradaSalida, cueva.getLenght());
        cerebro = new Razonamiento();
        percepciones = new Percepciones();
        vida = true;
        sinTesoros = false;
        tesoros = 0;
        municion = cueva.getMunicion();
    }

    @Override
    public void run() {
        posicionCueva = new Posicion(entradaSalida.getFila() + memoria.getFila(),
                entradaSalida.getColumna() + memoria.getColumna());
        espera();
        while (vida && !sinTesoros){
            percepciones.checkCell(cueva.getCelda(posicionCueva.getFila(), posicionCueva.getColumna()));
            Accion accion = cerebro.obtenerAccion(percepciones, memoria);
            realizarAccion(accion);
            espera();
            if (cueva.getTesoros() == 0){
                sinTesoros = true;
            }
        }
        if (sinTesoros){
            while (!memoria.estaEnSalida()){
                realizarAccion(cerebro.buscarCaminoSeguro(memoria));
                espera();
            }
        }
    }

    private void espera(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void realizarAccion(Accion accion) {
        Posicion muertoMonstruo = null;
        boolean golpe = false;
        Posicion move = posicionCueva;
        Transicion transicion;
        int movColumna = 0, movFila = 0;
        switch (accion){
            case MORIR:
                vida = false;
                System.out.println("Muero");
                break;
            case TOMAR:
                tesoros += 1;
                cueva.takeTesoro(posicionCueva);
                System.out.println("Coger tesoro");
                break;
            case DISPARAR:
                if (municion > 0){
                    municion -= 1;
                }
                muertoMonstruo = cueva.disparo(memoria.getPoscicionMonstruo(), memoria.getPosition(), posicionCueva);
                System.out.println("Disparo");
                break;
            case NORTE:
                move = new Posicion(posicionCueva.getFila() - 1, posicionCueva.getColumna());
                transicion = new Transicion(posicionCueva.getFila(), posicionCueva.getColumna(), move.getFila(), move.getColumna());
                movFila -= 1;
                golpe = cueva.moveAgente(transicion);
                System.out.println("Voy al NORTE");
                break;
            case SUR:
                move = new Posicion(posicionCueva.getFila() + 1, posicionCueva.getColumna());
                transicion = new Transicion(posicionCueva.getFila(), posicionCueva.getColumna(), move.getFila(), move.getColumna());
                movFila += 1;
                golpe = cueva.moveAgente(transicion);
                System.out.println("Voy al SUR");
                break;
            case ESTE:
                move = new Posicion(posicionCueva.getFila(), posicionCueva.getColumna() + 1);
                transicion = new Transicion(posicionCueva.getFila(), posicionCueva.getColumna(), move.getFila(), move.getColumna());
                movColumna += 1;
                golpe = cueva.moveAgente(transicion);
                System.out.println("Voy al ESTE");
                break;
            case OESTE:
                move = new Posicion(posicionCueva.getFila(), posicionCueva.getColumna() - 1);
                transicion = new Transicion(posicionCueva.getFila(), posicionCueva.getColumna(), move.getFila(), move.getColumna());
                movColumna -= 1;
                golpe = cueva.moveAgente(transicion);
                System.out.println("Voy al OESTE");
                break;
        }
        if (muertoMonstruo != null){
            memoria.matarMonstruo(realARelativa(muertoMonstruo));
        }
        percepciones.checkMovimiento(golpe);
        memoria.memorizarUltimoMovimiento(golpe, accion, movFila, movColumna);
        posicionCueva = move;
    }

    public int getTesoros(){
        return tesoros;
    }

    private Posicion relativaAReal(Posicion relativa){
        return new Posicion(relativa.getFila() + entradaSalida.getFila(),
                relativa.getColumna() + entradaSalida.getColumna());
    }

    private Posicion realARelativa(Posicion real){
        return new Posicion(real.getFila() - entradaSalida.getFila(),
                real.getColumna() - entradaSalida.getColumna());
    }
}
