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
     * Contains the characters that represent the different squares in the game
     */
    private char liveCell = 'L';
    private char deadCell = 'D';
    private char goalCell = 'G';
    private char wall = 'X';
    private char pMove = ' ';
    private char pScale = ' ';
    //Need to plan out the rest of the types of cells and powerups

    /**
     * Private constructor makes this a static-only class
     */
    private GWorldLoader() {}

    /**
     * Reads a world from a file
     * Format
     * Size of level
     * 2D array of squares in the level
     * @return A new GWorld object. Returns null if there is an error
     */
   public static GWorld getWorld(int level){

        GWorld gWorld = null;
        try {
            Scanner scan = new Scanner(new File("level" + level));

            // TODO Dan fix - if you set GAMESIZE as a constant, then
            // the size of the world cannot change between levels (and the world
            // must always be a square
//            if (scan.nextInt() != GWorld.GAMESIZE){
//                UI.println("Invalid Level Format: Incorrect Size");
//                return null;
//            }
            // TODO Dan - make the new gworld object here
        } catch(IOException e){
            UI.println("File Reading Error:" + e);
            return null;
        }

        return gWorld;
    }
}
