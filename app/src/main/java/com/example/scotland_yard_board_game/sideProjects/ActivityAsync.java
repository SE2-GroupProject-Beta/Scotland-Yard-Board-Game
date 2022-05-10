package com.example.scotland_yard_board_game.sideProjects;

import static java.lang.Character.isLowerCase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scotland_yard_board_game.R;


// to our development team: see instructions of use at the end of this file!

public class ActivityAsync extends AppCompatActivity {
    private static final String TAG = ActivityAsync.class.getSimpleName(); // ='MainActivityAsync'

    Button sendTextButton;
    Button backButton;
    EditText textInput;
    EditText textResult;

    SimpleHandlerThread simpleHandlerThread;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynchronous);

        // initialize variables from layout:
        sendTextButton = findViewById(R.id.sendTextButton);
        backButton = findViewById(R.id.backButton);
        textInput = findViewById(R.id.textInput);
        textResult = findViewById(R.id.textResult);

        // initialize onClickListeners:
        sendTextButton.setOnClickListener(view -> {
            String text = textInput.getText().toString();
            sendText(text);
        });
        backButton.setOnClickListener(view -> {
            Intent MainActivity = new Intent(this, com.example.scotland_yard_board_game.MainActivity.class);
            startActivity(MainActivity);
        });

        // start HandlerThread
        // note: Looper could be called inside 'new Handler()',
        // but like this it makes the role of the Looper clearer
        simpleHandlerThread = new SimpleHandlerThread("SimpleHandlerThread");
        simpleHandlerThread.start();
        Looper looper = simpleHandlerThread.getLooper();
        handler = new Handler(looper);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        if (simpleHandlerThread.isAlive()) {
            simpleHandlerThread.quit();
        }
    }

    // method sendText
    // show-case method to show the use of handler.post()
    public void sendText(String text) {
        Log.d(TAG, "sendText");

        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run");

                SystemClock.sleep(2000); // simulates slow network connection

                if (isLowerCase(text.charAt(0))) {
                    textResult.setText(text.toUpperCase());
                } else {
                    textResult.setText(text.toLowerCase());
                }
            }
        });
    }

    /*
    instructions:

    the HandlerThread is created once and doesn't need to be touched (in the end it will
    be destroyed automatically)

    to add a new asynchronous task:

    - in onCreate: add a new onClickListener (don't add it in layout.activity_*)
      and call the method that handles the action
    - in the called method: make an anonymous call with handler.post(), this call must implement the
      run() method, the general form being as follows:

      handler.post(new Runnable() {
          @Override
          public void run() {
              // do long-running task
          }
      }

      as an example, see method sendText() above.

      if the required next task should be done as the next task, use .postAtFrontOfQueue instead
      of .post

      note: I haven't implemented sendMessages at the moment, because for the time being
      we don't have many tasks yet. If there are a lot of tasks it could be easier
      with .sendMessage instead of .post

     */

}
