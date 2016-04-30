package game;

import ecs100.UI;
import ecs100.UIFileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by surface on 30/04/2016.
 */
public class GLevelMaker {
    public static GWorld world;

    public static void main(){
        UI.initialise();
        UI.addButton("Load World from txt",GLevelMaker::initparse);
    }

    private static void initparse() {
        String path = UIFileChooser.open("open a level in plaintext");
        File level = new File(path);

        GCell[][] cellArray = new GCell[25][25];
        try {
            Scanner sc = new Scanner(level);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        GWorld GW = new GWorld(cellArray);
    }
}
