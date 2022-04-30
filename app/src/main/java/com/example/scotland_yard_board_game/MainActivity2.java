package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity2";
    Server server = new Server();
    Client client = new Client();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button hostGameButton = findViewById(R.id.hostGameButton);
        Button joinGameButton = findViewById(R.id.joinGameButton);
        Button sendMessageToServerButton = findViewById(R.id.sendMessageToServerButton);
        EditText messageToServer = findViewById(R.id.messageToServer);
        EditText messageFromServer = findViewById(R.id.messageFromServer);

        hostGameButton.setOnClickListener(view -> {
            server.execute();
            client.execute();
        });

        joinGameButton.setOnClickListener(view -> { // todo: does this need to call AsyncTask?
            Toast.makeText(getApplicationContext(),
                    "Join Game-Button is not implemented yet...",
                    Toast.LENGTH_LONG).show();
            client.execute();
        });

        sendMessageToServerButton.setOnClickListener(view -> {

        });

        /* // use enter as alternative way to OnClickListener
        sendMessageToServerButton.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Log.d(TAG, "onKey: Enter pressed");
                    // send message to server
                    return true;
                }
                return false;
            }
        }); */


    }


}
