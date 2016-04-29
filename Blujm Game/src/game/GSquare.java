package game;

/**
 * Created by Daniel Young on 4/29/2016.
 */
public abstract class GSquare {

    private String imageName;

    public GSquare(String imageName){
        this.imageName = imageName;
    }

    public String getImageName(){
        return imageName;
    }
    public void setImageName(String newImageName){
        imageName = newImageName;
    }
}
