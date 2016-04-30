package game;

import java.io.Serializable;

/**
 * Created by Dylan on 30/04/16.
 *
 * Stores the data for the
 */
public class GGoal implements Serializable {

    boolean[][] goals;

    public GGoal(int width, int height) {
        goals = new boolean[height][width];
    }

    public int getWidth() {
        return goals[0].length;
    }

    public int getHeight() {
        return goals.length;
    }

    public void setValue(boolean isPartOfGoal, int row, int col) {
        goals[row][col] = isPartOfGoal;
    }

    public void setValuesInRect(boolean isPartOfGoal, int rowStart, int colStart, int rowEnd, int colEnd) {
        for (int row = rowStart; row < rowEnd; row++) {
            for (int col = colStart; col < colEnd; col++) {
                setValue(isPartOfGoal, row, col);
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

        for (int col = 0; col < getWidth(); col++) {
            for (int row = 0; row < getHeight(); row++) {
                if (goals[row][col]) sb.append('1');
                else sb.append('0');
                sb.append(' ');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
        }

        return sb.toString();
    }

}
