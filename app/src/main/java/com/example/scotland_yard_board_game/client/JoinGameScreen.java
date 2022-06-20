package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.scotland_yard_board_game.R;
import com.example.scotland_yard_board_game.server.ServerStart;

import java.io.IOException;

public class JoinGameScreen extends AppCompatActivity {

    EditText nicknameField;
    String nickname;
    static ClientData clientData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game_screen);

        //editText nickname, find source of input
        nicknameField = (EditText) findViewById(R.id.NicknameText);

        try {
            ClientStart client = new ClientStart(getApplicationContext(), false);
            this.clientData = client.getClientData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void goLobbyScreen(View view){

        nickname = nicknameField.getText().toString();
        clientData.setNickname(nickname);
        LobbyScreen.clientData = this.clientData;
        Intent LobbyScreen = new Intent(this, LobbyScreen.class);
        startActivity(LobbyScreen);
        finish();
    }
}