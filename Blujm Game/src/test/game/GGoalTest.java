package test.game;

import game.GGoal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * GGoal Tester.
 *
 * @author <Authors name>
 * @since <pre>Apr 30, 2016</pre>
 * @version 1.0
 */
public class GGoalTest {

    GGoal gGoal;

    private void setSomeValues(GGoal gg) {
        gg.setValue(true, 0, 0);
        gg.setValue(true, 1, 1);
        gg.setValue(true, 17, 21);
    }

    @Before
    public void before() throws Exception {
        gGoal = new GGoal(22, 18);
        setSomeValues(gGoal);
        for (int row = 5; row < 10; row++) {
            for (int col = 5; col < 10; col++) {
                gGoal.setValue(true, row, col);
            }
        }
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: toString()
     *
     */
    @Test
    public void testToString() throws Exception {
        String result = gGoal.toString();
        String idealResult = "" +
                "1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 1 1 1 1 1 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1\n";
        Assert.assertEquals("Result was not equal to the idealResult", idealResult, result);
    }

    @Test 
} 
