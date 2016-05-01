package game;

import ecs100.UI;

/**
 * Created by Dylan on 1/05/16.
 */
public class GLevel {

    public GLevel(GWorld world) {
        this.world = world;
    }

    private boolean shouldExit = false;
    private final GWorld world;

    /**
     * Plays the level until the game quits
     * @return
     */
    public boolean playLevel() {
        // Make sure we're getting a fresh GLogic
        GLogic.makeReferenceNull();
        GLogic gLogic = GLogic.getGLogic(world);

        while(shouldExit == false) {
            shouldExit = gLogic.shouldExit();
            gLogic.update();
            GGraphics.drawWorld(world);
            UI.sleep(30);
        }

        return true;
    }
}
