package com.example.masacuata;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.animation.ScaleAnimation;

public class Baleada {
    private Bitmap bm;
    private int x, y;
    private Rect r;//Rect contiene cuatro coordenadas enteras para un rectángulo.

    //Metodo Constructor
    public Baleada(Bitmap bm, int x, int y) {
        this.bm = bm;
        this.x = x;
        this.y = y;
    }//Fin de metodo constructor

    public void draw(Canvas canvas){
        canvas.drawBitmap(bm, x, y, null);
    }

    public void reset(int nx, int ny){
        this.x = nx;
        this.y = ny;
    }

    //Inicio de Encapsulamiento (getter y setter)
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

    //Aquí se le pasan 4 datos los cuales representan cada uno de los bordes
    public Rect getR()
    {
        return new Rect(this.x, this.y, this.x+VistaJuego.sizeElementMap, this.y+VistaJuego.sizeElementMap);
    }

    public void setR(Rect r) {
        this.r = r;
    }
    //Fin de Encapsulamiento (getter y setter)

}
