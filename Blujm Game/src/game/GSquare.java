package game;

/**
 * Created by Daniel Young on 4/29/2016.
 */
public abstract class GSquare {

    private String imagePath;
    private String type;

    public GSquare(String imagePath){
        this.imagePath = imagePath;
    }

    public String getImagePath(){
        return imagePath;
    }

    public String getType() { return type; }
}
