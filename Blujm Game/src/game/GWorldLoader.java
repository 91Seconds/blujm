package game;

import ecs100.UI;

import java.io.*;

/**
 * Created by Dylan on 29/04/16.
 *
 * Creates a GWorld object from a file. Details not decided.
 * Static-only class
 */
public class GWorldLoader {


    /**
     * WE WON'T NEED THIS CLASS ANYMORE TODO REMOVE THIS CLASS and move the constants
     */






    /**
     * Fit an integer between the prefix and suffix to get the file name
     */
    private static final String WINDOWS_WORLD_FOLDER = GFileChecker.RESOURCES_ROOT + File.separator + "worlds" + File.separator + "windows";
    private static final String UNIX_WORLD_FOLDER = GFileChecker.RESOURCES_ROOT + File.separator + "worlds" + File.separator + "unix";
    private static final String WORLD_FILE_SUFFIX = ".world";
    
    /**
     * @param includeFilePrefix
     * @return The world folder path if the above if includeFilePrefix is false,
     * otherwise it will include the "/world-" on the end as well
     */
    public static String getWorldFolderPath(boolean includeFilePrefix) {
        String folder = WINDOWS_WORLD_FOLDER;
        if (File.separator.equals("/")) folder = UNIX_WORLD_FOLDER;
        if (includeFilePrefix) folder +=  File.separator + "world-";
        return folder;
    }

    /**
     * Private constructor makes this a static-only class
     */
    private GWorldLoader() {}

    /**
     * Reads a world from a file
     * @return A new GWorld object. Returns null if there is an error
     */
    public static GWorld loadWorld(int world){

        GWorld gWorld = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(getWorldFolderPath(true) + world + WORLD_FILE_SUFFIX));
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

    public static void saveWorld(GWorld world, int worldNumber) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(getWorldFolderPath(true) + worldNumber + WORLD_FILE_SUFFIX));
            oos.writeObject((Object) world);
            oos.close();

            UI.println("World " + worldNumber + " saved");
        } catch (Exception e) {
            UI.println("Exception caught: " + e);
        }

    }
}
