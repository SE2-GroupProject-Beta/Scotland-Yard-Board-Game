package com.example.scotland_yard_board_game.client;

import static org.junit.Assert.*;

import android.content.Context;
import android.os.Looper;

import org.junit.Test;
import org.mockito.Mockito;

public class GameScreenTest {

        @Test
        public void test(){
            Looper mock = Mockito.mock(Looper.class);
            Context ctx = Mockito.mock(Context.class);
            Mockito.when(ctx.getMainLooper()).thenReturn(mock);
            GameScreen gameScreen = new GameScreen();
            gameScreen.setChosenTransport(0);
            gameScreen.blackTicket = false;
            gameScreen.moveOnConfirmButton();
            assertEquals(9,gameScreen.taxiTickets);
        }
}