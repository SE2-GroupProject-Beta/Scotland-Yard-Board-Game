package com.example.scotland_yard_board_game;

import java.util.List;
import java.util.Random;

public class Station {
    private final int number;
    // private boolean isFree;
    private List<Station> neighbourTaxi;
    private List<Station> neighbourBus;
    private List<Station> neighbourUground;
    private List<Station> neighbourFerry;
    private final int x;
    private final int y;

    // public Station() {}

    public Station(int number, int x, int y, List<Station> neighbourTaxi) {
        this.number = number;
        // this.isFree = true;
        this.x = x;
        this.y = y;
        this.neighbourTaxi = neighbourTaxi;
    }

    //Hier there are just some nodes, MUST change start nodes to those in the real game
    //TODO: implement a check that the start node is free
    /*
    public static Station chooseStartNode() {
        int[] startNodes = {11, 23, 45, 60, 123, 145, 166, 190};
        Random random = new Random();
        int number = random.nextInt(startNodes.length);
        Station startStation = new Station();
        // startStation.isFree = false;
        return startStation;
    } */

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumber() {
        return number;
    }
}