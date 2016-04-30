package game;

/**
 * Created by Dylan on 29/04/16.
 *
 * Accesses the GWorld object to perform logic
 *
 * This is a static class
 */
public class GLogic {

    GWorld world;

    public GLogic(GWorld world) {
        this.world = world;

    }

    public void update() {
        int width = world.getWidth();
        int height = world.getHeight();
        boolean[][] updated = new boolean[height][width];

        boolean movingDown = world.isMoveDown();
        boolean movingUp = world.isMoveUp();
        boolean movingLeft = world.isMoveLeft();
        boolean movingRight = world.isMoveRight();

        int drow;
        int dcol;

        if(movingDown) {
            drow = 1;
        } else if(movingUp) {
            drow = -1;
        }

        if(movingRight) {
            dcol = 1;
        } else if(movingLeft) {
            dcol = -1;
        }

        GSquare currentSquare;

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                currentSquare = world.getCell(i, j);

                if(world.getNeighbour(i, j, drow, dcol) != null) {
                    // determine action
                } else {
                    world.move(i, j, drow, dcol);
                }

                updated[i][j] = true;
            }
        }

        cleanUpAfterUpdate(updated);
    }

    private void cleanUpAfterUpdate(boolean[][] updated) {
    }
}
