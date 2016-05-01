package game;

import ecs100.UI;

/**
 * Created by Dylan on 1/05/16.
 */
public class GLevel {

    public GLevel(GWorld world) {
        this.world = world;
    }

    private final GWorld world;

    public static final int KEY_QUIT = 1;
    public static final int KEY_RESTART = 2;
    public static final int KEY_WON = 3;

    /**
     * Plays the level until the game quits
     * @return
     */

    public int playLevel(int levelNum) {
        // Make sure we're getting a fresh GLogic
        GLogic.makeReferenceNull();
        GLogic gLogic = GLogic.getGLogic(world);
        GSideMenu sideMenu = new GSideMenu(levelNum);

        while (!GLogic.shouldQuit() && !GLogic.shouldRestart() && !GLogic.hasWon()) {
            gLogic.update();
            sideMenu.update();
            GGraphics.drawWorld(world);
            UI.sleep(30);
        }

        if (GLogic.shouldRestart()) return KEY_RESTART;
        if (GLogic.hasWon()) return KEY_WON;
        return KEY_QUIT;

    }
}
