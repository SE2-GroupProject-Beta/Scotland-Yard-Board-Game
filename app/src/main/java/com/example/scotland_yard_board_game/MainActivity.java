package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.scotland_yard_board_game.client.ClientStart;
import com.example.scotland_yard_board_game.server.ServerStart;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goToTitleScreen = findViewById(R.id.goToTitleScreen);
        goToTitleScreen.setOnClickListener(view -> {
            Intent TitleScreen = new Intent(this,
                    com.example.scotland_yard_board_game.client.TitleScreen.class);
            startActivity(TitleScreen);
        });

    }
}
