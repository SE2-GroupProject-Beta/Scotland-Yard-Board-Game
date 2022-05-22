package com.example.scotland_yard_board_game.server;

public class ServerMrX implements Player {
    private int id;
    private String nickname;
    private int[] inventory = {2,0}; //Double move, Black Tickets
    private ServerStation position;
    private Colour colour = Colour.TRANSPARENT ;
    private boolean turn;
    private int moves =1;

    public ServerMrX(int clientid, String nickname) {
        this.id = clientid;
        this.nickname = nickname;
    }

    //MrX gets as many Double moves as there are detectives -> declared after game start
    public void setDoubleMove (int NumDetectives) {
        this.inventory[1] = NumDetectives;
    }

    public void setPosition(ServerStation position) {
        this.position = position;
    }


    public boolean validmove(int stationid, int type) {
        int[] neighbours = this.position.getNeighbours(type);
        for(int i=0; i<neighbours.length; i++) {
            if (neighbours[i] == stationid) {
                return true; // TODO: 5/5/2022 implement check for black ticket
            } else {
                return false;
            }
        }
        return false;
    }



    //If item available -> use it, otherwise return false
    public boolean useItem(int itemid){

        if(this.inventory[itemid]>0){ // TODO: 4/30/2022 implement double move action
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

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public int getmoves() {
        return moves;
    }

    public void setmoves(int moves) {
        this.moves = moves;
    }
}
