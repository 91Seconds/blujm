package game;

/**
 * Created by Dylan on 29/04/16.
 *
 * Deals with all of the game's drawing functionality.
 * Static-only class
 */
import ecs100.*;

public final class GGraphics {

    public static final int LEFT = 0;
    public static final int TOP = 0;
    public static final int UNIT_SIZE = 20;

    /**
     * Prevents any instances of the class
     */
    private GGraphics() {}

    public static void drawWorld(GWorld world) {
        drawWorldBackground(world);
        drawCells(world);
    }

    private static void drawWorldBackground(GWorld world) {
        String worldBGPath = world.getBACKGROUND_IMAGE_PATH();

        //Check if the image exists, otherwise crash the program
        Error e = GFileChecker.checkIfFilePathExists(worldBGPath);
        if (e != null) throw e;

        int bgSize =
    }

    private static void drawCells(GWorld world) {
        for (int col = 0; col < world.getWidth(); col++) {
            for (int row = 0; row < world.getHeight(); row++) {
                GCell gCell = world.getCell(row, col);
                String imagePath = gCell.getImagePath();

                Error imageError = GFileChecker.checkIfFilePathExists(imagePath);
                if (imageError != null) throw imageError;

                UI.drawImage(imagePath, LEFT + col* UNIT_SIZE, TOP + row* UNIT_SIZE,
                        UNIT_SIZE, UNIT_SIZE);
            }
        }
    }
}
