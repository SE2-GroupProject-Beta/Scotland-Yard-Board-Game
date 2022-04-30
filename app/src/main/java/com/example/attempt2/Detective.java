package com.example.attempt2;

public class Detective extends Player {
    public Detective(Colours colour, String nickname) {
        super(colour, nickname);
        //"give" the tickets to player - I don't know how many tickets each player schould receive at the beginning
        //TODO: check how many tickets should a player receive at the beginning
        this.taxiTickets = 11;
        this.busTickets = 8;
        this.undergroundTickets = 4;
        this.ferryTickets = 2;      //??
    }

    @Override
    public void makeMove() {

    }
}
