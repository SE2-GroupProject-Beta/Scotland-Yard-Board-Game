package com.example.scotland_yard_board_game.common;

public class Station {
   private int id;
   private int x;
   private int y;
   //Neighbours
   private int[] taxi;
   private int[] bus;
   private int[] underground;
   private int[] ferry;

    public Station() {
    }

    public Station(int id, int x, int y, int[] taxi, int[] bus, int[] underground, int[] ferry) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.taxi = taxi;
        this.bus = bus;
        this.underground = underground;
        this.ferry = ferry;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getTaxi() {
        return taxi;
    }

    public int[] getBus() {
        return bus;
    }

    public int[] getUnderground() {
        return underground;
    }

    public int[] getFerry() {
        return ferry;
    }

    public int[] getNeighbours(int type){
        int[] neighbours;
        switch(type){

            case 0: return neighbours = taxi;
            case 1: return neighbours = bus;
            case 2: return neighbours = underground;
            case 3: return neighbours = ferry;
        }

        return null;
    }
}
