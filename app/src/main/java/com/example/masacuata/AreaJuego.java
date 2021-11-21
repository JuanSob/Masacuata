package com.example.masacuata;

import android.graphics.Bitmap;

public class AreaJuego {
    private Bitmap bm;
    private int x, y, ancho, altura;

    public AreaJuego(Bitmap bm, int x, int y, int ancho, int altura) {
        this.bm = bm;
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.altura = altura;
    }

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

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
