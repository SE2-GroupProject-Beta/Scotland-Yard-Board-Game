package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.scotland_yard_board_game.R;

public class TitleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
    }

    public void goHostNicknameScreen(View view){

        Intent HostNicknameScreen = new Intent(this, HostNicknameScreen.class);
        startActivity(HostNicknameScreen);
    }
    public void goJoinGameScreen(View view){

        Intent JoinGameScreen = new Intent(this, JoinGameScreen.class);
        startActivity(JoinGameScreen);
    }
}