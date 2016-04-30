package game;

import java.awt.*;

/**
 * Created by Dylan on 29/04/16.
 */
public class GCell extends GSquare {


    private String type;

    public GCell(String imageFile, String type){
        super(imageFile);
        this.type = type;
    }



    public String getType(){
        return type;
    }
    public void setType(String newType){
        type = newType;
    }

}
