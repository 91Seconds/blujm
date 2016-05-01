package game;

import ecs100.UI;

/**
 * Created by groot on 29/04/16.
 */
public class GKeyInput {

    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public GKeyInput() {
        UI.setKeyListener(this::keyboardInput);
        System.out.println("key listener set");
    }

    public void update() {
        if (movingUp) {
            GLogic.setMoveUp();
        }
        if (movingDown) {
            GLogic.setMoveDown();
        }
        if (movingLeft) {
            GLogic.setMoveLeft();
        }
        if (movingRight) {
            GLogic.setMoveRight();
        }

        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
    }

    private void keyboardInput(String key) {
        switch(key) {
            case "w":
            case "W":
                movingUp = true;
//                System.out.println("w recieved from keyboard");
                break;
            case "a":
            case "A":
                movingLeft = true;
                break;
            case "s":
            case "S":
                movingDown = true;
                break;
            case "d":
            case "D":
                movingRight = true;
                break;
            case "r":
            case "R":
                GLogic.setShouldRestart(true);
                break;
            case "Escape":
                GLogic.setShouldQuit(true);
            default:
        }

        update();
    }
}
