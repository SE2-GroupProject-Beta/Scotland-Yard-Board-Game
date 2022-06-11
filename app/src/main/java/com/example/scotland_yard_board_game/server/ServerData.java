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
import com.example.scotland_yard_board_game.common.messages.fromserver.InvalidMove;
import com.example.scotland_yard_board_game.common.messages.fromserver.JourneyTable;
import com.example.scotland_yard_board_game.common.messages.fromserver.PlayerList;
import com.example.scotland_yard_board_game.common.player.Detective;
import com.example.scotland_yard_board_game.common.player.MrX;
import com.example.scotland_yard_board_game.common.player.Player;
import com.example.scotland_yard_board_game.common.StationDatabase;

import java.util.ArrayList;

public class ServerData {

    private Context context;
    private Server server;
    private StationDatabase stationDatabase;
    private ArrayList<Player> Clients = new ArrayList<Player>(4);
    private final int min_players = 2;
    private final int max_players = 4;
    private final int max_mrx = 1;
    private boolean started = false;
    private PlayerList playerList = new PlayerList();
    private JourneyTable journeyTable = new JourneyTable();
    //private int [][] journeyTable = new int[24][2];
    private int mrxturn = 0;

    public ServerData(Context context, Server server) {
        this.context = context;
        this.server = server;
        journeyTable.journeyTable = new int[24][2];

        this.stationDatabase = new StationDatabase(this.context);
        int[] testStart = stationDatabase.getRandomStart(4);
        for (int a: testStart) {
            Log.d(TAG, String.valueOf(a));
        }
        Station station = stationDatabase.getStation(1);
        Log.d(TAG, String.valueOf(station.getX()));
    }


// TODO: 6/7/2022 Implement turn based gameplay 


    //Check if Player colour available
    public void playercolour (Colour colour, int conid){
        for (Player a: Clients) {
            if(a.getColour() == colour ){
                server.sendToTCP(conid,new ColourTaken());
            }
        }
        for (Player a: Clients) {
            if(a.getConId() == conid ){
                a.setColour(colour);
                server.sendToTCP(conid, new ColourConfirmed());
                updatePlayerList();
            }
        }

    }

    //todo
    public boolean useItem(int clientid, int itemid){ //will be used for mrx
        for (Player a: Clients) {
            if(a.getId() == clientid ){
              return  a.useItem(itemid);
            }
        }
        return false;
    }

    public void move(int conid, int Stationid, int type, boolean mrx){
        for (Player a: Clients) {
            if(a.getConId() == conid ){
                boolean valid = a.validmove(Stationid, type);
                if(valid){
                    a.setPosition(stationDatabase.getStation(Stationid));
                    updatePlayerList();
                    if(mrx){
                        journeyTable.journeyTable[mrxturn][0] = type;
                        journeyTable.journeyTable[mrxturn][1] = Stationid;
                        server.sendToAllTCP(journeyTable);
                    }
                }
            }
        }
        server.sendToTCP(conid,new InvalidMove());
    }

    //Connect player if space in lobby and game not started
    public synchronized boolean connectPlayer() {
        if (!started && Clients.size() < max_players) {
            return true;
        }
        return false;
    }

    // Player fully joins when he sends his nickname
    public boolean joinPlayer(int conid, String nickname, int type) {
        int playerId = Clients.size();
        for (Player a: Clients){
            if(a.getNickname() == nickname){
            return false;
            }
        }
        if(type == 0){
            Clients.add(new MrX(playerId, conid, nickname));
        }else {
            Clients.add(new Detective(playerId, conid, nickname));
        }

        updatePlayerList();
        return true;
    }



    //On game start distribute starting points
    public void gameStart() {
        if(Clients.size() >= min_players){
            started = true;
            int[] startpoints = stationDatabase.getRandomStart(Clients.size());
            int index = 1;
            for (int i = 0; i < Clients.size(); i++) {
                if(Clients.get(i) instanceof MrX && startpoints[0] != 0){
                    Clients.get(i).setPosition(stationDatabase.getStation(startpoints[0]));
                    startpoints[0] = 0;
                } else {
                    Clients.get(i).setPosition(stationDatabase.getStation(startpoints[index]));
                    index++;
                }
            }
         updatePlayerList();
         server.sendToAllTCP(new GameStart());
        }


    }

    public void updatePlayerList(){
        playerList.Players = Clients;
        server.sendToAllTCP(playerList);
    }

    public void disconnectPlayer(int conid) {
        if(!Clients.isEmpty()){
            for (Player a: Clients){
                if(a.getConId() == conid){
                    Clients.remove(a.getId());
                }
            }
            updatePlayerList();
        }

    }

}
