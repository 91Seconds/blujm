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

    private static GSideMenu sideMenu;

    // TODO we will have to move the code in the main method into a new class
    // if we are to have a main menu screen (do we need one?)

    public static void main(String[] args) {
        UI.initialise();
        UI.setWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        UI.setDivider(DIVIDER_POSITION);
        UI.setImmediateRepaint(false);

        sideMenu = new GSideMenu(currentLevel, System.currentTimeMillis());
//        gSideMenu.update(); // TODO refactor side menu class to work

        playLevel(currentLevel);
    }

    private static void playLevel(int levelNum) {
        GWorld world = GWorldLoader.loadWorld(levelNum);
        GLevel level = new GLevel(world);
        level.playLevel();
    }
}
