package game.components;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;

import java.util.ArrayList;

public class Map extends TiledMap {
    private ArrayList<Rectangle> blocks = new ArrayList<>();

    public Map(String ref) throws SlickException {
        super(ref);
        final String COLLISION_NAME = "Collision";

        int collisionLayer = super.getLayerIndex(COLLISION_NAME);
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {

                // Read a Tile
                int tileID = getTileId(i, j, collisionLayer);

                // Get the value of the Property named "blocked"
                String value = getTileProperty(tileID, "blocked", "false");

                // If the value of the Property is "true"...
                if (value.equals("true")) {
                    //create the collision Rectangle
                    blocks.add(new Rectangle((float) i * tileWidth, (float) j * tileHeight, tileWidth, tileHeight));
                }
            }
        }

        layers.remove(collisionLayer);
    }

    public ArrayList<Rectangle> getCollisions() {
        return blocks;
    }
}
