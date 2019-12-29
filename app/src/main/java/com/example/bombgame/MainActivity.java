package com.example.bombgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // MyView 클래스에서 쉽게 접근하기 위해 변수들을 static으로 선언한다.
    static int lapseMin;
    static long startTime;
    static long endTime;
    static int randomMin;

    static SoundPool sdPool;
    static int soundOfBomb;

    static MediaPlayer backMusic;

    // 게임 시작 버튼, 게임 종료 버튼 선언
    Button startButton;
    Button endButton;

    // 화면 가로 및 세로 크기
    static int width;
    static int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 화면 가로, 세로 크기 얻기
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        startButton = findViewById(R.id.start_button);
        endButton = findViewById(R.id.end_button);

        // 폭탄 터지는 효과음
        sdPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundOfBomb = sdPool.load(this, R.raw.bomb, 2);

        // 시계 째깍째깍 소리
        backMusic = MediaPlayer.create(this, R.raw.crack);
        backMusic.setLooping(true); // 노래를 무한반복으로 나오게 하기

        // 게임 시작 버튼
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 폭탄이 터지는 시간을 5~84초 사이로 설정한다.
                randomMin = new Random().nextInt(80) + 5;
                startTime = System.currentTimeMillis();
                MyView.gameOver = false; // 게임이 진행중임을 의미한다.
                MyView.sayBomb = false; // 폭탄 소리가 나지 않도록 한다.
                backMusic.start(); // 시계 째깍째깍 소리가 나도록 한다.
            }
        });

        // 게임 종료 버튼
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMusic.stop(); // 배경 음악을 끈다.
                finish(); // Activity를 종료한다.
            }
        });
    }

    // 게임 시작 후 지나간 시간을 구한다.
    public static void checkMin() {
        endTime = System.currentTimeMillis();
        lapseMin = (int) endTime - (int) startTime;
        lapseMin = lapseMin / 1000;
    }

    // 폭탄이 터지게 되면 호출되어 시계가 째깍째깍 소리내는 것이 멈춘다.
    public static void stopBackSound() {
        backMusic.pause();
    }

    // 폭탄이 터지게 되면 호출되어 폭탄 효과음 소리가 나도록 한다.
    public static void sayBomb() {
        sdPool.play(soundOfBomb, 1.0F, 1.0F, 1, 0, 1.0F);
    }
}
