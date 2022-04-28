package com.example.scotland_yard_board_game;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity3 extends AppCompatActivity {
    private ServerHandlerThread serverHandlerThread =
            new ServerHandlerThread("ServerHandlerThread");
    private ClientHandlerThread clientHandlerThread =
            new ClientHandlerThread("ClientHandlerThread");

    private boolean isMisterX = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        if (isMisterX) {
            serverHandlerThread.start();
        }
        clientHandlerThread.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serverHandlerThread.quit();
        clientHandlerThread.quit();
    }
}
