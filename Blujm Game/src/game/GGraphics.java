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

        // Size in units
        final int bgSizeUnits = world.getBACK_IMAGE_SIZE();
        // Size in pixels
        final int bgSize = bgSizeUnits * UNIT_SIZE;

        float xRepeats = world.getWidth() / (float) bgSizeUnits;
        float yRepeats = world.getHeight() / (float) bgSizeUnits;

        for (int nthX = 0; nthX < xRepeats; nthX++) {
            for (int nthY = 0; nthY < yRepeats; nthY++) {

                UI.drawImage(bgPath, LEFT + nthX*bgSize, TOP + nthY*bgSize,
                        bgSize, bgSize);

                // TODO erase off the edges
            }
        }

        // TODO: 30/04/16 MANUAL TEST
    }

    private static void drawCells(GWorld world) {
        for (int col = 0; col < world.getWidth(); col++) {
            for (int row = 0; row < world.getHeight(); row++) {
                GCell gCell = world.getCell(row, col);
                if (gCell == null) continue;

                String imagePath = gCell.getImagePath();

                String errorMessage = GFileChecker.checkIfFilePathExists(imagePath);
                if (errorMessage != null) throw new Error("Cell at row: " + row + ", " +
                        "col: " + col + " image error: " + errorMessage);

                UI.drawImage(imagePath, LEFT + col* UNIT_SIZE, TOP + row* UNIT_SIZE,
                        UNIT_SIZE, UNIT_SIZE);
            }
        }
    }
}
