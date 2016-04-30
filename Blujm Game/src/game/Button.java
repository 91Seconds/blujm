package game;

import java.awt.*;

/**
 * Created by Daniel Young on 4/30/2016.
 */
public class Button {
    private String name;
    private Point topLeft;
    private Point topRight;

    public Button(String name, Point topLeft, Point topRight){
        this.name = name;
    }

    public void action(){
        if(name.equals("play")){

        }
        if(name.equals("instructions")){

        }
        if(name.equals("credits")){

        }

    }
}
