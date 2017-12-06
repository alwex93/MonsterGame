package Agente;

public class Acciones {

    public enum Accion {
        NORTE, SUR, ESTE, OESTE, TOMAR, SALIR, DISPARAR, MORIR
    }

    public static Accion invertirMovimiento(Accion movimiento){
        if (movimiento == null) return Accion.MORIR;
        switch (movimiento){
            case NORTE:
                return Accion.SUR;
            case SUR:
                return Accion.NORTE;
            case ESTE:
                return Accion.OESTE;
            case OESTE:
                return Accion.ESTE;
            default:
                return Accion.MORIR;
        }
    }
}
