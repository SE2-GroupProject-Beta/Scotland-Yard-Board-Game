package com.example.scotland_yard_board_game;

public class MrX {
    private int id;
    private String nickname;
    private int[] inventory = {2,0}; //Double move, Black Tickets
    private Station position;
    private Colour colour = Colour.TRANSPARENT ;
    private boolean turn;

    public MrX(int clientid, String nickname) {
        this.id = clientid;
        this.nickname = nickname;
    }

    //MrX gets as many Double moves as there are detectives -> declared after game start
    public void setDoubleMove (int NumDetectives) {
        this.inventory[1] = NumDetectives;
    }

    public void setPosition(Station position) {
        this.position = position;
    }

    //If item available -> use it, otherwise return false
    public boolean useItem(int itemeid){

        if(this.inventory[itemeid]>0){ // TODO: 4/30/2022 implement double move action
            this.inventory[itemeid] -=1;
            return true;
        }

        return false;
    }


}
