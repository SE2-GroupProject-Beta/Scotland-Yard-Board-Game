package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.annotation.SuppressLint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scotland_yard_board_game.R;
import com.ortiz.touchview.TouchImageView;

import java.util.Objects;

public class GameScreen extends AppCompatActivity { // extends View {
    private static final String TAG = "GameScreen";

    private ConstraintLayout gameScreenLayout;
    private TouchImageView gameBoardView;
    private ConstraintLayout journeyTableLayout;
    private ViewGroup.MarginLayoutParams moveCircle;

    //nickname
    TextView hostNameOut;
    String hostString;

    //ticket count (adjust maximum)
    int taxiTickets = 10;

    //implement draggable player button
    /*
    private Button button;
    private static final String BUTTON_VIEW_TAG = "DRAGGABLE BUTTON"; */

    private TextView showBoardX; // todo: delete later
    private TextView showBoardY;

    private final int BOARD_MAX_X = 4368;
    private final int BOARD_MAX_Y = 3312;
    // private int getBoardX = 0; todo: delete later
    int player1screenX;
    int player1screenY;

    @SuppressLint("ClickableViewAccessibility") // todo: remove later?
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide(); //hides the action bar
        Objects.requireNonNull(getSupportActionBar()).hide(); // todo: hides the action bar, suggestion to change to this line
        setContentView(R.layout.activity_game_screen);

        gameScreenLayout = findViewById(R.id.gameScreenLayout);
        gameBoardView = findViewById(R.id.gameBoardView);
        gameBoardView.setMaxZoom(6);

        journeyTableLayout = findViewById(R.id.journeyTableLayout);
        //nickname on GameScreen
        hostNameOut = findViewById(R.id.MrXNameGameView);            //find TextView for Host Nickname output
        hostString = getIntent().getExtras().getString("Val");  //get value from previous activity
        hostNameOut.setText(hostString);                            //setText to value of hostString variable


        showBoardX = findViewById(R.id.showBoardX);
        showBoardY = findViewById(R.id.showBoardY);

        // side bar buttons
        // memory for journeyTableButton and journeyTableLayout
        // journeyTableButton
        // Button journeyTableButton = (Button) findViewById(R.id.journeyTableButton); // todo: is never used
        // close journeyTable
        // Button closeJourneyTableButton = (Button) findViewById(R.id.closeJTButton); // todo: is never used

        // initialization of test stations, todo: remove later
        /* x- and y-coordinates of stations 1, 2, 3, 8 and 9 (0: unused)
            0, 0, 0
            1, 552, 162
            2, 1323, 104
            3, 1816, 111
            4, 2118, 87
            5, 3332, 128
            6, 3698, 132
            7, 4072, 156
            8, 402, 334
            9, 703, 351
             */
        int[][] station = new int[2][10]; // int[x/y][station number]
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                station[i][j] = 0;
            }
        }
        station[0][1] = 552;
        station[1][1] = 162;
        station[0][2] = 1323;
        station[1][2] = 104;
        station[0][3] = 1816;
        station[1][3] = 111;
        station[0][8] = 402;
        station[1][8] = 334;
        station[0][9] = 703;
        station[1][9] = 351;

        View circleView = findViewById(R.id.circle); // to move the circle
        moveCircle = (ViewGroup.MarginLayoutParams) circleView.getLayoutParams();
        // params.setMargins(600, 400, params.rightMargin, params.bottomMargin); // todo: delete later

        gameBoardView.setOnTouchListener((view, motionEvent) -> {
            int maxScreenWidth = gameScreenLayout.getWidth(); // screen size
            int maxScreenHeight = gameScreenLayout.getHeight();
            double conversionFactor = (double) BOARD_MAX_Y / maxScreenHeight; // on Samsung: ~4.88

            int touchedScreenX = (int) motionEvent.getX(); // touched screen coordinates
            int touchedScreenY = (int) motionEvent.getY();

            RectF rectF = gameBoardView.getZoomedRect(); // current left, top, zoom factor
            double left = rectF.left;
            double top = rectF.top;
            double zoomFactor = gameBoardView.getCurrentZoom();
            // double right = rectF.right; // todo: probably not needed
            // double bottom = rectF.bottom; // todo: probably not needed
            // double boardWidth = rectF.width(); // todo: probably not needed

            // calculation of getBoardX and getBoardY given touchedScreenX and -Y
            // double conversionFactor = (double) BOARD_MAX_Y / maxScreenHeight; // 5.710344827586207
            double currentBoardWidth = (BOARD_MAX_X * zoomFactor / conversionFactor);
            int offsetX = (int) (maxScreenWidth / 2 - currentBoardWidth / 2); // offset when boardWidth < maxScreenWidth()
            int getBoardX;
            int negativeOffsetX = 0;
            if (offsetX > 0) {
                getBoardX = (int) ((touchedScreenX - offsetX) * conversionFactor / zoomFactor);
            } else {
                negativeOffsetX = (int) (left * BOARD_MAX_X); // *** nächstes Heureka ***
                getBoardX = (int) (touchedScreenX * conversionFactor / zoomFactor) + negativeOffsetX;
            }
            int negativeOffsetY = (int) (top * BOARD_MAX_Y);
            int getBoardY = (int) (touchedScreenY * conversionFactor / zoomFactor) + negativeOffsetY;

            // print boardX and boardY to screen, todo: delete later
            showBoardX.setText("X = " + getBoardX);
            showBoardY.setText("Y = " + getBoardY);

            // calculation of screen x and y given board coordinates,
            // conversionFactor and zoomFactor
            int player1X = 703; // board coordinates station 9
            int player1Y = 351;

            // calculate closest distance of station
            int distance;
            int closestDistance = 2147483647; // max of int
            int deltaX;
            int deltaY;
            int closestStation = 0;

            for (int i = 0; i < 10; i++) {
                deltaX = getBoardX - station[0][i]; // not necessary to get absolute value,
                deltaY = getBoardY - station[1][i]; // because they are squared later

                distance = deltaX * deltaX + deltaY * deltaY;
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestStation = i;
                }
            }
            player1X = station[0][closestStation];
            player1Y = station[1][closestStation];

            // calculate player1screenX and -Y and set circle to these coordinates
            if (offsetX > 0) {
                player1screenX = (int) (player1X * zoomFactor / conversionFactor) + offsetX;
            } else {
                negativeOffsetX = (int) (left * BOARD_MAX_X);
                player1screenX = (int) ((player1X - negativeOffsetX) * zoomFactor / conversionFactor);
            }
            player1screenY = (int) ((player1Y - negativeOffsetY) * zoomFactor / conversionFactor);

            moveCircle.setMargins(player1screenX - 25, player1screenY - 25,
                    moveCircle.rightMargin, moveCircle.bottomMargin);

            Log.d(TAG, "onCreate: maxScreenWidth = " + maxScreenWidth +
                    ", maxScreenHeight = " + maxScreenHeight);
            Log.d(TAG, "onCreate: left = " + left + ", top = " + top +
                    ", zoom factor = " + zoomFactor);
            // Log.d(TAG, "onCreate: right = " + right + ", bottom = " + bottom);
            Log.d(TAG, "onCreate: touchedScreenX, touchedScreenY = " + touchedScreenX + ", " + touchedScreenY);
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
            // Log.d(TAG, "onCreate: boardWidth = " + boardWidth);

            return true;
        });
    }

    /* todo: not used yet, delete?
    public boolean onTouchEvent(MotionEvent motionEvent) { //
        int screenX = (int) motionEvent.getX(); // touched screen coordinates
        Log.d(TAG, "onTouchEvent: screenX = " + screenX);
        return super.onTouchEvent(motionEvent);
    } */

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
    /*
    private void findViews() {
        button = (Button) findViewById(R.id.charButton);
        button.setTag(BUTTON_VIEW_TAG);
    } */

    //implement LongClick and DragListener
    /*
    private void implementEvents() {
        button.setOnLongClickListener(this::onLongClick);

        findViewById(R.id.station1).setOnDragListener(this::onDrag);
        findViewById(R.id.station9).setOnDragListener(this::onDrag);
        findViewById(R.id.station46).setOnDragListener(this::onDrag);
    } */

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
        journeyTableLayout.setVisibility(View.VISIBLE);
    }

    //close journeyTableButton method
    public void cJTBClicked(View v) {
        journeyTableLayout.setVisibility(View.GONE);
    }

    //method to decrease taxi count value
    public void decreaseTaxiCount(View view){

        taxiTickets = taxiTickets -1;
        display(taxiTickets);
    }

    //method to display current taxi count value on textView
    private void display(int number){
        TextView displayTaxi = (TextView) findViewById(R.id.taxiTicketView1);
        displayTaxi.setText("" + number);
    }

}
