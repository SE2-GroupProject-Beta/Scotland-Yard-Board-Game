package com.example.scotland_yard_board_game.utils;

import androidx.appcompat.app.AppCompatActivity;



public class MessageEncoder extends AppCompatActivity {
    /*
    Encodes and decodes messages between client and server

    client -> server:
      select station to move to

    server -> client:
      provide list of all players and after each round

     */


    public boolean encodeMessage() {
        boolean isProperlyEncoded = false;


        return isProperlyEncoded;
    }

    /*public String playerPositionsAndWinningState(int misterX, int playerA, int playerB,
                                  int PlayerC, int PlayerD, int playerE) { */

    public String playerPositionsAndWinningState(int[] playerPositions) {

        StringBuilder playerPosition = new StringBuilder();
        for (int playerPos : playerPositions) {
            playerPosition.append(" ");
            playerPosition.append(Integer.toString(playerPos));
        }


        return playerPosition.toString();
    }
}
