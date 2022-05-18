package com.example.scotland_yard_board_game.server;

public class ServerDetective implements Player {
    private int id;
    private String nickname;
    private int[] inventory = {10,8,4}; //Taxi, Bus, Underground
    private ServerStation position;
    private Colour colour;
    private boolean turn;
    private int moves = 1;

    public ServerDetective(int clientid, String nickname) {
        this.id = clientid;
        this.nickname = nickname;
    }

    public void setColour(Colour colour){
        this.colour = colour;
    }

    public boolean validmove(int stationid) { //Validate if station is neighbour and if sufficient tickets are available
       /* int[][] neighbours = this.position.getNeighbours();
        for(int i=0; i<neighbours.length; i++) {
            if (neighbours[i][0] == stationid) {
                switch (neighbours[i][1]){
                    case -1:
                        return useItem(0);
                    case -2:
                        return useItem(1);
                    case -3:
                        return useItem(2);
                }
            } else {
                return false;
            }
        } */
        return false;
    }

    public void setPosition(ServerStation position) {
        this.position = position;
    }

    //If item available -> use it, otherwise return false
    public boolean useItem(int itemid){

        if(this.inventory[itemid]>0){
            this.inventory[itemid] -=1;
            return true;
        }

        return false;
    }

    //Get neighbours of current station
    public int[][] getPlayerNeighbours(){
      /*  int[][] neighbours;
        neighbours = this.position.getNeighbours();
        return neighbours; */
        return null;
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
}
