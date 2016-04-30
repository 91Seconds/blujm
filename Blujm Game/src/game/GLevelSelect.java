package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Daniel Young on 4/30/2016.
 */
public class GLevelSelect {
    private String[] worlds;

    //Image file for the background and title of level select screen
    private String fName = GFileChecker.RESOURCES_ROOT + File.separator
            + "images" + File.separator + "side-menu-1.png";

    private String worldsFolder = GFileChecker.RESOURCES_ROOT + File.separator
            + "world";
    private static final int spacing = 20;


    //Outlines the possible area for level select buttons
    public static final int LEFT = GGraphics.WORLD_LEFT + 2*spacing;
    private static final int RIGHT = GGraphics.WORLD_LEFT + GGraphics.WORLD_WIDTH - 4*spacing;
    private static final int TOP = GGraphics.WORLD_TOP + 90;
    private static final int BOT = GGraphics.WORLD_HEIGHT - 2*spacing;

    private static final int cellsPerRow = 5;
    private static final int buttonWidth = ((RIGHT - LEFT) - (cellsPerRow + 1)*spacing) / cellsPerRow;
    private static final int buttonHeight = ((BOT - TOP) - (cellsPerRow + 1)*spacing) / cellsPerRow;

    public GLevelSelect(){
        //Iterates through the worlds folder to find the names of the world to be loaded
        File f = new File(worldsFolder);
        worlds = f.list();
    }

    public void update(){
        for(int counter = 0; counter < worlds.length; counter++){
            
        }
    }
}
