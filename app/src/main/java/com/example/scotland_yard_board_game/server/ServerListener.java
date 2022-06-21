package com.example.scotland_yard_board_game.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.example.scotland_yard_board_game.common.Colour;
import com.example.scotland_yard_board_game.common.messages.fromclient.DetectiveNickname;
import com.example.scotland_yard_board_game.common.messages.GameStart;
import com.example.scotland_yard_board_game.common.messages.fromclient.Move;
import com.example.scotland_yard_board_game.common.messages.fromclient.MrXNickname;
import com.example.scotland_yard_board_game.common.messages.fromserver.PlayerConnected;
import com.example.scotland_yard_board_game.common.messages.fromserver.ServerFull;

public class ServerListener extends Listener {
        private ServerData serverData;

        public ServerListener(ServerData serverData) {
            this.serverData = serverData;
        }

        @Override
        public void connected(Connection connection) {
            Log.info("Player connected: " + connection.toString() +
                    " from endpoint " + connection.getRemoteAddressTCP().toString());

            if (serverData.connectPlayer()) {
                Log.info("Player " + connection + "joined the server.");
                connection.sendTCP(new PlayerConnected());
            } else {
                Log.error("Connection: " + connection + " Lobby already full! ");
                connection.sendTCP(new ServerFull());
                connection.close();
            }
        }

        @Override
        public void received(Connection connection, Object object) {

            // keep KeepAlive messages from spamming the log
            if (!(object instanceof FrameworkMessage.KeepAlive)) {
                Log.info("Message received from Client " + connection.toString() +
                        " at endpoint " + connection.getRemoteAddressTCP().toString() +
                        "; message class = " + object.getClass().getTypeName());
            }

            if (object instanceof DetectiveNickname) {
                DetectiveNickname name = (DetectiveNickname) object;
                Log.debug("Detective " + connection.getID() + ": " + name.nickname + "joined the game");
                serverData.joinPlayer(connection.getID(), name.nickname, 1);
            } else if (object instanceof MrXNickname) {
                MrXNickname name = (MrXNickname) object;
                Log.debug("MrX " + connection.getID() + ": " + name.nickname + "joined the game");
                serverData.joinPlayer(connection.getID(), name.nickname, 0);
            } else if (object instanceof GameStart) {
                Log.debug(connection.getID() + "game started");
                serverData.gameStart();
            } else if (object instanceof Move){
                Move move = (Move) object;
                Log.debug("Moving " + connection.getID() + "to stationid " + move.station);
                serverData.move(connection.getID(), move.station, move.type, move.mrx);
            } else if (object instanceof Colour) {
                Colour colour = (Colour) object;
                Log.debug("Player " + connection.getID() + "selected colour " + colour);
                serverData.playercolour(colour,connection.getID());
            }
        }

        @Override
        public void disconnected (Connection connection) {
            Log.info("Player disconnected");
            serverData.disconnectPlayer(connection.getID());
        }
}


