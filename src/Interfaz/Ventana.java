package Interfaz;

import tablero.Cueva;

import java.net.MalformedURLException;
import javax.swing.*;

public class Ventana extends JFrame{
    public static int ancho = 1080;
    public static int alto = 720;
    private static Menu PMenu;
    public Ventana ventana;

    public Ventana() throws MalformedURLException {
        super("La cueva del monstruo");
        setSize(ancho, alto);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.ventana = this;
        this.visualizarMenu();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException {
        new Ventana().setVisible(true);

    }

    public void visualizarMenu() throws MalformedURLException {
        PMenu = new Menu(this);
        this.add(PMenu);
        PMenu.setVisible(true);
    }

    void Empezar() {
        //PMenu.setVisible(false);
        //this.add(Simulador);
        //Simulador.setVisible(true);
        InterfazControl controler = new InterfazControl(PMenu);
        PMenu.initSimulador(controler);
        PMenu.repaint();
        PMenu.addNotify();
    }

}

