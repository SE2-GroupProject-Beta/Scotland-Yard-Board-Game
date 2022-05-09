package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ortiz.touchview.TouchImageView;


public class GameScreen extends AppCompatActivity { // implements View.OnTouchListener {
    private static final String TAG = "GameScreen";

    TouchImageView gameBackgroundView;
    volatile int zoom;

    @SuppressLint("ClickableViewAccessibility") // todo: care about this later
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        gameBackgroundView = findViewById(R.id.gameBackgroundView);

        zoom = (int) gameBackgroundView.getCurrentZoom();
        gameBackgroundView.setOnTouchListener((view, motionEvent) -> {
            // int x = (int) view.getX(); // getX() shows screen pixels, not jpg pixels
            // int x = (int) view.getScaleX();

            // int x = (int) motionEvent.getX();
            // int x = (int) motionEvent.getRawX();
            // int x = (int) motionEvent.getPointerCount();



            Log.d(TAG, "onCreate: zoom = " + zoom); // todo: not working yet
            return true; // true if the event was handled, false if it should be passed on to child view
        });




        /*
        gameBackgroundView.setOnTouchListener(view -> {
            onTouch(View view, MotionEvent motionevent);
                }


                onTouch(View view, MotionEvent motionEvent) {
            int x = (int) gameBackgroundView.getX();
            Log.d(TAG, "onCreate: x = " + x);

            return false;
        }); */
    }
    /*
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.getX()
        int x = (int) gameBackgroundView.getX();
        Log.d(TAG, "onTouch: x = " + x);
        return false;
    } */

    /*
        imageButton.setOnTouchListener(new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP){

                // Do what you want
                return true;
            }
            return false;
        }
        });
        */
        /*
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Log.d(TAG, "onTouch");
            int x = 0;
            x = (int) view.getX();
            Log.d(TAG, "onTouch: x = " + x);
            // view.getY();

            // String result = (String) motionEvent.getAction();
            return false;
        } */



}