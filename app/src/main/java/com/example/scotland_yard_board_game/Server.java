package com.example.scotland_yard_board_game;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends AsyncTask<Void, Void, Void> { // todo: static?
    private static final String TAG = "Server";
    int PORT = 9999;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: before Task");

        /*
        Toast.makeText(getApplicationContext(), "Server started...",
                Toast.LENGTH_LONG).show(); */
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground: Respond to Client");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            InputStream inputStream;
            InputStreamReader inputStreamReader;
            BufferedReader bufferedReader;
            String message;

            OutputStream outputStream;
            DataOutputStream dataOutputStream;


                Socket socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                message = bufferedReader.readLine();



                outputStream = socket.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeUTF("This is our answer: " + message);
                dataOutputStream.close();
                socket.close();



        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        Log.d(TAG, "onPostExecute: after Task");
    }
}