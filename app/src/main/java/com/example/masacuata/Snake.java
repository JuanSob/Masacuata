package com.example.masacuata;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

//en esta clase realizamos cada movimiento de la serpiente
public class Snake {
    private boolean move_left, move_right, move_top, move_bottom;

    //Se importa la clase Bitmap y se hace un atributo para cada una de las partes de la serpiente
    private Bitmap bm, bm_head_up, bm_head_down, bm_head_left, bm_head_right, bm_head_body_vertical, bm_body_horizontal,
    bm_body_top_right, bm_body_top_left, bm_body_bottom_right, bm_body_botoom_left, bm_tail_right, bm_tail_left,
    bm_tail_up, bm_tail_down;

    //Se crea una variable para el eje x y el eje y, tambien para el tamaño que es el mismo de la serpiente
    private int x, y, length;

    //Se crea un arreglo el cual se utiliza para formar la serpiente
    private ArrayList<PartSnake> arrPartSnake= new ArrayList<>();

    //Metodo constructor
    public Snake(Bitmap bm, int x, int y, int length)
    {
        this.bm = bm;
        this.x = x;
        this.y = y;
        this.length = length;
        //**************************

        /*Devuelve un mapa de bits del subconjunto especificado del mapa de bits de origen. El nuevo mapa de bits puede ser el
          mismo objeto que el origen o se puede haber realizado una copia. Se inicializa con la misma densidad y espacio de color que el
          mapa de bits original.
          mejor explicación:
          https://developer.android.com/reference/android/graphics/Bitmap#createBitmap(android.graphics.Bitmap,%20int,%20int,%20int,%20int)
        */
        bm_body_botoom_left= Bitmap.createBitmap(bm,0, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_body_bottom_right= Bitmap.createBitmap(bm,VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_body_horizontal= Bitmap.createBitmap(bm, 2*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_body_top_left= Bitmap.createBitmap(bm, 3*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_body_top_right= Bitmap.createBitmap(bm, 4*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_head_body_vertical= Bitmap.createBitmap(bm, 5*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_head_down= Bitmap.createBitmap(bm, 6*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_head_left= Bitmap.createBitmap(bm, 7*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_head_right= Bitmap.createBitmap(bm, 8*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_head_up= Bitmap.createBitmap(bm, 9*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_tail_up= Bitmap.createBitmap(bm, 10*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_tail_right= Bitmap.createBitmap(bm, 11*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_tail_left= Bitmap.createBitmap(bm, 12*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);
        bm_tail_down= Bitmap.createBitmap(bm, 13*VistaJuego.sizeElementMap, 0, VistaJuego.sizeElementMap, VistaJuego.sizeElementMap);

        //Aquí se crea una serpiente por default cuando se inicia el juego
        arrPartSnake.add(new PartSnake(bm_head_right, x, y));

        //En este ciclo se van añadiendo las partes del cuerpo de la serpiente
        for (int i= 1; i< length - 1; i++)
        {
            arrPartSnake.add(new PartSnake(bm_body_horizontal, arrPartSnake.get(i-1).getX() - VistaJuego.sizeElementMap, y));
        }

        //En esta parte se añade la cola
        arrPartSnake.add(new PartSnake(bm_tail_right, arrPartSnake.get(length-2).getX() - VistaJuego.sizeElementMap, y));

        setMove_right(true);
    }

    public void update() {
        for (int i = length - 1; i > 0; i--) {
            arrPartSnake.get(i).setX(arrPartSnake.get(i - 1).getX());
            arrPartSnake.get(i).setY(arrPartSnake.get(i - 1).getY());
        }
        if (move_right) {
            arrPartSnake.get(0).setX(arrPartSnake.get(0).getX() + VistaJuego.sizeElementMap);
            arrPartSnake.get(0).setBm(bm_head_right);
        } else if (move_left) {
            arrPartSnake.get(0).setX(arrPartSnake.get(0).getX() - VistaJuego.sizeElementMap);
            arrPartSnake.get(0).setBm(bm_head_left);
        } else if (move_top) {
            arrPartSnake.get(0).setY(arrPartSnake.get(0).getY() - VistaJuego.sizeElementMap);
            arrPartSnake.get(0).setBm(bm_head_up);
        } else if (move_bottom) {
            arrPartSnake.get(0).setY(arrPartSnake.get(0).getY() + VistaJuego.sizeElementMap);
            arrPartSnake.get(0).setBm(bm_head_down);
        }
        for (int i = 1; i < length - 1; i++) {
            if (arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i + 1).getrBody())) {
                arrPartSnake.get(i).setBm(bm_body_botoom_left);
            } else if (arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i + 1).getrBody())) {
                arrPartSnake.get(i).setBm(bm_body_bottom_right);
            } else if (arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i + 1).getrBody())) {
                arrPartSnake.get(i).setBm(bm_body_top_left);
            } else if (arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i + 1).getrBody())) {
                arrPartSnake.get(i).setBm(bm_body_top_right);
            } else if (arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrTop().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrBottom().intersect(arrPartSnake.get(i + 1).getrBody())) {
                arrPartSnake.get(i).setBm(bm_head_body_vertical);
            } else if (arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i + 1).getrBody())
                    && arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i - 1).getrBody())
                    || arrPartSnake.get(i).getrLeft().intersect(arrPartSnake.get(i - 1).getrBody())
                    && arrPartSnake.get(i).getrRight().intersect(arrPartSnake.get(i + 1).getrBody())) {
                arrPartSnake.get(i).setBm(bm_body_horizontal);
            }

        }
        if(arrPartSnake.get(length-1).getrRight().intersect(arrPartSnake.get(length-2).getrBody())){
            arrPartSnake.get(length-1).setBm(bm_tail_right);
        }else if(arrPartSnake.get(length-1).getrLeft().intersect(arrPartSnake.get(length-2).getrBody())){
            arrPartSnake.get(length-1).setBm(bm_tail_left);
        }else if(arrPartSnake.get(length-1).getrTop().intersect(arrPartSnake.get(length-2).getrBody())){
            arrPartSnake.get(length-1).setBm(bm_tail_up);
        }else if(arrPartSnake.get(length-1).getrBottom().intersect(arrPartSnake.get(length-2).getrBody())){
            arrPartSnake.get(length-1).setBm(bm_tail_down);
        }
    }

    //En este metodo se dibuja la serpiente, de la misma manera con la que se hizo el area de juego
    public void draw(Canvas canvas){

        for (int i= 0; i< length; i++)
        {
            canvas.drawBitmap(arrPartSnake.get(i).getBm(), arrPartSnake.get(i).getX(), arrPartSnake.get(i).getY(), null);
        }

    }

    //Inicio del encapsulamiento
    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Bitmap getBm_head_up() {
        return bm_head_up;
    }

    public void setBm_head_up(Bitmap bm_head_up) {
        this.bm_head_up = bm_head_up;
    }

    public Bitmap getBm_head_down() {
        return bm_head_down;
    }

    public void setBm_head_down(Bitmap bm_head_down) {
        this.bm_head_down = bm_head_down;
    }

    public Bitmap getBm_head_left() {
        return bm_head_left;
    }

    public void setBm_head_left(Bitmap bm_head_left) {
        this.bm_head_left = bm_head_left;
    }

    public Bitmap getBm_head_right() {
        return bm_head_right;
    }

    public void setBm_head_right(Bitmap bm_head_right) {
        this.bm_head_right = bm_head_right;
    }

    public Bitmap getBm_head_body_vertical() {
        return bm_head_body_vertical;
    }

    public void setBm_head_body_vertical(Bitmap bm_head_body_vertical) {
        this.bm_head_body_vertical = bm_head_body_vertical;
    }

    public Bitmap getBm_head_horizontal() {
        return bm_body_horizontal;
    }

    public void setBm_head_horizontal(Bitmap bm_head_horizontal) {
        this.bm_body_horizontal = bm_head_horizontal;
    }

    public Bitmap getBm_body_top_right() {
        return bm_body_top_right;
    }

    public void setBm_body_top_right(Bitmap bm_body_top_right) {
        this.bm_body_top_right = bm_body_top_right;
    }

    public Bitmap getBm_body_top_left() {
        return bm_body_top_left;
    }

    public void setBm_body_top_left(Bitmap bm_body_top_left) {
        this.bm_body_top_left = bm_body_top_left;
    }

    public Bitmap getBm_body_bottom_right() {
        return bm_body_bottom_right;
    }

    public void setBm_body_bottom_right(Bitmap bm_body_bottom_right) {
        this.bm_body_bottom_right = bm_body_bottom_right;
    }

    public Bitmap getBm_body_botoom_left() {
        return bm_body_botoom_left;
    }

    public void setBm_body_botoom_left(Bitmap bm_body_botoom_left) {
        this.bm_body_botoom_left = bm_body_botoom_left;
    }

    public Bitmap getBm_tail_right() {
        return bm_tail_right;
    }

    public void setBm_tail_right(Bitmap bm_tail_right) {
        this.bm_tail_right = bm_tail_right;
    }

    public Bitmap getBm_tail_left() {
        return bm_tail_left;
    }

    public void setBm_tail_left(Bitmap bm_tail_left) {
        this.bm_tail_left = bm_tail_left;
    }

    public Bitmap getBm_tail_up() {
        return bm_tail_up;
    }

    public void setBm_tail_up(Bitmap bm_tail_up) {
        this.bm_tail_up = bm_tail_up;
    }

    public Bitmap getBm_tail_down() {
        return bm_tail_down;
    }

    public void setBm_tail_down(Bitmap bm_tail_down) {
        this.bm_tail_down = bm_tail_down;
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    //Fin del del encapsulamiento

    public ArrayList<PartSnake> getArrPartSnake() {
        return arrPartSnake;
    }

    public void setArrPartSnake(ArrayList<PartSnake> arrPartSnake) {
        this.arrPartSnake = arrPartSnake;
    }

    public boolean isMove_left() {
        return move_left;
    }

    public void setMove_left(boolean move_left) {
        s();
        this.move_left = move_left;

    }

    public boolean isMove_right() {
        return move_right;
    }

    public void setMove_right(boolean move_right) {
        s();
        this.move_right = move_right;
    }

    public boolean isMove_top() {
        return move_top;
    }

    public void setMove_top(boolean move_top) {
        s();
        this.move_top = move_top;
    }

    public boolean isMove_bottom() {
        return move_bottom;
    }

    public void setMove_bottom(boolean move_bottom) {
        s();
        this.move_bottom = move_bottom;
    }
    public void s(){
        this.move_left=false;
        this.move_right=false;
        this.move_top=false;
        this.move_bottom=false;
    }

    public void addPart() {
        PartSnake p = this.arrPartSnake.get(length-1);
        this.length +=1;
        if(p.getBm() == bm_tail_right){
            this.arrPartSnake.add(new PartSnake(bm_tail_right, p.getX() - VistaJuego.sizeElementMap, p.getY()));
        }else if(p.getBm() == bm_tail_left){
            this.arrPartSnake.add(new PartSnake(bm_tail_left, p.getX() + VistaJuego.sizeElementMap, p.getY()));
        }else if(p.getBm() == bm_tail_up){
            this.arrPartSnake.add(new PartSnake(bm_tail_up, p.getX(), p.getY()+VistaJuego.sizeElementMap));
        }else if(p.getBm() == bm_tail_down){
            this.arrPartSnake.add(new PartSnake(bm_tail_down, p.getX(), p.getY()-VistaJuego.sizeElementMap));
        }
    }
}
