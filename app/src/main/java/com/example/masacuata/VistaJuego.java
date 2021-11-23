package com.example.masacuata;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class VistaJuego extends View {
    private Bitmap bmArea1, bmArea2;
    public static int tamanoMapa = 75*Constante.PANTALLA_ANCHO/1080;
    private int al= 19, an = 12;
    private ArrayList<AreaJuego> arrArea = new ArrayList<>();
    public VistaJuego(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bmArea1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.piedra);
        bmArea1 = Bitmap.createScaledBitmap(bmArea1,tamanoMapa, tamanoMapa, true);
        bmArea2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.piedra2);
        bmArea2 = Bitmap.createScaledBitmap(bmArea2,tamanoMapa, tamanoMapa, true);

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
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for (int i = 0; i < arrArea.size(); i++)
        {
            canvas.drawBitmap(arrArea.get(i).getBm(), arrArea.get(i).getX(), arrArea.get(i).getY(), null);
        }
    }
}
