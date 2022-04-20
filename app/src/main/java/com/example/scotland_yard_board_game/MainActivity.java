package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText ipAddress, sendData;
    public static final int PORT = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipAddress = (EditText) findViewById(R.id.ipAddress); // at the moment: 10.0.0.79
        sendData = (EditText) findViewById(R.id.sendData);

        Thread thread = new Thread(new Server());
        thread.start();
    }


    class Server implements Runnable {

        ServerSocket serverSocket;
        Socket socket;
        Handler handler = new Handler(); // parameterless handler is deprecated

        DataInputStream dataInputStream;
        String message;


        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(PORT);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Waiting for client",
                                Toast.LENGTH_LONG).show();
                    }
                });

                while(true) {
                    socket = serverSocket.accept();
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    message = dataInputStream.readUTF();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Message received from client: " +
                                    message, Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void button_click(View view) {
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(ipAddress.getText().toString(),
                sendData.getText().toString()); // .execute calls doInBackground internally, may *not* be called manually!
    }

    class BackgroundTask extends AsyncTask<String, Void, String> { //AsyncTask<params, progress, result>
        Socket clientSocket;
        DataOutputStream dataOutputStream;
        String ip, message;


        @Override
        protected String doInBackground(String... params) {
            ip = ""; // params[0]; // first parameter is the ip-address
            message = params[1]; // second parameter is the message
            try {
                clientSocket = new Socket(ip, PORT);
                dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                dataOutputStream.writeUTF(message);
                dataOutputStream.close();
                clientSocket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


}