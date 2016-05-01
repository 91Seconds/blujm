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

    private GSideMenu sideMenu;

    public static void main(String[] args) {
        GMain main = new GMain();
    }

    public GMain() {
        UI.initialise();
        UI.setWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        UI.setDivider(DIVIDER_POSITION);
        UI.setImmediateRepaint(false);

//        UI.setKeyListener(this::doKey); // TODO restart button

        sideMenu = new GSideMenu(currentLevel, System.currentTimeMillis());
//        gSideMenu.update(); // TODO refactor side menu class to work

        playLevel(currentLevel);
    }

    private void playLevel(int levelNum) {
        GWorld world = GWorldMaker.loadWorld(levelNum);
        GLevel level = new GLevel(world);
        level.playLevel();
    }

    public void doKey(String key) {
        UI.println(key + ": adfadsf");
        if (key.equals("r") || key.equals("R")) {
            UI.println("restart game");
        }
    }
}
