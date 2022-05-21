package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.scotland_yard_board_game.sideProjects.ActivityAsync;
import com.example.scotland_yard_board_game.sideProjects.DrawTest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goToTitleScreen = findViewById(R.id.goToTitleScreen);
        goToTitleScreen.setOnClickListener(view -> {
            Intent TitleScreen = new Intent(this, com.example.scotland_yard_board_game.client.TitleScreen.class);
            startActivity(TitleScreen);
        });
        Button goToGameScreen = findViewById(R.id.goToGameScreen);
        goToGameScreen.setOnClickListener(view -> {
            Intent GameScreen = new Intent(this, com.example.scotland_yard_board_game.client.GameScreen.class);
            startActivity(GameScreen);
        });
        Button goToAsync = findViewById(R.id.goToAsync);
        goToAsync.setOnClickListener(view -> {
            Intent ActivityAsync = new Intent(this, com.example.scotland_yard_board_game.sideProjects.ActivityAsync.class);
            startActivity(ActivityAsync);
        });
        Button goToDraw = findViewById(R.id.goToDraw);
        goToDraw.setOnClickListener(view -> {
            Intent DrawTest = new Intent(this, com.example.scotland_yard_board_game.sideProjects.DrawTest.class);
            startActivity(DrawTest);
        });

    }
}
