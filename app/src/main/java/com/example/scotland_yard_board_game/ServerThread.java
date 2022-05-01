package com.example.scotland_yard_board_game;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

public class ServerThread extends Thread {
    private Context context;


    public ServerThread(Context context) {
        this.context = context;
    }
// TODO: 4/28/2022 implement server initialization and gamestate management

    public void run() {

        Log.d(TAG, "Server Started");
        Database database = new Database(this.context);
    }



}
