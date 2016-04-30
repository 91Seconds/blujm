package game;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Dylan on 29/04/16.
 *
 * Contains all of the level's contents
 */
public class GWorld implements Serializable {
    /**
     * The image that is repeated to make the background
     */
    private final String BACKGROUND_IMAGE_PATH = GFileChecker.RESOURCES_ROOT + File.separator
            + "images" + File.separator + "background-5x5.png";

    /**
     * The number of units (in width, and in height) that
     * the back image should fill up - e.g. when set to 5,
     * the GGraphics class will assume the BACKGROUND_IMAGE_PATH
     * is of an image that is a 5x5 grid
     */
    private final int BACK_IMAGE_SIZE = 5;

    private final GCell[][] cells;

    private final GGoal goal;

    private boolean[][] cantMove = new boolean[25][25];

    public GWorld(GCell[][] cellArray, GGoal goalObj) {
        cells = cellArray;
        goal = goalObj;
        validateSelf();
    }

    public GWorld() {
        int width = GGraphics.WORLD_WIDTH/GGraphics.UNIT_SIZE;
        int height = GGraphics.WORLD_HEIGHT/GGraphics.UNIT_SIZE;
        cells = new GCell[height][width];
        goal = new GGoal(width, height);
        validateSelf();
    }

    /**
     * Testing constructor only.
     */
    public GWorld(int width, int height, String specialKey_dontUseThisConstructorForAnythingButTesting) {
        if (!specialKey_dontUseThisConstructorForAnythingButTesting
                .equals(KEY_I_AM_TESTING_ONLY)) {
            Error e = new Error("This constructor is for testing only. Don't use it");
            e.printStackTrace();
            throw e;
        }
        cells = new GCell[height][width];
        goal = new GGoal(width, height);
        validateSelf();
    }
    /**
     * Pass this string into the testing constructor to use it.
     */
    public static final String KEY_I_AM_TESTING_ONLY = "KEY_I_AM_TESTING_ONLY";

    /**
     * Currently, just crashes the program if the goal and cells are different sizes
     */
    private void validateSelf() {
        if (cells[0].length != goal.getWidth())
            throw new Error("cells and goal must be the same width");
        if (cells.length != goal.getHeight())
            throw new Error("cells and goal must be the same height");
    }

    public void move(int row, int col, int drow, int dcol) {
        if(cantMove[row][col] == false) {
            cells[row + drow][col + dcol] = cells[row][col];
            cells[row][col] = new GCell(GSquare.EMPTY_PATH, GSquare.EMPTY_TYPE);
            cantMove[row+drow][col+dcol] = true;
        }
    }

    public void grow(int row, int col) {
        String userImagePath = "user";

        for(int i = row - 1; i < row + 1; i++) {
            for(int j = col - 1; j < col + 1; j++) {
                if(cells[i][j] == null) {
                    cells[i][j] = new GCell("user", userImagePath);
                }
            }
        }
    }

    public String getBACKGROUND_IMAGE_PATH() {
        return BACKGROUND_IMAGE_PATH;
    }

    public int getBACK_IMAGE_SIZE() {
        return BACK_IMAGE_SIZE;
    }

    /**
     * @return The number of cells that can fit across the width of the world
     */
    public int getWidth() {
        return cells[0].length;
    }
    /**
     * @return The number of cells that can fit across the height of the world
     */
    public int getHeight() {
        return cells.length;
    }

    public GCell getCell(int row, int col) {
        if((row >= 25 || col >= 25) || (row <= 0 || col <= 0)){
            return new GCell(GSquare.WALL_PATH, GSquare.WALL_TYPE);
        }

        return cells[row][col];
    }

    public void setCell(GCell gCell, int row, int col) {
        cells[row][col] = gCell;
    }

    public GGoal getGoal() {
        return goal;
    }

    // TODO: 30/04/16 DYLAN clean up this file
}
