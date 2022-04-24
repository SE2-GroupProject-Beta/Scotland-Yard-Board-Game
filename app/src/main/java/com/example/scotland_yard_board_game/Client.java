package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;


public class Client extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "Client";
    int PORT = 9999;
    DataOutputStream outToServer;
    BufferedReader inFromServer;
    String result;

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground: Call the Server");

        try {
            Socket clientSocket = new Socket(InetAddress.getLocalHost(), PORT);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes("1234\n"); //every Input must be a line and end with \n
            result = inFromServer.readLine();
            Log.d(TAG, "doInBackground: " + result);
            outToServer.flush();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void sendMessage() {

    }
}

/*


        try {
            clientSocket = new Socket(DOMAIN_NAME, PORT);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes(matrikelnummer + "\n"); //every Input must be a line and end with \n
            result = inFromServer.readLine();

            outToServer.flush();
            clientSocket.close();
 */