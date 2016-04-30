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
     * Fit an integer between the prefix and suffix to get the file name
     */
    private static final String WINDOWS_WORLD_FILE_PREFIX = GFileChecker.RESOURCES_ROOT + File.separator + "worlds" + File.separator + "windows" + File.separator + "world-";
    private static final String UNIX_WORLD_FILE_PREFIX = GFileChecker.RESOURCES_ROOT + File.separator + "worlds" + File.separator + "unix" + File.separator + "world-";
    private static final String WORLD_FILE_SUFFIX = ".world";
    private static final int GAMESIZE = 25;
    public static String getWorldFilePrefix() {
        if (File.separator.equals("\\")) return WINDOWS_WORLD_FILE_PREFIX;
        return UNIX_WORLD_FILE_PREFIX;
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
            ois = new ObjectInputStream(new FileInputStream(getWorldFilePrefix() + world + WORLD_FILE_SUFFIX));
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
            oos = new ObjectOutputStream(new FileOutputStream(getWorldFilePrefix() + worldNumber + WORLD_FILE_SUFFIX));
            oos.writeObject((Object) world);
            oos.close();

            UI.println("World " + worldNumber + " saved");
        } catch (Exception e) {
            UI.println("Exception caught: " + e);
        }

    }
}
