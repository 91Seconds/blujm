package manual_test;

import ecs100.UI;
import game.GCell;
import game.GGraphics;
import game.GWorld;

/**
 * Created by Dylan on 30/04/16.
 */
public class GGraphicsManualTest {

    public static void main(String[] args){
        GGraphicsManualTest gTest = new GGraphicsManualTest();
        gTest.testBG();
    }

    /**
     * Tested and completed
     */
    public void testBG() {
        UI.initialise();

        GWorld w = new GWorld(7, 11, GWorld.KEY_I_AM_TESTING_ONLY);
        GGraphics.drawWorld(w);
    }

    /**
     *
     */
    public void testCellsAndBG() {
        UI.initialise();

        GWorld w = new GWorld(22, 18, GWorld.KEY_I_AM_TESTING_ONLY);

        {
//            GCell gCell = new GCell();
        }

        GGraphics.drawWorld(w);
    }

}
