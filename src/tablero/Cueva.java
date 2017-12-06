package tablero;

public class Cueva {
    private Celda[][] cueva;
    private int tesoros;

    public Cueva(int n){
        cueva = new Celda[n][n];
        for (int fila = 0; fila < n; fila++){
            for (int columna = 0; columna < n; columna++){
                cueva[fila][columna] = new Celda();
            }
        }
        tesoros = 0;
    }

    public void setMounstruo(int fila, int columna){
        cueva[fila][columna].setElemento(Elementos.MONSTRUO);
        extenderEfecto(fila, columna, Celda.HEDOR);
    }

    public void setTesoro(int fila, int columna){
        cueva[fila][columna].setElemento(Elementos.TESORO);
        tesoros += 1;
    }

    public void setAgente(int fila, int columna){
        cueva[fila][columna].setElemento(Elementos.AGENTE);
    }

    public void setPrecipicio(int fila, int columna){
        cueva[fila][columna].setElemento(Elementos.PRECIPICIO);
        extenderEfecto(fila, columna, Celda.BRISA);
    }

    private void extenderEfecto(int fila, int columna, int efecto){
        if (fila > 0) {
            cueva[fila - 1][columna].setPerception(efecto);
        }
        if (columna < (cueva.length - 1)) {
            cueva[fila + 1][columna].setPerception(efecto);
        }
        if (columna > 0) {
            cueva[fila][columna - 1].setPerception(efecto);
        }
        if (columna < (cueva.length - 1)) {
            cueva[fila][columna + 1].setPerception(efecto);
        }
    }

    public boolean moveAgente(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino){
        int borderPosLimitSup = cueva.length - 1, preBorderPosLimitSup = cueva.length - 2;
        int borderPosLimitInf = 0, preBorderPosLimitInf = 1;

        cueva[filaOrigen][columnaOrigen].setElemento(Elementos.SEGURO);
        cueva[filaDestino][columnaDestino].setElemento(Elementos.AGENTE);
        System.out.println(this.toString());
        return (filaOrigen == preBorderPosLimitSup && filaDestino == borderPosLimitSup) ||
                (columnaOrigen == preBorderPosLimitSup && columnaDestino == borderPosLimitSup) ||
                (filaOrigen == preBorderPosLimitInf && filaDestino == borderPosLimitInf) ||
                (columnaOrigen == preBorderPosLimitInf && columnaDestino == borderPosLimitInf);
    }

    public boolean disparo(int fila, int columna){
        return true;//TO DO
    }

    public Celda getCelda(int fila, int columna){
        return cueva[fila][columna];
    }

    private String ENTER = System.getProperty("line.separator");
    private String TAB = "  ";
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("Cueva:" + ENTER);

        for (Celda[] aCueva : cueva) {
            for (int columna = 0; columna < cueva.length; columna++) {
                buffer.append(aCueva[columna].getElemento()).append(aCueva[columna].getPercepciones()).append(TAB);
            }
            buffer.append(ENTER);
        }

        return buffer.toString();
    }

    public int getLenght(){
        return cueva.length;
    }

    public int getTesoros(){
        return tesoros;
    }

    public void takeTesoro(){
        tesoros -= 1;
    }
}
