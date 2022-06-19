package com.example.scotland_yard_board_game.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.scotland_yard_board_game.R;

public class LobbyScreen extends AppCompatActivity {

    static ClientData clientData;
    //Variables for LobbyScreen Views (nicknames)
    TextView player1View;
    TextView player2View;
    TextView player3View;
    TextView player4View;
    TextView player5View;
    TextView player6View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_screen);

        clientData.setLobbyScreen(this); //set clientData to LobbyScreen

        //find output TextViews
        player1View = findViewById(R.id.player1View);
        player2View = findViewById(R.id.player2View);
        player3View = findViewById(R.id.player3View);
        player4View = findViewById(R.id.player4View);
        player5View = findViewById(R.id.player5View);
        player6View = findViewById(R.id.player6View);

        //get saved Nicknames from Server for display
        displayNicknames(clientData.getNicknames());

    }

    void displayNicknames(String[] nicknames){
        //display nickname for each case of player joined
        for(int i = 0; i < nicknames.length; i++){
            switch (i){
                case 0: player1View.setText(nicknames[i]);
                    break;
                case 1: player2View.setText(nicknames[i]);
                    break;
                case 2: player3View.setText(nicknames[i]);
                    break;
                case 3: player4View.setText(nicknames[i]);
                    break;
                case 4: player5View.setText(nicknames[i]);
                    break;
                case 5: player6View.setText(nicknames[i]);
                    break;
            }
        }

    }

    //method with intent to transition to GameScreen (add method to onClick in LobbyScreen.xml)
    public void goGameScreen(View view){

        GameScreen.clientData = this.clientData;
        Intent GameScreen = new Intent(this, GameScreen.class);
        startActivity(GameScreen);
        finish();
    }

}