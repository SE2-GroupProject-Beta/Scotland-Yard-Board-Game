package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.ServerSocket;


public class MainActivity2 extends AppCompatActivity {

    // private static final String TAG = "Server";
    private boolean isHost = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button hostGameButton = findViewById(R.id.hostGameButton);
        Button joinGameButton = findViewById(R.id.joinGameButton);
        EditText messageToServer = findViewById(R.id.messageToServer);
        EditText messageFromServer = findViewById(R.id.messageFromServer);

        if (isHost) { // todo: only true at the moment, change to check whether player is really the host
            new Server().execute(); // todo: named variable necessary for Server?
        }


        joinGameButton.setOnClickListener(view -> { // todo: does this need to call AsyncTask?
            Toast.makeText(getApplicationContext(),
                    "Join Game-Button is not implemented yet...",
                     Toast.LENGTH_LONG).show();
            new Client().execute();
        });

    }


}
