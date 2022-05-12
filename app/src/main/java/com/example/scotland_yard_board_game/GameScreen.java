package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ortiz.touchview.TouchImageView;


public class GameScreen extends AppCompatActivity { // implements View.OnTouchListener {
    private static final String TAG = "GameScreen";

    TouchImageView gameBackgroundView;
    // volatile int zoom;

    @SuppressLint("ClickableViewAccessibility") // todo: care about this later
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        gameBackgroundView = findViewById(R.id.gameBackgroundView);

        // zoom = (int) gameBackgroundView.getCurrentZoom();
        gameBackgroundView.setOnTouchListener((view, motionEvent) -> {
            //int x = (int) motionEvent.getRawX(); // getX() shows screen pixels, not jpg pixels
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            float screenX = motionEvent.getRawX();
            float screenY = motionEvent.getRawY();
            float viewX = screenX - location[0];
            float viewY = screenY - location[1];

            // Container, auf den dann den Listener

            // also tried:
            // int x = (int) motionEvent.getScaleX(); // not a method of motionEvent
            // int x = (int) motionEvent.getRawX();
            // int x = (int) motionEvent.getPointerCount();

            Log.d(TAG, "onCreate: x = " + viewX); // todo: correct coordinates
            Log.d(TAG, "onCreate: y = " + viewY); // todo: correct coordinates

            return true; // true if the event was handled, false if it should be passed on to child view
        });
    }
}