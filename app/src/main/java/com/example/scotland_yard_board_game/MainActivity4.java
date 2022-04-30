package com.example.scotland_yard_board_game;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.scotland_yard_board_game.ClientHandlerThread3.MESSAGE_TO_PLAYERS;

public class MainActivity4 extends AppCompatActivity {
    private static final String TAG = "MainActivity4";
    Button hostGameButton;
    Button joinGameButton;
    Button sendMessageToServerButton;
    EditText messageToServer;
    EditText messageFromServer;

    private ServerHandlerThread4 serverHandlerThread =
            new ServerHandlerThread4("ServerHandlerThread4");
    private ClientHandlerThread4 clientHandlerThread =
            new ClientHandlerThread4("ClientHandlerThread4");

    private boolean isMisterX = false; // todo: change to true

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

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
        clientHandlerThread.start();
        // Handler clientHandler = new Handler(clientHandlerThread.getLooper());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // serverHandlerThread.quit(); // todo: only quit when actually running
        clientHandlerThread.quit();
    }

    private void sendMessageToServer() {
        Log.d(TAG, "sendMessageToServer");

        Message message = Message.obtain(clientHandlerThread.getHandler());
        message.what = MESSAGE_TO_PLAYERS;
        message.obj = "x 114 a 56 b 15 0"; // the actual message as string

        clientHandlerThread.getHandler().sendMessage(message);


        String transferMessage = clientHandlerThread.getTransferMessage(); // todo: not correct
        messageFromServer.setText(transferMessage);

    }
}
