package com.example.masacuata;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

//en esta clase realizamos cada movimiento de la serpiente
public class Snake {

    //Atributos utilizados para visualizar determinar adonde se mueve la serpiente
    private boolean moverIzquierda, moverDerecha, moverArriba, moverAbajo;

    //Se importa la clase Bitmap y se hace un atributo para cada una de las partes de la serpiente
    private Bitmap bm, bm_head_up, bm_head_down, bm_head_left, bm_head_right, bm_head_body_vertical, bm_body_horizontal,
    bm_body_top_right, bm_body_top_left, bm_body_bottom_right, bm_body_botoom_left, bm_tail_right, bm_tail_left,
    bm_tail_up, bm_tail_down;

    //Se crea una variable para el eje x y el eje y, tambien para el tamaño que es el mismo de la serpiente
    private int x, y, length;

    //Se crea un arreglo el cual se utiliza para formar la serpiente
    private ArrayList<PartSnake> arregloParteSerpiente= new ArrayList<>();

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
        arregloParteSerpiente.add(new PartSnake(bm_head_right, x, y));

        //En este ciclo se van añadiendo las partes del cuerpo de la serpiente
        for (int i= 1; i< length - 1; i++)
        {
            arregloParteSerpiente.add(new PartSnake(bm_body_horizontal, arregloParteSerpiente.get(i-1).getX() - VistaJuego.sizeElementMap, y));
        }

        //En esta parte se añade la cola
        arregloParteSerpiente.add(new PartSnake(bm_tail_right, arregloParteSerpiente.get(length-2).getX() - VistaJuego.sizeElementMap, y));

        //Se inicializa que la serpiente siempre se mueva al lado derecho
        setMove_right(true);
    }

    //En este metodo se actualiza el movimiento de la serpiente
    public void update()
    {
        //Aquí en el for se utiliza el valor de la cabeza como inicio
        for (int i = length - 1; i > 0; i--)
        {
            /*
                Se utiliza i-1, el valor de la cabeza es i
                entonces el resto del cuerpo irá atras, una parte atras de la matriz donde está
                la cabeza igualando la frecuencia
            */
            arregloParteSerpiente.get(i).setX(arregloParteSerpiente.get(i - 1).getX());
            arregloParteSerpiente.get(i).setY(arregloParteSerpiente.get(i - 1).getY());
        }

        /*Decisiones que determinan en que parte del mapa se ubicará la serpiente y que
        imagen de la cabeza se verá, para arriba, izquierda, derecha
        se necesita unicamente el movimiento de la cabeza luego las demás partes del cuermo seguirán el movimiento
        */
        if (moverDerecha)
        {
            arregloParteSerpiente.get(0).setX(arregloParteSerpiente.get(0).getX() + VistaJuego.sizeElementMap);
            arregloParteSerpiente.get(0).setBm(bm_head_right);
        }
        else if (moverIzquierda)
        {
            arregloParteSerpiente.get(0).setX(arregloParteSerpiente.get(0).getX() - VistaJuego.sizeElementMap);
            arregloParteSerpiente.get(0).setBm(bm_head_left);
        }
        else if (moverArriba)
        {
            arregloParteSerpiente.get(0).setY(arregloParteSerpiente.get(0).getY() - VistaJuego.sizeElementMap);
            arregloParteSerpiente.get(0).setBm(bm_head_up);
        }
        else if (moverAbajo)
        {
            arregloParteSerpiente.get(0).setY(arregloParteSerpiente.get(0).getY() + VistaJuego.sizeElementMap);
            arregloParteSerpiente.get(0).setBm(bm_head_down);
        }
        //Fin movimiento de cabeza

        /*
        * movimiento del cuerpo cuando la serpiennte se mueve
        * */
        for (int i = 1; i < length - 1; i++)
        {
            /*Se toman los disntintos tipos de caso en los que la serpiente se podría mover ejemplo:
            ir a la derecha mientras esta para arriba
            */
            if (arregloParteSerpiente.get(i).getrLeft().intersect(arregloParteSerpiente.get(i + 1).getrBody())
                    && arregloParteSerpiente.get(i).getrBottom().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    || arregloParteSerpiente.get(i).getrLeft().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    && arregloParteSerpiente.get(i).getrBottom().intersect(arregloParteSerpiente.get(i + 1).getrBody()))
            {
                arregloParteSerpiente.get(i).setBm(bm_body_botoom_left);
            }
            else if (arregloParteSerpiente.get(i).getrRight().intersect(arregloParteSerpiente.get(i + 1).getrBody())
                    && arregloParteSerpiente.get(i).getrBottom().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    || arregloParteSerpiente.get(i).getrRight().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    && arregloParteSerpiente.get(i).getrBottom().intersect(arregloParteSerpiente.get(i + 1).getrBody()))
            {
                arregloParteSerpiente.get(i).setBm(bm_body_bottom_right);
            }
            else if (arregloParteSerpiente.get(i).getrLeft().intersect(arregloParteSerpiente.get(i + 1).getrBody())
                    && arregloParteSerpiente.get(i).getrTop().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    || arregloParteSerpiente.get(i).getrLeft().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    && arregloParteSerpiente.get(i).getrTop().intersect(arregloParteSerpiente.get(i + 1).getrBody()))
            {
                arregloParteSerpiente.get(i).setBm(bm_body_top_left);
            }
            else if (arregloParteSerpiente.get(i).getrRight().intersect(arregloParteSerpiente.get(i + 1).getrBody())
                    && arregloParteSerpiente.get(i).getrTop().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    || arregloParteSerpiente.get(i).getrRight().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    && arregloParteSerpiente.get(i).getrTop().intersect(arregloParteSerpiente.get(i + 1).getrBody()))
            {
                arregloParteSerpiente.get(i).setBm(bm_body_top_right);
            }
            else if (arregloParteSerpiente.get(i).getrTop().intersect(arregloParteSerpiente.get(i + 1).getrBody())
                    && arregloParteSerpiente.get(i).getrBottom().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    || arregloParteSerpiente.get(i).getrTop().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    && arregloParteSerpiente.get(i).getrBottom().intersect(arregloParteSerpiente.get(i + 1).getrBody()))
            {
                arregloParteSerpiente.get(i).setBm(bm_head_body_vertical);
            }
            else if (arregloParteSerpiente.get(i).getrLeft().intersect(arregloParteSerpiente.get(i + 1).getrBody())
                    && arregloParteSerpiente.get(i).getrRight().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    || arregloParteSerpiente.get(i).getrLeft().intersect(arregloParteSerpiente.get(i - 1).getrBody())
                    && arregloParteSerpiente.get(i).getrRight().intersect(arregloParteSerpiente.get(i + 1).getrBody()))
            {
                arregloParteSerpiente.get(i).setBm(bm_body_horizontal);
            }

        }

        //Aquí se coloca el lugar en el que estará la cola de la serpiente
        if(arregloParteSerpiente.get(length-1).getrRight().intersect(arregloParteSerpiente.get(length-2).getrBody()))
        {
            arregloParteSerpiente.get(length-1).setBm(bm_tail_right);
        }
        else if(arregloParteSerpiente.get(length-1).getrLeft().intersect(arregloParteSerpiente.get(length-2).getrBody()))
        {
            arregloParteSerpiente.get(length-1).setBm(bm_tail_left);
        }
        else if(arregloParteSerpiente.get(length-1).getrTop().intersect(arregloParteSerpiente.get(length-2).getrBody()))
        {
            arregloParteSerpiente.get(length-1).setBm(bm_tail_up);
        }
        else if(arregloParteSerpiente.get(length-1).getrBottom().intersect(arregloParteSerpiente.get(length-2).getrBody()))
        {
            arregloParteSerpiente.get(length-1).setBm(bm_tail_down);
        }
    }

    //En este metodo se dibuja la serpiente, de la misma manera con la que se hizo el area de juego
    public void draw(Canvas canvas){

        for (int i= 0; i< length; i++)
        {
            canvas.drawBitmap(arregloParteSerpiente.get(i).getBm(), arregloParteSerpiente.get(i).getX(), arregloParteSerpiente.get(i).getY(), null);
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
        return arregloParteSerpiente;
    }

    public void setArrPartSnake(ArrayList<PartSnake> arrPartSnake) {
        this.arregloParteSerpiente = arrPartSnake;
    }

    public boolean isMove_left() {
        return moverIzquierda;
    }

    public void setMove_left(boolean move_left) {
        s();
        this.moverIzquierda = move_left;

    }

    public boolean isMove_right() {
        return moverDerecha;
    }

    public void setMove_right(boolean move_right) {
        s();
        this.moverDerecha = move_right;
    }

    public boolean isMove_top() {
        return moverArriba;
    }

    public void setMove_top(boolean move_top) {
        s();
        this.moverArriba = move_top;
    }

    public boolean isMove_bottom() {
        return moverAbajo;
    }

    public void setMove_bottom(boolean move_bottom) {
        s();
        this.moverAbajo = move_bottom;
    }

    //Inicializan todas la variables en falso antes que se tome el valor que se pase en otra clase con el setter
    public void s(){
        this.moverIzquierda=false;
        this.moverDerecha=false;
        this.moverArriba=false;
        this.moverAbajo=false;
    }

    //Metodo llamado en la clase main para añadir una parte mas a la serpiente cada vez que come
    public void AgregarParte()
    {
        PartSnake p = this.arregloParteSerpiente.get(length-1);
        //Añadimos una parte mas
        this.length +=1;

        //Observammos que imagen de la cola estamos utilizando para saber que imagen añadir
        if(p.getBm() == bm_tail_right){
            this.arregloParteSerpiente.add(new PartSnake(bm_tail_right, p.getX() - VistaJuego.sizeElementMap, p.getY()));
        }else if(p.getBm() == bm_tail_left){
            this.arregloParteSerpiente.add(new PartSnake(bm_tail_left, p.getX() + VistaJuego.sizeElementMap, p.getY()));
        }else if(p.getBm() == bm_tail_up){
            this.arregloParteSerpiente.add(new PartSnake(bm_tail_up, p.getX(), p.getY()+VistaJuego.sizeElementMap));
        }else if(p.getBm() == bm_tail_down){
            this.arregloParteSerpiente.add(new PartSnake(bm_tail_down, p.getX(), p.getY()-VistaJuego.sizeElementMap));
        }
    }
}
