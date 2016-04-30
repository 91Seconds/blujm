package manual_test;

import ecs100.UI;
import game.GCell;
import game.GFileChecker;
import game.GGraphics;
import game.GWorld;

import java.io.File;

/**
 * Created by Dylan on 30/04/16.
 */
public class GGraphicsManualTest {

    public static void main(String[] args){
        GGraphicsManualTest gTest = new GGraphicsManualTest();
        gTest.testBG();
    }

    /**
     * Tested and completed
     */
    public void testBG() {
        GWorld w = new GWorld(7, 11, GWorld.KEY_I_AM_TESTING_ONLY);
        GGraphics.drawWorld(w);
    }

    /**
     * Tested and completed
     *
     * This (since it draws) tests both the background
     * and the cell drawing
     */
    public void testCellsAndBG() {
        GWorld gWorld = new GWorld(22, 18, GWorld.KEY_I_AM_TESTING_ONLY);
        CellAdder cs = new CellAdder(gWorld);

        cs.addLiveCell(0, 0);
        cs.addLiveCell(2, 0);
        cs.addLiveCell(16, 20);

        cs.addWallCell(1, 1);
        cs.addWallCell(10, 1);
        cs.addWallCell(10, 5);
        cs.addWallCell(17, 21);

        GGraphics.drawWorld(gWorld);
    }

    /**
     * This class is used to quickly add cells to different coordinates
     */
    private class CellAdder {
        public final String wallPath = GFileChecker.RESOURCES_ROOT + File.separator
                + "images" + File.separator + "wall.png";
        public final String liveCellPath = GFileChecker.RESOURCES_ROOT + File.separator
                + "images" + File.separator + "live-cell.png";
        private GWorld gWorld;
        public CellAdder(GWorld gWorld) {
            this.gWorld = gWorld;
        }
        public void addLiveCell(int row, int col) {
            // types weren't defined when I was making this so I'm using "test"
            gWorld.setCell(new GCell(liveCellPath, "test"), row, col);
        }
        public void addWallCell(int row, int col) {
            gWorld.setCell(new GCell(wallPath, "test"), row, col);
        }
    }
}
