//Interfaz del juego
package com.example.masacuata;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.LogRecord;

public class VistaJuego extends View {

    //Importat la clase Bitmap con sus atributos
    private Bitmap pierda1, piedra2, bmMasacuata, bmBaleada;

    private ArrayList<AreaJuego> arregloPiedras = new ArrayList<>();
    //Atributos en los que le damos el numero de cuadros por ancho y por alto
    private int anchoArea = 12, altoArea=21;
    //Atributo para el ancho de la pagina
    public static int sizeElementMap = 75*Constante.PANTALLA_ANCHO/1080;

    private Snake snake;
    private Baleada apple;
    private Handler handler;
    private Runnable r;
    private boolean move = false;
    private float mx, my;
    public static boolean isPlaying = false;
    public static int score = 0, bestScore = 0;
    private Context context;
    private int soundEat, soundDie;
    private float volume;
    private boolean loadedsound;
    private SoundPool soundPool;

    //Metodo en el que se determina el area en el que se va mover el gusanito
    public VistaJuego(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);

        if(sp!=null)
        {
            bestScore = sp.getInt("bestscore",0);
        }

        /*
        Aquí se descomponen las imagenes con el uso de la clase bitmap
        en donde le damos el tamaño que queremos para que sea el adecuado al momento
        de visualizarlo en el area de juego
        * */
        pierda1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.piedra);
        pierda1 = Bitmap.createScaledBitmap(pierda1, sizeElementMap, sizeElementMap, true);
        piedra2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.piedra2);
        piedra2 = Bitmap.createScaledBitmap(piedra2, sizeElementMap, sizeElementMap, true);
        bmMasacuata = BitmapFactory.decodeResource(this.getResources(), R.drawable.mazacuata);
        bmMasacuata = Bitmap.createScaledBitmap(bmMasacuata, 14*sizeElementMap, sizeElementMap, true);
        bmBaleada = BitmapFactory.decodeResource(this.getResources(), R.drawable.baleada);
        bmBaleada = Bitmap.createScaledBitmap(bmBaleada, sizeElementMap, sizeElementMap, true);

        /*  En esta matriz distrinuimos las imagenes al rededor del area del juego por
            medio de dos ciclos for
         */
        for(int i = 0; i < altoArea; i++)
        {
            for (int j = 0; j < anchoArea; j++)
            {
                /*Decisión donde observamos que siempre sean numeros pares (con % sabemos si hay remainder o no)
                *Si es par el numero ingresará una pieda de la imagen 1, sino agregará una piedra de la imagen 2
                * */
                if((j+i)%2==0)
                {
                    arregloPiedras.add(new AreaJuego(pierda1, j*pierda1.getWidth() + Constante.PANTALLA_ANCHO/2 - (anchoArea/2)*pierda1.getWidth(), i*pierda1.getHeight()+50*Constante.PANTALLA_ALTURA/1920, pierda1.getWidth(), pierda1.getHeight()));
                }
                else
                {
                    arregloPiedras.add(new AreaJuego(piedra2, j*piedra2.getWidth() + Constante.PANTALLA_ANCHO/2 - (anchoArea/2)*piedra2.getWidth(), i*piedra2.getHeight()+50*Constante.PANTALLA_ALTURA/1920, piedra2.getWidth(), piedra2.getHeight()));
                }
            }
        }

        //Aquí se coloca en que lado deseamos que aparezca la masacuata al iniciar el juego
        snake = new Snake(bmMasacuata,arregloPiedras.get(126).getX(),arregloPiedras.get(126).getY(), 4);

        apple = new Baleada(bmBaleada, arregloPiedras.get(randomApple()[0]).getX(), arregloPiedras.get(randomApple()[1]).getY());
        handler = new Handler();

        r = new Runnable()
        {
            @Override
            public void run() {
                invalidate();
            }
        };

        if(Build.VERSION.SDK_INT>=21)
        {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
            this.soundPool = builder.build();
        }
        else
        {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
        {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loadedsound = true;
            }
        });

        soundEat = this.soundPool.load(context, R.raw.eat, 1);
        soundDie = this.soundPool.load(context, R.raw.die, 1);
    }

    private int[] randomApple(){
        int []xy = new int[2];
        Random r = new Random();
        xy[0] = r.nextInt(arregloPiedras.size()-1);
        xy[1] = r.nextInt(arregloPiedras.size()-1);
        Rect rect = new Rect(arregloPiedras.get(xy[0]).getX(), arregloPiedras.get(xy[1]).getY(), arregloPiedras.get(xy[0]).getX()+sizeElementMap, arregloPiedras.get(xy[1]).getY()+sizeElementMap);
        boolean check = true;
        while (check){
            check = false;
            for (int i = 0; i < snake.getArrPartSnake().size(); i++){
                if(rect.intersect(snake.getArrPartSnake().get(i).getrBody())){
                    check = true;
                    xy[0] = r.nextInt(arregloPiedras.size()-1);
                    xy[1] = r.nextInt(arregloPiedras.size()-1);
                    rect = new Rect(arregloPiedras.get(xy[0]).getX(), arregloPiedras.get(xy[1]).getY(), arregloPiedras.get(xy[0]).getX()+sizeElementMap, arregloPiedras.get(xy[1]).getY()+sizeElementMap);
                }
            }
        }
        return xy;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int a = event.getActionMasked();
        switch (a){
            case  MotionEvent.ACTION_MOVE:{
                if(move==false){
                    mx = event.getX();
                    my = event.getY();
                    move = true;
                }else{
                    if(mx - event.getX() > 100 && !snake.isMove_right()){
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_left(true);
                        isPlaying = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }else if(event.getX() - mx > 100 &&!snake.isMove_left()){
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_right(true);
                        isPlaying = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }else if(event.getY() - my > 100 && !snake.isMove_top()){
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_bottom(true);
                        isPlaying = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }else if(my - event.getY() > 100 && !snake.isMove_bottom()){
                        mx = event.getX();
                        my = event.getY();
                        this.snake.setMove_top(true);
                        isPlaying = true;
                        MainActivity.img_swipe.setVisibility(INVISIBLE);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                mx = 0;
                my = 0;
                move = false;
                break;
            }
        }
        return true;
    }

    //Metodo utilizado para poder dibujar
    public void draw(Canvas canvas){
        super.draw(canvas);
        for(int i = 0; i < arregloPiedras.size(); i++)
        {
            canvas.drawBitmap(arregloPiedras.get(i).getBm(), arregloPiedras.get(i).getX(), arregloPiedras.get(i).getY(), null);
        }

        //Se llama el metodo de la clase Snake para que sea dibujado
        snake.draw(canvas);

        if(isPlaying){
            snake.update();
            if(snake.getArrPartSnake().get(0).getX() < this.arregloPiedras.get(0).getX()
                    ||snake.getArrPartSnake().get(0).getY() < this.arregloPiedras.get(0).getY()
                    ||snake.getArrPartSnake().get(0).getY()+sizeElementMap>this.arregloPiedras.get(this.arregloPiedras.size()-1).getY() + sizeElementMap
                    ||snake.getArrPartSnake().get(0).getX()+sizeElementMap>this.arregloPiedras.get(this.arregloPiedras.size()-1).getX() + sizeElementMap){
                gameOver();
            }
            for (int i = 1; i < snake.getArrPartSnake().size(); i++){
                if (snake.getArrPartSnake().get(0).getrBody().intersect(snake.getArrPartSnake().get(i).getrBody())){
                    gameOver();
                }
            }
        }

        apple.draw(canvas);
        if(snake.getArrPartSnake().get(0).getrBody().intersect(apple.getR())){
            if(loadedsound){
                int streamId = this.soundPool.play(this.soundEat, (float)0.5, (float)0.5, 1, 0, 1f);
            }
            apple.reset(arregloPiedras.get(randomApple()[0]).getX(), arregloPiedras.get(randomApple()[1]).getY());
            snake.addPart();
            score++;
            MainActivity.txt_score.setText(score+"");
            if(score > bestScore){
                bestScore = score;
                SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("bestscore", bestScore);
                editor.apply();
                MainActivity.txt_best_score.setText(bestScore+"");
            }
        }
        handler.postDelayed(r, 100);
    }

    private void gameOver() {
        isPlaying = false;
        MainActivity.dialogScore.show();
        MainActivity.txt_dialog_best_score.setText(bestScore+"");
        MainActivity.txt_dialog_score.setText(score+"");
        if(loadedsound){
            int streamId = this.soundPool.play(this.soundDie, (float)0.5, (float)0.5, 1, 0, 1f);
        }
    }

    public void reset(){
        for(int i = 0; i < altoArea; i++){
            for (int j = 0; j < anchoArea; j++){
                if((j+i)%2==0){
                    arregloPiedras.add(new AreaJuego(pierda1, j*pierda1.getWidth() + Constante.PANTALLA_ANCHO/2 - (anchoArea/2)*pierda1.getWidth(), i*pierda1.getHeight()+50*Constante.PANTALLA_ALTURA/1920, pierda1.getWidth(), pierda1.getHeight()));
                }else{
                    arregloPiedras.add(new AreaJuego(piedra2, j*piedra2.getWidth() + Constante.PANTALLA_ANCHO/2 - (anchoArea/2)*piedra2.getWidth(), i*piedra2.getHeight()+50*Constante.PANTALLA_ALTURA/1920, piedra2.getWidth(), piedra2.getHeight()));
                }
            }
        }
        snake = new Snake(bmMasacuata,arregloPiedras.get(126).getX(),arregloPiedras.get(126).getY(), 4);
        apple = new Baleada(bmBaleada, arregloPiedras.get(randomApple()[0]).getX(), arregloPiedras.get(randomApple()[1]).getY());
        score = 0;
    }

}
