package game;

import java.io.Serializable;

/**
 * Created by Daniel Young on 4/29/2016.
 */
public abstract class GSquare implements Serializable{

    private String imagePath;
    private String type;

    public GSquare(String imagePath, String type){
        this.imagePath = imagePath;
        this.type = type;
    }

    public String getImagePath(){
        return imagePath;
    }

    public String getType() { return type; }
}
