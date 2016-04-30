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

        GSquare currentSquare;

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                currentSquare = world.getCell(i, j);
                if(currentSquare instanceof GCell) {
                    switch(currentSquare.getType()) {
                        case "user":
                            // defer
                            break;
                        case "wall":
                            // don't move
                            break;
                    }
                } else if(currentSquare instanceof GPowerup) {
                    switch(currentSquare.getType()) {
                        case "expand":
                            // todo
                            break;
                        case "contract":
                            // todo
                            break;
                    }
                }

                updated[i][j] = true;
            }
        }

        cleanUpAfterUpdate(updated);
    }

    private void cleanUpAfterUpdate(boolean[][] updated) {
    }
}
