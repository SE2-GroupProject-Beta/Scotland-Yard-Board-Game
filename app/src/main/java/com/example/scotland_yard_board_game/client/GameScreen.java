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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scotland_yard_board_game.R;
import com.example.scotland_yard_board_game.server.ServerDatabase;
import com.example.scotland_yard_board_game.server.ServerStation;
import com.ortiz.touchview.TouchImageView;

import java.util.Objects;

public class GameScreen extends AppCompatActivity { // extends View {
    private static final String TAG = "GameScreen";

    private ConstraintLayout gameScreenLayout;
    private TouchImageView gameBoardView;
    private ConstraintLayout journeyTableLayout;
    private ViewGroup.MarginLayoutParams player1ViewGroup;

    private Button taxiDrawButton;
    private Button busDrawButton;
    private Button undergroundDrawButton;
    private Button journeyTableButton;

    private final int BOARD_MAX_X = 4368;
    private final int BOARD_MAX_Y = 3312;

    private TextView showBoardX; // todo: delete before production release
    private TextView showBoardY;
    private TextView showTransport;

    private int[] touchedBoardCoordinates = new int[2];
    private int[] touchedScreenCoordinates = new int[2];
    private int[] player1BoardCoordinates = new int[2];
    private int[] player1ScreenCoordinates = new int[2];

    private final int TAXI_STATIONS = 199;
    private final int BUS_STATIONS = 62;
    private final int UNDERGROUND_STATIONS = 199;
    private final int MAX_TAXI_NEIGHBORS = 7;
    private final int MAX_BUS_NEIGHBORS = 5;
    private final int MAX_UNDERGROUND_NEIGHBORS = 4;

    private int[][] station = new int[200][2]; // int[x/y][station number]

    private int[][] taxiNeighbors = new int[TAXI_STATIONS + 1][MAX_TAXI_NEIGHBORS]; // int[station number][neighbors]
    private int[][] busNeighbors = new int[BUS_STATIONS + 1][MAX_BUS_NEIGHBORS]; // info: station number 0 not used, so
    private int[][] undergroundNeighbors = new int[UNDERGROUND_STATIONS + 1][MAX_UNDERGROUND_NEIGHBORS]; // 15 means 14 possible stations

    private ServerDatabase serverDatabase;
    private ServerStation serverStation;

    int[] selectionOfStations = new int[200];

    @SuppressLint("ClickableViewAccessibility") // todo: remove later?
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide(); //hides the action bar
        Objects.requireNonNull(getSupportActionBar()).hide(); // todo: hides the action bar, suggestion to change to this line
        setContentView(R.layout.activity_game_screen);

        gameScreenLayout = findViewById(R.id.gameScreenLayout);
        gameBoardView = findViewById(R.id.gameBoardView);
        journeyTableLayout = findViewById(R.id.journeyTableLayout);
        showBoardX = findViewById(R.id.showBoardX);
        showBoardY = findViewById(R.id.showBoardY);
        showTransport = findViewById(R.id.showTransport);
        taxiDrawButton = findViewById(R.id.taxiDrawButton);
        busDrawButton = findViewById(R.id.busDrawButton);
        undergroundDrawButton = findViewById(R.id.undergroundDrawButton);
        journeyTableButton = findViewById(R.id.journeyTableButton);

        View player1View = findViewById(R.id.player1); // to move the circle (player)
        player1ViewGroup = (ViewGroup.MarginLayoutParams) player1View.getLayoutParams();

        gameBoardView.setMaxZoom(6); // augment zoom

        // selectionOfStations = new int[200];



        // initialization of test stations, todo: remove later, replace by initialization code
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

        // int[][] station = new int[2][10]; // int[x/y][station number]
        /*
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 21; j++) {
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
        station[0][19] = 509;
        station[1][19] = 512;
        station[0][20] = 874;
        station[1][20] = 432;


        for (int i = 0; i < TAXI_STATIONS; i++) {
            for (int j = 0; j < MAX_TAXI_NEIGHBORS; j++) {
                taxiNeighbors[i][j] = 0;
            }
        }

        taxiNeighbors[1][0] = 8;
        taxiNeighbors[1][1] = 9;
        taxiNeighbors[8][0] = 1;
        taxiNeighbors[8][1] = 19;
        taxiNeighbors[9][0] = 1;
        taxiNeighbors[9][1] = 19;
        taxiNeighbors[9][2] = 29;
        */

        // todo: remove until here


        // *****
        // read data from ServerDatabase
        // *****

        /*
        serverStation = serverDatabase.getStation(3);
        int testX = serverStation.getX();
        int testY = serverStation.getY();
        Log.d(TAG, "onCreate: serverStation.getX(), .getY(): " + testX + ", " + testY); // 1816, 111
        -----
        this.serverDatabase = new ServerDatabase(this.context); [-> context bekommt man in der mainActivity mit getApplicationContext()]
        ServerStation station = serverDatabase.getStation(1);
        Log.d(TAG, String.valueOf(station.getX()));
        die nachbarn bekommt du dann mit station.getTaxi/getUnderground usw. sind int arrays.
        */

        serverDatabase = new ServerDatabase(this.getApplicationContext());
        // serverStation = serverDatabase.getStation(9); // todo: delete later
        // Log.d(TAG, "onCreate: serverStation.getX(), .getY(): " + serverStation.getX() +
        //                 ", " + serverStation.getY());


        gameBoardView.setOnTouchListener((view, motionEvent) -> {

            touchedScreenCoordinates[0] = (int) motionEvent.getX(); // touched screen coordinates
            touchedScreenCoordinates[1] = (int) motionEvent.getY();

            touchedBoardCoordinates = calculateBoardCoordinates(touchedScreenCoordinates);

            // print boardX and boardY to screen, todo: delete later
            showBoardX.setText("X = " + touchedBoardCoordinates[0]);
            showBoardY.setText("Y = " + touchedBoardCoordinates[1]);


            // *****
            // calculate player1ScreenCoordinates and set circle to these coordinates
            // *****

            player1ScreenCoordinates = calculateScreenCoordinates(player1BoardCoordinates);

            player1ViewGroup.setMargins(player1ScreenCoordinates[0] - 25, player1ScreenCoordinates[1] - 25,
                    player1ViewGroup.rightMargin, player1ViewGroup.bottomMargin);
            return true;
        });

        gameBoardView.setOnLongClickListener(motionEvent -> {
            Log.d(TAG, "onCreate: onLongClick");

            // info: uses touchedScreenCoordinates from .setOnTouchListener:
            Log.d(TAG, "onCreate: onLongClick: touchedScreenCoordinates = " +
                    touchedScreenCoordinates[0] + ", " + touchedScreenCoordinates[1]);
            touchedBoardCoordinates = calculateBoardCoordinates(touchedScreenCoordinates);
            Log.d(TAG, "onCreate: onLongClick: player1BoardCoordinates = " +
                    player1BoardCoordinates[0] + ", " + player1BoardCoordinates[1]);


            // *****
            // calculation of player1BoardCoordinates given touchedBoardCoordinates
            // *****

            // calculate closest distance of station
            /* // todo: moved to method
            int distance;
            int closestDistance = 2147483647; // max of int
            int deltaX;
            int deltaY;
            int closestStation = 0;

            for (int i = 1; i < 200; i++) {
                deltaX = touchedBoardCoordinates[0] - station[i][0]; // no need to get absolute value,
                deltaY = touchedBoardCoordinates[1] - station[i][1]; // because they are squared later

                distance = deltaX * deltaX + deltaY * deltaY;
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestStation = i;
                }
            } */
            /*
            int[] selectionOfStations = new int[200];
            selectionOfStations[0] = 0; // station 0 doesn't exist
            selectionOfStations[0] = 0;
            for (int i = 1; i < 200; i++) {
                selectionOfStations[i] = serverDatabase.getStation(i).getId();
            } */
            /*
            selectionOfStations[0] = 0; // station 0 doesn't exist
            for (int i = 1; i < 200; i++) {
                selectionOfStations[i] = serverDatabase.getStation(i).getId();
            }
             */

            // player1BoardCoordinates = getClosestStationToTouchedBoardCoordinates(
            //         touchedBoardCoordinates, selectionOfStationCoordinates);


            for (int i = 1; i < 200; i++) {
                selectionOfStations[i] = i;
            }

            int player1CurrentStation = getClosestStationToTouchedBoardCoordinates(
                    touchedBoardCoordinates, selectionOfStations);
            player1BoardCoordinates[0] = serverDatabase.getStation(player1CurrentStation).getX();
            player1BoardCoordinates[1] = serverDatabase.getStation(player1CurrentStation).getY();

            // player1BoardCoordinates[0] = station[closestStation][0]; // todo: delete
            // player1BoardCoordinates[1] = station[closestStation][1];

            Log.d(TAG, "onCreate: onLongClick: player1BoardCoordinates = " +
                    player1BoardCoordinates[0] + ", " + player1BoardCoordinates[1]);
            player1ScreenCoordinates = calculateScreenCoordinates(player1BoardCoordinates);
            Log.d(TAG, "onCreate: onLongClick: player1ScreenCoordinates = " +
                    player1ScreenCoordinates[0] + ", " + player1ScreenCoordinates[1]);
            player1ViewGroup.setMargins(player1ScreenCoordinates[0] - 25, player1ScreenCoordinates[1] - 25,
                    player1ViewGroup.rightMargin, player1ViewGroup.bottomMargin);

            return true;
        });


        taxiDrawButton.setOnTouchListener((view, motionEvent) -> {
            showTransport.setText("Taxi");


            return true;
        });

        busDrawButton.setOnTouchListener((view, motionEvent) -> {
            showTransport.setText("Bus");
            return true;
        });

        undergroundDrawButton.setOnTouchListener((view, motionEvent) -> {
            showTransport.setText("Underground");
            return true;
        });
        
    }

    int[] calculateBoardCoordinates(int[] screenCoordinates) {
        int[] boardCoordinates = new int[2];

        int maxScreenWidth = gameScreenLayout.getWidth(); // screen size
        int maxScreenHeight = gameScreenLayout.getHeight();
        double conversionFactor = (double) BOARD_MAX_Y / maxScreenHeight; // on Samsung: ~4.88

        RectF rectF = gameBoardView.getZoomedRect(); // current left, top, zoom factor
        double left = rectF.left;
        double top = rectF.top;
        double zoomFactor = gameBoardView.getCurrentZoom();

        // calculation of boardX and boardY given screenCoordinates[0] and screenCoordinates[1]
        double currentBoardWidth = (BOARD_MAX_X * zoomFactor / conversionFactor);
        int offsetX = (int) (maxScreenWidth / 2 - currentBoardWidth / 2); // offset when boardWidth < maxScreenWidth()

        int negativeOffsetX = 0;
        if (offsetX > 0) {
            boardCoordinates[0] = (int) ((screenCoordinates[0] - offsetX) * conversionFactor / zoomFactor);
        } else {
            negativeOffsetX = (int) (left * BOARD_MAX_X); // *** nächstes Heureka ***
            boardCoordinates[0] = (int) (screenCoordinates[0] * conversionFactor / zoomFactor) + negativeOffsetX;
        }
        int negativeOffsetY = (int) (top * BOARD_MAX_Y);
        boardCoordinates[1] = (int) (screenCoordinates[1] * conversionFactor / zoomFactor) + negativeOffsetY;

        Log.d(TAG, "onCreate: maxScreenWidth = " + maxScreenWidth +
                ", maxScreenHeight = " + maxScreenHeight);
        Log.d(TAG, "onCreate: left = " + left + ", top = " + top +
                ", zoom factor = " + zoomFactor);
        Log.d(TAG, "onCreate: touchedScreenX, touchedScreenY = " +
                screenCoordinates[0] + ", " + screenCoordinates[1]);
        Log.d(TAG, "onCreate: conversionFactor = " + conversionFactor);
        Log.d(TAG, "onCreate: offsetX = " + offsetX);
        if (offsetX < 0) Log.d(TAG, "onCreate: offsetX < 0");
        Log.d(TAG, "onCreate: negativeOffsetX = " + negativeOffsetX +
                ", negativeOffsetY = " + negativeOffsetY);
        Log.d(TAG, "***** onCreate: boardX = " + player1BoardCoordinates[0] +
                ", boardY = " + player1BoardCoordinates[1] + " *****");

        // boardCoordinates[0] = 1; // player1BoardX;
        // boardCoordinates[1] = 2; // player1BoardY;

        return boardCoordinates;
    }

    int[] calculateScreenCoordinates(int[] boardCoordinates) {
        int[] screenCoordinates = new int[2];

        /*boardCoordinates[0] = 2504; // todo: delete
        boardCoordinates[1] = 1077;
        Log.d(TAG, "calculateScreenCoordinates: set boardCoordinates to station 67"); */

        int maxScreenWidth = gameScreenLayout.getWidth(); // screen size
        int maxScreenHeight = gameScreenLayout.getHeight();
        double conversionFactor = (double) BOARD_MAX_Y / maxScreenHeight; // on Samsung: ~4.88

        RectF rectF = gameBoardView.getZoomedRect(); // current left, top, zoom factor
        double left = rectF.left;
        double top = rectF.top;
        double zoomFactor = gameBoardView.getCurrentZoom();

        double currentBoardWidth = (BOARD_MAX_X * zoomFactor / conversionFactor);
        int offsetX = (int) (maxScreenWidth / 2 - currentBoardWidth / 2);

        int negativeOffsetX = 0;

        if (offsetX > 0) {
            screenCoordinates[0] = (int) (boardCoordinates[0] * zoomFactor / conversionFactor) + offsetX;
        } else {
            negativeOffsetX = (int) (left * BOARD_MAX_X);
            screenCoordinates[0] = (int) ((boardCoordinates[0] - negativeOffsetX) * zoomFactor / conversionFactor);
        }

        int negativeOffsetY = (int) (top * BOARD_MAX_Y);
        screenCoordinates[1] = (int) ((boardCoordinates[1] - negativeOffsetY) * zoomFactor / conversionFactor);

        return screenCoordinates;
    }

    int getClosestStationToTouchedBoardCoordinates(int[] touchedBoardCoordinates,
                                                   int[] selectionOfStations) {
        int distance;
        int closestDistance = 2147483647; // max of int
        int deltaX;
        int deltaY;
        int closestStation = 0;
        int amountOfStations = selectionOfStations.length; // info: station 0 does not exist
        // int[] stations = new int[amountOfStations + 1]; // todo: delete later
        int[] stationsXCoordinates = new int[amountOfStations];
        int[] stationsYCoordinates = new int[amountOfStations];
        for (int i = 1; i < amountOfStations; i++) {
            stationsXCoordinates[i] = serverDatabase.getStation(i).getX();
            stationsYCoordinates[i] = serverDatabase.getStation(i).getY();
        }

        for (int i = 1; i < amountOfStations; i++) {
            deltaX = touchedBoardCoordinates[0] - stationsXCoordinates[i]; // no need to get absolute value,
            deltaY = touchedBoardCoordinates[1] - stationsYCoordinates[i]; // because they are squared later

            distance = deltaX * deltaX + deltaY * deltaY;
            if (distance < closestDistance) {
                closestDistance = distance;
                closestStation = i;
            }
        }
        return closestStation;
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


}
