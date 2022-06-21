package com.example.scotland_yard_board_game.server;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.esotericsoftware.kryonet.Server;
import com.example.scotland_yard_board_game.common.KryoRegister;

import java.io.IOException;

public class ServerStart {
    private final Server server;
    private ServerData serverData;

    public ServerStart(Context context) throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        server = new Server();
        serverData = new ServerData(context, server);

        KryoRegister.registerClasses(server.getKryo());
        server.addListener(new ServerListener(serverData));

        server.start();
        server.bind(54555, 54777);
        Log.d(TAG, "Server Started!");

    }
}
