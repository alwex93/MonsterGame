package tablero;

public class Celda {
    public static final int HEDOR_POS = 0, BRISA_POS = 1, RESPLANDOR_POS = 2;
    public static final int HEDOR = 0b1, BRISA = 0b10, RESPLANDOR = 0b100;
    private boolean[] percepciones;
    private char elemento;


    public Celda(){
        elemento = Elementos.SEGURO;
        percepciones = new boolean[]{false, false, false};
    }

    public void setPerception(int perception){
        percepciones[HEDOR_POS] |= getPerception(perception, HEDOR);
        percepciones[BRISA_POS] |= getPerception(perception, BRISA);
        percepciones[RESPLANDOR_POS] |= getPerception(perception, RESPLANDOR);
    }

    private boolean getPerception(int perception, int mask){
        int flag = (perception & mask);
        return (perception & mask) == mask;
    }

    public void setElemento(char elemento){
        this.elemento = elemento;
        switch (elemento){
            case Elementos.MONSTRUO:
                percepciones[HEDOR_POS] = true;
                break;
            case Elementos.PRECIPICIO:
                percepciones[BRISA_POS] = true;
                break;
            case Elementos.TESORO:
                percepciones[RESPLANDOR_POS] = true;
                break;
        }
    }

    public boolean isCell(int pos){
        return percepciones[pos];
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
