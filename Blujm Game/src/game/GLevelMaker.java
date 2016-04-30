package game;

import ecs100.UI;
import ecs100.UIFileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by surface on 30/04/2016.
 *
 * reads a text file into a GWorld object
 * Saves the GWorld object with the GWorldLoader class
 *
 * format of text file
 *
 * 0 is nothing
 * 1 is living cell
 * x is wall
 * e is enemy
 * p is power-up
 *
 * modifiers are done in prefixes. //note, currently no support for modifiers
 *
 */
public class GLevelMaker {
    public static GWorld world;

    public static void main(String[] args){
        UI.initialise();
        UI.addButton("Load World from txt", GLevelMaker::parse);
    }

    private static void parse() {
        String path = UIFileChooser.open("open a level in plaintext");
        File level = new File(path);

        GCell[][] cellArray = new GCell[25][25];
        try {
            Scanner sc = new Scanner(level);
            for(int i=0;i<25;i++)   {
                String[] line = getNonEmpty(sc.nextLine().split(" "));

                for(int j=0;j<25;j++)   {
                    System.out.print(line[j]);
                    if(line[j].equals("x")) {
                        cellArray[i][j] = new GCell(GSquare.wallPath,GSquare.wallType);
                    }
                    if(line[j].equals("1")) {
                        cellArray[i][j] = new GCell(GSquare.userPath,GSquare.userType);
                    }
                    if(line[j].equals("0")) {
                        cellArray[i][j] = null;
                    }
                }
                System.out.print("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        GWorld GW = new GWorld(cellArray);

        GWorldLoader.saveWorld(GW,1);
    }

    private static String[] getNonEmpty(String[] possiblyEmptyStuff) {
        int EmptyValues = 0;
        int nonEmptyValues = possiblyEmptyStuff.length;
        for(int i = 0; i < possiblyEmptyStuff.length; i++) {
            if(possiblyEmptyStuff[i].equals("")) {
                EmptyValues += 1;
            }
        }

        nonEmptyValues -= EmptyValues;

        String[] nonEmpty = new String[nonEmptyValues];

        int dnonEmpty = 0;
        for(int i = 0; i < possiblyEmptyStuff.length; i++) {
            if(!possiblyEmptyStuff[i].equals("")) {
                nonEmpty[dnonEmpty] = possiblyEmptyStuff[i];
                dnonEmpty++;
            }
        }

        return nonEmpty;
    }
}
