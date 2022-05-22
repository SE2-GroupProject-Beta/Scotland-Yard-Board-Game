package com.example.scotland_yard_board_game.client;

import static com.example.scotland_yard_board_game.R.drawable.circle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.scotland_yard_board_game.R;
import com.ortiz.touchview.TouchImageView;

public class GameScreen extends AppCompatActivity { // extends View {
    private static final String TAG = "GameScreen";
    private ConstraintLayout gameScreenLayout;
    private TouchImageView gameBoardView; //  = null;
    private Shape circle;
    private final int BOARD_MAX_X = 4368;
    private final int BOARD_MAX_Y = 3312;

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
        /*
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);

        circle.draw(canvas, paint);
        circle.resize(20, 20); */

        /*
        LayoutParams params = new LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT
        );
        params.setMargins(left, top, right, bottom);
        yourbutton.setLayoutParams(params);
         */
        View circleView = findViewById(R.id.circle);
        // circleView.setLeft(700);
        // circleView.setTop(150);
        // circleView.requestLayout();

        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) circleView.getLayoutParams();
        // params.setMargins(700, 150, 750, 200);
        params.setMargins(900, params.topMargin, params.rightMargin, params.bottomMargin);

        /*
        public static void setMarginLeft(View v, int left) {
        ViewGroup.MarginLayoutParams params =
                                    (ViewGroup.MarginLayoutParams)v.getLayoutParams();
        params.setMargins(left, params.topMargin,
                                params.rightMargin, params.bottomMargin);
}
         */



        gameBoardView.setOnTouchListener((view, motionEvent) -> {
            int maxScreenWidth = gameScreenLayout.getWidth(); // screen size
            int maxScreenHeight = gameScreenLayout.getHeight();

            int screenX = (int) motionEvent.getX(); // touched screen coordinates
            int screenY = (int) motionEvent.getY();



            RectF rectF = gameBoardView.getZoomedRect(); // current left, top, zoom factor
            double left = rectF.left;
            double top = rectF.top;
            double zoomFactor = gameBoardView.getCurrentZoom();
            double right = rectF.right; // probably not needed
            double bottom = rectF.bottom; // probably not needed
            double boardWidth = rectF.width();

            // calculation of currentBoardX and currentBoardY given screenX and -Y
            double conversionFactor = (double) BOARD_MAX_Y / maxScreenHeight; // 5.710344827586207
            double currentBoardWidth = (double) (BOARD_MAX_X * zoomFactor / conversionFactor);
            int offsetX = (int) (maxScreenWidth / 2 - currentBoardWidth / 2);
            int negativeOffsetX = 0;
            int currentBoardX;
            if (offsetX > 0) {
                currentBoardX = (int) ((screenX - offsetX) * conversionFactor / zoomFactor);
            } else {
                negativeOffsetX = (int) (left * BOARD_MAX_X * (zoomFactor * 2) / conversionFactor); // todo: not correct yet
                currentBoardX = (int) (screenX * conversionFactor / zoomFactor) + negativeOffsetX;
            }
            int negativeOffsetY = (int) (top * BOARD_MAX_Y * (zoomFactor * 2) / conversionFactor); // todo: not correct yet
            int currentBoardY = (int) (screenY * conversionFactor / zoomFactor) + negativeOffsetY;

            // calculation of screenX and screenY given currentBoardX and -Y
            screenX = (int) (703 * zoomFactor / conversionFactor + offsetX); // currentBoardX = 703 (station 9)



            Log.d(TAG, "onCreate: maxScreenWidth = " + maxScreenWidth +
                    ", maxScreenHeight = " + maxScreenHeight);
            Log.d(TAG, "onCreate: left = " + left + ", top = " + top +
                    ", zoom factor = " + zoomFactor);
            // Log.d(TAG, "onCreate: right = " + right + ", bottom = " + bottom);
            Log.d(TAG, "onCreate: screenX, screenY = " + screenX + ", " + screenY);
            Log.d(TAG, "onCreate: conversionFactor = " + conversionFactor);
            // Log.d(TAG, "onCreate: currentBoardWidth = " + currentBoardWidth);
            /* Log.d(TAG, "onCreate: offsetX = " + offsetX);
            if (offsetX < 0 ) {
                Log.d(TAG, "onCreate: offsetX < 0");
            } */
            Log.d(TAG, "onCreate: negativeOffsetX = " + negativeOffsetX +
                    ", negativeOffsetY = " + negativeOffsetY);
            Log.d(TAG, "***** onCreate: currentBoardX = " + currentBoardX +
                    ", currentBoardY = " + currentBoardY + " *****");
            Log.d(TAG, "onCreate: delta-height = " + (bottom - top));
            Log.d(TAG, "onCreate: boardWidth = " + boardWidth);


            return true;
        });
    }
    /*
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int screenX = (int) motionEvent.getX(); // touched screen coordinates
        Log.d(TAG, "onTouchEvent: screenX = " + screenX);
        return super.onTouchEvent(motionEvent);
    } */
}
