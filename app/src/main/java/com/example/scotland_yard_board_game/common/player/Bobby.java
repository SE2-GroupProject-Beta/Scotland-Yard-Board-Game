package com.example.scotland_yard_board_game.common.player;

import com.example.scotland_yard_board_game.common.Colour;
import com.example.scotland_yard_board_game.common.Station;

// this class is unused
public class Bobby {
    private int id;
    private String nickname;
    private Station position;
    private Colour colour = Colour.RAINBOW;
    private boolean turn;

    public Bobby(int clientid, String nickname) {
        this.id = clientid;
        this.nickname = nickname;
    }
    
    public void setPosition(Station position) {
        this.position = position;
    }
    
    //Get neighbours of current station
    public int[] getPlayerNeighbours(int type){
        int[] neighbours;
        neighbours = position.getNeighbours(type);
        return neighbours;
    }

}
