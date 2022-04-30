package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HostNicknameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_nickname_screen);
    }

    public void goLobbyScreen(View view){

        Intent LobbyScreen = new Intent(this, LobbyScreen.class);
        startActivity(LobbyScreen);
    }
}