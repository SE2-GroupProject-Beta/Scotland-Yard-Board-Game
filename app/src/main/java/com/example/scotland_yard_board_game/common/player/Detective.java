package com.example.scotland_yard_board_game.common.player;

import com.example.scotland_yard_board_game.common.Colour;
import com.example.scotland_yard_board_game.common.Station;

public class Detective implements Player {
    private int id;
    private int conId;
    private String nickname;
    private int[] inventory = {10,8,4}; //Taxi, Bus, Underground
    private Station position;
    private Colour colour;
    private boolean turn;
    private int moves = 1;

    //Kryonet
    public Detective() {
    }

    public Detective(int clientid, int conId, String nickname) {
        this.id = clientid;
        this.conId = conId;
        this.nickname = nickname;
    }

    public void setColour(Colour colour){
        this.colour = colour;
    }

    public boolean validmove(int stationid, int type) { //Validate if station is neighbour and if sufficient tickets are available
       int[] neighbours = this.position.getNeighbours(type);
        for(int i=0; i<neighbours.length; i++) {
            if (neighbours[i] == stationid) {
                switch (type){
                    case 0:
                        return useItem(0);
                    case 1:
                        return useItem(1);
                    case 2:
                        return useItem(2);
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public void setPosition(Station position) {
        this.position = position;
    }

    public Station getPosition() {
        return position;
    }

    //If item available -> use it, otherwise return false
    public boolean useItem(int itemid){

        if(this.inventory[itemid]>0){
            this.inventory[itemid] -=1;
            return true;
        }

        return false;
    }

    public String getNickname() {
        return nickname;
    }

    public Colour getColour() {
        return colour;
    }

    public int getId() {
        return id;
    }

    public int getmoves() {
        return moves;
    }

    public void setmoves(int moves) {
        this.moves = moves;
    }

    public int getConId() {
        return conId;
    }
}
