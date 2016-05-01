package game;

import ecs100.UI;

/**
 * Created by Dylan on 24/04/16.
 */
public class GMain {

    private static int currentLevel = 1;

    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 1000;
    public static final double DIVIDER_POSITION = 0;

    private GLevelSelect gLevelSelect;

    public static void main(String[] args) {
        GMain main = new GMain();
    }

    public GMain() {
        UI.initialise();
        UI.setWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        UI.setDivider(DIVIDER_POSITION);
        UI.setImmediateRepaint(false);

        @SuppressWarnings("unused")
        GKeyInput gKeyInput = new GKeyInput();

        gLevelSelect = new GLevelSelect();
        gLevelSelect.update();
        UI.setMouseListener(this::doMouse);

//        playLevel(currentLevel);
    }

    private void playLevel(int levelNum) {
        gLevelSelect.setDisable(true);

        GWorld world;
        GLevel level;

        while (true) {
            UI.clearGraphics();
            world = GWorldMaker.loadWorld(levelNum);
            level = new GLevel(world);

            int result = level.playLevel(levelNum);
            if (result == GLevel.KEY_RESTART) continue;
            if (result == GLevel.KEY_QUIT) break;
        }

        gLevelSelect.setDisable(false);
    }

    public void doMouse(String action, double x, double y) {
        gLevelSelect.update();

        if (action.equals("released")){
            int level = gLevelSelect.worldSelect(x,y);
            if (level != -1)
                playLevel(level);
        }
    }
}
