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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import android.animation.TimeAnimator.TimeListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.scotland_yard_board_game.R;

import com.example.scotland_yard_board_game.common.StationDatabase;

import com.example.scotland_yard_board_game.common.player.Player;
import com.ortiz.touchview.TouchImageView;

import java.util.ArrayList;
import java.util.Objects;

public class GameScreen extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, View.OnDragListener, View.OnLongClickListener { // extends View {
    private static final String TAG = "GameScreen";

    private ConstraintLayout gameScreenLayout;
    private TouchImageView gameBoardView;
    private ConstraintLayout journeyTableLayout;
    private TimeListener timeListener;

    //nickname
    TextView MrXNameGameView; //Mr.X
    TextView player2NameGameView; //detective/player 1

    //LinearLayout variables + tags
    private LinearLayout playerLayout1;
    private static final String pLAYOUT_VIEW_TAG = "1";

    //ticket count (adjust maximum)
    int taxiTickets = 10;
    int busTickets = 10;
    int undergroundTickets = 8;

    //mode of transportation on journey table
    private int chosenStation = -1; // variable for chosen station for confirmButton
    private int chosenTransport = -1; // variable for mode of transport for confirmButton
    //set method for test
    public void setChosenTransport(int value){
        chosenTransport = value;
    }
    TextView turnView; //variable for transportation marker

    //check if blackTicket was used (Mr. X Abilities)
    boolean blackTicket = false;
    int blackTicketCount = 3;

    private int turnCounter = 1; //turn count view

    // declare buttons
    private Button taxiDrawButton;
    private Button busDrawButton;
    private Button undergroundDrawButton;
    private Button journeyTableButton;
    private Button confirmButton;

    // static data of board-pixels and neighbors_max
    private final int BOARD_MAX_X = 4368; // pixel coordinates of board
    private final int BOARD_MAX_Y = 3312;
    private final int TAXI_NEIGHBORS_MAX = 7;
    private final int BUS_NEIGHBORS_MAX = 5;
    private final int UNDERGROUND_NEIGHBORS_MAX = 4;

    // show board coordinates and means of transport
    private TextView showBoardX;
    private TextView showBoardY;
    private TextView showTransport;

    // touched coordinates
    private int[] touchedBoardCoordinates = new int[2];
    private int[] touchedScreenCoordinates = new int[2];

    // player coordinates
    private int[] player0BoardCoordinates = new int[2];
    private int[] player0ScreenCoordinates = new int[2];
    private int[] player1BoardCoordinates = new int[2];
    private int[] player1ScreenCoordinates = new int[2];
    private int[] player2BoardCoordinates = new int[2];
    private int[] player2ScreenCoordinates = new int[2];
    private int[] player3BoardCoordinates = new int[2];
    private int[] player3ScreenCoordinates = new int[2];
    private int[] player4BoardCoordinates = new int[2];
    private int[] player4ScreenCoordinates = new int[2];
    private int[] player5BoardCoordinates = new int[2];
    private int[] player5ScreenCoordinates = new int[2];

    // which player's turn
    private int activePlayer;

    // declare player views
    private View player0View;
    private MarginLayoutParams player0ViewGroup;
    private View player1View;
    private MarginLayoutParams player1ViewGroup;
    private View player2View;
    private MarginLayoutParams player2ViewGroup;
    private View player3View;
    private MarginLayoutParams player3ViewGroup;
    private View player4View;
    private MarginLayoutParams player4ViewGroup;
    private View player5View;
    private MarginLayoutParams player5ViewGroup;

    // declare neighbor views (taxi, bus, underground)
    private MarginLayoutParams[] taxiNeighborMarginLayoutParams =
            new MarginLayoutParams[TAXI_NEIGHBORS_MAX];
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

    private MarginLayoutParams[] busNeighborMarginLayoutParams =
            new MarginLayoutParams[BUS_NEIGHBORS_MAX];
    private View busNeighbor0View;
    private View busNeighbor1View;
    private View busNeighbor2View;
    private View busNeighbor3View;
    private View busNeighbor4View;
    private int[] busNeighborStations;
    private int[][] busNeighborsBoardCoordinates = new int[BUS_NEIGHBORS_MAX][2];
    private int[][] busNeighborsScreenCoordinates = new int[BUS_NEIGHBORS_MAX][2];

    private MarginLayoutParams[] undergroundNeighborMarginLayoutParams =
            new MarginLayoutParams[UNDERGROUND_NEIGHBORS_MAX];
    private View undergroundNeighbor0View;
    private View undergroundNeighbor1View;
    private View undergroundNeighbor2View;
    private View undergroundNeighbor3View;
    private int[] undergroundNeighborStations;
    private int[][] undergroundNeighborsBoardCoordinates = new int[UNDERGROUND_NEIGHBORS_MAX][2];
    private int[][] undergroundNeighborsScreenCoordinates = new int[UNDERGROUND_NEIGHBORS_MAX][2];


    // private int[] selectionOfStations = new int[200]; // was used for testing purposes

    // declare serverDatabase and clientData in GameScreen
    private StationDatabase serverDatabase;
    static ClientData clientData;
    int currentStation;


    // method onCreate to initialize variables and set listeners
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        // set according layout
        setContentView(R.layout.activity_game_screen);

        findViews();
        implementEvents();

        gameScreenLayout = findViewById(R.id.gameScreenLayout);
        gameBoardView = findViewById(R.id.gameBoardView);
        journeyTableLayout = findViewById(R.id.journeyTableLayout);

        //Send start game signal
        clientData.setGameScreen(this);
        clientData.gameStart();

        //assign nickname variable to TextView for display
        MrXNameGameView = findViewById(R.id.MrXNameGameView); //Mr. X
        player2NameGameView = findViewById(R.id.player2NameGameView); //detective/player 1
        //call display method "displayNicknames"
        displayNicknames(clientData.getNicknames());

        // initialize show coordinates and means of transport
        showBoardX = findViewById(R.id.showBoardX);
        showBoardY = findViewById(R.id.showBoardY);
        showTransport = findViewById(R.id.showTransport);

        // initialize buttons on GameScreen
        taxiDrawButton = findViewById(R.id.taxiDrawButton);
        busDrawButton = findViewById(R.id.busDrawButton);
        undergroundDrawButton = findViewById(R.id.undergroundDrawButton);
        journeyTableButton = findViewById(R.id.journeyTableButton);
        confirmButton = findViewById(R.id.confirmButton);

        // augment zoom to 6 times max zoom
        gameBoardView.setMaxZoom(6);

        // initialize player views (to move players)
        player0View = findViewById(R.id.player0);
        player0ViewGroup = (MarginLayoutParams) player0View.getLayoutParams();
        player1View = findViewById(R.id.player1);
        player1ViewGroup = (MarginLayoutParams) player1View.getLayoutParams();
        player2View = findViewById(R.id.player2);
        player2ViewGroup = (MarginLayoutParams) player2View.getLayoutParams();
        player3View = findViewById(R.id.player3);
        player3ViewGroup = (MarginLayoutParams) player3View.getLayoutParams();
        player4View = findViewById(R.id.player4);
        player4ViewGroup = (MarginLayoutParams) player4View.getLayoutParams();
        player5View = findViewById(R.id.player5);
        player5ViewGroup = (MarginLayoutParams) player5View.getLayoutParams();

        // hardcoded active player (= mister X) and test setup of players
        activePlayer = 0;
        initializePlayerBoardCoordinates();

        // initialize neighbor views
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

        undergroundNeighbor0View = findViewById(R.id.underground_neighbor0);
        undergroundNeighbor1View = findViewById(R.id.underground_neighbor1);
        undergroundNeighbor2View = findViewById(R.id.underground_neighbor2);
        undergroundNeighbor3View = findViewById(R.id.underground_neighbor3);
        undergroundNeighborMarginLayoutParams[0] = (MarginLayoutParams) undergroundNeighbor0View.getLayoutParams();
        undergroundNeighborMarginLayoutParams[1] = (MarginLayoutParams) undergroundNeighbor1View.getLayoutParams();
        undergroundNeighborMarginLayoutParams[2] = (MarginLayoutParams) undergroundNeighbor2View.getLayoutParams();
        undergroundNeighborMarginLayoutParams[3] = (MarginLayoutParams) undergroundNeighbor3View.getLayoutParams();

        // initialize ServerDatabase
        serverDatabase = clientData.getStationDatabase();

        // general onTouchListener
        gameBoardView.setOnTouchListener((view, motionEvent) -> {
            // get touched coordinates
            touchedScreenCoordinates[0] = (int) motionEvent.getX();
            touchedScreenCoordinates[1] = (int) motionEvent.getY();
            touchedBoardCoordinates = calculateBoardCoordinates(touchedScreenCoordinates);

            // print boardX and boardY to screen
            showBoardX.setText("X = " + touchedBoardCoordinates[0]);
            showBoardY.setText("Y = " + touchedBoardCoordinates[1]);

            // calculate player screen coordinates and set circle to these coordinates
            player0ScreenCoordinates = calculateScreenCoordinates(player0BoardCoordinates);
            player1ScreenCoordinates = calculateScreenCoordinates(player1BoardCoordinates);
            player2ScreenCoordinates = calculateScreenCoordinates(player2BoardCoordinates);
            player3ScreenCoordinates = calculateScreenCoordinates(player3BoardCoordinates);
            player4ScreenCoordinates = calculateScreenCoordinates(player4BoardCoordinates);
            player5ScreenCoordinates = calculateScreenCoordinates(player5BoardCoordinates);

            // call method to draw all players and the according neighbors to board
            placePlayersAndNeighbors();

            return true;
        });

        // onLongClickListener
        gameBoardView.setOnLongClickListener(motionEvent -> {
            // info: uses touchedScreenCoordinates from .setOnTouchListener!
            touchedBoardCoordinates = calculateBoardCoordinates(touchedScreenCoordinates);

            // find closest station to touched coordinates
            int closestStation = 1;
            if (chosenTransport == 0) {
                closestStation = getClosestStationToTouchedBoardCoordinates(
                        touchedBoardCoordinates, taxiNeighborStations);
            } else if (chosenTransport == 1) {
                closestStation = getClosestStationToTouchedBoardCoordinates(
                        touchedBoardCoordinates, busNeighborStations);
            } else if (chosenTransport == 2) {
                closestStation = getClosestStationToTouchedBoardCoordinates(
                        touchedBoardCoordinates, undergroundNeighborStations);
            }
            chosenStation = closestStation;
            Log.d(TAG, "onLongClickListener, chosen transport: " + chosenTransport);
            Log.d(TAG, "onLongClickListener, closest station: " + closestStation);

            // change text of confirmButton to show the chosen station
            confirmButton.setText("Go to " + chosenStation);
            return true;
        });

        // onTouchListener for taxiDrawButton, busDrawButton, undergroundDrawButton
        taxiDrawButton.setOnTouchListener((view, motionEvent) -> {
            showTransport.setText("Taxi");
            chosenTransport = 0;
            clearAllNeighborStations(); // otherwise the old ones are left behind...
            currentStation = getCurrentStation(activePlayer);
            taxiNeighborStations = getTaxiNeighborStationsFromGivenStation(currentStation);
            placePlayersAndNeighbors();

            return true;
        });

        busDrawButton.setOnTouchListener((view, motionEvent) -> {
            showTransport.setText("Bus");
            chosenTransport = 1;
            clearAllNeighborStations();
            currentStation = getCurrentStation(activePlayer);
            busNeighborStations = getBusNeighborStationsFromGivenStation(currentStation);
            placePlayersAndNeighbors();

            return true;
        });

        undergroundDrawButton.setOnTouchListener((view, motionEvent) -> {
            showTransport.setText("Underground");
            chosenTransport = 2;
            clearAllNeighborStations();
            currentStation = getCurrentStation(activePlayer);
            undergroundNeighborStations = getUndergroundNeighborStationsFromGivenStation(currentStation);
            placePlayersAndNeighbors();

            return true;
        });

        // onClickListener for confirmButton
        confirmButton.setOnClickListener((view) -> {
            clientData.validateMove(chosenStation, chosenTransport);

            moveOnConfirmButton();

            placePlayersAndNeighbors();

        });
    }
    //set public for Test access
    public void moveOnConfirmButton() {
        switch(chosenTransport){
            case 0:     //taxi chosen
                if(!blackTicket) {
                    //change turnView background color
                    changeBackground("#FFEB3B");
                    taxiTickets = taxiTickets - 1; //decrease taxi tickets
                    displayTaxi(taxiTickets);         //display current taxi tickets
                    Toast.makeText(this, "Go to station " + chosenStation +
                        " by taxi", Toast.LENGTH_SHORT).show();
                }
                blackTicket = false;
                break;
            case 1:     //bus chosen
                if(!blackTicket) {
                    changeBackground("#22EE22");
                    busTickets = busTickets - 1;
                    displayBus(busTickets);
                    Toast.makeText(this, "Go to station " + chosenStation +
                        " by bus", Toast.LENGTH_SHORT).show();
                }
                blackTicket = false;
                break;
            case 2:     //underground chosen
                if(!blackTicket) {
                    changeBackground("#E91E1E");
                    undergroundTickets = undergroundTickets - 1;
                    displayUnderground(undergroundTickets);
                    Toast.makeText(this, "Go to station " + chosenStation +
                        " by underground", Toast.LENGTH_SHORT).show();
                }
                blackTicket = false;
                break;
        }

        //increase turnCounter on "confirm"
        turnCounter = turnCounter +1;
        displayTurnCount(turnCounter);
        displayBlackTicketCount(blackTicketCount);
    }

    //method set JourneyTable view backgrounds
    void changeBackground(String color){

        switch(turnCounter){
            case 1:
                turnView = findViewById(R.id.turnView1);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 2:
                turnView = findViewById(R.id.turnView2);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 3:
                turnView = findViewById(R.id.turnView3);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 4:
                turnView = findViewById(R.id.turnView4);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 5:
                turnView = findViewById(R.id.turnView5);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 6:
                turnView = findViewById(R.id.turnView6);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 7:
                turnView = findViewById(R.id.turnView7);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 8:
                turnView = findViewById(R.id.turnView8);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 9:
                turnView = findViewById(R.id.turnView9);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 10:
                turnView = findViewById(R.id.turnView10);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 11:
                turnView = findViewById(R.id.turnView11);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 12:
                turnView = findViewById(R.id.turnView12);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 13:
                turnView = findViewById(R.id.turnView13);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 14:
                turnView = findViewById(R.id.turnView14);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 15:
                turnView = findViewById(R.id.turnView15);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 16:
                turnView = findViewById(R.id.turnView16);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 17:
                turnView = findViewById(R.id.turnView17);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 18:
                turnView = findViewById(R.id.turnView18);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 19:
                turnView = findViewById(R.id.turnView19);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 20:
                turnView = findViewById(R.id.turnView20);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 21:
                turnView = findViewById(R.id.turnView21);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 22:
                turnView = findViewById(R.id.turnView22);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 23:
                turnView = findViewById(R.id.turnView23);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
            case 24:
                turnView = findViewById(R.id.turnView24);
                turnView.setBackgroundColor(Color.parseColor(color));
                break;
        }
    }

    // clears all neighbors, otherwise not newly assigned neighbors
    // are "left behind" on the board, all are deleted first
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
            busNeighborsScreenCoordinates[i][0] = -100;
            busNeighborsScreenCoordinates[i][1] = -100;
            busNeighborMarginLayoutParams[i].setMargins(
                    busNeighborsScreenCoordinates[i][0] - 25,
                    busNeighborsScreenCoordinates[i][1] - 25,
                    busNeighborMarginLayoutParams[i].rightMargin,
                    busNeighborMarginLayoutParams[i].bottomMargin);
        }
        for (int i = 0; i < UNDERGROUND_NEIGHBORS_MAX; i++) {
            undergroundNeighborsScreenCoordinates[i][0] = -100;
            undergroundNeighborsScreenCoordinates[i][1] = -100;
            undergroundNeighborMarginLayoutParams[i].setMargins(
                    undergroundNeighborsScreenCoordinates[i][0] - 25,
                    undergroundNeighborsScreenCoordinates[i][1] - 25,
                    undergroundNeighborMarginLayoutParams[i].rightMargin,
                    undergroundNeighborMarginLayoutParams[i].bottomMargin);
        }
    }

    // get data from server and place all players accordingly
    void updatePlayerBoardCoordinates(ArrayList<Player> playerList) {

        for (Player player : playerList) {
            switch (player.getId()) {
                case 0:
                    player0BoardCoordinates[0] = player.getPosition().getX();
                    player0BoardCoordinates[1] = player.getPosition().getY();
                    break;
                case 1:
                    player1BoardCoordinates[0] = player.getPosition().getX();
                    player1BoardCoordinates[1] = player.getPosition().getY();
                    break;
                case 2:
                    player2BoardCoordinates[0] = player.getPosition().getX();
                    player2BoardCoordinates[1] = player.getPosition().getY();
                    break;
                case 3:
                    player3BoardCoordinates[0] = player.getPosition().getX();
                    player3BoardCoordinates[1] = player.getPosition().getY();
                    break;
                case 4:
                    player4BoardCoordinates[0] = player.getPosition().getX();
                    player4BoardCoordinates[1] = player.getPosition().getY();
                    break;
                case 5:
                    player5BoardCoordinates[0] = player.getPosition().getX();
                    player5BoardCoordinates[1] = player.getPosition().getY();
                    break;
            }
        }

        placePlayersAndNeighbors();

    }
    void initializePlayerBoardCoordinates() {
        // later coordinates are received from server
        player0BoardCoordinates[0] = 552;
        player0BoardCoordinates[1] = 162;
        player1BoardCoordinates[0] = 1601;
        player1BoardCoordinates[1] = 721;
        player2BoardCoordinates[0] = 779;
        player2BoardCoordinates[1] = 1089;
        player3BoardCoordinates[0] = 2504; // active at start (station 67)
        player3BoardCoordinates[1] = 1077;
        player4BoardCoordinates[0] = 4017;
        player4BoardCoordinates[1] = 2227;
        player5BoardCoordinates[0] = 2867;
        player5BoardCoordinates[1] = 2317;
    }
    //
    int getCurrentStation(int activePlayer) {
        // returns the coordinates of player 0 (=mister X) at the moment
        ArrayList<Player> playerArray = clientData.getPlayers();
        Log.d(TAG, String.valueOf(playerArray.get(0).getPosition().getId())+" ");
        return playerArray.get(activePlayer).getPosition().getId();
    }

    // method to draw players and the taxi-, bus- or underground-neighbors
    // of the active player to the board
    void placePlayersAndNeighbors() {
        player0ViewGroup.setMargins(player0ScreenCoordinates[0] - 25, player0ScreenCoordinates[1] - 25,
                player0ViewGroup.rightMargin, player0ViewGroup.bottomMargin);
        player1ViewGroup.setMargins(player1ScreenCoordinates[0] - 25, player1ScreenCoordinates[1] - 25,
                player1ViewGroup.rightMargin, player1ViewGroup.bottomMargin);
        player2ViewGroup.setMargins(player2ScreenCoordinates[0] - 25, player2ScreenCoordinates[1] - 25,
                player2ViewGroup.rightMargin, player2ViewGroup.bottomMargin);
        player3ViewGroup.setMargins(player3ScreenCoordinates[0] - 25, player3ScreenCoordinates[1] - 25,
                player3ViewGroup.rightMargin, player3ViewGroup.bottomMargin);
        player4ViewGroup.setMargins(player4ScreenCoordinates[0] - 25, player4ScreenCoordinates[1] - 25,
                player4ViewGroup.rightMargin, player4ViewGroup.bottomMargin);
        player5ViewGroup.setMargins(player5ScreenCoordinates[0] - 25, player5ScreenCoordinates[1] - 25,
                player5ViewGroup.rightMargin, player5ViewGroup.bottomMargin);

        if (chosenTransport == 0) {
            taxiNeighborStations = getTaxiNeighborStationsFromGivenStation(currentStation); // todo: change to currentStation
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
        } else if (chosenTransport == 1) {
            busNeighborStations = getBusNeighborStationsFromGivenStation(currentStation);
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
        } else if (chosenTransport == 2) {
            undergroundNeighborStations = getUndergroundNeighborStationsFromGivenStation(currentStation);
            for (int i = 0; i < undergroundNeighborStations.length; i++) {
                Log.d(TAG, "busNeighbor " + i + " = " + undergroundNeighborStations[i]);
                undergroundNeighborsBoardCoordinates[i][0] = serverDatabase.getStation(undergroundNeighborStations[i]).getX();
                undergroundNeighborsBoardCoordinates[i][1] = serverDatabase.getStation(undergroundNeighborStations[i]).getY();
                undergroundNeighborsScreenCoordinates[i] = calculateScreenCoordinates(undergroundNeighborsBoardCoordinates[i]);
                undergroundNeighborMarginLayoutParams[i].setMargins(
                        undergroundNeighborsScreenCoordinates[i][0] - 25,
                        undergroundNeighborsScreenCoordinates[i][1] - 25,
                        undergroundNeighborMarginLayoutParams[i].rightMargin,
                        undergroundNeighborMarginLayoutParams[i].bottomMargin);
            }
        }
    }

    // converts board coordinates to screen coordinates
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
        int offsetX = ((int) (maxScreenWidth / 2) - (int) (currentBoardWidth / 2)); // offset when boardWidth < maxScreenWidth()

        int negativeOffsetX = 0;
        if (offsetX > 0) {
            boardCoordinates[0] = (int) ((screenCoordinates[0] - offsetX) * conversionFactor / zoomFactor);
        } else {
            negativeOffsetX = (int) (left * BOARD_MAX_X);
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

    // convert screen coordinates to board coordinates
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
        int offsetX = ((int) (maxScreenWidth / 2) - (int) (currentBoardWidth / 2));

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

    // general function to get closest station from board coordinates
    // pass in the board coordinates and a selection of stations (i.e. the neighbors of a station)
    int getClosestStationToTouchedBoardCoordinates(int[] touchedBoardCoordinates,
                                                   int[] selectionOfStations) {
        int distance;
        int closestDistance = 2147483647; // max of int
        int deltaX;
        int deltaY;
        int closestStation = 0;
        int amountOfStations = selectionOfStations.length;
        Log.d(TAG, "getClosestStationToTouchedBoardCoordinates: amountOfStations = " + amountOfStations);
        // int[] stations = new int[amountOfStations + 1]; // todo: delete later
        int[] stationsXCoordinates = new int[amountOfStations];
        int[] stationsYCoordinates = new int[amountOfStations];
        for (int i = 0; i < amountOfStations; i++) {
            stationsXCoordinates[i] = serverDatabase.getStation(selectionOfStations[i]).getX();
            stationsYCoordinates[i] = serverDatabase.getStation(selectionOfStations[i]).getY();
        }

        for (int i = 0; i < amountOfStations; i++) {
            deltaX = touchedBoardCoordinates[0] - stationsXCoordinates[i]; // no need to get absolute value,
            deltaY = touchedBoardCoordinates[1] - stationsYCoordinates[i]; // because they are squared later

            // get closest distance by pythagoras
            distance = deltaX * deltaX + deltaY * deltaY;
            if (distance < closestDistance) {
                closestDistance = distance;
                closestStation = selectionOfStations[i];
                Log.d(TAG, "getClosestStationToTouchedBoardCoordinates: loop closestStation = " +
                        closestStation);
            }
        }
        return closestStation;
    }


    int[] getTaxiNeighborStationsFromGivenStation(int station) {
        return serverDatabase.getStation(station).getTaxi();
    }

    int[] getBusNeighborStationsFromGivenStation(int station) {
        return serverDatabase.getStation(station).getBus();
    }

    int[] getUndergroundNeighborStationsFromGivenStation(int station) {
        return serverDatabase.getStation(station).getUnderground();
    }

    // was here for testing purposes only
    int[] getAllStations() {
        int[] allStations = new int[200];
        // select all Stations for testing purposes
        for (int i = 1; i < 200; i++) {
            allStations[i] = i;
        }
        return allStations;
    }

    //display PopupMenu on button click
    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }
    //what happens for each option selected from Mr. X' abilities
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemBlackTicket:
                //check if there are any blackTickets remaining
                if(0 < blackTicketCount) {
                    blackTicket = true;
                    blackTicketCount = blackTicketCount -1;
                    Toast.makeText(this, "Black Ticket selected", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this, "No Black Tickets remaining", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemDoubleTurn:
                Toast.makeText(this, "Double Turn selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    //Find all views and set Tag to all draggable views
    private void findViews() {
        playerLayout1 = (LinearLayout) findViewById(R.id.playerLayout1);
        playerLayout1.setTag(pLAYOUT_VIEW_TAG);
    }

    //Implement long click and drag listener
    private void implementEvents() {
        //add or remove any view that you don't want to be dragged
        playerLayout1.setOnLongClickListener(this);

        //add or remove any layout view that you don't want to accept dragged view
        findViewById(R.id.jTPosition1).setOnDragListener(this); //require color set in xml
        findViewById(R.id.jTPosition2).setOnDragListener(this);
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
                    return true; // returns true to indicate that the View can accept the dragged data.
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
                    Toast.makeText(this, "Player Info has been moved", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Player Info has not been moved", Toast.LENGTH_SHORT).show();

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

    //method to display current taxi count value on textView
    private void displayTaxi(int number) {
        TextView displayTaxi = (TextView) findViewById(R.id.taxiTicketView1);
        displayTaxi.setText("" + number);
    }

    private void displayBus(int number) {
        TextView displayBus = (TextView) findViewById(R.id.busTicketView1);
        displayBus.setText("" + number);
    }

    private void displayUnderground(int number) {
        TextView displayUnderground = (TextView) findViewById(R.id.undergroundTicketView1);
        displayUnderground.setText("" + number);
    }

    private void displayTurnCount(int number){
        TextView displayTurnCount = (TextView) findViewById(R.id.currentTurnView);
        displayTurnCount.setText("Turn " + number);
    }

    private void displayBlackTicketCount(int number){
        TextView displayTurnCount = (TextView) findViewById(R.id.blackTicketCountView);
        displayTurnCount.setText("" + number);
    }

    //method called to display nicknames on GameScreen
    void displayNicknames(String[] nicknames) {
        for (int i = 0; i < nicknames.length; i++) {
            switch (i) {
                case 0:
                    MrXNameGameView.setText(nicknames[i]);
                    break;
                case 1:
                    player2NameGameView.setText(nicknames[i]);
                    break;
            }
        }
    }
}
