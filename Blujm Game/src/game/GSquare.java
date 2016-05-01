package game;

import java.lang.String;
import java.io.File;
import java.io.Serializable;

/**
 * Created by Daniel Young on 4/29/2016.
 */
public abstract class GSquare implements Serializable{

    protected static final String USER_TYPE = "user";
    protected static final String USER_MOVED_TYPE = "usermoved";
    protected static final String WALL_TYPE = "wall";
    protected static final String ENEMY_TYPE = "enemy";
    protected static final String EMPTY_TYPE = "empty";

    protected static final String POWERUP_GROW_TYPE = "grow";
    protected static final String POWERUP_KILL_TYPE = "kill";
    protected static final String POWERUP_TELEPORT_UP_TYPE = "tup";
    protected static final String POWERUP_TELEPORT_DOWN_TYPE = "tdown";
    protected static final String POWERUP_TELEPORT_RIGHT_TYPE = "tright";
    protected static final String POWERUP_TELEPORT_LEFT_TYPE = "tleft";



    protected static final String USER_PATH = GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "live-cell.png";
    protected static final String WALL_PATH = GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "wall3.png";
    protected static final String ENEMY_PATH = GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "background-image-placeholder.png";
    protected static final String EMPTY_PATH = GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "wall4.png";

    protected static final String POWERUP_GROW_PATH =
            GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator  + "powerup-increase.png";
    protected static final String POWERUP_KILL_PATH =
            GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator  + "powerup-kill.png";
    protected static final String POWERUP_TELEPORT_UP_PATH =
            GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "tpup.png";
    protected static final String POWERUP_TELEPORT_DOWN_PATH =
            GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "tpdown.png";
    protected static final String POWERUP_TELEPORT_LEFT_PATH =
            GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "tpleft.png";
    protected static final String POWERUP_TELEPORT_RIGHT_PATH =
            GFileChecker.RESOURCES_ROOT + File.separator + "images" + File.separator + "tpright.png";

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
