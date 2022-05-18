package com.example.scotland_yard_board_game.server;

public class ServerStation {
   private int id;
   private int x;
   private int y;
   //Neighbours
   private int[] taxi;
   private int[] bus;
   private int[] underground;
   private int[] ferry;

    public ServerStation(int id, int x, int y, int[] taxi, int[] bus, int[] underground, int[] ferry) {
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
}
