package com.example.scotland_yard_board_game;

public class Detective {
    private int id;
    private String nickname;
    private int[] inventory = {10,8,4};
    private Station position;
    private Colour colour;

    public Detective(int Clientid, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public void setColour(Colour a){
        this.colour = a;
    }

    public void setPosition(Station position) {
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



}
