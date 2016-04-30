package game;

import ecs100.UI;

/**
 * Created by Dylan on 24/04/16.
 */
public class GMain {


    private static boolean shouldExit = false;
    private static int currentWorld = 1;

    /**
     * Keep these public so the manual test can reuse these values
     */
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 1000;
    public static final double DIVIDER_POSITION = 0;

    // TODO we will have to move the code in the main method into a new class
    // if we are to have a main menu screen (do we need one?)

    public static void main(String[] args) {
        UI.initialise();
        UI.setWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        UI.setDivider(DIVIDER_POSITION);
        UI.setImmediateRepaint(false);

        GWorld world = loadWorld(currentWorld);
        GLogic gLogic = new GLogic(world);
        GInput gInput = new GInput(gLogic);
        GSideMenu gSideMenu = new GSideMenu(currentWorld, System.currentTimeMillis());

        while(shouldExit == false) {
            gInput.update();
            gLogic.update();
            gSideMenu.update();
            GGraphics.drawWorld(world);
            UI.sleep(10);
        }

    }

    /**
     * Tries to load the windows save, and if that fails
     * loads the unix save
     * @param world
     * @return
     */
    private static GWorld loadWorld(int world) {
        try {
            GWorldLoader.loadWorld(world, true);
        } catch (Exception e) {
            GWorldLoader.loadWorld(world, false);
        }
    }

}
