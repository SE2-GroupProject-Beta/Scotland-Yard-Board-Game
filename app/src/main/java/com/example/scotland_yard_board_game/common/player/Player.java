package com.example.scotland_yard_board_game.common.player;

import com.example.scotland_yard_board_game.common.Colour;
import com.example.scotland_yard_board_game.common.Station;

    public interface Player {

        int getId();
        String getNickname();
        int[][] getPlayerNeighbours();
        boolean useItem(int itemid);
        void setPosition(Station position);
        Colour getColour();
        void setColour(Colour colour);
        boolean validmove(int stationid, int type);
        int getmoves ();
        void setmoves (int moves);
        int getConId();
        Station getPosition();
    }

