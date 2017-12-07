package Interfaz;

import Agente.Memoria.Transicion;
import tablero.Cueva;

import java.util.Observable;
import java.util.Observer;

public class InterfazControl implements Observer{
    private Menu ventana;

    public InterfazControl(Menu panel){
        ventana = panel;
    }

    @Override
    public void update(Observable o, Object arg) {
        Transicion transicion = (Transicion) arg;
        Cuadricula cueva = ventana.getCuadricula();
        cueva.actualizarCueva(transicion);
    }
}
