package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ortiz.touchview.TouchImageView;


public class GameScreen extends AppCompatActivity {

    private TouchImageView mapZoom = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        mapZoom = (TouchImageView) findViewById(R.id.mapZoom);

        mapZoom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "I was touched", Toast.LENGTH_SHORT).show();

                int x = (int)event.getX();
                int y = (int)event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("TAG", "touched down: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("TAG", "moving: (" + x + ", " + y + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("TAG", "touched up");
                        break;
                }
                return true;
            }
        });
    }
}