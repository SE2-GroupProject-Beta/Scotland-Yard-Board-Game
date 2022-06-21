package com.example.scotland_yard_board_game.client;

// import static org.junit.jupiter.api.Assertions.assertEquals;

import junit.framework.TestCase;

public class GameScreenTest extends TestCase {

    /*
    public void testCalculateScreenCoordinates1() {
        GameScreen gameScreen = new GameScreen();

        int[] test1 = {700, 363};
        int[] result1 = {1601, 721};
        assertEquals(result1, gameScreen.calculateScreenCoordinates(test1));
    } */

    // @Test

    public void testMyTestMethod() {
        GameScreen gameScreen = new GameScreen();
        assertEquals(6, gameScreen.myTestMethod(3));
    }
}