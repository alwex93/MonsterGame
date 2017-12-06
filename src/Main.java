import tablero.Cueva;
import Agente.Agente;

public class Main {

    public static void main(String[] args) {
        Cueva cueva = new Cueva(5);
        cueva.setAgente(4,0);
        cueva.setMounstruo(1,0);
        cueva.setPrecipicio(1,2);
        cueva.setTesoro(4,3);
        Agente nuevo = new Agente(cueva, 4, 0);
        System.out.println(cueva.toString());
        nuevo.run();

    }
}
