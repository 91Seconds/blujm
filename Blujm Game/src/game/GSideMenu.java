package game;

import ecs100.UI;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.currentTimeMillis;

/**
 * Created by Daniel Young on 4/30/2016.
 */
public class GSideMenu {

    public static final int SIDE_MENU_WIDTH = 90;
    public static final int SIDE_MENU_HEIGHT = GGraphics.WORLD_HEIGHT;
    public static int LEFT = GGraphics.WORLD_WIDTH;
    public static int TOP = GGraphics.WORLD_TOP;

    private String fName = GFileChecker.RESOURCES_ROOT + File.separator
            + "images" + File.separator + "side-menu-1.png";

    private long startTimeMillis;
    private int level;

    public static void main(String[]args){
        GSideMenu g = new GSideMenu(0,System.currentTimeMillis());
        g.draw();
        //g.toReadableTime();
    }

    //Need to implement level incrementing in other classes before this one
    public GSideMenu(int level, long timeMillis){
        startTimeMillis = currentTimeMillis();
        this.level = level;
    }

    public String toReadableTime(){
        long millisDifference = System.currentTimeMillis() - startTimeMillis;
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        Date resultDate = new Date(millisDifference);
        UI.println(sdf.format(resultDate));
        return sdf.format(resultDate);
    }

    public void draw(){
        UI.drawImage(fName, LEFT, TOP, SIDE_MENU_WIDTH, SIDE_MENU_HEIGHT);
        UI.setFontSize(28);
        UI.setColor(Color.WHITE);
        UI.drawString(Integer.toString(level), LEFT + 37, 350);
        UI.drawString(toReadableTime(), LEFT + 8, 575);
    }
}
*/