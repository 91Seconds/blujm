package game;

import ecs100.UI;

/**
 * Created by root on 29/04/16.
 */
public class GInput {

    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    GWorld world;

    public GInput(GWorld world) {
        UI.setKeyListener(this::keyboardInput);
        this.world = world;
    }

    public void updateMovements() {
        if(movingUp) {
            world.setMoveUp();
        }
        if(movingDown) {
            world.setMoveDown();
        }
        if(movingLeft) {
            world.setMoveLeft();
        }
        if(movingRight) {
            world.setMoveRight();
        }
    }

    private void keyboardInput(String key) {
        switch(key) {
            case "w":
                movingUp = true;
                break;
            case "a":
                movingLeft = true;
                break;
            case "s":
                movingDown = true;
                break;
            case "d":
                movingRight = true;
                break;
            default:
        }
    }
}
