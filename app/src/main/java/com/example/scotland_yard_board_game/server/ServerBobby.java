package com.example.scotland_yard_board_game.server;

// TODO: 5/1/2022 Clarify implementation in terms of how bobbys move 
public class ServerBobby {
    private int id;
    private String nickname;
    private ServerStation position;
    private Colour colour = Colour.RAINBOW;
    private boolean turn;

    public ServerBobby(int clientid, String nickname) {
        this.id = clientid;
        this.nickname = nickname;
    }
    
    public void setPosition(ServerStation position) {
        this.position = position;
    }
    
    //Get neighbours of current station
    public int[][] getPlayerNeighbours(){
       /* int[][] neighbours;
        neighbours = this.position.getNeighbours();
        return neighbours; */
        return null;
    }

}
