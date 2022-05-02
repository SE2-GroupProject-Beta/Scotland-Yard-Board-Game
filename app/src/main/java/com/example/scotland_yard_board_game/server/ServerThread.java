package com.example.scotland_yard_board_game.server;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

public class ServerThread extends Thread {
    private Context context;
    private ServerDatabase serverDatabase;

    public ServerThread(Context context) {
        this.context = context;
    }
// TODO: 4/28/2022 implement server initialization and gamestate management

    public void run() {

        Log.d(TAG, "Server Started");
        this.serverDatabase = new ServerDatabase(this.context);
        int[] testStart = serverDatabase.getRandomStart(4);
        for (int a: testStart) {
            Log.d(TAG, String.valueOf(a));
        }
    }



}
