package game;

/**
 * Created by Dylan on 29/04/16.
 */
import ecs100.*;

public class GGraphics {
    GWorld world;

    int cellSize = 20;

    public GGraphics(GWorld world) {
        this.world = world;
    }

    public void draw() {

        UI.drawImage(world.backImage, 0, 0);

        for(int i = 0; i < world.cells.length; i++) {
            for(int j = 0; j < world.cells[0].length; j++) {
                UI.drawImage(world.cells.get(i, j).getImage(), j*cellSize, i*cellSize);
            }
        }
    }
}
