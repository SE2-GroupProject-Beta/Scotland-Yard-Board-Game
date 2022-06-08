package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scotland_yard_board_game.R;

public class HostNicknameScreen extends AppCompatActivity {

    //nickname variables
    EditText hostNameIn;
    String hostString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_nickname_screen);

        //editText nickname, find source of input
        hostNameIn = (EditText) findViewById(R.id.editTextHostName);
    }

    public void goLobbyScreen(View view){

        Intent LobbyScreen = new Intent(this, LobbyScreen.class);
        //nickname intent
        hostString = hostNameIn.getText().toString();
        LobbyScreen.putExtra("Value", hostString);
        startActivity(LobbyScreen);

    }
}