package game;

/**
 * Created by Dylan on 29/04/16.
 *
 * Accesses the GWorld object to perform logic
 *
 * This is a static class
 */
public class GLogic {

    private GWorld world;

    private int drow;
    private int dcol;

    public GLogic(GWorld world) {
        this.world = world;

    }



    public void update() {
        int width = world.getWidth();
        int height = world.getHeight();
        boolean[][] updated = new boolean[height][width];

        checkMovements();

        GSquare currentSquare;
        GSquare neighbourSquare;

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                currentSquare = world.getCell(i, j);
                neighbourSquare = world.getNeighbour(i, j, drow, dcol);


                // need to reduce cyclomatic complexity
                if(currentSquare != null && currentSquare.getType().equals("user")) {
                    if(neighbourSquare != null) {
                        switch(neighbourSquare.getType()) {
                            case "user":
                                // defer action
                                break;
                            case "wall":
                                // stay still
                                break;

                        }
                    } else {
                        world.move(i, j, drow, dcol);
                    }
                } else {
                    continue;
                }
                updated[i][j] = true;
            }
        }

        cleanUpAfterUpdate(updated);

        drow = 0;
        dcol = 0;
    }

    private void checkMovements() {

        boolean movingDown = world.isMoveDown();
        boolean movingUp = world.isMoveUp();
        boolean movingLeft = world.isMoveLeft();
        boolean movingRight = world.isMoveRight();

        int drow = 0;
        int dcol = 0;

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

    }

    private void cleanUpAfterUpdate(boolean[][] updated) {
    }
}
