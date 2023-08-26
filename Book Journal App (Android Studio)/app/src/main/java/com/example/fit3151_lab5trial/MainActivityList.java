package com.example.fit3151_lab5trial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivityList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        getSupportFragmentManager().beginTransaction().replace(R.id.frag2,new Fragment1()).addToBackStack("f1").commit();
    }
}