package game;

import java.lang.String;
import java.io.File;
import java.io.Serializable;

/**
 * Created by Daniel Young on 4/29/2016.
 */
public abstract class GSquare implements Serializable{

    protected static final String USER_TYPE = "user";
    protected static final String WALL_TYPE = "wall";
    protected static final String ENEMY_TYPE = "enemy";
    protected static final String EMPTY_TYPE = "empty";

    protected static final String USER_PATH = GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "live-cell.png";
    protected static final String WALL_PATH = GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "wall3.png";
    protected static final String ENEMY_PATH = GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "background-image-placeholder.png";
    protected static final String EMPTY_PATH = GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "wall4.png";

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
