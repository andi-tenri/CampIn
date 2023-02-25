package com.example.campin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class OnBoarding extends AppCompatActivity {

    // Variabel
    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button btnMulai;
    Animation animation;
    Session session;

    // btnNext
    int currentPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        session                 = new Session(this);

        // Hooks
        viewPager = findViewById(R.id.slider_view);
        dotsLayout = findViewById(R.id.dots);
        btnMulai = findViewById(R.id.btn_mulai);

        // Call Adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        // Dots
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoarding.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        if (session.loggedin()) {
            startActivity(new Intent(OnBoarding.this, BerandaActivity.class));
            finish();
        }

    }

    public void skip(View view){

        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

    private void addDots(int position){

        dots = new TextView[4];
        dotsLayout.removeAllViews();

        for(int i=0; i<dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(37);

            dotsLayout.addView(dots[i]);
        }

        if(dots.length >0){
            dots[position].setTextColor(getResources().getColor(R.color.color1));
        }


    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);

            if (position == 0) {
                btnMulai.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                btnMulai.setVisibility(View.INVISIBLE);
            } else if (position == 2) {
                btnMulai.setVisibility(View.INVISIBLE);
            } else {
                animation = AnimationUtils.loadAnimation(OnBoarding.this,R.anim.horizontal_animasi);
                btnMulai.setAnimation(animation);
                btnMulai.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}