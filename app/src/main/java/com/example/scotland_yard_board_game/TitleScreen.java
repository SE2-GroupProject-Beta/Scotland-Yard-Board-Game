package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TitleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
    }

    public void goHostNicknameScreen(){ // todo: change by Klemens: I took out the parameter View view because SonarCloud showed this to be a major error

        Intent HostNicknameScreen = new Intent(this, HostNicknameScreen.class);
        startActivity(HostNicknameScreen);
    }
    public void goJoinGameScreen(){ // todo: change by Klemens: same as above

        Intent JoinGameScreen = new Intent(this, JoinGameScreen.class);
        startActivity(JoinGameScreen);
    }
}
