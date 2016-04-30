package game;
import ecs100.*;

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

    public void setMoveUp() {
        if(!(moveDown || moveLeft || moveRight)) {
            moveUp = true;
        }
    }
    public void setMoveDown() {
        if(!(moveUp || moveLeft || moveRight)) {
            moveDown = true;
        }
    }
    public void setMoveLeft() {
        if(!(moveUp || moveRight || moveDown)) {
            moveLeft = true;
        }
    }
    public void setMoveRight() {
        if(!(moveLeft || moveUp || moveDown)) {
            moveRight = true;
        }
    }

    private GWorld world;

    private int dRow;
    private int dCol;

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
                neighbourSquare = world.getCell(i + dRow, j + dCol);

                String decision = getMoveDecision(currentSquare, neighbourSquare);
                UI.println(decision);
                switch(decision) {
                    case "defer":
                        continue;
                    case "move":
                        UI.println("Moving in this direction");
                        world.move(i, j, dRow, dCol);
                        break;
                    case "stay":
                        break;
                    case "nothing":
                        break;
                    case "moveGrow":
                        world.move(i, j, dRow, dCol);
                        world.grow(i, j);
                    default:
                        break;
                }
                updated[i][j] = true;
            }
        }

        cleanUpAfterUpdate(updated);

        dRow = 0;
        dCol = 0;
    }

    private void checkMovements() {
        if(moveDown) {
            dRow = 1;
        } else if(moveUp) {
            dRow = -1;
        }

        if(moveRight) {
            dCol = 1;
        } else if(moveLeft) {
            dCol = -1;
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

    // TODO: 30/04/16 DYLAN check the ggoal object if the user won
}
