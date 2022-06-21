package com.example.scotland_yard_board_game.server;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import com.esotericsoftware.kryonet.Server;
import com.example.scotland_yard_board_game.common.Colour;
import com.example.scotland_yard_board_game.common.Station;
import com.example.scotland_yard_board_game.common.messages.GameStart;
import com.example.scotland_yard_board_game.common.messages.fromserver.ColourConfirmed;
import com.example.scotland_yard_board_game.common.messages.fromserver.ColourTaken;
import com.example.scotland_yard_board_game.common.messages.fromserver.DetectivesWon;
import com.example.scotland_yard_board_game.common.messages.fromserver.EndTurn;
import com.example.scotland_yard_board_game.common.messages.fromserver.InvalidMove;
import com.example.scotland_yard_board_game.common.messages.fromserver.JourneyTable;
import com.example.scotland_yard_board_game.common.messages.fromserver.MrXWon;
import com.example.scotland_yard_board_game.common.messages.fromserver.NameTaken;
import com.example.scotland_yard_board_game.common.messages.fromserver.PlayerList;
import com.example.scotland_yard_board_game.common.messages.fromserver.StartTurn;
import com.example.scotland_yard_board_game.common.player.Detective;
import com.example.scotland_yard_board_game.common.player.MrX;
import com.example.scotland_yard_board_game.common.player.Player;
import com.example.scotland_yard_board_game.common.StationDatabase;

import java.util.ArrayList;

public class ServerData {

    private final Server server;
    private final StationDatabase stationDatabase;
    private final ArrayList<Player> clients = new ArrayList<>(6);
    private final PlayerList playerList = new PlayerList();
    private final JourneyTable journeyTable = new JourneyTable();
    private boolean started = false;
    private int mrxturns = 0;
    private int playerturn;  //Which player is allowed to move
    private int[] playerOrder; //In which order players move

    public ServerData(Context context, Server server) {
        this.server = server;
        journeyTable.journeyTable = new int[24][2];

        this.stationDatabase = new StationDatabase(context);
        int[] testStart = stationDatabase.getRandomStart(4);
        for (int a : testStart) {
            Log.d(TAG, String.valueOf(a));
        }
        Station station = stationDatabase.getStation(1);
        Log.d(TAG, String.valueOf(station.getX()));
    }

    //Check if Player colour available
    public void playercolour(Colour colour, int conid) {
        for (Player a : clients) {
            if (a.getColour() == colour) {
                server.sendToTCP(conid, new ColourTaken());
            }
        }
        for (Player a : clients) {
            if (a.getConId() == conid) {
                a.setColour(colour);
                server.sendToTCP(conid, new ColourConfirmed());
                updatePlayerList();
            }
        }

    }

    //Not implemented
    public boolean useItem(int clientid, int itemid) { //will be used for mrx
        for (Player a : clients) {
            if (a.getId() == clientid) {
                return a.useItem(itemid);
            }
        }
        return false;
    }

    //Validates player moves, triggers refresh of journeyTable and starts next players move
    public void move(int conid, int Stationid, int type, boolean mrx) {
        for (Player a : clients) {
            if (a.getConId() == conid && a.getConId() == playerOrder[playerturn]) {
                Log.d(TAG, Stationid + " " + type);
                boolean valid = a.validmove(Stationid, type);
                if (valid && Stationid != clients.get(0).getPosition().getId()) {
                    a.setPosition(stationDatabase.getStation(Stationid));
                    updatePlayerList();
                    if (mrx && mrxturns < journeyTable.journeyTable.length) {
                        journeyTable.journeyTable[mrxturns][0] = type;
                        journeyTable.journeyTable[mrxturns][1] = Stationid;
                        mrxturns++;
                        server.sendToAllTCP(journeyTable);
                    }
                    server.sendToTCP(conid, new EndTurn());
                    playerturn++;
                    startPlayerTurn();
                } else if (valid && Stationid == clients.get(0).getPosition().getId()) {
                    server.sendToAllTCP(new DetectivesWon());
                } else {
                    server.sendToTCP(conid, new InvalidMove());
                }
            } else if (a.getPosition().getId() == Stationid && !(a instanceof MrX)) {
                server.sendToTCP(conid, new InvalidMove());
            }
        }

    }

    //Connect player if space in lobby and game not started
    public boolean connectPlayer() {
        return !started && clients.size() < 6;
    }

    // Player fully joins when he sends his nickname
    public void joinPlayer(int conid, String nickname, int type) {
        int playerId = clients.size();
        for (Player a : clients) {
            if (nickname.equals(a.getNickname())) {
                server.sendToTCP(conid, new NameTaken());
            }
        }
        if (type == 0) {
            clients.add(new MrX(playerId, conid, nickname));
        } else {
            clients.add(new Detective(playerId, conid, nickname));
        }

        updatePlayerList();
    }


    //On game start distribute starting points and calculate turn order
    public void gameStart() {
        if (/*Clients.size() >= 2*/ true) {
            started = true;
            calculatePlayerOrder(clients.size());
            int[] startpoints = stationDatabase.getRandomStart(clients.size());
            int index = 1;
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i) instanceof MrX && startpoints[0] != 0) {
                    clients.get(i).setPosition(stationDatabase.getStation(startpoints[0]));
                    startpoints[0] = 0;
                } else {
                    clients.get(i).setPosition(stationDatabase.getStation(startpoints[index]));
                    index++;
                }
            }
            updatePlayerList();
            server.sendToAllTCP(new GameStart());
            startPlayerTurn();
        }

    }

    //Send updated PlayerList to all clients
    public void updatePlayerList() {
        playerList.Players = clients;
        server.sendToAllTCP(playerList);
    }

    //Remove disconnected player from clients
    public void disconnectPlayer(int conid) {
        if (!clients.isEmpty()) {
            for (Player a : clients) {
                if (a.getConId() == conid) {
                    clients.remove(a.getId());
                }
            }
            updatePlayerList();
            calculatePlayerOrder(clients.size());
        }

    }

    //Start player turn
    public void startPlayerTurn() {
        //If the last player did not land on MrX field on the last turn -> MrX winns
        if (playerturn >= playerOrder.length && mrxturns != journeyTable.journeyTable.length - 1) {
            playerturn = 0;
        } else if (mrxturns == journeyTable.journeyTable.length - 1) {
            server.sendToAllTCP(new MrXWon());
        }
        server.sendToTCP(playerOrder[playerturn], new StartTurn());
    }

    //Calculate player turn order
    private void calculatePlayerOrder(int playerCount) {
        playerOrder = new int[playerCount];
        //Get MrX conid
        for (Player a : clients) {
            if (a instanceof MrX) {
                playerOrder[0] = a.getConId();
            }
        }

        //Get order for other players
        int index = 1;
        for (Player a : clients) {
            if (a instanceof Detective) {
                playerOrder[index] = a.getConId();
                index++;
            }
        }
    }

}
