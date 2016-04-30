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
        // ('Completed' means the test has passed before)

//        testPath(); // Completed
//        (new GGraphicsManualTest()).testBG(); // Completed
//        (new GGraphicsManualTest()).testCellsAndBG(); // Completed

    }

    /**
     * Use this to find the root path
     */
    public static void testPath() {
        String p = UIFileChooser.open("Manual test: path");
        UI.println(p);
    }
}
