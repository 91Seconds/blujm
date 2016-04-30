package game;

import ecs100.UI;

/**
 * Created by groot on 29/04/16.
 */
public class GInput {

    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    GLogic logic;


    //coordinates are for the button areas
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
    // TODO need to put in the calls for the above methods and probably correct my code lel huehuehue

    public GInput(GLogic logic) {
        UI.setKeyListener(this::keyboardInput);
        System.out.println("key listener set");
        this.logic = logic;
    }

    public void update() {
        if(movingUp) {
            logic.setMoveUp();
        }
        if(movingDown) {
            logic.setMoveDown();
        }
        if(movingLeft) {
            logic.setMoveLeft();
        }
        if(movingRight) {
            logic.setMoveRight();
        }
    }

    private void keyboardInput(String key) {
        switch(key) {
            case "w":
                movingUp = true;
//                System.out.println("w recieved from keyboard");
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
