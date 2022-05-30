package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.scotland_yard_board_game.R;

public class LobbyScreen extends AppCompatActivity {

    //nickname
    TextView hostNameOut;
    String hostString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_screen);

        //nickname
        hostNameOut = findViewById(R.id.player1View);

        hostString = getIntent().getExtras().getString("Value");
        hostNameOut.setText(hostString);
    }


    //method with intent to transition to GameScreen (add method to onClick in LobbyScreen.xml)
    public void goGameScreen(View view){

        Intent GameScreen = new Intent(this, GameScreen.class);
        startActivity(GameScreen);
    }

}