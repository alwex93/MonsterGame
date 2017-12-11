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
        position.setPasado(true);
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
        if (posicionDudosa.monstruoEnPosicion()){
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

    public boolean siguienteEsSalida(int movimiento){
        int fila = position.getFila(), columna = position.getColumna();
        switch (movimiento){
            case 1:
                fila += 1;
                break;
            case 2:
                columna += 1;
                break;
            case 3:
                columna -= 1;
                break;
            default:
                fila -= 1;
                break;

        }
        return entradaSalida.equals(new Posicion(fila, columna));
    }

    public boolean hayMounstruoATiro(){
        Posicion monstruo = getPoscicionMonstruo();

        return !celdasPeligrosas.isEmpty() &&
                (monstruo.getFila() == position.getFila() || monstruo.getColumna() == position.getColumna());
    }

    public void matarMonstruo(Posicion monstruoMuerto){
        Posicion monstruo = getPoscicionMonstruo();
        if(monstruo != null && monstruo.equals(monstruoMuerto)){
            celdasPeligrosas.remove(monstruo);
            guardarNuevaSegura(new Posicion(monstruo.getFila(), monstruo.getColumna()));
            guardarNuevaSegura(new Posicion(monstruo.getFila() - 1, monstruo.getColumna()));
            guardarNuevaSegura(new Posicion(monstruo.getFila() + 1, monstruo.getColumna()));
            guardarNuevaSegura(new Posicion(monstruo.getFila(), monstruo.getColumna() - 1));
            guardarNuevaSegura(new Posicion(monstruo.getFila(), monstruo.getColumna() + 1));
        }

    }

    public Posicion getPoscicionMonstruo(){
        if (celdasPeligrosas.isEmpty()){
            return null;
        } else {
            return celdasPeligrosas.get(0);
        }
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

    public Posicion getCeldaSegura(Posicion pos){
        return celdasSeguras.get(celdasSeguras.indexOf(pos));
    }

    public boolean colindantesSeguras(Posicion pos){
        return esSeguro(new Posicion(position.getFila() - 1, position.getColumna())) &&
                esSeguro(new Posicion(position.getFila() + 1, position.getColumna())) &&
                esSeguro(new Posicion(position.getFila(), position.getColumna() - 1)) &&
                esSeguro(new Posicion(position.getFila(), position.getColumna() + 1));
    }

    public void eliminarSegura(Posicion pos){
        if (celdasSeguras.contains(pos)){
            celdasSeguras.remove(pos);
        }
    }

}
