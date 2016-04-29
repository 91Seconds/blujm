package game;

/**
 * Created by Daniel Young on 4/29/2016.
 */
public class GPowerup extends GSquare{

    private String type;

    public GPowerup(String imageName, String type){
        super(imageName);
        this.type = type;
    }

    public String getType(){
        return type;
    }
    public void setType(String newType){
        type = newType;
    }
}
