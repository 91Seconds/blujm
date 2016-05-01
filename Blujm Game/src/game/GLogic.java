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

    private static GLogic currentInstance;

    public static boolean shouldQuit() {
        return shouldQuit;
    }
    public static void setShouldQuit(boolean shouldQuit) {
        GLogic.shouldQuit = shouldQuit;
    }
    public static boolean shouldRestart() {
        return shouldRestart;
    }
    public static void setShouldRestart(boolean shouldRestart) {
        GLogic.shouldRestart = shouldRestart;
    }

    private static boolean shouldQuit;
    private static boolean shouldRestart;

    public static boolean hasWon() {
        return hasWon;
    }

    private static boolean hasWon;

    // Movement functionality
    private static boolean moveUp = false;
    private static boolean moveDown = false;
    private static boolean moveLeft = false;
    private static boolean moveRight = false;



    public static void setMoveUp() {
        if(moveDown == false && moveLeft == false && moveRight == false) {
            moveUp = true;
//            UI.println("Moving up");
//            System.out.println("moved up");
       }
    }
    public static void setMoveDown() {
        if(moveUp == false && moveLeft == false && moveRight == false) {
            moveDown = true;
//            UI.println("Moving down");
        }
    }
    public static void setMoveLeft() {
        if(moveRight == false && moveUp == false && moveDown == false) {
            moveLeft = true;
//            UI.println("Moving left");
        }
    }
    public static void setMoveRight() {
        if(moveLeft == false && moveUp == false && moveDown == false) {
            moveRight = true;
//            UI.println("Moving right");
        }
    }

    private GWorld world;

    private int dRow;
    private int dCol;

    private GLogic(GWorld world) {
        this.world = world;
        shouldQuit = false;
        shouldRestart = false;
        hasWon = false;
    }

    public static GLogic getGLogic(GWorld world) {
        if(currentInstance != null) {
            return currentInstance;
        } else {
            return new GLogic(world);
        }
    }

    public static void makeReferenceNull() {
        currentInstance = null;
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
//                if(!decision.equals("nothing")){
//                    UI.println("Single movement");
//                    UI.println(decision);
//                    UI.println(currentSquare);
//                    UI.println(currentSquare.getType());
//                    UI.println(neighbourSquare);
//                    UI.println(neighbourSquare.getType());
//                    UI.println("\n");
//                }
                switch(decision) {
                    case "defer":
                        continue;
                    case "move":
//                        UI.println("Moving in this direction");
                        world.move(i, j, dRow, dCol);
                        break;
                    case "stay":
                        break;
                    case "nothing":
                        break;
                    case "moveGrow":
                        world.move(i, j, dRow, dCol);
                        world.grow(i, j);
                        break;
                    case "moveKill":
                        world.move(i, j, dRow, dCol);
                        world.setCell(new GCell(GSquare.EMPTY_PATH, GSquare.EMPTY_TYPE), i + dRow, j + dCol);
                        break;

                    default:
                        break;
                }
                updated[i][j] = true;
            }
        }

        cleanUpAfterUpdate(updated);
        cleanMovement();
        world.resetCantMoveField();

        if (hasUserWon()) {
            hasWon = true;
        }
    }


    private void cleanMovement() {
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

        moveDown = false;
        moveUp = false;
        moveLeft = false;
        moveRight = false;
    }

    private String getMoveDecision(GSquare thisSquare, GSquare nextSquare) {
        if(thisSquare.getType().equals(GSquare.USER_TYPE)) {
             if(nextSquare.getType().equals(GSquare.EMPTY_TYPE)) {
                 return "move";
             } else if(nextSquare.getType().equals(GSquare.USER_TYPE)) {
                 return "defer";
             } else if(nextSquare.getType().equals(GSquare.WALL_TYPE)) {
                 return "stay";
             } else if(nextSquare.getType().equals(GSquare.POWERUP_GROW_TYPE)) {
                 return "moveGrow";
             } else if(nextSquare.getType().equals(GSquare.POWERUP_KILL_TYPE)) {
                 return "moveKill";
             }
        } else {
            return "nothing";
        }

        return "nothing";
    }

    private void cleanUpAfterUpdate(boolean[][] updated) {
        GSquare currentSquare;
        GSquare nextSquare;
        String decision;

        for(int i = 24; i > 0; i--) {
            for(int j = 24; j > 0; j--) {
                if(updated[i][j] == false) {
                    currentSquare = world.getCell(i, j);
                    nextSquare = world.getCell(i + dRow, j + dCol);

                    decision = getMoveDecision(currentSquare, nextSquare);
                    switch(decision) {
                        case "move":
                            world.move(i, j, dRow, dCol);
                        default:
                            break;
                    }
                }
            }
        }
    }

    private boolean hasUserWon() {
        return world.getGoal().matchesUserState(getUserTileState());
    }

    /**
     * The true values are where the user has a tile there
     * @return This result is compared with GGoal to check if the user fits the goal
     */
    private boolean[][] getUserTileState() {
        boolean[][] state = new boolean[world.getHeight()][world.getWidth()];
        for (int col = 0; col < world.getWidth(); col++) {
            for (int row = 0; row < world.getHeight(); row++) {
                GCell cell = world.getCell(row, col);
                if (cell.getType().equals(GSquare.USER_TYPE)) {
                    state[row][col] = true;
                }
            }
        }
        return state;
    }
}
