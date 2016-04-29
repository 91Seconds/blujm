package game;

import ecs100.UI;

/**
 * Created by Dylan on 24/04/16.
 */
public class GMain {

    private boolean exit = false;

    public static void main(String[] args) {
        UI.initialise();
        GWorld world = GWorldLoader.getWorld();
        GGraphics g = new GGraphics(world);
        GLogic l = new GLogic(world);

        while(exit == false) {
            l.update();
            g.draw();
        }

    }

}
