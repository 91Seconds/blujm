package game;

import ecs100.UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

/**
 * Created by Dylan on 29/04/16.
 *
 * Creates a GWorld object from a file. Details not decided.
 * Static-only class
 */
public class GWorldLoader {

    /**
     * Fit an integer between the prefix and suffix to get the file name
     */
    private static final String WORLD_FILE_PREFIX = "resources/worlds/world-";
    private static final String WORLD_FILE_SUFFIX = ".world";
    private static final int GAMESIZE = 25;

    /**
     * Private constructor makes this a static-only class
     */
    private GWorldLoader() {}

    /**
     * Reads a world from a file
     * @return A new GWorld object. Returns null if there is an error
     */
   public static GWorld getWorld(int world){

        GWorld gWorld = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(WORLD_FILE_PREFIX + world + WORLD_FILE_SUFFIX));
            gWorld = (GWorld)(ois.readObject());
            ois.close();
            
        } catch(IOException e){
            UI.println("File Reading Error:" + e);
            return null;
        } catch (ClassNotFoundException e) {
            UI.println(e);
            return null;
        }

       return gWorld;
    }
}
