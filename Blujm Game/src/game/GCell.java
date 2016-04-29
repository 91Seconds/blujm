package game;

import java.awt.*;

/**
 * Created by Dylan on 29/04/16.
 */
public class GCell {


    private String imageFile;
    private String name;

    public GCell(int x, int y,String imageFile){
        location = new Point(x,y);
        this.imageFile = imageFile;
    }

}
