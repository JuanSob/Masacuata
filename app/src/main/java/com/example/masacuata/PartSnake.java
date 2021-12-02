package com.example.masacuata;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class PartSnake {

    //importa la clase Bitmap
    private Bitmap bm;

    //Se crean los atributos
    private  int x, y;

    /*Se el atributo tipo rect este se importa de una clase
     el cual representa un rectangulo, en do se coloca cada parte del
    * Rectagulo, con la parte del cuerpo de la masacuata, arriba, abajo, izquierda y derecha
    * */
    private Rect rCuerpo, rArriba, rAbajo, rIzquierda, rDerecha;

    //Metodo Constructor
    public PartSnake(Bitmap bm, int x, int y) {
        this.bm = bm;
        this.x = x;
        this.y = y;
    }

    //Inicio  Encapsulamiento
    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //Toma los valores que se le dan al mapa
    public Rect getrBody()
    {
        return new Rect(this.x, this.y, this.x+VistaJuego.sizeElementMap, this.y+VistaJuego.sizeElementMap);
    }

    public void setrBody(Rect rBody) {
        this.rCuerpo = rBody;
    }

    //Toma los valores que se le dan al mapa, pasa valores de altura
    public Rect getrTop() {
        return new Rect(this.x, this.y-10*Constante.PANTALLA_ALTURA/1920, this.x+VistaJuego.sizeElementMap, this.y);
    }

    public void setrTop(Rect rTop) {
        this.rArriba = rTop;
    }

    //Toma los valores que se le dan al mapa, pasa valores de altura
    public Rect getrBottom() {
        return new Rect(this.x, this.y+VistaJuego.sizeElementMap, this.x+VistaJuego.sizeElementMap, this.y+VistaJuego.sizeElementMap+10*Constante.PANTALLA_ALTURA/1920);
    }

    public void setrBottom(Rect rBottom) {
        this.rAbajo = rBottom;
    }

    //Toma los valores que se le dan al mapa, pasa valores de anchura
    public Rect getrLeft() {
        return new Rect(this.x-10*Constante.PANTALLA_ANCHO/1080, this.y, this.x, this.y+VistaJuego.sizeElementMap);
    }

    public void setrLeft(Rect rLeft) {
        this.rIzquierda = rLeft;
    }

    //Toma los valores que se le dan al mapa, pasa valores de anchura
    public Rect getrRight() {
        return new Rect(this.x+VistaJuego.sizeElementMap, this.y, this.x+VistaJuego.sizeElementMap+10*Constante.PANTALLA_ANCHO/1080, this.y+VistaJuego.sizeElementMap);
    }

    public void setrRight(Rect rRight) {
        this.rDerecha = rRight;
    }
    //Fin del Encapsulamiento
}
