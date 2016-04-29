package game;

import ecs100.UI;

/**
 * Created by Dylan on 24/04/16.
 */
public class GMain {

    private static boolean shouldExit = false;
    private static int currentWorld = 1;

    // TODO we will have to move the code in the main method into a new class
    // if we are to have a main menu screen (do we need one?)

    public static void main(String[] args) {
        UI.initialise();
        GWorld world = GWorldLoader.getWorld(currentWorld);
        GLogic l = new GLogic(world);

        while(shouldExit == false) {
            l.update();
            GGraphics.drawWorld(world);
        }

    }

}
