package com.app.phonebook.LoadingScreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.app.phonebook.Home.Home;
import com.app.phonebook.R;

public class SplashScreen extends AppCompatActivity {

    private TextView txtJulius, txtAnthony, txtEstrada;

    private Animation moveUp, moveDown, fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initViews();
        initAnimations();
        startAnimations();
        goHome();

    }

    private void goHome() {
        Thread loading = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2 * 2000);
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        loading.start();

    }

    private void startAnimations() {
        txtJulius.startAnimation(moveDown);
        txtAnthony.startAnimation(fadeIn);
        txtEstrada.startAnimation(moveUp);
    }

    private void initAnimations() {
        moveUp = AnimationUtils.loadAnimation(this, R.anim.move_up);
        moveDown = AnimationUtils.loadAnimation(this, R.anim.move_down);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
    }

    private void initViews() {
        txtJulius = findViewById(R.id.txt1);
        txtAnthony = findViewById(R.id.txt2);
        txtEstrada = findViewById(R.id.txt3);
    }

}
