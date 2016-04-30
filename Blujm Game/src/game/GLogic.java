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
        if(moveDown == false && moveLeft == false && moveRight == false) {
            moveUp = true;
            UI.println("Moving up");
        }
    }
    public void setMoveDown() {
        if(moveUp == false && moveLeft == false && moveRight == false) {
            moveDown = true;
            UI.println("Moving down");
        }
    }
    public void setMoveLeft() {
        if(moveRight == false && moveUp == false && moveDown == false) {
            moveLeft = true;
            UI.println("Moving left");
        }
    }
    public void setMoveRight() {
        if(moveLeft == false && moveUp == false && moveDown == false) {
            moveRight = true;
            UI.println("Moving right");
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

        if(dRow == 0 && dCol == 0) {
            return; // no change
        }

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                currentSquare = world.getCell(i, j);
                neighbourSquare = world.getCell(i + dRow, j + dCol);

                String decision = getMoveDecision(currentSquare, neighbourSquare);
                if(!decision.equals("nothing")){
                    UI.println("Single movement");
                    UI.println(decision);
                    UI.println(currentSquare);
                    UI.println(currentSquare.getType());
                    UI.println(neighbourSquare);
                    UI.println(neighbourSquare.getType());
                    UI.println("\n");
                }
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
        cleanMovement();
    }

    private void cleanMovement() {
        moveUp = false;
        moveDown = false;
        moveLeft = false;
        moveRight = false;

        dRow = 0;
        dCol = 0;
    }

    private void checkMovements() {
        if(moveDown == true) {
            dRow = 1;
        } else if(moveUp == true) {
            dRow = -1;
        } else {
            dRow = 0;
        }

        if(moveRight == true) {
            dCol = 1;
        } else if(moveLeft == true) {
            dCol = -1;
        } else {
            dCol = 0;
        }

    }

    private String getMoveDecision(GSquare thisSquare, GSquare nextSquare) {
        if(thisSquare.getType().equals(GSquare.USER_TYPE)) {
             if(nextSquare.getType().equals(GSquare.EMPTY_TYPE)) {
                 return "move";
             } else if(nextSquare.getType().equals(GSquare.USER_TYPE)) {
                 return "defer";
             } else if(nextSquare.getType().equals(GSquare.WALL_TYPE)) {
                 return "stay";
             }
        } else {
            return "nothing";
        }

        return "nothing";
    }

    private void cleanUpAfterUpdate(boolean[][] updated) {
        
    }

    // TODO NEXT: 30/04/16 DYLAN check the ggoal object if the user won
}
