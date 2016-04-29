package game;

import java.io.File;

/**
 * Created by Dylan on 30/04/16.
 *
 * For checking file existence.
 * Static-only class
 */
public class GFileChecker {

    private GFileChecker() {}

    /**
     * @param imagePath
     * @return null if the file exists, otherwise an error object to throw
     */
    public static Error checkIfFilePathExists(String imagePath) {
        File f = new File(imagePath);

        if (!f.exists()) return new Error("File " + imagePath + " does not exist");
        if (f.isDirectory()) return new Error("File " + imagePath + " is a directory, not an image");

        return null;
    }
}
