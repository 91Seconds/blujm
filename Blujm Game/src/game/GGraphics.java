package game;

/**
 * Created by Dylan on 29/04/16.
 *
 * Deals with all of the game's drawing functionality.
 * Static-only class
 */

import ecs100.UI;

import java.awt.*;
import java.io.File;

public final class GGraphics {

    public static final int UNIT_SIZE = 25;
    public static final int WORLD_LEFT = UNIT_SIZE;
    public static final int WORLD_TOP = WORLD_LEFT;
    public static final int WORLD_WIDTH_IN_UNITS = 25;
    public static final int WORLD_HEIGHT_IN_UNITS = 25;
    public static final int WORLD_WIDTH = UNIT_SIZE*WORLD_WIDTH_IN_UNITS;
    public static final int WORLD_HEIGHT = UNIT_SIZE*WORLD_HEIGHT_IN_UNITS;

    public static final String GOAL_IMAGE_PATH = GFileChecker.RESOURCES_ROOT + File.separator
            + "images" + File.separator + "goal-square.png";
    public static final String MENU_PATH = GFileChecker.RESOURCES_ROOT + File.separator
            + "images" + File.separator + "Menu.png";


    private static String[] levelText =
            {"Used WASD to move into the green box", "Eat a cat!"};

    /**
     * Prevents any instances of the class
     */
    private GGraphics() {}


    //draws the menu for the game
    public static void drawMenu(){
        UI.clearGraphics();

        UI.drawImage(MENU_PATH,0,0);
        //drawing menu

       // drawMenu()
    }
    //ToDo make this method great again

    /**
     * Calls all the other draw methods, in the right order
     * @param world
     */
    public static void drawWorld(GWorld world) {
//        UI.clearGraphics();
        int currentLevel = GMain.getCurrentLevel();

        drawWorldBackground(world);
        drawGoal(world.getGoal());
        drawCells(world);
        drawLevelText(currentLevel);

        UI.repaintAllGraphics();
        //System.out.println("Graphics Drew");
    }

    private static void drawLevelText(int currentLevel) {
        Graphics2D graphics = UI.getGraphics();
        graphics.setFont(new Font("Monospaced", Font.PLAIN, 30));
        int halfString = (levelText[currentLevel].length()) / 2;
        halfString *= 10;
        UI.drawString(levelText[currentLevel], (WORLD_HEIGHT / 2) - halfString, (WORLD_HEIGHT / 2) - 12);
    }

    /**
     * Draws the background using the repeating image.
     *
     * If the world width or height is not a multiple of
     * world.getBACK_IMAGE_SIZE(), then the method will
     * erase whatever part of the image goes over the edge.
     * NOTE: The background should be drawn first to avoid
     * erasing other objects the world in this case.
     * @param world The world to draw
     */
    private static void drawWorldBackground(GWorld world) {
        final String bgPath = world.getBACKGROUND_IMAGE_PATH();

        // Check if the image exists, otherwise crash the program
        String errorMessage = GFileChecker.checkIfFilePathExists(bgPath);
        if (errorMessage != null) throw new Error("World background error: " + errorMessage);

        // Size in units of one chunk
        final int bgSizeUnits = world.getBACK_IMAGE_SIZE();
        // Size in pixels of one chunk
        final int bgSize = bgSizeUnits * UNIT_SIZE;

        float xRepeats = world.getWidth() / (float) bgSizeUnits;
        float yRepeats = world.getHeight() / (float) bgSizeUnits;

        // Draw each background chunk, not erasing the sides
        for (int nthBGX = 0; nthBGX < xRepeats; nthBGX++) {
            for (int nthBGY = 0; nthBGY < yRepeats; nthBGY++) {
                UI.drawImage(bgPath, WORLD_LEFT + nthBGX*bgSize, WORLD_TOP + nthBGY*bgSize,
                        bgSize, bgSize);
            }
        }

        // Trim the sides

        boolean shouldTrimRight = Math.ceil(xRepeats) > xRepeats;
        boolean shouldTrimBottom = Math.ceil(yRepeats) > yRepeats;

        if (!shouldTrimRight && !shouldTrimBottom) return;

        if (shouldTrimRight) {
            double right = WORLD_LEFT + Math.ceil(xRepeats) * bgSize;
            double left = right - (Math.ceil(xRepeats) - xRepeats) * bgSize;
            double top = WORLD_TOP;
            double bottom = WORLD_TOP + Math.ceil(yRepeats)*bgSize;
            UI.eraseRect(left, top, right - left, bottom - top);
        }

        if (shouldTrimBottom) {
            double left = WORLD_LEFT;
            double right = WORLD_LEFT + Math.ceil(xRepeats)*bgSize;
            double bottom = WORLD_TOP + Math.ceil(yRepeats) * bgSize;
            double top = bottom - (Math.ceil(yRepeats) - yRepeats) * bgSize;
            UI.eraseRect(left, top, right - left, bottom - top);
        }
    }

    private static void drawGoal(GGoal goal) {
        for (int col = 0; col < goal.getWidth(); col++) {
            for (int row = 0; row < goal.getHeight(); row++) {
                if (goal.getValue(row, col)) {
                    UI.drawImage(GOAL_IMAGE_PATH, WORLD_LEFT + col*UNIT_SIZE, WORLD_TOP
                            + row*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }
            }
        }
    }

    private static void drawCells(GWorld world) {
        for (int col = 0; col < world.getWidth(); col++) {
            for (int row = 0; row < world.getHeight(); row++) {
                GSquare currentSquare = world.getCell(row, col);
                if (currentSquare == null) continue;

                String imagePath = currentSquare.getImagePath();

                String errorMessage = GFileChecker.checkIfFilePathExists(imagePath);
                if (errorMessage != null) throw new Error("Cell at row: " + row + ", " +
                        "col: " + col + " image error: " + errorMessage);

                if (imagePath.equals(GSquare.EMPTY_PATH)) continue;

                UI.drawImage(imagePath, WORLD_LEFT + col*UNIT_SIZE, WORLD_TOP + row*UNIT_SIZE,
                        UNIT_SIZE, UNIT_SIZE);
            }
        }
    }
}
