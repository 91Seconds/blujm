package game;

/**
 * Created by Dylan on 29/04/16.
 *
 * Accesses the GWorld object to perform logic
 *
 * This is a static class
 */
public class GLogic {

    // Movement functionality
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    public void setMoveUp() { moveUp = true; }
    public void setMoveDown() { moveDown = true; }
    public void setMoveLeft() { moveLeft = true; }
    public void setMoveRight() { moveRight = true; }

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
                neighbourSquare = world.getCell(i + drow, j + dcol);


                switch(getMoveDecision(currentSquare, neighbourSquare)) {
                    case "defer":
                        continue;
                    case "move":
                        world.move(i, j, drow, dcol);
                        break;
                    case "stay":
                        break;
                    case "nothing":
                        break;
                    case "moveGrow":
                        world.move(i, j, drow, dcol);
                        world.grow(i, j);
                    default:
                        break;
                }
                updated[i][j] = true;
            }
        }

        cleanUpAfterUpdate(updated);

        drow = 0;
        dcol = 0;
    }

    private void checkMovements() {
        if(moveDown) {
            drow = 1;
        } else if(moveUp) {
            drow = -1;
        }

        if(moveRight) {
            dcol = 1;
        } else if(moveLeft) {
            dcol = -1;
        }

    }

    private String getMoveDecision(GSquare thisSquare, GSquare nextSquare) {
        if(thisSquare != null && thisSquare.getType().equals("user")) {
            if(nextSquare != null) {
                switch(nextSquare.getType()) {
                    case "user":
                        return "defer";
                    case "wall":
                        return "stay";
                    case "grow":
                        return "moveGrow";
                }
            } else {
                return "move";
            }
        } else {
            return "nothing";
        }

        return "nothing";
    }

    private void cleanUpAfterUpdate(boolean[][] updated) {
    }
}
