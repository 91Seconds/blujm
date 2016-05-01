package game;

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

 * + is increase
 * - is kill
 * modifiers are done in prefixes. //note, currently no support for modifiers
 *
 */
public class GWorldMaker {


    /**
     * TODO rename this class to something else
     */


    public static GWorld world;

    public static final String WORLD_PROTOTYPE_FOLDER = GFileChecker.RESOURCES_ROOT + File.separator + "worlds";
    public static final String WORLD_PROTOTYPE_FILE_PREFIX = "world-";
    public static final String WORLD_PROTOTYPE_FILE_SUFFIX = ".txt";

    public static GWorld loadWorld(int world){
        String worldPath = WORLD_PROTOTYPE_FOLDER + File.separator + WORLD_PROTOTYPE_FILE_PREFIX + world + WORLD_PROTOTYPE_FILE_SUFFIX;
        File worldFile = new File(worldPath);
        return loadWorld(worldFile);
    }

    public static GWorld loadWorld(File world) {
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
                    if(line[col].equals("+")) {
                        cellArray[row][col] = new GCell(GSquare.POWERUP_GROW_PATH, GSquare.POWERUP_GROW_TYPE);
                    }
                    if(line[col].equals("-")) {
                        cellArray[row][col] = new GCell(GSquare.POWERUP_KILL_PATH, GSquare.POWERUP_KILL_TYPE);
                    }
                    if(line[col].equals("l")) {
                        cellArray[row][col] = new GCell(GSquare.POWERUP_TELEPORT_LEFT_PATH, GSquare.POWERUP_TELEPORT_LEFT_TYPE);
                    }
                    if(line[col].equals("d")) {
                        cellArray[row][col] = new GCell(GSquare.POWERUP_TELEPORT_DOWN_PATH, GSquare.POWERUP_TELEPORT_DOWN_TYPE);
                    }
                    if(line[col].equals("r")) {
                        cellArray[row][col] = new GCell(GSquare.POWERUP_TELEPORT_RIGHT_PATH, GSquare.POWERUP_TELEPORT_RIGHT_TYPE);
                    }
                    if(line[col].equals("u")) {
                        cellArray[row][col] = new GCell(GSquare.POWERUP_TELEPORT_UP_PATH, GSquare.POWERUP_TELEPORT_UP_TYPE);
                    }
                }
                System.out.print("\n");
            }

            // TODO: 1/05/16 move world height in units into gworld
            while (true) {
                String line = sc.nextLine();
                if (line.equals("//GOAL")) break;
            }

            String[] goalRows = new String[GGraphics.WORLD_HEIGHT_IN_UNITS];
            for (int row = 0; row < GGraphics.WORLD_HEIGHT_IN_UNITS; row++) {
                goalRows[row] = sc.nextLine();
            }
            GGoal levelGoal = new GGoal(goalRows);

            return new GWorld(cellArray,levelGoal);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
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
