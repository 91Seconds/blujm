package game;

/**
 * Created by Dylan on 29/04/16.
 *
 * Deals with all of the game's drawing functionality.
 * Static-only class
 */

import ecs100.UI;

public final class GGraphics {

    public static final int UNIT_SIZE = 25;
    public static final int WORLD_LEFT = UNIT_SIZE;
    public static final int WORLD_TOP = WORLD_LEFT;
    public static final int WORLD_WIDTH = 625;
    public static final int WORLD_HEIGHT = 625;

    /**
     * Prevents any instances of the class
     */
    private GGraphics() {}

    public static void drawWorld(GWorld world) {
//        UI.clearGraphics();
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

        // Size in units of one chunk
        final int bgSizeUnits = world.getBACK_IMAGE_SIZE();
        // Size in pixels of one chunk
        final int bgSize = bgSizeUnits * UNIT_SIZE;

        float xRepeats = world.getWidth() / (float) bgSizeUnits;
        float yRepeats = world.getHeight() / (float) bgSizeUnits;

        // Draw each background chunk, and erase edges as necessary
        for (int nthBGX = 0; nthBGX < xRepeats; nthBGX++) {
            for (int nthBGY = 0; nthBGY < yRepeats; nthBGY++) {

                UI.drawImage(bgPath, WORLD_LEFT + nthBGX*bgSize, WORLD_TOP + nthBGY*bgSize,
                        bgSize, bgSize);

                boolean shouldTrimRight = nthBGX > xRepeats - 1;
                boolean shouldTrimBottom = nthBGY > yRepeats - 1;

                if (!shouldTrimRight && !shouldTrimBottom) continue;

                // Erase off the edges if there are

                if (shouldTrimRight && shouldTrimBottom) {
                    { // Trim entire right side
                        double right = WORLD_LEFT + Math.ceil(xRepeats) * bgSize;
                        double left = right - (Math.ceil(xRepeats) - xRepeats) * bgSize;
                        double top = WORLD_TOP;
                        double bottom = WORLD_TOP + Math.ceil(yRepeats)*bgSize;
                        UI.eraseRect(left, top, right - left, bottom - top);
                    }
                    { // Trim entire right side
                        double left = WORLD_LEFT;
                        double right = WORLD_LEFT + Math.ceil(xRepeats)*bgSize;
                        double bottom = WORLD_TOP + Math.ceil(yRepeats) * bgSize;
                        double top = bottom - (Math.ceil(yRepeats) - yRepeats) * bgSize;
                        UI.eraseRect(left, top, right - left, bottom - top);
                    }
                } else {
                    double left, width, top, height;

                    if (shouldTrimRight) {
                        double right = WORLD_LEFT + Math.ceil(xRepeats) * bgSize;
                        left = right - (Math.ceil(xRepeats) - xRepeats) * bgSize;
                        width = right - left;
                    } else {
                        left = WORLD_LEFT + nthBGX * bgSize;
                        width = bgSize;
                    }
                    if (shouldTrimBottom) {
                        double bottom = WORLD_TOP + Math.ceil(yRepeats) * bgSize;
                        top = bottom - (Math.ceil(yRepeats) - yRepeats) * bgSize;
                        height = bottom - top;
                    } else {
                        top = WORLD_TOP + nthBGY * bgSize;
                        height = bgSize;
                    }

                    UI.eraseRect(left, top, width, height);
                }
            }
        }
    }

    private static void drawCells(GWorld world) {
        // TODO DYLAN NEXT test this
        for (int col = 0; col < world.getWidth(); col++) {
            for (int row = 0; row < world.getHeight(); row++) {
                GCell gCell = world.getCell(row, col);
                if (gCell == null) continue;

                String imagePath = gCell.getImagePath();

                String errorMessage = GFileChecker.checkIfFilePathExists(imagePath);
                if (errorMessage != null) throw new Error("Cell at row: " + row + ", " +
                        "col: " + col + " image error: " + errorMessage);

                UI.drawImage(imagePath, WORLD_LEFT + col*UNIT_SIZE, WORLD_TOP + row*UNIT_SIZE,
                        UNIT_SIZE, UNIT_SIZE);
            }
        }
    }
}
