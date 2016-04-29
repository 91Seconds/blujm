package game;

import java.awt.*;

/**
 * Created by Dylan on 29/04/16.
 */
public class GCell {


    private String imageFile;
    private String name;

    public GCell(String imageFile){
        location = new Point(x,y);
        this.imageFile = imageFile;
    }

    public String getImage() {
        return imageFile;
    }

}
