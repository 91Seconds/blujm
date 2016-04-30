package game;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Created by Dylan on 30/04/16.
 *
 * Stores the data for the goal
 */
public class GGoal implements Serializable {

    boolean[][] goals;

    public GGoal(int width, int height) {
        goals = new boolean[height][width];
    }

    public GGoal(String fromString) {
        String[] rows = fromString.split("\n");
        String[][] values = new String[rows.length][];
        for (int r = 0; r < rows.length; r++) {
            String row = rows[r];
            values[r] = row.split(" +");
        }
        goals = new boolean[values.length][values[0].length];
        for (int row = 0; row < goals.length; row++) {
            for (int col = 0; col < goals[0].length; col++) {
                String valString = values[row][col];
                boolean valBool;
                if (valString.equals("1")) valBool = true;
                else if (valString.equals("0")) valBool = false;
                else throw new Error("You cannot load the goal with a '" + valString
                            + "' character (currently at row: " + row + ", col: " + col);
                goals[row][col] = valBool;
            }
        }
    }

    public int getWidth() {
        return goals[0].length;
    }

    public int getHeight() {
        return goals.length;
    }

    public boolean getValue(int row, int col) { return goals[row][col]; }
    public void setValue(boolean isPartOfGoal, int row, int col) {
        goals[row][col] = isPartOfGoal;
    }

    public void setValuesInRect(boolean isPartOfGoal, int left, int top, int width, int height) {
        for (int x = left; x < width + left; x++) {
            for (int y = top; y < height + top; y++) {
                setValue(isPartOfGoal, y, x);
            }
        }
    }

    /**
     * Allows you to set multiple values (in a rectangle)
     * at a time without being annoyed
     * @param isPartOfGoal
     * @param rowStart
     * @param colStart
     */
    public void setValue(boolean isPartOfGoal, int rowStart, int colStart, int rowEnd, int colEnd) {
        goals[rowStart][colStart] = isPartOfGoal;

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (goals[row][col]) sb.append('1');
                else sb.append('0');
                sb.append(' ');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * See GLogic.getUserState()
     * @param userState
     * @return
     */
    public boolean matchesUserState(boolean[][] userState) {
        for (int col = 0; col < userState[0].length; col++) {
            for (int row = 0; row < userState.length; row++) {
                if (userState[row][col] != getValue(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

}
