package com.example.scotland_yard_board_game.utils;

import androidx.appcompat.app.AppCompatActivity;



public class MessageEncoder extends AppCompatActivity {
    /*
    Encodes and decodes messages between client and server

    client -> server:
      select station to move to

    server -> client:
      provide list of all players and after each round (the only
      message that will be implemented for merge_0516

     */

    public String encodeMessage(int[] playerPositions) {
        // boolean isProperlyEncoded = false;

        // todo: implement encodeMessage();

        return "x 1"; // Mister X is on station 1
    }

    /*public String playerPositionsAndWinningState(int misterX, int playerA, int playerB,
                                  int PlayerC, int PlayerD, int playerE) { */

    public String playerPositionsAndWinningState(int[] playerPositions) {

        StringBuilder messageString = new StringBuilder();
        for (int playerPos : playerPositions) {
            messageString.append(" ");
            messageString.append(Integer.toString(playerPos));
        }

        // todo: implement winning state,
        // todo: add more players, for now only the position of Mister X is sent

        //return messageString.toString();
        return "x 1"; // Mister X is on station 1
    }
}
