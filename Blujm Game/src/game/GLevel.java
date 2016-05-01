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

    /**
     * Plays the level until the game quits
     * @return
     */

    public int playLevel() {
        // Make sure we're getting a fresh GLogic
        GLogic.makeReferenceNull();
        GLogic gLogic = GLogic.getGLogic(world);

        while (!GLogic.shouldQuit() && !GLogic.shouldRestart()) {
            gLogic.update();
            GGraphics.drawWorld(world);
            UI.sleep(30);
        }

        if (GLogic.shouldRestart()) return KEY_RESTART;
        return KEY_QUIT;

    }
}
