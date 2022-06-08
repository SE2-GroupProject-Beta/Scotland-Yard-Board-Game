package com.example.scotland_yard_board_game.common;

import com.esotericsoftware.kryo.Kryo;
import com.example.scotland_yard_board_game.common.messages.ColourTaken;
import com.example.scotland_yard_board_game.common.messages.DetectiveNickname;
import com.example.scotland_yard_board_game.common.messages.GameStart;
import com.example.scotland_yard_board_game.common.messages.InvalidMove;
import com.example.scotland_yard_board_game.common.messages.JourneyTable;
import com.example.scotland_yard_board_game.common.messages.Move;
import com.example.scotland_yard_board_game.common.messages.MrXNickname;
import com.example.scotland_yard_board_game.common.messages.PlayerConnected;
import com.example.scotland_yard_board_game.common.messages.PlayerJoined;
import com.example.scotland_yard_board_game.common.messages.PlayerList;
import com.example.scotland_yard_board_game.common.messages.ServerFull;

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
    }

}
