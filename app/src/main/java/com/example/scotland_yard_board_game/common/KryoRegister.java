package com.example.scotland_yard_board_game.common;

import com.esotericsoftware.kryo.Kryo;
import com.example.scotland_yard_board_game.common.messages.fromserver.ColourTaken;
import com.example.scotland_yard_board_game.common.messages.fromclient.DetectiveNickname;
import com.example.scotland_yard_board_game.common.messages.GameStart;
import com.example.scotland_yard_board_game.common.messages.fromserver.InvalidMove;
import com.example.scotland_yard_board_game.common.messages.fromserver.JourneyTable;
import com.example.scotland_yard_board_game.common.messages.fromclient.Move;
import com.example.scotland_yard_board_game.common.messages.fromclient.MrXNickname;
import com.example.scotland_yard_board_game.common.messages.fromserver.PlayerConnected;
import com.example.scotland_yard_board_game.common.messages.fromserver.PlayerJoined;
import com.example.scotland_yard_board_game.common.messages.fromserver.PlayerList;
import com.example.scotland_yard_board_game.common.messages.fromserver.ServerFull;
import com.example.scotland_yard_board_game.common.player.Detective;
import com.example.scotland_yard_board_game.common.player.MrX;

// This class registers all needed messages for Kryo

public class KryoRegister {

    private KryoRegister() {
    }

    public static void registerClasses(Kryo kryo) {
        kryo.register(DetectiveNickname.class);
        kryo.register(MrXNickname.class);
        kryo.register(PlayerConnected.class);
        kryo.register(PlayerJoined.class);
        kryo.register(PlayerList.class);
        kryo.register(ServerFull.class);
        kryo.register(GameStart.class);
        kryo.register(Move.class);
        kryo.register(InvalidMove.class);
        kryo.register(ColourTaken.class);
        kryo.register(Colour.class);
        kryo.register(JourneyTable.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(MrX.class);
        kryo.register(Detective.class);
        kryo.register(int[].class);
        kryo.register(Station.class);
    }

}
