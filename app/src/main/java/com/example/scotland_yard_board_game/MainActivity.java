package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText ipAddress, sendData, writeResult;
    public static final int PORT = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipAddress = findViewById(R.id.ipAddress); // at the moment: 10.0.0.79
        sendData = findViewById(R.id.sendData);
        writeResult = findViewById(R.id.writeResult);

        Thread thread = new Thread(new ServerThread());
        thread.start();
    }


}