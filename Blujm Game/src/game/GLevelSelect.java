package game;

import ecs100.UI;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Daniel Young on 4/30/2016.
 */
public class GLevelSelect {

//    public static void main(String[] args){
//        GLevelSelect temp = new GLevelSelect();
//        temp.update();
//
//    }


    private String[] worlds;

    //Image file for the background and title of world select screen
    private String backgroundImage = GFileChecker.RESOURCES_ROOT + File.separator
            + "images" + File.separator + "LevelSelect.png";
    private String buttonImage = GFileChecker.RESOURCES_ROOT + File.separator
            + "images" + File.separator + "world-select-button.png";


    private String worldsFolder = GWorldMaker.WORLD_PROTOTYPE_FOLDER;
    private static final int SPACING = 20;

    //Outlines the possible area for world select buttons
    public static final int BUTTON_LEFT = GGraphics.WORLD_LEFT + 2* SPACING;
    private static final int RIGHT = GGraphics.WORLD_LEFT + GGraphics.WORLD_WIDTH - 4* SPACING;
    private static final int BUTTON_TOP = GGraphics.WORLD_TOP + 150;
    private static final int BOT = GGraphics.WORLD_HEIGHT - 2* SPACING;

    private static final int cellsPerRow = 5;
    private static final int buttonWidth = ((RIGHT - BUTTON_LEFT) - (cellsPerRow + 1)* SPACING) / cellsPerRow;
    private static final int buttonHeight = ((BOT - BUTTON_TOP) - (cellsPerRow + 1)* SPACING) / cellsPerRow;

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    private boolean disable = false;

    public GLevelSelect(){
        //Iterates through the worlds folder to find the names of the world to be loaded
        worlds = getWorldFiles();
    }

    private String[] getWorldFiles() {
        File f = new File(worldsFolder);
        ArrayList<String> fileNames = new ArrayList<>(Arrays.asList(f.list()));

        for (int a = 0; a < fileNames.size(); a++) {
            String fileName = fileNames.get(a);
            if (!fileName.startsWith(GWorldMaker.WORLD_PROTOTYPE_FILE_PREFIX) ||
                    !fileName.endsWith(GWorldMaker.WORLD_PROTOTYPE_FILE_SUFFIX)) {
                fileNames.remove(fileName);
                continue;
            }
        }

        return fileNames.toArray(new String[fileNames.size()]);
    }

    /**
     * Returns button clicked on or -1 if not on a button
     * @param x
     * @param y
     * @return
     */
    public int worldSelect(double x, double y){
        int row = 0;
        int col = 0;
        int buttonLeft = 0;
        int buttonTop = 0;
        for(int counter = 0; counter < worlds.length; counter++){
            row = counter / cellsPerRow;
            col = counter % cellsPerRow;
            buttonLeft = getButtonLeft(col);
            buttonTop = getButtonTop(row);
            if(x > buttonLeft && x < buttonLeft + buttonWidth &&
                    y > buttonTop && y < buttonTop + buttonHeight){
                return counter ;
            }
        }
        return -1;
    }

    public void update(){
        if (disable) return;

        UI.drawImage(backgroundImage, GGraphics.WORLD_LEFT, GGraphics.WORLD_TOP, GGraphics.WORLD_WIDTH, GGraphics.WORLD_HEIGHT);
        int row = 0;
        int col = 0;
        int index = 0;
        int buttonLeft = 0;
        int buttonTop = 0;
        for(int counter = 0; counter < worlds.length; counter++){

            row = counter / cellsPerRow;
            col = counter % cellsPerRow;
            buttonLeft = getButtonLeft(col);
            buttonTop = getButtonTop(row);
            UI.drawImage(buttonImage, buttonLeft , buttonTop,
                          buttonWidth, buttonHeight);
            index = worlds[counter].indexOf(".");
            UI.setFontSize(24);
            UI.setColor(Color.BLACK);
            UI.drawString("Level", buttonLeft + buttonWidth* .1, buttonTop + buttonHeight*.5);
            UI.drawString(worlds[counter].substring(index - 1, index), buttonLeft + buttonWidth*.43,
                            buttonTop + buttonHeight*.85);
        }
        UI.repaintAllGraphics();
    }

    private int getButtonLeft(int col) {
        return BUTTON_LEFT + (col* SPACING) + SPACING +col*buttonWidth   + GGraphics.WORLD_LEFT;
    }

    private int getButtonTop(int row) {
        return BUTTON_TOP + (row* SPACING) + SPACING + row*buttonHeight   + GGraphics.WORLD_TOP;
    }
}
