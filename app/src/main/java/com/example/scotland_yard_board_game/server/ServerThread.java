package com.example.scotland_yard_board_game.server;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class ServerThread extends Thread {
    private Context context;
    private ServerDatabase serverDatabase;
    private ArrayList<Player> Clients;

    public ServerThread(Context context) {
        this.context = context;
    }
// TODO: 4/28/2022 implement server initialization and gamestate management

    public void run() {

        Log.d(TAG, "Server Started");
        this.serverDatabase = new ServerDatabase(this.context);
        int[] testStart = serverDatabase.getRandomStart(4);
        for (int a: testStart) {
            Log.d(TAG, String.valueOf(a));
        }
        ServerStation station = serverDatabase.getStation(1);
        Log.d(TAG, String.valueOf(station.getX()));

    }

    public void addPlayer(String nickname){ // TODO: 5/5/2022  also needs connection information -> added later
        int clientId;

        if (Clients.size()>0){
            clientId = Clients.size()-1;
            Clients.add(new ServerDetective(clientId,nickname));
        }else {
            clientId = 0; //This assumes mrx is always the first to connect
            Clients.add(new ServerMrX(clientId, nickname));
        }

    }

    public void removePlayer(String nickname){ // TODO: 5/5/2022  how to handle leaving the lobby
        for (Player a: Clients) {
            if(a.getNickname() == nickname ){
                Clients.remove(a);
            }
        }
    }

    //Check if Player colour available
    public boolean playercolour (Colour colour, int clientid){ // TODO: 5/5/2022  which information to use to know who requested change
        for (Player a: Clients) {
            if(a.getColour() == colour ){
                return false;
            }
        }
        for (Player a: Clients) {
            if(a.getId() == clientid ){
                a.setColour(colour);
                return true;
            }
        }

        return false;
    }

    public boolean useItem(int clientid, int itemid){ //will be used for mrx
        for (Player a: Clients) {
            if(a.getId() == clientid ){
              return  a.useItem(itemid);
            }
        }
        return false;
    }

    public boolean move(int clientid, int Stationid, int type){
        for (Player a: Clients) {
            if(a.getId() == clientid ){
                boolean valid = a.validmove(Stationid, type);
                if(valid){
                    a.setPosition(serverDatabase.getStation(Stationid));
                    return true;
                }
            }
        }
        return false;
    }

}
