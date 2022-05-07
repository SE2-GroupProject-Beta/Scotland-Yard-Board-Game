package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goToMainAsync = findViewById(R.id.goToMainAsync);
        goToMainAsync.setOnClickListener(view -> {
            Intent MainActivityAsync = new Intent(this, MainActivityAsync.class);
            startActivity(MainActivityAsync);
        });

        /*
        int touchX = 0;
        int touchY = 0;
        int deltaX;
        int deltaY;
        int distance = 2147483647; // highest value for int
        int closestStation;
        // int[] station = new int[200]; // 0 bleibt frei

        Station[] station = new Station[200];

        for (int i = 1; i <= 199; i++) {
            deltaX = touchX - station[i].getX();
            deltaY = touchY - station[i].getY();
            if (deltaX * deltaX + deltaY * deltaY < distance) {
                closestStation = station[i].getNumber();
            }
        }

        String string = "123: 110, 114".replaceAll("\\s", ""); // removes whitespace
        String[] newString = string.split(":");
        station[123]. = newString[0]; // '123'
        // neighbourTaxi // ' 110, 114'
        // newString[1].replaceAll("\\s+",""); // vorher '\n'
        Integer.parseInt("324"); */



    }
}