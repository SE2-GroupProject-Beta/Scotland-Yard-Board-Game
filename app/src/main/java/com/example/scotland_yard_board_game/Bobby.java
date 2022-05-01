package com.example.scotland_yard_board_game;

// TODO: 5/1/2022 Clarify implementation in terms of how bobbys move 
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
    public int[][] getPlayerNeighbours(){
        int[][] neighbours;
        neighbours = this.position.getNeighbours();
        return neighbours;
    }

}
