package com.example.scotland_yard_board_game;

import android.os.HandlerThread;
import android.util.Log;

public class SimpleHandlerThread extends HandlerThread {
    private static final String TAG = "SimpleHandlerThread";

    public SimpleHandlerThread(String name) {
        super(name);
    }
}
