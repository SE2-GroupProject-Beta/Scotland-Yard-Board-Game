package com.example.scotland_yard_board_game.server;

public class ServerDetective {
    private int id;
    private String nickname;
    private int[] inventory = {10,8,4}; //Taxi, Bus, Underground
    private ServerStation position;
    private Colour colour;
    private boolean turn;

    public ServerDetective(int clientid, String nickname) {
        this.id = clientid;
        this.nickname = nickname;
    }

    public void setColour(Colour a){
        this.colour = a;
    }

    public void setPosition(ServerStation position) {
        this.position = position;
    }

    //If item available -> use it, otherwise return false
    public boolean useItem(int itemeid){

        if(this.inventory[itemeid]>0){
            this.inventory[itemeid] -=1;
            return true;
        }

        return false;
    }

    //Get neighbours of current station
    public int[][] getPlayerNeighbours(){
        int[][] neighbours;
        neighbours = this.position.getNeighbours();
        return neighbours;
    }


}
