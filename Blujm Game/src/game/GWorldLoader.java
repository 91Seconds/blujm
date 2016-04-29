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

    //Contains the characters that represent the different squares in the game
    private char liveCell = 'L';
    private char deadCell = 'D';
    private char goalCell = 'G';
    private char wall = 'X';
    private char pMove = '';
    private char pScale = '';
    //Need to plan out the rest of the types of cells and powerups


    public GWorldLoader(){

    }

    /**
     * Reads a world from a file
     * Format
     * Size of level
     * 2D array of squares in the level
     * @return
     */
    public GWorld getWorld(int level){

        try{
            Scanner scan = new Scanner(new File("level" + level));
            if(scan.nextInt() != GWorld.GAMESIZE){
                UI.println("Invalid Level Format: Incorrect Size");
                return null;
            }
        }
        catch(IOException e){
            UI.println("File Reading Error:" + e);
        }
        return (new GWorld());
    }
}
