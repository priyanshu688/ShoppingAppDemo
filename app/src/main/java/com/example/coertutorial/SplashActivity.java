package com.example.coertutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      SingleTask singleTask = (SingleTask) getApplication();
      sp = singleTask.getSharedPreferences();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);
        TextView tv = findViewById(R.id.mytitle);

       Animation anim = AnimationUtils.loadAnimation(this,R.anim.move_down);

       tv.setAnimation(anim);

       anim.setAnimationListener(new Animation.AnimationListener() {
           @Override
           public void onAnimationStart(Animation animation) {

           }

           @Override
           public void onAnimationEnd(Animation animation) {
              boolean status = sp.getBoolean("status",false);
              if (status){
                  Intent in = new Intent(SplashActivity.this, HomePage.class);
                  startActivity(in);
                  finish();
              }else{
                  Intent in = new Intent(SplashActivity.this, LoginActivity.class);
                  startActivity(in);
                  finish();
              }

           }

           @Override
           public void onAnimationRepeat(Animation animation) {

           }
       });
    }
}