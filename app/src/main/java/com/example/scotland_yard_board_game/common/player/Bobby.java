package com.example.scotland_yard_board_game.common.player;

import com.example.scotland_yard_board_game.common.Colour;
import com.example.scotland_yard_board_game.common.ServerStation;

// TODO: 5/1/2022 Clarify implementation in terms of how bobbys move
public class Bobby {
    private int id;
    private String nickname;
    private ServerStation position;
    private Colour colour = Colour.RAINBOW;
    private boolean turn;

    public Bobby(int clientid, String nickname) {
        this.id = clientid;
        this.nickname = nickname;
    }
    
    public void setPosition(ServerStation position) {
        this.position = position;
    }
    
    //Get neighbours of current station
    public int[] getPlayerNeighbours(int type){
        int[] neighbours;
        neighbours = position.getNeighbours(type);
        return neighbours;
    }

}
