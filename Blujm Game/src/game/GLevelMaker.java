package game;

import ecs100.*;
import ecs100.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by surface on 30/04/2016.
 */
public class GLevelMaker {
    public static GWorld world;

    public static void main(String[] args){
        UI.initialise();
        UI.addButton("Load World from txt",GLevelMaker::initparse);
    }

    private static void initparse() {
        String path = UIFileChooser.open("open a level in plaintext");
        File level = new File(path);

        GCell[][] cellArray = new GCell[25][25];
        try {
            Scanner sc = new Scanner(level);
            for(int i=0;i<25;i++)   {
                for(int j=0;j<25;j++)   {
                    System.out.println(sc.nextLine());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        GWorld GW = new GWorld(cellArray);
    }
}
