package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.scotland_yard_board_game.R;

public class LobbyScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_screen);
    }


    //method with intent to transition to GameScreen (add method to onClick in LobbyScreen.xml)
    public void goGameScreen(View view){

        Intent GameScreen = new Intent(this, GameScreen.class);
        startActivity(GameScreen);
    }

}