package manual_test;

import ecs100.UI;
import game.GGraphics;
import game.GWorld;

/**
 * Created by Dylan on 30/04/16.
 */
public class GGraphicsManualTest {

    public void testBG() {
        UI.initialise();

        // Note: may break as constructor is changed
        GWorld w = new GWorld(20, 15);
        GGraphics.drawWorld(w);
    }

    public void testCells() {
//        UI.initialise();
//
//        // Note: may break as constructor is changed
//        GWorld w = new GWorld(20, 15);
        // TODO setup some cells
//        GGraphics.drawWorld(w);
    }

}
