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
    TextView hostNameIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_screen);

        //nickname
        //find output TextView
        hostNameOut = findViewById(R.id.player1View);

        //get value from previous activity
        hostString = getIntent().getExtras().getString("Value");
        hostNameOut.setText(hostString);

        //TextView, find source of input
        hostNameIn = (TextView) findViewById(R.id.player1View);
    }


    //method with intent to transition to GameScreen (add method to onClick in LobbyScreen.xml)
    public void goGameScreen(View view){

        Intent GameScreen = new Intent(this, GameScreen.class);
        //pass host nickname to GameScreen
        hostString = hostNameIn.getText().toString();
        GameScreen.putExtra("Val", hostString);
        startActivity(GameScreen);
    }

}