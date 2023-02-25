package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3500;

    // Variables
    Animation animasi;
    ImageView logo;

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Animations
        animasi = AnimationUtils.loadAnimation(this,R.anim.vertikal_animasi);

        // Hooks
        logo = findViewById(R.id.idLogoSplashScreen);

        logo.setAnimation(animasi);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                boolean FirstTime = onBoardingScreen.getBoolean("firstTime", false);

                    Intent intent = new Intent(MainActivity.this,OnBoarding.class);
                    startActivity(intent);
                    finish();

//                }

            }
        }, SPLASH_SCREEN);

    }
}