package com.example.scotland_yard_board_game;

public class MisterX extends Player {
    private int blackTickets;
    private int doubleMoveTickets;

    public MisterX (String nickname) {
        super(Colours.TRANSPARENT, nickname);
        this.blackTickets = 5;
        this.doubleMoveTickets = 2;
        this.busTickets = 0;
        this.taxiTickets = 0;
        this.ferryTickets = 0;
        this.undergroundTickets = 0;
    }

    @Override
    public void makeMove() {

    }
}
