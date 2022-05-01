package com.example.scotland_yard_board_game;

public class Station {
   private int id;
   private int x;
   private int y;
   private int[][] neighbours; //2d array -> [ [id,type] ]

    public Station(int id, int x, int y, int[][] neighbours) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.neighbours = neighbours;
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

    public int[][] getNeighbours() {
        return neighbours;
    }
}
