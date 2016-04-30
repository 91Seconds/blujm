package game;

import java.io.Serializable;

/**
 * Created by Daniel Young on 4/29/2016.
 */
public class GPowerup extends GSquare implements Serializable{

    private String type;

    public GPowerup(String imageName, String type) {
        super(imageName, type);
    }
}
