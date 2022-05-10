package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.scotland_yard_board_game.R;

public class JoinGameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game_screen);
    }

    public void goLobbyScreen(View view){

        Intent LobbyScreen = new Intent(this, LobbyScreen.class);
        startActivity(LobbyScreen);
    }
}