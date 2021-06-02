package com.example.coertutorial;

import android.app.Application;
import android.content.SharedPreferences;

public class SingleTask extends Application {
    private SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("session",MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences(){
        return sp;
    }
}
