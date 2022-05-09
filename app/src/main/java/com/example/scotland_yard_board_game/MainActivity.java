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
            Intent MainActivityAsync = new Intent(this, com.example.scotland_yard_board_game.sideProjects.MainActivityAsync.class);
            startActivity(MainActivityAsync);
        });
        Button goToGameScreen = findViewById(R.id.goToGameScreen);
        goToGameScreen.setOnClickListener(view -> {
            Intent GameScreen = new Intent(this, GameScreen.class);
            startActivity(GameScreen);
        });
    }
}
