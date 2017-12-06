/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2_si;

import java.net.MalformedURLException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
        
/**
 *
 * @author yolan
 */
public class Ventana extends JFrame {
    public static int ancho = 1500;
    public static int alto = 840;
    private PanelInicio PInicio;
    private PanelMenu PMenu;
    private Simulador PSimulador;
    public Ventana ventana;
    
    public Ventana() {
        super("La cueva del mostruo");
        setSize(ancho, alto);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        ventana = this;
        visualizarPanelInicio(); //Visualizar el menú por pantalla
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Ventana().setVisible(true);
    }
    
    public void visualizarPanelInicio(){
        PInicio = new PanelInicio(this);
        this.add(PInicio);
    }
    
    public void visualizarMenu() throws MalformedURLException {
        PMenu = new PanelMenu(this);
        PInicio.setVisible(false);
        this.add(PMenu);
        PMenu.setVisible(true);
    }

    void Empezar(Casilla[][] tablero) throws InterruptedException {
        PSimulador = new Simulador(this, tablero);
        PMenu.setVisible(false);
        this.add(PSimulador);
        PSimulador.setVisible(true);
        InitSimulador();
    }
    
    public void InitSimulador(){
        SwingUtilities.invokeLater(new Runnable() {
                SimuladorListener timerListener = new SimuladorListener(PSimulador, ventana);
                 @Override
                 public void run() {
                    new Timer(200, timerListener).start();
                 }
              }); 
    }
}
