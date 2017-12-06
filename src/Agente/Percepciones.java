package Agente;

import tablero.Celda;
import tablero.Elementos;

public class Percepciones {

    private boolean[] percepciones;
    public static int HEDOR = 0, BRISA = 1, RESPLANDOR = 2, GOLPE = 3, GEMIDO = 4, MUERTO = 5;
    public static final int HEDOR_MASK = 0b1, BRISA_MASK = 0b10;

    public Percepciones(){
        percepciones = new boolean[]{false, false, false, false, false, false};
    }

    public void checkCell(Celda habitacion){
        setPercepcion(MUERTO, muere(habitacion.getElemento()));
        setPercepcion(HEDOR, habitacion.isCell(Celda.HEDOR_POS));
        setPercepcion(BRISA, habitacion.isCell(Celda.BRISA_POS));
        setPercepcion(RESPLANDOR, habitacion.isCell(Celda.RESPLANDOR_POS));
    }

    public void checkMovimiento(boolean golpe){
        setPercepcion(GOLPE, golpe);
    }

    public void muereMounstruo(boolean gemido){
        setPercepcion(GEMIDO, gemido);
    }

    private void setPercepcion(int percepcion, boolean estado){
        percepciones[percepcion] = estado;
    }

    public boolean get(int perception){
        return percepciones[perception];
    }

    private boolean muere(char elemento){
        return elemento == Elementos.MONSTRUO || elemento == Elementos.PRECIPICIO;
    }
}
