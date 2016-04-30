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
public class GWorldMaker {
    public static GWorld world;

    public static void main(String[] args){
        UI.initialise();
        UI.addButton("Load World from txt", GWorldMaker::loadChosenPrototype);
        UI.addButton("Load Default Prototype", GWorldMaker::loadAndSaveFromDefaultPrototype);
    }

    private static void loadAndSaveFromDefaultPrototype()   {
        File world = new File(GFileChecker.RESOURCES_ROOT + File.separator + "world-prototype.txt");
        GWorldLoader.saveWorld(loadWorldFromPrototype(world), 1);
    }

    private static void loadChosenPrototype() {
        String path = UIFileChooser.open("open a world in plaintext");
        File worldFile = new File(path);
        GWorld world = loadWorldFromPrototype(worldFile);
        GWorldLoader.saveWorld(world, 1);
    }

    private static GWorld loadWorldFromPrototype(File world) {
        GCell[][] cellArray = new GCell[GGraphics.WORLD_HEIGHT_IN_UNITS][GGraphics.WORLD_WIDTH_IN_UNITS];
        try {
            Scanner sc = new Scanner(world);
            for(int row=0;row<GGraphics.WORLD_HEIGHT_IN_UNITS;row++)   {
                String[] line = getNonEmpty(sc.nextLine().split(" "));

                for(int col=0;col<GGraphics.WORLD_WIDTH_IN_UNITS;col++)   {
                    System.out.print(line[col]);
                    if(line[col].equals("x")) {
                        cellArray[row][col] = new GCell(GSquare.WALL_PATH,GSquare.WALL_TYPE);
                    }
                    if(line[col].equals("1")) {
                        cellArray[row][col] = new GCell(GSquare.USER_PATH,GSquare.USER_TYPE);
                    }
                    if(line[col].equals("0")) {
                        cellArray[row][col] = new GCell(GSquare.EMPTY_PATH, GSquare.EMPTY_TYPE);
                    }
                    if(line[col].equals("e")) {
                        cellArray[row][col] = new GCell(GSquare.ENEMY_PATH,GSquare.ENEMY_TYPE);
                    }
                }
                System.out.print("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        GGoal levelGoal = new GGoal(GGraphics.WORLD_WIDTH_IN_UNITS, GGraphics.WORLD_HEIGHT_IN_UNITS);
        levelGoal.setValuesInRect(true,1,8,3,3);

        String g = levelGoal.toString();

        return new GWorld(cellArray,levelGoal);
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
