package game;

import game.GCell;

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

    private GCell[][] cells;

    public GCell getCell(int row, int column) {
        return cells[row][column];
    }

    // Responds with the neighbour in a given direction to the given row/column
    // Directions are drow (right is +1, left is -1), dcol (down is +1, up is -1)
    // RETURN FOR OVERFLOW ARRAY MUST BE UNAMBIGUOUS!!!!!!!!!!!!!!!!!!!
    public GCell getNeighbour(int row, int column, int drow, int dcol) {
        if(row + drow > cells.length) {
            return null;
        } else if(row + drow < 0) {
            return null;
        }

        if(column + dcol > cells[0].length) {
            return null;
        } else if(column + dcol < 0) {
            return null;
        }

        return cells[row+drow][col+dcol];
    }

    public void setMoveUp() { moveUp = true; }
    public void setMoveDown() { moveDown = true; }
    public void setMoveLeft() { moveLeft = true; }
    public void setMoveRight() { moveRight = true; }

    public boolean isMoveUp() { return moveUp; }
    public boolean isMoveDown() { return moveDown; }
    public boolean isMoveLeft() { return moveLeft; }
    public boolean isMoveRight() { return moveRight; }
}
