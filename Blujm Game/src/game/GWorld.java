package game;

import java.io.File;

/**
 * Created by Dylan on 29/04/16.
 *
 * Contains all of the level's contents
 */
public class GWorld {

    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    // Drawing
    /**
     * The image that is repeated to make the background
     * It may not need to be final, but is for now
     */
    private final String BACKGROUND_IMAGE_PATH = "resources" + File.pathSeparator +
            "images" + File.pathSeparator + "backgroundImage.png";

    /**
     * The number of units (in width, and in height) that
     * the back image should fill up
     */
    private final int BACK_IMAGE_SIZE = 5;

    private final GCell[][] cells;

    public GWorld(int width, int height) {
        cells = new GCell[width][height];
        // TODO someone complete this
    }

    /**
     * Responds with the neighbour in a given direction to the given row/col
     * Directions are drow (right is +1, left is -1), dcol (down is +1, up is -1)
     * RETURN FOR OVERFLOW ARRAY MUST BE UNAMBIGUOUS!!!!!!!!!!!!!!!!!!!
     */
    public GCell getNeighbour(int row, int col, int drow, int dcol) {
        if (row + drow > cells.length) {
            return null;
        } else if(row + drow < 0) {
            return null;
        }

        if (col + dcol > cells[0].length) {
            return null;
        } else if(col + dcol < 0) {
            return null;
        }

        return cells[row+drow][col+dcol];
    }

    public GCell getCell(int row, int column) {
        return cells[row][column];
    }

    public void setMoveUp() { moveUp = true; }

    public void setMoveDown() { moveDown = true; }
    public void setMoveLeft() { moveLeft = true; }
    public void setMoveRight() { moveRight = true; }
    public boolean isMoveUp() { return moveUp; }

    public boolean isMoveDown() { return moveDown; }
    public boolean isMoveLeft() { return moveLeft; }
    public boolean isMoveRight() { return moveRight; }

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
}
