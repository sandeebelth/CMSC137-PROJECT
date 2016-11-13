package game.components;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;

public class Map extends TiledMap {
    private ArrayList<Rectangle> blocks = new ArrayList<>();
    private int upperLayerStart = 0;

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
        int size = layers.size();
        if (size >= 2) {
            upperLayerStart = 2;
        } else {
            upperLayerStart = -1;
        }
    }

    public ArrayList<Rectangle> getCollisions() {
        return blocks;
    }

    public void renderBottom(int x, int y) {
        int layerEnd;
        if (upperLayerStart < 0) {
            layerEnd = layers.size();
        } else {
            layerEnd = upperLayerStart;
        }

        for (int ty = 0; ty < height; ty++) {
            for (int i = 0; i < layerEnd; i++) {
                Layer layer = (Layer) layers.get(i);
                layer.render(x, y, 0, 0, width, ty, false,
                        tileWidth, tileHeight);
            }
        }
    }

    public void renderUpper(int x, int y) {
        if (upperLayerStart < 0) {
            return;
        }

        for (int ty = 0; ty < height; ty++) {
            for (int i = upperLayerStart; i < layers.size(); i++) {
                Layer layer = (Layer) layers.get(i);
                layer.render(x, y, 0, 0, width, ty, false,
                        tileWidth, tileHeight);
            }
        }
    }
}
