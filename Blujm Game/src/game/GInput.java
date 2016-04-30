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

    public void doMouse(String action, double x, double y){
        if(action.equals("released")){
            if(x > 63 && x < 764 && y > 268 && y <570){
                if(x > 63 && x <356 && y > 268 && y < 377){
                    //new game

                }else if(x > 475 && x < 764 && y > 268 && y <377){
                    //load
                }else if(x > 63 && x < 356 && y > 445 && y < 570) {
                    //quit
                };//what happens when user clicks on invalid area
            }
        }
    }

    public GInput(GWorld world) {
        UI.setKeyListener(this::keyboardInput);
        this.world = world;
    }

    public void update() {
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
