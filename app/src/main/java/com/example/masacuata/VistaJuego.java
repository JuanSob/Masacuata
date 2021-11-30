package com.example.masacuata;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogRecord;

public class VistaJuego extends View {
    private Bitmap bmArea1, bmArea2, bmSnake, bmBaleada;
    public static int tamanoMapa = 75*Constante.PANTALLA_ANCHO/1080;
    private int al= 19, an = 12;
    private ArrayList<AreaJuego> arrArea = new ArrayList<>();

    private Snake snake;
    private boolean move=false;
    private float mx,my;
    private Handler handler;
    private Runnable r;
    private Baleada baleada;
    public VistaJuego(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bmArea1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.piedra);
        bmArea1 = Bitmap.createScaledBitmap(bmArea1,tamanoMapa, tamanoMapa, true);
        bmArea2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.piedra2);
        bmArea2 = Bitmap.createScaledBitmap(bmArea2,tamanoMapa, tamanoMapa, true);
        //*********************************
        bmSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.mazacuata);
        bmSnake = Bitmap.createScaledBitmap(bmSnake,14*tamanoMapa, tamanoMapa, true);
        bmBaleada = BitmapFactory.decodeResource(this.getResources(), R.drawable.baleada);
        bmBaleada = Bitmap.createScaledBitmap(bmBaleada,14*tamanoMapa, tamanoMapa, true);

        for (int i = 0; i < al; i++){
            for (int j = 0; j < an; j++){
                if((i+j)%2==0)
                {
                    arrArea.add(new AreaJuego(bmArea1, j*tamanoMapa + Constante.PANTALLA_ANCHO/2-(an/2)*tamanoMapa,
                            i*tamanoMapa+100*Constante.PANTALLA_ALTURA/1920, tamanoMapa, tamanoMapa));
                }else{
                    arrArea.add(new AreaJuego(bmArea2, j*tamanoMapa + Constante.PANTALLA_ANCHO/2-(an/2)*tamanoMapa,
                            i*tamanoMapa+100*Constante.PANTALLA_ALTURA/1920, tamanoMapa, tamanoMapa));

                }
            }
        }
        snake= new Snake(bmSnake, arrArea.get(126).getX(), arrArea.get(126).getY(), 4);
        baleada = new Baleada(bmBaleada,arrArea.get(randomBaleada()[0]).getX(), arrArea.get(randomBaleada()[1]).getY());
        handler= new Handler();
        r= new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a= event.getActionMasked();
        switch (a){
            case MotionEvent.ACTION_MOVE:{
                if(move == false){
                    mx=event.getX();
                    my=event.getY();
                    move=true;
                }else{
                    if(mx-event.getX()>100*Constante.PANTALLA_ANCHO/1080 && !snake.isMove_right()){
                        mx= event.getX();
                        my=event.getY();
                        snake.setMove_left(true);
                    }else if(event.getX()-mx>100*Constante.PANTALLA_ANCHO/1080 && !snake.isMove_left()){
                        mx= event.getX();
                        my=event.getY();
                        snake.setMove_right(true);
                    }else if(my-event.getY()>100*Constante.PANTALLA_ANCHO/1080 && !snake.isMove_bottom()){
                        mx= event.getX();
                        my=event.getY();
                        snake.setMove_top(true);
                    }else if(event.getY()-my>100*Constante.PANTALLA_ANCHO/1080 && !snake.isMove_top()){
                        mx= event.getX();
                        my=event.getY();
                        snake.setMove_bottom(true);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                mx=0;
                my=0;
                move=false;
                break;
            }
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for (int i = 0; i < arrArea.size(); i++)
        {
            canvas.drawBitmap(arrArea.get(i).getBm(), arrArea.get(i).getX(), arrArea.get(i).getY(), null);
        }
        snake.update();
        snake.draw(canvas);
        baleada.draw(canvas);
        if(snake.getArrPartSnake().get(0).getrBody().intersect(baleada.getR())){
            randomBaleada();
            baleada.reset(arrArea.get(randomBaleada()[0]).getX(), arrArea.get(randomBaleada()[1]).getY());
            snake.addPart();
        }
        handler.postDelayed(r,100);
    }

    public int[] randomBaleada(){
        int []xy = new int[2];
        Random r = new Random();
        xy[0] = r.nextInt(arrArea.size() - 1);
        xy[1] = r.nextInt(arrArea.size() - 1);
        Rect rect = new Rect(arrArea.get(xy[0]).getX(), arrArea.get(xy[1]).getY(), arrArea.get(xy[0]).getX()+tamanoMapa, arrArea.get(xy[1]).getY()+tamanoMapa);
        boolean check = true;
        while (check){
            check = false;
            for(int i=0; i < snake.getArrPartSnake().size(); i++){
                if(rect.intersect(snake.getArrPartSnake().get(i).getrBody())){
                    check = true;
                    xy[0] = r.nextInt(arrArea.size()-1);
                    xy[1] = r.nextInt(arrArea.size()-1);
                    rect = new Rect(arrArea.get(xy[0]).getX(), arrArea.get(xy[1]).getY(), arrArea.get(xy[0]).getX()+tamanoMapa, arrArea.get(xy[1]).getY()+tamanoMapa);

                }
            }
        }

        return xy;


    }
}
