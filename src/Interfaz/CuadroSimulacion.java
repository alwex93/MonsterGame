package Interfaz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CuadroSimulacion implements ActionListener {

    private Menu menu;

    public CuadroSimulacion(Menu m){
        menu = m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            switch (menu.Etapa) {
                case 1:
                    menu.Lposiciones.setVisible(false);
                    menu.nposiciones.setVisible(false);
                    menu.Etapa = 2;
                    menu.initApariencia();
                    break;
                case 2:
                    if (menu.nMonstruos != 0) {
                        menu.Lmonstruos.setVisible(false);
                        menu.Etapa = 3;
                        menu.initApariencia();
                    }
                    break;
                case 3:
                    if (menu.nTesoros != 0) {
                        menu.Ltesoro.setVisible(false);
                        menu.Etapa = 4;
                        menu.initApariencia();
                    }
                    break;
                case 4:
                    if (menu.nPrecipicios != 0) {
                        menu.Lprecipicios.setVisible(false);
                        menu.Etapa = 5;
                    }
                    break;
                case 5:
                    menu.getCuadricula().setAgente(menu.getCuadricula().getN() - 1,0);
                    menu.repaint();
                    menu.ventana.Empezar();
                    break;
            }
    }
}
