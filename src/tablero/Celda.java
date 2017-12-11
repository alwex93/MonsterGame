package tablero;

import Interfaz.Casilla;

public class Celda {
    public static final int HEDOR_POS = 0, BRISA_POS = 1, RESPLANDOR_POS = 2;
    public static final int HEDOR = 0b1, BRISA = 0b10, RESPLANDOR = 0b100;
    private boolean[] percepciones;
    private char elemento;
    private String pathImg;

    public Celda(){
        elemento = Elementos.SEGURO;
        percepciones = new boolean[]{false, false, false};
        this.pathImg = " ";
    }

    public void setPerception(int perception){
        percepciones[HEDOR_POS] |= getPerception(perception, HEDOR);
        percepciones[BRISA_POS] |= getPerception(perception, BRISA);
        percepciones[RESPLANDOR_POS] |= getPerception(perception, RESPLANDOR);
    }

    public void quitarPercepcion(int pos){
        percepciones[pos] = false;
    }

    private boolean getPerception(int perception, int mask){
        return (perception & mask) == mask;
    }

    public void setElemento(char elemento){
        this.elemento = elemento;
        switch (elemento){
            case Elementos.MONSTRUO:
                this.pathImg = Casilla.PATH_MONSTRUO;
                percepciones[HEDOR_POS] = true;
                break;
            case Elementos.PRECIPICIO:
                this.pathImg = Casilla.PATH_PRECIPICIO;
                percepciones[BRISA_POS] = true;
                break;
            case Elementos.TESORO:
                this.pathImg = Casilla.PATH_TESORO;
                percepciones[RESPLANDOR_POS] = true;
                break;
            case Elementos.AGENTE:
                this.pathImg = Casilla.PATH_AGENTE;
                break;
            case Elementos.CADAVER:
                pathImg = Casilla.PATH_MUERTO;
                break;
        }
    }

    public boolean isCell(int pos){
        return percepciones[pos];
    }
    public String getPathImg () {
        return this.pathImg;
    }
    public void setPathImg (String value) {
        this.pathImg = value;
    }
    public char getElemento(){
        return elemento;
    }

    public String getPercepciones(){
        StringBuilder print = new StringBuilder();
        if (percepciones[HEDOR_POS]){
            print.append("h");
        } else {
            print.append("x");
        }

        if (percepciones[BRISA_POS]){
            print.append("b");
        } else {
            print.append("x");
        }

        if (percepciones[RESPLANDOR_POS]){
            print.append("r");
        } else {
            print.append("x");
        }
        return print.toString();
    }
}
