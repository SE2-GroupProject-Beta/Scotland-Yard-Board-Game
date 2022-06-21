package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.scotland_yard_board_game.R;
import com.example.scotland_yard_board_game.server.ServerStart;

import java.io.IOException;

public class HostNicknameScreen extends AppCompatActivity {

    //nickname variables
    EditText nicknameField;
    String nickname;
    static ClientData clientData;   //static, global variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_nickname_screen);

        //editText nickname, find source of input
        nicknameField = (EditText) findViewById(R.id.editTextHostName);

        try {
            new ServerStart(getApplicationContext());
            ClientStart client = new ClientStart(getApplicationContext(), true); //Host is always Mr.X
            this.clientData = client.getClientData(); //receive data from clientData
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void goLobbyScreen(View view){

        nickname = nicknameField.getText().toString();
        clientData.setNickname(nickname);           //set nickname on Server
        LobbyScreen.clientData = this.clientData;   //pass data to LobbyScreen
        Intent LobbyScreen = new Intent(this, LobbyScreen.class);
        startActivity(LobbyScreen);
        finish();           //close activity HostNicknameScreen
    }



}