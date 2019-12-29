package com.example.bombgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.jar.Attributes;

public class MyView extends View {
    Bitmap bomb1;
    Bitmap bomb2;
    Bitmap bomb3;

    static Boolean sayBomb = false;
    static Boolean gameOver = false;

    public MyView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        setBackgroundColor(Color.BLUE);

    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        bomb1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.bomb1);
        bomb2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.bomb2);
        bomb3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.bomb3);

        bomb1 = Bitmap.createScaledBitmap(bomb1, MainActivity.width * 2 / 3, MainActivity.width * 2 / 3, true);
        bomb2 = Bitmap.createScaledBitmap(bomb2, MainActivity.width * 2 / 3, MainActivity.width * 2 / 3, true);
        bomb3 = Bitmap.createScaledBitmap(bomb3, MainActivity.width * 2 / 3, MainActivity.width * 2 / 3, true);

    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint p = new Paint();
        p.setTextSize(60);
        MainActivity.checkMin(); // 시간이 얼마나 지났는지 체크하는 메소드를 호출한다.

        // 무작위로 정한 시간이 되어 폭탄이 터지게 된다.
        if (MainActivity.lapseMin >= MainActivity.randomMin) {
            canvas.drawBitmap(bomb3, 50, 150, null);
            canvas.drawText("폭탄 터진 시간: " + MainActivity.randomMin + "초", 100, 150, p);

            if (sayBomb == false) {
                sayBomb = true;
                gameOver = true;
                MainActivity.sayBomb(); // 폭탄 터지는 소리를 호출한다.

                // 시계가 째깍째깍 하는 소리 멈추도록 하는 메소드를 호출한다.
                MainActivity.stopBackSound();
            }
        } else {
            // 폭탄의 심줄이 타는 모습을 번갈아 가면서 보여준다.
            if ((MainActivity.lapseMin) % 2 == 0) {
                canvas.drawBitmap(bomb1, 50, 150, null);
            } else {
                canvas.drawBitmap(bomb2, 50, 150, null);
            }

            // 시간이 얼마나 지나가는지 화면에 제시해준다.
            canvas.drawText("지나간 시간: " + MainActivity.lapseMin + "", 100, 100, p);
        }
        // invalidate 메소드는 onDraw 메소드를 호출한다.
        // 그래야 계속해서 변하는화면이 반영된다.
        invalidate();
    }

}
