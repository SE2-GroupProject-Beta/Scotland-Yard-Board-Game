package com.example.scotland_yard_board_game;

import android.os.HandlerThread;

public class ClientHandlerThread extends HandlerThread {
    private static final String TAG = "ClientHandlerThread";

    public ClientHandlerThread(String name) {
        super(name);
    }


}
