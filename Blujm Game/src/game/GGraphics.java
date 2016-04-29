package game;

/**
 * Created by Dylan on 29/04/16.
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

        // TODO dylan check if the image exists, otherwise crash the program
        UI.drawImage(world.getBACKGROUND_IMAGE_PATH(), 0, 0);

        for (int col = 0; col < world.getWidth(); col++) {
            for (int row = 0; row < world.getHeight(); row++) {
                GCell gCell = world.getCell(row, col);
                String imageFile = gCell.getImagePath();
                // TODO dylan check if the image file exists

                UI.drawImage(imageFile, LEFT + col* UNIT_SIZE, TOP + row* UNIT_SIZE,
                        UNIT_SIZE, UNIT_SIZE);
            }
        }

        // TODO dylan draw backgournd
    }

    private boolean doesImageExist(String path) {

    }
}
