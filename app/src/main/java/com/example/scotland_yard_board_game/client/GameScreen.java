package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.annotation.SuppressLint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scotland_yard_board_game.R;
import com.ortiz.touchview.TouchImageView;

public class GameScreen extends AppCompatActivity { // extends View {
    private static final String TAG = "GameScreen";

    //implement draggable player button
    private Button button;
    private static final String BUTTON_VIEW_TAG = "DRAGGABLE BUTTON";

    Button jTB; //journeyTableButton
    ConstraintLayout jTL;
    Button cJTB; //close journeyTable

    private ConstraintLayout gameScreenLayout;
    private TouchImageView gameBoardView;
    private TextView showBoardX;
    private TextView showBoardY;
    private final int BOARD_MAX_X = 4368;
    private final int BOARD_MAX_Y = 3312;
    private int getBoardX = 0;

    @SuppressLint("ClickableViewAccessibility") // todo: remove later?
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //hides the action bar
        setContentView(R.layout.activity_game_screen);

        // findViews(); // todo: necessary?
        // implementEvents(); // todo: necessary?

        gameScreenLayout = (ConstraintLayout) findViewById(R.id.gameScreenLayout);
        gameBoardView = (TouchImageView) findViewById(R.id.gameBoardView);
        gameBoardView.setMaxZoom(5);

        showBoardX = (TextView) findViewById(R.id.showBoardX);
        showBoardY = (TextView) findViewById(R.id.showBoardY);

        //side bar buttons
        //memory for journeyTableButton and journeyTableLayout
        jTB = (Button) findViewById(R.id.journeyTableButton);
        jTL = (ConstraintLayout) findViewById(R.id.journeyTableLayout);
        cJTB = (Button) findViewById(R.id.closeJTButton);

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

            // show boardX and boardY on screen, todo: delete later
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
            if (offsetX < 0) {
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

    // @Override // todo: @Override necessary?
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemTaxi:
                Toast.makeText(this, "Taxi selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemBus:
                Toast.makeText(this, "Bus selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemUnderground:
                Toast.makeText(this, "Underground selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemFerry:
                Toast.makeText(this, "Ferry selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    //Find view and set Tag to draggable view
    private void findViews() {
        button = (Button) findViewById(R.id.charButton);
        button.setTag(BUTTON_VIEW_TAG);
    }

    //implement LongClick and DragListener
    private void implementEvents() {
        button.setOnLongClickListener(this::onLongClick);

        findViewById(R.id.station1).setOnDragListener(this::onDrag);
        findViewById(R.id.station9).setOnDragListener(this::onDrag);
        findViewById(R.id.station46).setOnDragListener(this::onDrag);
    }

    //drag object
    //response to long press on a view
    public boolean onLongClick(View view) {
        // Create a new ClipData.Item from object's tag
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

        //This will create a new ClipDescription object within the
        //ClipData, and set its MIME type entry to "text/plain"
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        view.startDragAndDrop(data, shadowBuilder, view, 0);

        view.setVisibility(View.INVISIBLE);
        return true;
    }

    //called method at drag event
    public boolean onDrag(View view, DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();

        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // returns true to indicate that the View can accept the dragged data.
                    return true;
                }
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;
            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
                // Return true; the return value is ignored.
                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.
                //view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                view.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);
                // Gets the text data from the item.
                String dragData = item.getText().toString();

                // Displays a message containing the dragged data.
                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();

                // Turns off any color tints
                view.getBackground().clearColorFilter();
                view.invalidate();

                //remove dragged view, cast view into LinearLayout, add dragged view, set visible
                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                owner.removeView(v);
                LinearLayout container = (LinearLayout) view;
                container.addView(v);
                v.setVisibility(View.VISIBLE);

                // Returns true. DragEvent.getResult() will return true.
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
                view.getBackground().clearColorFilter();

                view.invalidate();

                // Does a getResult(), and displays what happened.
                if (event.getResult())
                    Toast.makeText(this, "Character has been moved", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Character has not been moved", Toast.LENGTH_SHORT).show();

                return true;
            //unknown action type received
            default:
                Log.e("DragDrop", "Unknown action type received by OnDragListener");
                break;
        }
        return false;

    }// onDrag done

    //journeyTableButton onClick method
    public void jTBClicked(View v) {
        jTL.setVisibility(View.VISIBLE);
    }

    //close journeyTableButton method
    public void cJTBClicked(View v) {
        jTL.setVisibility(View.GONE);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) { // todo: not used yet
        int screenX = (int) motionEvent.getX(); // touched screen coordinates
        Log.d(TAG, "onTouchEvent: screenX = " + screenX);
        return super.onTouchEvent(motionEvent);
    }
}
