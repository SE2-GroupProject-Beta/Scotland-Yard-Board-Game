package com.example.scotland_yard_board_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LobbyScreen extends AppCompatActivity {

    ClientNetworkClient networkClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_screen);

        networkClient = new ClientNetworkClient(this);
    }

    /*
    //method with intent to transition to GameScreen (add method to onClick in LobbyScreen.xml)
    public void goGameScreen(View view){

        Intent GameScreen = new Intent(this, GameScreen.class);
        startActivity(GameScreen);
    }
    */

    //In your application's Activity

    @Override
    protected void onPause() {
        networkClient.tearDown();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (networkClient != null) {
            nsdHelper.registerService(connection.getLocalPort());
            nsdHelper.discoverServices();
        }
    }

    @Override
    protected void onDestroy() {
        nsdHelper.tearDown();
        connection.tearDown();
        super.onDestroy();
    }

    // NsdHelper's tearDown method
    public void tearDown() {
        nsdManager.unregisterService(registrationListener);
        nsdManager.stopServiceDiscovery(discoveryListener);
    }

}