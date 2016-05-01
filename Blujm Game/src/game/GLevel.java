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

        GLogic gLogic = new GLogic(world);
        GInput gInput = new GInput(gLogic);

        while(shouldExit == false) {
            gInput.update();
            gLogic.update();
            GGraphics.drawWorld(world);
            UI.sleep(10);
        }

        return true;
    }
}
