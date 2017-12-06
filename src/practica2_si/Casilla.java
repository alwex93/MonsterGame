/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2_si;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author yolan
 */
class Casilla extends JPanel {

    private int mida;
    private int fila;
    private int columna;
    private Ocupacion ocupacion;
    private static final String PATH = "C:\\Users\\alejandro\\Desktop\\Alex\\Curso6\\sistemas inteligentes\\Practica2_SI\\img\\";
    public static final String pathTesoro = PATH + "tesoro.png";
    public final String pathAstronauta = PATH + "astronauta2.png";
    public static final String pathPrecipicio = PATH + "precipicio2.png";
    public static final String pathMonstruo = PATH + "monstruo2.png";
    public final String pathResplandor = PATH + "r2.png";
    public final String pathHedor = PATH + "h2.png";
    public final String pathBrisa = PATH + "b2.png";
    public final String pathPPrecipicio = PATH + "p2.png";
    public final String pathPTesoro = PATH + "t2.png";
    public final String pathPMonstruo = PATH + "m2.png";
    public final String pathSimbolo = PATH + "question.png";
    public final String pathVisitado = PATH + "v2.png";
    public final String pathOk = PATH + "ok.png";
    public final String pathFlecha = PATH + "flecha.png";
    private boolean brisa = false;
    private boolean hedor = false;
    private boolean resplandor = false;
    private boolean PosiblePrecipicio = false;
    private boolean PosibleMonstruo = false;
    private boolean PosibleTesoro = false;
    private boolean astronauta = false;
    private boolean visitado = false;
    private boolean noMonstruo = false;
    private boolean noPrecipicio = false;
    private boolean noTesoro = false;
    private boolean flecha = false;
    private int nVisitas = 0;

    public Casilla(int midaLado, int f, int c) {
        this.mida = midaLado;
        this.fila = f;
        this.columna = c;
        this.ocupacion = Ocupacion.VACIA;
    }

    public void paintComponent(Graphics g) {
        try {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            g2d.drawRect(mida * columna, mida * fila, mida, mida);

            int constanteX = (2 * mida) / 3;
            if (ocupacion != Ocupacion.VACIA) {
                BufferedImage imagenCasilla = null;
                switch (ocupacion) {
                    case MONSTRUO:
                        imagenCasilla = ImageIO.read(new File(pathMonstruo));
                        break;
                    case PRECIPICIO:
                        imagenCasilla = ImageIO.read(new File(pathPrecipicio));
                        break;
                    case TESORO:
                        imagenCasilla = ImageIO.read(new File(pathTesoro));
                        break;
                }

                g2d.drawImage(imagenCasilla, getCoordenadaX() + mida / 2 - constanteX / 2, getCoordenadaY() + +mida / 2 - constanteX / 2, constanteX, constanteX, null);
            }

            if (astronauta) {
                BufferedImage imagenCasilla = ImageIO.read(new File(pathAstronauta));
                g2d.drawImage(imagenCasilla, getCoordenadaX() + mida / 2 - constanteX / 2, getCoordenadaY() + +mida / 2 - constanteX / 2, constanteX, constanteX, null);
            }

            BufferedImage imagenL;
            if (resplandor) {
                imagenL = ImageIO.read(new File(pathResplandor));
                g2d.drawImage(imagenL, columna * mida + mida / 7, fila * mida + mida / 20, mida / 7, mida / 7, null);
            }
            if (hedor) {
                imagenL = ImageIO.read(new File(pathHedor));
                g2d.drawImage(imagenL, columna * mida + (3 * mida) / 7, fila * mida + mida / 20, mida / 6, mida / 7, null);
            }
            if (brisa) {
                imagenL = ImageIO.read(new File(pathBrisa));
                g2d.drawImage(imagenL, columna * mida + (5 * mida) / 7, fila * mida + mida / 20, mida / 7, mida / 7, null);
            }

            BufferedImage imagenP = ImageIO.read(new File(pathSimbolo));
            BufferedImage imagenL2 = null;
            if (PosibleTesoro) {
                imagenL2 = ImageIO.read(new File(pathPTesoro));
                g2d.drawImage(imagenL2, columna * mida + mida / 7, fila * mida + mida / 20, mida / 7, mida / 7, null);
                g2d.drawImage(imagenP, columna * mida + (2 * mida) / 7, fila * mida + mida / 20, mida / 7, mida / 7, null);
            }
            if (PosibleMonstruo) {
                imagenL2 = ImageIO.read(new File(pathPMonstruo));
                g2d.drawImage(imagenL2, columna * mida + (3 * mida) / 7, fila * mida + mida / 20, mida / 6, mida / 7, null);
                g2d.drawImage(imagenP, columna * mida + (4 * mida) / 7, fila * mida + mida / 20, mida / 7, mida / 7, null);
            }
            if (PosiblePrecipicio) {
                imagenL2 = ImageIO.read(new File(pathPPrecipicio));
                g2d.drawImage(imagenL2, columna * mida + (5 * mida) / 7, fila * mida + mida / 20, mida / 7, mida / 7, null);
                g2d.drawImage(imagenP, columna * mida + (6 * mida) / 7, fila * mida + mida / 20, mida / 7, mida / 7, null);
            }
            if (visitado == true) {
                imagenL2 = ImageIO.read(new File(pathVisitado));
                g2d.drawImage(imagenL2, columna * mida + (int)(5.5 * mida) / 7, fila * mida + mida - mida / 7 - mida / 20, mida / 6, mida / 7, null);
            }
            
            if (noMonstruo && noPrecipicio){
                imagenL2 = ImageIO.read(new File(pathOk));
                g2d.drawImage(imagenL2,  columna * mida + (int)(1.5 * mida) / 7 - mida/6, fila * mida + mida - mida / 7 - mida / 20, mida / 6, mida / 7, null);
            }
            if (flecha) {
                imagenL = ImageIO.read(new File(pathFlecha));
                g2d.drawImage(imagenL, getCoordenadaX() + mida / 2 - constanteX / 2, getCoordenadaY() + +mida / 2 - constanteX / 2, constanteX, constanteX, null);
            }

        } catch (IOException ex) {
            Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setOcupacion(Ocupacion ocupacion) {
        this.ocupacion = ocupacion;
    }

    private int getCoordenadaX() {
        return columna * mida;
    }

    private int getCoordenadaY() {
        return fila * mida;
    }

    boolean getOcupada() {
        return (ocupacion != Ocupacion.VACIA);
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public Ocupacion getOcupacion() {
        return ocupacion;
    }

    public void marcarBrisa() {
        brisa = true;
    }
    
    public void marcarPosiblePrecipicio() {
        PosiblePrecipicio = true;
    }
    
    public void desmarcarPosiblePrecipicio() {
        PosiblePrecipicio = false;
    }
    
    public void marcarHedor() {
        hedor = true;
    }
    
    public void marcarPosibleMonstruo() {
        PosibleMonstruo = true;
    }
    
    public void desmarcarPosibleMonstruo() {
        PosibleMonstruo = false;
    }

    public void marcarResplandor() {
        resplandor = true;
    }
    
    public void desmarcarResplandor(){
        resplandor = false;
    }
    
    public void marcarPosibleTesoro() {
        PosibleTesoro = true;
    }

    public void desmarcarPosibleTesoro() {
        PosibleTesoro = false;
    }

    public void marcarAstronauta() {
        astronauta = true;
    }

    public void desmarcarAstronauta() {
        astronauta = false;
    }
    
    public void marcarVisitado() {
        visitado = true;
    }

    public boolean isBrisa() {
        return brisa;
    }

    public boolean isHedor() {
        return hedor;
    }

    public boolean isResplandor() {
        return resplandor;
    }

    public boolean isPosiblePrecipicio() {
        return PosiblePrecipicio;
    }

    public boolean isPosibleMonstruo() {
        return PosibleMonstruo;
    }

    public boolean isPosibleTesoro() {
        return PosibleTesoro;
    }

    public boolean isAstronauta() {
        return astronauta;
    }

    public boolean isVisitado() {
        return visitado;
    }
    
    public boolean isNoMonstruo(){
        return noMonstruo;
    }

    public boolean isNoPrecipicio(){
        return noPrecipicio;
    }

    public boolean isNoTesoro(){
        return noTesoro;
    }
    
    public void marcarNoMonstruo() {
        noMonstruo = true;
    }
    
    public void marcarNoPrecipicio() {
        noPrecipicio = true;
    }
    
    public void marcarNoTesoro() {
        noTesoro = true;
    }

    boolean isSeguro() {
        return noMonstruo && noPrecipicio;
    }
    
    void marcarFlecha(){
        flecha = true;
    }
    
    void desmarcarFlecha(){
        flecha = false;
    }
    
    int getVisitas() {
        return nVisitas;
    }
    
    void incVisitas(){
        nVisitas++;
    }

}
