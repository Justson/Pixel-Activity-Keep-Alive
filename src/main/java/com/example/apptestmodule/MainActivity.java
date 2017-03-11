package com.example.apptestmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.just.pixelsdk.ActivityManager;
import com.just.pixelsdk.PixelActivityUnion;
import com.just.pixelsdk.PointActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        PixelActivityUnion.with(this)//
        .targetActivityClazz(PointActivity.class)//
        .setActiviyManager(ActivityManager.getInstance())//
        .args(null)//
        .start();


    }
}
