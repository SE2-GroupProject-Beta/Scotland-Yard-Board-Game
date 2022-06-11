package com.example.scotland_yard_board_game.client;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.animation.TimeAnimator.TimeListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.scotland_yard_board_game.R;
import com.example.scotland_yard_board_game.common.StationDatabase;
import com.example.scotland_yard_board_game.common.ServerStation;
import com.ortiz.touchview.TouchImageView;

import java.util.Objects;

public class GameScreen extends AppCompatActivity { // extends View {
    private static final String TAG = "GameScreen";

    private ConstraintLayout gameScreenLayout;
    private TouchImageView gameBoardView;
    private ConstraintLayout journeyTableLayout;
    private TimeListener timeListener;

    //nickname
    TextView hostNameOut;
    String hostString;

    //ticket count (adjust maximum)
    int taxiTickets = 10;

    private Button taxiDrawButton;
    private Button busDrawButton;
    private Button undergroundDrawButton;
    private Button journeyTableButton;

    private final int BOARD_MAX_X = 4368;
    private final int BOARD_MAX_Y = 3312;
    private final int TAXI_NEIGHBORS_MAX = 7;
    private final int BUS_NEIGHBORS_MAX = 5;
    private final int UNDERGROUND_NEIGHBORS_MAX = 4;


    private TextView showBoardX; // todo: delete before production release
    private TextView showBoardY;
    private TextView showTransport;

    private int[] touchedBoardCoordinates = new int[2];
    private int[] touchedScreenCoordinates = new int[2];
    private int[] player1BoardCoordinates = new int[2];
    private int[] player1ScreenCoordinates = new int[2];


    private MarginLayoutParams player1ViewGroup;
    private View player1View;

    private MarginLayoutParams[] taxiNeighborMarginLayoutParams = new MarginLayoutParams[TAXI_NEIGHBORS_MAX];
    private View taxiNeighbor0View; // sorry for violation of 'dry'...
    private View taxiNeighbor1View;
    private View taxiNeighbor2View;
    private View taxiNeighbor3View;
    private View taxiNeighbor4View;
    private View taxiNeighbor5View;
    private View taxiNeighbor6View;
    private int[] taxiNeighborStations;

    private int[][] taxiNeighborsBoardCoordinates = new int[TAXI_NEIGHBORS_MAX][2];
    private int[][] taxiNeighborsScreenCoordinates = new int[TAXI_NEIGHBORS_MAX][2];


    private MarginLayoutParams[] busNeighborMarginLayoutParams = new MarginLayoutParams[BUS_NEIGHBORS_MAX];
    private View busNeighbor0View;
    private View busNeighbor1View;
    private View busNeighbor2View;
    private View busNeighbor3View;
    private View busNeighbor4View;
    private int[] busNeighborStations;

    private int[][] busNeighborsBoardCoordinates = new int[BUS_NEIGHBORS_MAX][2];
    private int[][] busNeighborsScreenCoordinates = new int[BUS_NEIGHBORS_MAX][2];



    private int[] selectionOfStations = new int[200];

    private StationDatabase serverDatabase;
    private ServerStation serverStation; // todo: delete if not needed

    int player1CurrentStation = 1; // todo: initialize players coming from lobby


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

        //nickname on GameScreen
        hostNameOut = findViewById(R.id.MrXNameGameView);            //find TextView for Host Nickname output
        // hostString = getIntent().getExtras().getString("Val");  //get value from previous activity
        hostNameOut.setText(hostString);                            //setText to value of hostString variable

        showBoardX = findViewById(R.id.showBoardX);
        showBoardY = findViewById(R.id.showBoardY);
        showTransport = findViewById(R.id.showTransport);
      
        taxiDrawButton = findViewById(R.id.taxiDrawButton);
        busDrawButton = findViewById(R.id.busDrawButton);
        undergroundDrawButton = findViewById(R.id.undergroundDrawButton);
        journeyTableButton = findViewById(R.id.journeyTableButton);

        gameBoardView.setMaxZoom(6); // augment zoom


        /* todo: implement listener/thread to draw bord after 100 ms
        boardDrawingThread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!boardDrawingThread.isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "running...");
                                taxiNeighborMarginLayoutParams[0].setMargins(
                                        taxiNeighborsScreenCoordinates[0][0] - 25,
                                        taxiNeighborsScreenCoordinates[0][1] - 25,
                                        taxiNeighborMarginLayoutParams[0].rightMargin,
                                        taxiNeighborMarginLayoutParams[0].bottomMargin);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        }; */

        /* // todo: try to get the moving after releasing of finger captured
        gameBoardView.setOnGenericMotionListener((view, motionEvent) -> {  // setOnSystemUiVisibilityChangeListener(view -> {
            player1ViewGroup.setMargins(player1ScreenCoordinates[0] - 25, player1ScreenCoordinates[1] - 25,
                    player1ViewGroup.rightMargin, player1ViewGroup.bottomMargin);
            return true;
        }); */

        // timeListener.onTimeUpdate((a, b) -> {});
        /*
        timeListener = (timeAnimator, l, l1) -> { // todo: working?
            Log.d(TAG, "running...");
            taxiNeighborMarginLayoutParams[0].setMargins(
                    taxiNeighborsScreenCoordinates[0][0] - 25,
                    taxiNeighborsScreenCoordinates[0][1] - 25,
                    taxiNeighborMarginLayoutParams[0].rightMargin,
                    taxiNeighborMarginLayoutParams[0].bottomMargin);
        }; */

        player1View = findViewById(R.id.player1); // to move player1
        player1ViewGroup = (MarginLayoutParams) player1View.getLayoutParams();

        taxiNeighbor0View = findViewById(R.id.taxi_neighbor0); // :( 'dry' again
        taxiNeighbor1View = findViewById(R.id.taxi_neighbor1);
        taxiNeighbor2View = findViewById(R.id.taxi_neighbor2);
        taxiNeighbor3View = findViewById(R.id.taxi_neighbor3);
        taxiNeighbor4View = findViewById(R.id.taxi_neighbor4);
        taxiNeighbor5View = findViewById(R.id.taxi_neighbor5);
        taxiNeighbor6View = findViewById(R.id.taxi_neighbor6);
        taxiNeighborMarginLayoutParams[0] = (MarginLayoutParams) taxiNeighbor0View.getLayoutParams();
        taxiNeighborMarginLayoutParams[1] = (MarginLayoutParams) taxiNeighbor1View.getLayoutParams();
        taxiNeighborMarginLayoutParams[2] = (MarginLayoutParams) taxiNeighbor2View.getLayoutParams();
        taxiNeighborMarginLayoutParams[3] = (MarginLayoutParams) taxiNeighbor3View.getLayoutParams();
        taxiNeighborMarginLayoutParams[4] = (MarginLayoutParams) taxiNeighbor4View.getLayoutParams();
        taxiNeighborMarginLayoutParams[5] = (MarginLayoutParams) taxiNeighbor5View.getLayoutParams();
        taxiNeighborMarginLayoutParams[6] = (MarginLayoutParams) taxiNeighbor6View.getLayoutParams();

        busNeighbor0View = findViewById(R.id.bus_neighbor0);
        busNeighbor1View = findViewById(R.id.bus_neighbor1);
        busNeighbor2View = findViewById(R.id.bus_neighbor2);
        busNeighbor3View = findViewById(R.id.bus_neighbor3);
        busNeighbor4View = findViewById(R.id.bus_neighbor4);
        busNeighborMarginLayoutParams[0] = (MarginLayoutParams) busNeighbor0View.getLayoutParams();
        busNeighborMarginLayoutParams[1] = (MarginLayoutParams) busNeighbor1View.getLayoutParams();
        busNeighborMarginLayoutParams[2] = (MarginLayoutParams) busNeighbor2View.getLayoutParams();
        busNeighborMarginLayoutParams[3] = (MarginLayoutParams) busNeighbor3View.getLayoutParams();
        busNeighborMarginLayoutParams[4] = (MarginLayoutParams) busNeighbor4View.getLayoutParams();
        /*
        for (int i = 0; i < BUS_NEIGHBORS_MAX; i++) {
            busNeighborStations[i] = 0;
        } */


        // initialize ServerDatabase
        serverDatabase = new StationDatabase(this.getApplicationContext());



        gameBoardView.setOnTouchListener((view, motionEvent) -> {

            touchedScreenCoordinates[0] = (int) motionEvent.getX(); // touched screen coordinates
            touchedScreenCoordinates[1] = (int) motionEvent.getY();

            touchedBoardCoordinates = calculateBoardCoordinates(touchedScreenCoordinates);

            // print boardX and boardY to screen, todo: delete later
            showBoardX.setText("X = " + touchedBoardCoordinates[0]);
            showBoardY.setText("Y = " + touchedBoardCoordinates[1]);

            // calculate player1ScreenCoordinates and set circle to these coordinates
            player1ScreenCoordinates = calculateScreenCoordinates(player1BoardCoordinates);

            placeDrawables();
            /* todo: delete later
            player1ViewGroup.setMargins(player1ScreenCoordinates[0] - 25, player1ScreenCoordinates[1] - 25,
                    player1ViewGroup.rightMargin, player1ViewGroup.bottomMargin); */

            return true;
        });

        gameBoardView.setOnLongClickListener(motionEvent -> {

            // info: uses touchedScreenCoordinates from .setOnTouchListener:
            touchedBoardCoordinates = calculateBoardCoordinates(touchedScreenCoordinates);

            selectionOfStations = getAllStations(); // for testing purposes

            player1CurrentStation = getClosestStationToTouchedBoardCoordinates(
                    touchedBoardCoordinates, selectionOfStations);
            player1BoardCoordinates[0] = serverDatabase.getStation(player1CurrentStation).getX();
            player1BoardCoordinates[1] = serverDatabase.getStation(player1CurrentStation).getY();

            player1ScreenCoordinates = calculateScreenCoordinates(player1BoardCoordinates);

            return true;
        });


        taxiDrawButton.setOnTouchListener((view, motionEvent) -> {
            showTransport.setText("Taxi");
            clearAllNeighborStations();
            taxiNeighborStations = getTaxiNeighborStationsFromGivenStation(player1CurrentStation);
            /*
            for (int i = taxiNeighborStations.length; i < TAXI_NEIGHBORS_MAX; i++) { // clear former station values that are
                taxiNeighborsScreenCoordinates[i][0] = -100; // higher than taxiNeighborStation.length
                taxiNeighborsScreenCoordinates[i][1] = -100;
                taxiNeighborMarginLayoutParams[i].setMargins(
                        taxiNeighborsScreenCoordinates[i][0] - 25,
                        taxiNeighborsScreenCoordinates[i][1] - 25,
                        taxiNeighborMarginLayoutParams[i].rightMargin,
                        taxiNeighborMarginLayoutParams[i].bottomMargin);
            } */

            for (int i = 0; i < taxiNeighborStations.length; i++) {
                Log.d(TAG, "taxiNeighbor " + i + " = " + taxiNeighborStations[i]);
                taxiNeighborsBoardCoordinates[i][0] = serverDatabase.getStation(taxiNeighborStations[i]).getX();
                taxiNeighborsBoardCoordinates[i][1] = serverDatabase.getStation(taxiNeighborStations[i]).getY();
                taxiNeighborsScreenCoordinates[i] = calculateScreenCoordinates(taxiNeighborsBoardCoordinates[i]);
                taxiNeighborMarginLayoutParams[i].setMargins(
                        taxiNeighborsScreenCoordinates[i][0] - 25,
                        taxiNeighborsScreenCoordinates[i][1] - 25,
                        taxiNeighborMarginLayoutParams[i].rightMargin,
                        taxiNeighborMarginLayoutParams[i].bottomMargin);
            }
            return true;
        });

        busDrawButton.setOnTouchListener((view, motionEvent) -> {
            showTransport.setText("Bus");
            clearAllNeighborStations();
            busNeighborStations = getBusNeighborStationsFromGivenStation(player1CurrentStation);
            placeDrawables();


            for (int i = 0; i < busNeighborStations.length; i++) {
                Log.d(TAG, "busNeighbor " + i + " = " + busNeighborStations[i]);
                busNeighborsBoardCoordinates[i][0] = serverDatabase.getStation(busNeighborStations[i]).getX();
                busNeighborsBoardCoordinates[i][1] = serverDatabase.getStation(busNeighborStations[i]).getY();
                busNeighborsScreenCoordinates[i] = calculateScreenCoordinates(busNeighborsBoardCoordinates[i]);

                busNeighborMarginLayoutParams[i].setMargins(
                        busNeighborsScreenCoordinates[i][0] - 25,
                        busNeighborsScreenCoordinates[i][1] - 25,
                        busNeighborMarginLayoutParams[i].rightMargin,
                        busNeighborMarginLayoutParams[i].bottomMargin);
            }
            return true;

        });

        undergroundDrawButton.setOnTouchListener((view, motionEvent) -> {
            showTransport.setText("Underground");
            return true;
        });



    }

    void clearAllNeighborStations() {
        for (int i = 0; i < TAXI_NEIGHBORS_MAX; i++) {
            taxiNeighborsScreenCoordinates[i][0] = -100;
            taxiNeighborsScreenCoordinates[i][1] = -100;
            taxiNeighborMarginLayoutParams[i].setMargins(
                    taxiNeighborsScreenCoordinates[i][0] - 25,
                    taxiNeighborsScreenCoordinates[i][1] - 25,
                    taxiNeighborMarginLayoutParams[i].rightMargin,
                    taxiNeighborMarginLayoutParams[i].bottomMargin);
        }
        for (int i = 0; i < BUS_NEIGHBORS_MAX; i++) {
            // busNeighborStations[i] = 0;
            busNeighborsScreenCoordinates[i][0] = -100;
            busNeighborsScreenCoordinates[i][1] = -100;
            busNeighborMarginLayoutParams[i].setMargins(
                    busNeighborsScreenCoordinates[i][0] - 25,
                    busNeighborsScreenCoordinates[i][1] - 25,
                    busNeighborMarginLayoutParams[i].rightMargin,
                    busNeighborMarginLayoutParams[i].bottomMargin);
        }


    }

    void placeDrawables() {
        player1ViewGroup.setMargins(player1ScreenCoordinates[0] - 25, player1ScreenCoordinates[1] - 25,
                player1ViewGroup.rightMargin, player1ViewGroup.bottomMargin);
        /*
        for (int i = 0; i < BUS_NEIGHBORS_MAX; i++) {
            Log.d(TAG, "busNeighbor " + i + " = " + busNeighborStations[i]);
            busNeighborsBoardCoordinates[i][0] = serverDatabase.getStation(busNeighborStations[i]).getX();
            busNeighborsBoardCoordinates[i][1] = serverDatabase.getStation(busNeighborStations[i]).getY();
            busNeighborsScreenCoordinates[i] = calculateScreenCoordinates(busNeighborsBoardCoordinates[i]);

            busNeighborMarginLayoutParams[i].setMargins(
                    busNeighborsScreenCoordinates[i][0] - 25,
                    busNeighborsScreenCoordinates[i][1] - 25,
                    busNeighborMarginLayoutParams[i].rightMargin,
                    busNeighborMarginLayoutParams[i].bottomMargin);
        } */
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

        return boardCoordinates;
    }

    int[] calculateScreenCoordinates(int[] boardCoordinates) {
        int[] screenCoordinates = new int[2];

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

    int[] getPlayerStationsFromTouchCoordinates(int[] coordinates) {
        int[] playerStations = new int[2]; // change to amount of players

        return playerStations;
    }

    ;

    int[] getTaxiNeighborStationsFromGivenStation(int station) {
        return serverDatabase.getStation(station).getTaxi();
    }

    int[] getBusNeighborStationsFromGivenStation(int station) {
        return serverDatabase.getStation(station).getBus();
    }

    int[] getUndergroundNeighborStationsFromGivenStation(int station) {
        return serverDatabase.getStation(station).getUnderground();
    }

    int[] getAllStations() { // todo: delete later
        int[] allStations = new int[200];
        // select all Stations for testing purposes
        for (int i = 1; i < 200; i++) {
            allStations[i] = i;
        }
        return allStations;
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
