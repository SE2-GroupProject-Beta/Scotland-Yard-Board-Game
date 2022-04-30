package com.example.scotland_yard_board_game;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

public class ClientHandlerThread4 extends HandlerThread {
    private static final String TAG = "ClientHandlerThread";
    public static final int MESSAGE_TO_PLAYERS = 1;

    private Handler handler;
    private String transferMessage = "";

    public ClientHandlerThread4(String name) {
        super(name);
        Log.d(TAG, "ClientHandlerThread4: constructor");
    }

    @Override
    protected void onLooperPrepared() { // initialize the handler
        Log.d(TAG, "onLooperPrepared");
        handler = new Handler(Looper.getMainLooper()) { // Handler with no Looper is deprecated, put it on main (?) loop
            // or (Looper.myLooper) ?

            @SuppressLint("HandlerLeak") // todo: make a separate static function, "Handler class should be static"
            @Override
            public void handleMessage(@NonNull Message message) { // @NonNull vor Message?
                switch (message.what) {
                    case MESSAGE_TO_PLAYERS:
                        Log.d(TAG, "handleMessage: MESSAGE_TO_PLAYERS, " +
                                "message.what: " + message.what + ", message.obj: " +
                                message.obj + ", before SystemClock.sleep(2000)");
                        SystemClock.sleep(2000); // long running task
                        Log.d(TAG, "handleMessage: MESSAGE_TO_PLAYERS, " +
                                "message.what: " + message.what + ", message.obj: " +
                                message.obj + ", after SystemClock.sleep(2000)");


                        // transferMessage = message.obj.toString();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public String getTransferMessage() {
        return  transferMessage;
    }

    public Handler getHandler() {
        return handler;
    }

}
