package Agente.Memoria;

import Agente.Acciones;
import Agente.Acciones.Accion;
import Agente.Percepciones;

import java.util.ArrayList;

public class MemoriaCueva {

    private ArrayList<Posicion> celdasSeguras;
    private ArrayList<Posicion> celdasDudosas;
    private ArrayList<Posicion> celdasPeligrosas;
    private Posicion position;
    private Accion ultimoMovimiento;
    private boolean[] golpes;
    private Posicion entradaSalida;

    public MemoriaCueva(Posicion posicion, int len){
        celdasSeguras = new ArrayList<>();
        celdasDudosas = new ArrayList<>();
        celdasPeligrosas = new ArrayList<>();
        entradaSalida = new Posicion(0, 0);
        position = entradaSalida;
        celdasSeguras.add(position);
        golpes = new boolean[] {posicion.getFila() == 0, posicion.getFila() == len - 1,
                                posicion.getColumna() == len - 1, posicion.getColumna() == 0};
    }

    public void guardarPosicionSegura(){
        guardarNuevaSegura(position);
        guardarNuevaSegura(new Posicion(position.getFila() - 1, position.getColumna()));
        guardarNuevaSegura(new Posicion(position.getFila() + 1, position.getColumna()));
        guardarNuevaSegura(new Posicion(position.getFila(), position.getColumna() - 1));
        guardarNuevaSegura(new Posicion(position.getFila(), position.getColumna() + 1));
    }

    private void guardarNuevaSegura(Posicion pos){
        if(celdasDudosas.contains(pos)){
            celdasDudosas.remove(pos);
        }
        if (!celdasSeguras.contains(pos) && !celdasPeligrosas.contains(pos)){
            celdasSeguras.add(pos);
        }

    }

    public void guardarPosicionDudosa(boolean hedor, boolean brisa){
        int percepcion = 0;
        if (hedor){
            percepcion |= Percepciones.HEDOR_MASK;
        }
        if (brisa){
            percepcion |= Percepciones.BRISA_MASK;
        }
        guardarNoRepetidasDudosas(new Posicion(position.getFila() - 1, position.getColumna()), percepcion);
        guardarNoRepetidasDudosas(new Posicion(position.getFila() + 1, position.getColumna()), percepcion);
        guardarNoRepetidasDudosas(new Posicion(position.getFila(), position.getColumna() - 1), percepcion);
        guardarNoRepetidasDudosas(new Posicion(position.getFila(), position.getColumna() + 1), percepcion);

    }

    private void guardarNoRepetidasDudosas(Posicion colindante, int percepcion){
        if (!celdasSeguras.contains(colindante) && !celdasDudosas.contains(colindante)){
            colindante.detectarBrisa(position, percepcion);
            colindante.detectarHedor(position, percepcion);
            celdasDudosas.add(colindante);
        } else if (celdasDudosas.contains(colindante)){
            determinarCelda(celdasDudosas.get(celdasDudosas.indexOf(colindante)), percepcion);
        }
    }

    private void determinarCelda(Posicion posicionDudosa, int percepcion){
        posicionDudosa.detectarBrisa(position, percepcion);
        posicionDudosa.detectarHedor(position, percepcion);
        if (posicionDudosa.monstruoEnPosicion() || posicionDudosa.precipicioEnPosicion()){
            celdasDudosas.remove(posicionDudosa);
            celdasPeligrosas.add(posicionDudosa);
        }
    }

    public int getFila(){
        return position.getFila();
    }

    public int getColumna(){
        return position.getColumna();
    }

    public void memorizarUltimoMovimiento(boolean golpe, Accion movimiento, int fila, int columna){
        if (movimiento.ordinal() < 4){
            ultimoMovimiento = movimiento;
            golpes[movimiento.ordinal()] = golpe;
            if (golpes[Acciones.invertirMovimiento(movimiento).ordinal()]){
                golpes[Acciones.invertirMovimiento(movimiento).ordinal()] = false;
            }
            position = new Posicion(position.getFila() + fila, position.getColumna() + columna);
        }
    }

    public boolean movimientoBloqueado(int movimiento){
        return golpes[movimiento];
    }

    public Accion recordarUltimoMovimiento(){
        return ultimoMovimiento;
    }

    public Posicion getPosition(){
        return position;
    }

    public boolean estaEnSalida(){
        return this.position.equals(entradaSalida);
    }

    public Posicion getEntradaSalida(){
        return entradaSalida;
    }

    public boolean esSeguro(Posicion pos){
        return celdasSeguras.contains(pos);
    }

}
