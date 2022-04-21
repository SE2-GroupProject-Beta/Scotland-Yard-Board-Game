package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

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

        Thread thread = new Thread(new Server());
        thread.start();
    }


    class Server implements Runnable {

        ServerSocket serverSocket;
        Socket socket;
        Handler handler = new Handler(Looper.getMainLooper()); // parameterless handler is deprecated

        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String message;

        OutputStream outputStream;
        DataOutputStream dataOutputStream;


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
                    // I deliberately use each of the following steps in a sequence
                    // to make the wrapping process clearer
                    socket = serverSocket.accept();
                    inputStream = socket.getInputStream();
                    inputStreamReader = new InputStreamReader(inputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    message = bufferedReader.readLine();

                    outputStream = socket.getOutputStream();
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF("This is the message: " + message);
                    dataOutputStream.close();
                    socket.close();

                    /*
                     dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                    dataOutputStream.writeUTF(message);
                    dataOutputStream.close();
                    clientSocket.close();
                     */

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            writeResult.setText("This is the message: " + message);

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
        OutputStreamWriter outputStreamWriter;
        BufferedWriter bufferedWriter;


        DataOutputStream dataOutputStream;
        String ip, message;


        @Override
        protected String doInBackground(String... params) {
            ip = params[0]; // first parameter is the ip-address
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