package manual_test;

import ecs100.UI;
import ecs100.UIFileChooser;
import game.GMain;

/**
 * Created by Dylan on 30/04/16.
 */
public class MainManualTest {

    /**
     * Run your manual tests here and then comment them out when done
     */
    public static void main(String[] args) {
        UI.setWindowSize(GMain.WINDOW_WIDTH, GMain.WINDOW_HEIGHT);
        UI.setDivider(GMain.DIVIDER_POSITION);

        // TESTS HERE:

//        testPath(); // Completed
        for(int row = 0; row < 25; row++){
            for(int col = 0; col < 25; col++){
                UI.drawRect(col*30, row*30, 30, 30);
            }
        }
        UI.drawRect(25*30, 0, 90, 25*30);
//          (new GGraphicsManualTest()).testBG(); // Completed
//        (new GGraphicsManualTest()).testCells();

    }

    /**
     * Use this to find the root path
     */
    public static void testPath() {
        String p = UIFileChooser.open("Manual test: path");
        UI.println(p);
    }
}
