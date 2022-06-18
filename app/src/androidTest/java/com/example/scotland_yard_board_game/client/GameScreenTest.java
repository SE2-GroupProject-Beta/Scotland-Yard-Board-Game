
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

package com.example.scotland_yard_board_game.client;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import androidx.test.runner.AndroidJUnitRunner; // todo: from Fabian
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

public class GameScreenTest {
    /*
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int[] testTouched = {550, 160};
        int[] testStations = {1, 2, 3};
        // assertEquals(1, appContext.getClosestStationToTouchedBoardCoordinates(testTouched, testStations));
        assertTrue(true); // ;)
    }

    @Test
    public void testGetClosestStationToTouchedBoardCoordinates() {
        // Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int[] testTouched = {550, 160};
        int[] testStations = {1, 2, 3};

        // assertEquals(1, appContext.getClosestStationToTouchedBoardCoordinates(testTouched, testStations));

    } */
}

/*
public class MainActivityTest extends ActivityTestCase {


public void testHappy(){
    Assert.assertTrue(true);
}

}


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MyJunit4ActivityInstrumentationTest
extends ActivityInstrumentationTestCase2<MyActivity> {

  private MyActivity mActivity;

  public MyJunit4ActivityInstrumentationTest() {
    super(MyActivity.class);
  }

  @Before
  public void setUp() throws Exception {
    super.setUp();
    injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    mActivity = getActivity();
  }

  @Test
  public void checkPreconditions() {
    assertThat(mActivity, notNullValue());
    // Check that Instrumentation was correctly injected in setUp()
    assertThat(getInstrumentation(), notNullValue());
  }

  @After
  public void tearDown() throws Exception {
    super.tearDown();
  }

}
 */