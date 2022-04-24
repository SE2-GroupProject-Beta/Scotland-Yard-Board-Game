package com.example.scotland_yard_board_game;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class Server extends AsyncTask<Void, Void, Void> { // todo: static?
    private static final String TAG = "Server";
    // ServerSocket serverSocket = new ServerSocket(9999);


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


        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        Log.d(TAG, "onPostExecute: after Task");
    }
}