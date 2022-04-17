package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText ipAddress, sendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipAddress = (EditText) findViewById(R.id.ipAddress);
        sendData = (EditText) findViewById(R.id.sendData);

    }

    public void button_click(View view) {

    }

    class BackgroundTask extends AsyncTask<String, Void, String> {
        Socket socket;
        DataOutputStream dataOutputStream;
        String ip, message;


        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }


}