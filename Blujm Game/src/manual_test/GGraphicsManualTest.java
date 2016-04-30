package manual_test;

import ecs100.UI;
import game.GGraphics;
import game.GWorld;
//import game.GWorld;

/**
 * Created by Dylan on 30/04/16.
 */
public class GGraphicsManualTest {

    public static void main(String[] args){
        GGraphicsManualTest gTest = new GGraphicsManualTest();
        gTest.testBG();
    }

    // Tested and completed
    public void testBG() {
        UI.initialise();

        // Note: may break as constructor is changed
        GWorld w = new GWorld();
        GGraphics.drawWorld(w);
    }

    public void testCells() {
//        UI.initialise();
//
//      // Note: may break as constructor is changed
//      GWorld w = new GWorld();
        // TODO setup some cells

//      GGraphics.drawWorld(w);
    }

}
