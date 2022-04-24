package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;



public class Client extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "Client";

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground: Call the Server");

        return null;
    }
}
