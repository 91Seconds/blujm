package game;

import game.cells.GCell;

/**
 * Created by Dylan on 29/04/16.
 *
 * Contains all of the level's contents
 */
public class GWorld {

    private GCell[][] cells;

    public GCell getCell(int row, int column) {
        return cells[row][column];
    }

}
