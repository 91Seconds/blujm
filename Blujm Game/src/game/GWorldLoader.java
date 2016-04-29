package game;

import ecs100.UI;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Dylan on 29/04/16.
 *
 * Creates a GWorld object from a file. Details not decided
 */
public class GWorldLoader {

    /**
     * Fit an integer between the prefix and suffix to get the file name
     */
    private static final String WORLD_FILE_PREFIX = "resources/worlds/world-";
    private static final String WORLD_FILE_SUFFIX = ".world";

    /**
     * Private constructor makes this a static-only class
     */
    private GWorldLoader() {}

    /**
     * Reads a world from a file
     * @return
     */
    public static GWorld getWorld(int level){

        GWorld gWorld = null;
        try {
            Scanner scan = new Scanner(new File("level" + level));
            /// TODO finish this method and construct a new world object
        }
        catch(IOException e){
            UI.println("File Reading Error:" + e);
            return null;
        }

        return gWorld;
    }
}
