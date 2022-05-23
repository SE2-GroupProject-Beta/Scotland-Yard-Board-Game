package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.scotland_yard_board_game.R;
import com.ortiz.touchview.TouchImageView;

public class GameScreen extends AppCompatActivity { // extends View {
    private static final String TAG = "GameScreen";
    private ConstraintLayout gameScreenLayout;
    private TouchImageView gameBoardView;
    private TextView showBoardX;
    private TextView showBoardY;
    private Shape circle;
    private final int BOARD_MAX_X = 4368;
    private final int BOARD_MAX_Y = 3312;
    private int getBoardX = 0;

    private Paint paint = new Paint();
    private Path path = new Path();

    @SuppressLint("ClickableViewAccessibility") // todo: remove later?
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        // findViews(); // todo: necessary?
        // implementEvents(); // todo: necessary?

        gameScreenLayout = (ConstraintLayout) findViewById(R.id.gameScreenLayout);
        gameBoardView = (TouchImageView) findViewById(R.id.gameBoardView);
        gameBoardView.setMaxZoom(5);

        showBoardX = (TextView) findViewById(R.id.showBoardX);
        showBoardY = (TextView) findViewById(R.id.showBoardY);

        View circleView = findViewById(R.id.circle); // to move the circle, todo: move to onTouch method
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) circleView.getLayoutParams();
        params.setMargins(600, 400, params.rightMargin, params.bottomMargin);

        gameBoardView.setOnTouchListener((view, motionEvent) -> {
            int maxScreenWidth = gameScreenLayout.getWidth(); // screen size
            int maxScreenHeight = gameScreenLayout.getHeight();

            int getScreenX = (int) motionEvent.getX(); // touched screen coordinates
            int getScreenY = (int) motionEvent.getY();

            RectF rectF = gameBoardView.getZoomedRect(); // current left, top, zoom factor
            double left = rectF.left;
            double top = rectF.top;
            double zoomFactor = gameBoardView.getCurrentZoom();
            // double right = rectF.right; // probably not needed
            // double bottom = rectF.bottom; // probably not needed
            double boardWidth = rectF.width();

            // calculation of getBoardX and getBoardY given getScreenX and -Y
            double conversionFactor = (double) BOARD_MAX_Y / maxScreenHeight; // 5.710344827586207
            double currentBoardWidth = (double) (BOARD_MAX_X * zoomFactor / conversionFactor);
            int offsetX = (int) (maxScreenWidth / 2 - currentBoardWidth / 2);
            int negativeOffsetX = 0;
            getBoardX = 0;
            if (offsetX > 0) {
                getBoardX = (int) ((getScreenX - offsetX) * conversionFactor / zoomFactor);
            } else {
                negativeOffsetX = (int) (left * BOARD_MAX_X * (zoomFactor * 2) / conversionFactor); // todo: not correct yet
                getBoardX = (int) (getScreenX * conversionFactor / zoomFactor) + negativeOffsetX;
            }
            int negativeOffsetY = (int) (top * BOARD_MAX_Y * (zoomFactor * 2) / conversionFactor); // todo: not correct yet
            int getBoardY = (int) (getScreenY * conversionFactor / zoomFactor) + negativeOffsetY;

            // show boardX and boardY on screen
            showBoardX.setText("X = " + getBoardX);
            showBoardY.setText("Y = " + getBoardY);


            // calculation of getScreenX and getScreenY given getBoardX and -Y, conversionFactor = 5.71
            // getScreenX = (int) (703 * zoomFactor / conversionFactor + offsetX); // getBoardX = 703, y = 351 (station 9)

            Log.d(TAG, "onCreate: maxScreenWidth = " + maxScreenWidth +
                    ", maxScreenHeight = " + maxScreenHeight);
            Log.d(TAG, "onCreate: left = " + left + ", top = " + top +
                    ", zoom factor = " + zoomFactor);
            // Log.d(TAG, "onCreate: right = " + right + ", bottom = " + bottom);
            Log.d(TAG, "onCreate: getScreenX, getScreenY = " + getScreenX + ", " + getScreenY);
            Log.d(TAG, "onCreate: conversionFactor = " + conversionFactor);
            // Log.d(TAG, "onCreate: currentBoardWidth = " + currentBoardWidth);
            Log.d(TAG, "onCreate: offsetX = " + offsetX);
            if (offsetX < 0 ) {
                Log.d(TAG, "onCreate: offsetX < 0");
            }
            Log.d(TAG, "onCreate: negativeOffsetX = " + negativeOffsetX +
                    ", negativeOffsetY = " + negativeOffsetY);
            Log.d(TAG, "***** onCreate: getBoardX = " + getBoardX +
                    ", getBoardY = " + getBoardY + " *****");
            Log.d(TAG, "onCreate: boardWidth = " + boardWidth);

            return true;
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) { // todo: not used yet
        int screenX = (int) motionEvent.getX(); // touched screen coordinates
        Log.d(TAG, "onTouchEvent: screenX = " + screenX);
        return super.onTouchEvent(motionEvent);
    }
}
