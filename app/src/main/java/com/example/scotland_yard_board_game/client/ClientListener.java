package com.example.scotland_yard_board_game.client;

import static android.content.ContentValues.TAG;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.example.scotland_yard_board_game.common.Colour;
import com.example.scotland_yard_board_game.common.messages.fromclient.DetectiveNickname;
import com.example.scotland_yard_board_game.common.messages.GameStart;
import com.example.scotland_yard_board_game.common.messages.fromclient.Move;
import com.example.scotland_yard_board_game.common.messages.fromclient.MrXNickname;
import com.example.scotland_yard_board_game.common.messages.fromserver.ColourTaken;
import com.example.scotland_yard_board_game.common.messages.fromserver.InvalidMove;
import com.example.scotland_yard_board_game.common.messages.fromserver.JourneyTable;
import com.example.scotland_yard_board_game.common.messages.fromserver.PlayerConnected;
import com.example.scotland_yard_board_game.common.messages.fromserver.PlayerList;
import com.example.scotland_yard_board_game.common.messages.fromserver.ServerFull;

public class ClientListener extends Listener {
        private final Client client;
        private ClientData clientData;

        public ClientListener(Client client, ClientData clientData) {
            this.client = client;
            this.clientData = clientData;

        }

        @Override
        public void received(Connection connection, Object object) {

            // keep KeepAlive messages from spamming the log
            if (!(object instanceof FrameworkMessage.KeepAlive)) {
                Log.info("Message received from Server " + connection.toString() +
                        " at endpoint " + connection.getRemoteAddressTCP().toString() +
                        "; message class = " + object.getClass().getTypeName());
            }

            if (object instanceof ServerFull) {
                clientData.serverFull();
            } else if (object instanceof ColourTaken) {
                clientData.colourTaken();
            } else if (object instanceof InvalidMove) {
                clientData.invalidMove();
            } else if (object instanceof JourneyTable) {
                JourneyTable jtable = (JourneyTable) object;
                clientData.updateJourneyTable(jtable);
            } else if (object instanceof PlayerConnected) {
                Log.debug(TAG,"Connection successfull");
            } else if (object instanceof PlayerList) {
                PlayerList list = (PlayerList) object;
                clientData.updatePlayers(list);
            } else if (object instanceof GameStart) {
                clientData.gameStarted();
            }


        }

        @Override
        public void disconnected (Connection connection) {
            Log.info("Disconnected");
            clientData.disconnectClient();
            // TODO: 6/9/2022 handle disconnect
        }
}


