package com.example.scotland_yard_board_game.client;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.esotericsoftware.kryonet.Client;
import com.example.scotland_yard_board_game.common.KryoRegister;

import java.io.IOException;

public class ClientStart {
    private final Client client;
    private ClientData clientData;

    public ClientStart(Context context, boolean mrx) throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        client = new Client();
        clientData = new ClientData(context, client, mrx);

        KryoRegister.registerClasses(client.getKryo());

        client.start();
        client.addListener(new ClientListener(clientData));
        Log.d(TAG, "Client Started!");
        clientData.connectServer();

    }

    public ClientData getClientData() {
        return clientData;
    }
}
