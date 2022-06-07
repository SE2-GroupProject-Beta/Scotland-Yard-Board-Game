package com.example.scotland_yard_board_game.common.player;

import com.example.scotland_yard_board_game.common.Colour;
import com.example.scotland_yard_board_game.common.ServerStation;

    public interface Player {

        public int getId();
        public String getNickname();
        public int[][] getPlayerNeighbours();
        public boolean useItem(int itemid);
        public void setPosition(ServerStation position);
        public Colour getColour();
        public void setColour(Colour colour);
        public boolean validmove(int stationid, int type);
        public int getmoves ();
        public void setmoves (int moves);
        public int getConId();
    }

