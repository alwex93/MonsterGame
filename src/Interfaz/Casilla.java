package Interfaz;
import tablero.Celda;
import tablero.Elementos;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Casilla extends JPanel {

    private int lado;
    private int fila;
    private int columna;
    private Celda celda;
    public static final String PATH_TESORO = "img/tesoro.png";
    public static final String PATH_AGENTE = "img/agente.png";
    public static final String PATH_PRECIPICIO = "img/precipicio.png";
    public static final String PATH_MONSTRUO = "img/monstruo.png";
    public static final String PATH_RESPLANDOR = "img/r.png";
    public static final String PATH_HEDOR = "img/h.png";
    public static final String PATH_BRISA = "img/b.png";
    public static final String PATH_QUESTION = "img/question.png";
    public static final String PATH_SEGURA = "img/ok.png";
    public static final String PATH_VISITADA = "img/ok.png";
    public static final String PATH_FLECHA = "img/flecha.png";
    private boolean brisa = false;
    private boolean hedor = false;
    private boolean resplandor = false;
    private boolean PosiblePrecipicio = false;
    private boolean PosibleMonstruo = false;
    private boolean agente = false;
    private boolean visitada = false;
    private boolean noMonstruo = false;
    private boolean noPrecipicio = false;
    private boolean flecha = false;
    private boolean vacia = true;

    public Casilla(int lado, int fila, int columna, Celda celda) {
        this.lado = lado;
        this.fila = fila;
        this.columna = columna;
        this.celda = celda;
    }

    public void pintarElemento (Graphics2D g2d, String path) {
        try {
            int constanteX = (2 * this.lado) / 3;
            BufferedImage imagenCasilla = ImageIO.read(new File(path));
            g2d.drawImage(imagenCasilla, getCoordenadaX() + this.lado / 2 - constanteX / 2, getCoordenadaY() + +this.lado / 2 - constanteX / 2, constanteX, constanteX, null);
        } catch (IOException ex) {
            Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void pintarPercepcion (Graphics2D g2d, String path) {
        try {
            BufferedImage imagenCasilla = ImageIO.read(new File(path));
            g2d.drawImage(imagenCasilla, columna * this.lado + (3 * this.lado) / 7, fila * this.lado + this.lado / 20, this.lado / 6, this.lado / 7, null);
        } catch (IOException ex) {
            Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private int getCoordenadaX() {
        return this.columna * this.lado;
    }

    private int getCoordenadaY() {
        return this.fila * this.lado;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.decode("#6D0BDE"));
        g2d.drawRect(this.lado * columna, this.lado * fila, this.lado, this.lado);

        if (!this.vacia) {
            this.pintarElemento(g2d, this.celda.getPathImg());
        }
        if (agente) this.pintarElemento(g2d, PATH_AGENTE);
        if (resplandor) {
            this.pintarElemento(g2d, PATH_TESORO);
            this.pintarPercepcion(g2d, PATH_RESPLANDOR);
        }
        if (hedor) this.pintarPercepcion(g2d, PATH_HEDOR);
        if (brisa) this.pintarPercepcion(g2d, PATH_BRISA);
        if (PosiblePrecipicio || PosibleMonstruo) this.pintarPercepcion(g2d, PATH_QUESTION);
        if (flecha) this.pintarElemento(g2d, PATH_FLECHA);
        if (visitada) this.pintarPercepcion(g2d, PATH_VISITADA);
        if (noMonstruo && noPrecipicio) this.pintarPercepcion(g2d, PATH_SEGURA);
    }

    public void setElemento (char elemento) {
        agente = Elementos.AGENTE == elemento;
        this.celda.setElemento(elemento);
    }
    public int getFila() {
        return fila;
    }
    public int getColumna() {
        return columna;
    }
    public char getElemento() {
        return this.celda.getElemento();
    }
    public void marcarBrisa (boolean value) {
        brisa = value;
    }
    public boolean getBrisa (){
        return this.brisa;
    }
    public void marcarPosiblePrecipicio (boolean value) {
        PosiblePrecipicio = value;
    }
    public boolean getPosiblePrecipicio () {
        return this.PosiblePrecipicio;
    }
    public void marcarHedor (boolean value) {
        hedor = value;
    }
    public boolean getHedor () {
        return this.hedor;
    }
    public void marcarPosibleMonstruo (boolean value) {
        PosibleMonstruo = value;
    }
    public void marcarResplandor (boolean value) {
        resplandor = value;
    }
    public boolean getResplandor () {
        return this.resplandor;
    }
    public void marcarAgente (boolean value) {
        agente = value;
    }
    public boolean getAgente () {
        return this.agente;
    }
    public void marcarVisitado (boolean value) {
        visitada = value;
    }
    public boolean getNoMonstruo (){
        return noMonstruo;
    }
    public boolean getNoPrecipicio (){
        return noPrecipicio;
    }
    public void marcarNoMonstruo (boolean value) {
        noMonstruo = value;
    }
    public void marcarNoPrecipicio (boolean value) {
        noPrecipicio = value;
    }
    boolean getSeguro () {
        return noMonstruo && noPrecipicio;
    }
    public void marcarFlecha (boolean value){
        flecha = value;
    }
    public void marcarVacia (boolean value) {
        this.vacia = value;
    }
    public boolean getVacia (){
        return this.vacia;
    }

}
