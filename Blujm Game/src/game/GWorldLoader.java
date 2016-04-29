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

    public GWorldLoader(){

    }

    /**
     * Reads a world from a file
     * @return
     */
    public GWorld getWorld(int level){
        try{
            Scanner scan = new Scanner(new File("level" + level));
        }
        catch(IOException e){
            UI.println("File Reading Error:" + e);
        }
    }
}
