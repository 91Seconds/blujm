package game;

import java.io.File;

/**
 * Created by Dylan on 30/04/16.
 *
 * For checking file existence.
 * Static-only class
 */
public class GFileChecker {

    public static final String RESOURCES_ROOT = "Blujm Game" + File.separator + "resources";

    private GFileChecker() {}

    /**
     * @param imagePath
     * @return null if the file exists, otherwise an error message
     */
    public static String checkIfFilePathExists(String imagePath) {
        File f = new File(imagePath);

        if (!f.exists()) return "File '" + imagePath + "' does not exist";
        if (f.isDirectory()) return "File '" + imagePath + "' is a directory, not an image";

        return null;
    }
}
