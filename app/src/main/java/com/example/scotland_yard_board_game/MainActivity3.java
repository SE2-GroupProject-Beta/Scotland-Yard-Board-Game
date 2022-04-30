package com.example.scotland_yard_board_game;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.scotland_yard_board_game.ClientHandlerThread3.MESSAGE_TO_PLAYERS;

public class MainActivity3 extends AppCompatActivity {
    private static final String TAG = "MainActivity3";
    Button hostGameButton;
    Button joinGameButton;
    Button sendMessageToServerButton;
    EditText messageToServer;
    EditText messageFromServer;

    private ServerHandlerThread serverHandlerThread =
            new ServerHandlerThread("ServerHandlerThread");
    private ClientHandlerThread3 clientHandlerThread3 =
            new ClientHandlerThread3("ClientHandlerThread");

    private boolean isMisterX = false; // todo: change to true

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        hostGameButton = findViewById(R.id.hostGameButton);
        joinGameButton = findViewById(R.id.joinGameButton);
        sendMessageToServerButton = findViewById(R.id.sendMessageToServerButton);
        messageToServer = findViewById(R.id.messageToServer);
        messageFromServer = findViewById(R.id.messageFromServer);

        sendMessageToServerButton.setOnClickListener(view -> {
            sendMessageToServer();
        });


        if (isMisterX) {
            serverHandlerThread.start();
        }
        clientHandlerThread3.start();
        // Handler clientHandler = new Handler(clientHandlerThread.getLooper());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // serverHandlerThread.quit(); // todo: only quit when actually running
        clientHandlerThread3.quit();
    }

    private void sendMessageToServer() {
        Log.d(TAG, "sendMessageToServer");

        Message message = Message.obtain(clientHandlerThread3.getHandler());
        message.what = MESSAGE_TO_PLAYERS;
        message.obj = "x 114 a 56 b 15 0"; // the actual message as string

        clientHandlerThread3.getHandler().sendMessage(message);


        String transferMessage = clientHandlerThread3.getTransferMessage(); // todo: not correct
        messageFromServer.setText(transferMessage);

    }
}
