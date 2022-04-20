package com.example.scotland_yard_board_game;

import android.os.Handler;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Runnable {

    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    String message;
    Handler handler = new Handler();

    public static final int PORT = 9999;


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // Toast.makeText(getApplicationContext(), "Waiting for client",
                    //        Toast.LENGTH_SHORT).show();
                }
            });

            while(true) {
                socket = serverSocket.accept();
                dataInputStream = new DataInputStream(socket.getInputStream());
                message = dataInputStream.readUTF();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Toast.makeText(getApplicationContext(), "Message received from client: " +
                        //        message, Toast.LENGTH_SHORT).show();
                    }
                });

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
