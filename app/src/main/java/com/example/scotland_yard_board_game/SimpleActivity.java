package com.example.scotland_yard_board_game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SimpleActivity extends AppCompatActivity {
    private static final String TAG = "SimpleActivity";
    Button sendTextButton;
    EditText textInput;
    EditText textResult;

    SimpleHandlerThread simpleHandlerThread;
    Handler handler;

    int toggle = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        sendTextButton = findViewById(R.id.sendTextButton);
        textInput = findViewById(R.id.textInput);
        textResult = findViewById(R.id.textResult);

        sendTextButton.setOnClickListener(view -> {
            sendText();
        });

        simpleHandlerThread = new SimpleHandlerThread("SimpleHandlerThread");
        simpleHandlerThread.start();
        Looper looper = simpleHandlerThread.getLooper(); // can be called inside next line, but
                // like this it makes the role of the Looper clearer
        handler = new Handler(looper);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleHandlerThread.quit(); // todo: only quit if running...
    }

    public void sendText() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run, toggle: " + toggle);
                // SystemClock.sleep(2000);
                if (toggle++ % 2 == 0){
                    textResult.setText("Button was pressed");
                } else {
                    textResult.setText("it was pressed again...");
                }
            }
        });
    }

}
