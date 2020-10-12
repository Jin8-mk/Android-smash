package com.example.p01_projectecology;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity {
    public static int highestPoint;
    Integer time = 11;
    ImageView image, play;
    TextView textPoint, point10, lifes, allPoint, timer;
    List<String> results;
    String photo;
    Integer point, life;
    Animation pointShowAnimation, pointHiding;
    LinearLayout whiteBack;
    CountDownTimer cTimer = null;
    Boolean userActive, animate = false;
    View view;
    int mAnimationCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        userActive = arguments.getBoolean("userActive");
        if (userActive) {
            setContentView(R.layout.activity_game);
            initElement();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
            alertDialog.setTitle("Статус");
            alertDialog.setMessage("Користувач не залогінився");
            alertDialog.show();
            alertDialog.getWindow().setLayout(1000, 1000);
            setContentView(R.layout.activity_main);
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        getPhotos();
        changePhoto();
        init();
    }

    public void init() {
        life = 3;
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        point = 0;
        highestPoint = sharedPreferences.getInt("sortPoint", 0);
        point10.setAlpha(0);
        lifes.setText(life.toString());
        allPoint.setText("Макс. очков: " + highestPoint);
    }

    public void initElement() {
        image = (ImageView) findViewById(R.id.imageView12);
        play = (ImageView) findViewById(R.id.imageView13);
        textPoint = (TextView) findViewById(R.id.textView22);
        point10 = (TextView) findViewById(R.id.textView26);
        lifes = (TextView) findViewById(R.id.textView29);
        allPoint = (TextView) findViewById(R.id.textView31);
        timer = (TextView) findViewById(R.id.textView32);
        whiteBack = (LinearLayout) findViewById(R.id.whiteBack);
        pointShowAnimation = AnimationUtils.loadAnimation(this, R.anim.point_show);
        pointHiding = AnimationUtils.loadAnimation(this, R.anim.point_hiding);

    }

    public void getPhotos() {
        results = new ArrayList<String>();
        results.add("bottle_aluminium");
        results.add("bottle_plastik");
        results.add("bottle_glass");
    }


    public static int rnd(int max) {
        return (int) (Math.random() * ++max);
    }

    public void back(View view) {
        finish();
    }
//    public void action(){
//        view.setOnTouchListener(new OnSwipeTouchListener(this) {
//            @Override
//            public void onClick() {
//                super.onClick();
//            }
//
//            @Override
//            public void onSwipeUp() {
//                super.onSwipeUp();
//
//            }
//
//            @Override
//            public void onSwipeDown() {
//                super.onSwipeDown();
//
//            }
//
//            @Override
//            public void onSwipeLeft() {
//                super.onSwipeLeft();
//
//            }
//
//            @Override
//            public void onSwipeRight() {
//                super.onSwipeRight();
//
//            }
//        });
//    }
    public void aluminium(View view) {
        actionButton("aluminium");

    }

    public void plastik(View view) {
        actionButton("plastik");
    }

    public void glass(View view) {
        actionButton("glass");
    }

    public void non_eligible(View view) {
        actionButton("noneligible");

    }

    public void actionButton(String button) {
        if (photo.equals(button)) {
            changePhoto();
            setPoint();
        } else {
            life--;
            if (life == 0) {
                cancelTimer();
                restart();
            } else {
                lifes.setText(life.toString());
            }
        }
    }

    public void restart(){
        if (point > highestPoint){
            highestPoint = point;
            SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("sortPoint", highestPoint);
            editor.apply();
        }
        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        } else {
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            cancelTimer();
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
            cancelTimer();
        }
    }

    public void changePhoto() {
        final int max = 2; // Максимальное число для диапазона от 0 до max
        final int rnd = rnd(max);
        for (String retval : results.get(rnd).split("_", 2)) {
            photo = retval;
    }
        try {
            // получаем входной поток
            InputStream ims = getAssets().open(results.get(rnd) + ".png");
            // загружаем как Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // выводим картинку в ImageView
            image.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }

    }

    public void setPoint() {
        point += 10;
        time = 11;
        cancelTimer();
        startTimer();
        showPoint10();
        textPoint.setText(point.toString());
    }


    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                time--;
                timer.setText(time.toString());
            }
            public void onFinish() {
                restart();
            }
        };
        cTimer.start();
    }

    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }


    public void showPoint10() {
        point10.setAlpha(1);

        if (!animate){
            animatePoint(true);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    animatePoint(false);
                }
            }, 800);
        }

    }



    public void animatePoint(Boolean show) {
        if (show) {
            Animation animationst = AnimationUtils.loadAnimation(this, R.anim.point_show);
            animationst.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animationst) {
                   animate = true;
                }

                @Override
                public void onAnimationEnd(Animation animationst) {

                }

                @Override
                public void onAnimationRepeat(Animation animationst) {

                }
            });
            point10.startAnimation(animationst);
        } else {
            Animation animationend = AnimationUtils.loadAnimation(this, R.anim.point_hiding);
            animationend.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animationend) {
                }

                @Override
                public void onAnimationEnd(Animation animationend) {
                    animate = false;
                }

                @Override
                public void onAnimationRepeat(Animation animationend) {

                }
            });
            point10.startAnimation(animationend);
        }
    }

    public void play(View view) {
        play.setAlpha(0);
        allPoint.setAlpha(0);
        whiteBack.setAlpha(0);
        play.setClickable(false);
        allPoint.setClickable(false);
        whiteBack.setClickable(false);
        startTimer();
    }
}
