package game.scene;


import game.components.Map;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.ShapeRenderer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGame extends BasicGame {
    private final int UP = 0,
            RIGHT = 1,
            DOWN = 2,
            LEFT = 3;
    private final int DIRECTION_COUNT = 4;
    private final int MOVEMENT_FRAME_COUNT = 3;

    private Map testMap;
    private SpriteSheet characterSprite;
    private Animation sprite;
    private Animation[] movements;
    private ArrayList<Rectangle> collisions = new ArrayList<>();
    private Rectangle playerBox;
    private float x = 0, y = 0;
    private float playerX, playerY;
    private float centerX, centerY;

    private MainGame(String gamename) {
        super(gamename);
    }

    private void movePlayer(float x, float y) {
        this.x = centerX - x;
        this.y = centerY - y;
        playerX = x;
        playerY = y;
    }

    //The loading of the resources should be done in the init() method
    @Override
    public void init(GameContainer gc) throws SlickException {
        testMap = new Map("Assets/Maps/test2.tmx");
        characterSprite = new SpriteSheet("Assets/Art/rpgsprites1/warrior_f.png", 32, 36);
        movements = new Animation[DIRECTION_COUNT];
        Image[] holder;
        int[] duration = {300, 300, 300};
        for (int ii = 0; ii < DIRECTION_COUNT; ii++) {
            holder = new Image[MOVEMENT_FRAME_COUNT];
            for (int jj = 0; jj < MOVEMENT_FRAME_COUNT; jj++) {
                holder[jj] = characterSprite.getSprite(jj, ii);
            }
            movements[ii] = new Animation(holder, duration, false);
        }

        sprite = movements[RIGHT];
        collisions.addAll(testMap.getCollisions());
        playerBox = new Rectangle(gc.getWidth() / 2f - 16f, gc.getHeight() / 2f - 18f, 32, 36);

        centerX = playerX = gc.getWidth()/2f;
        centerY = playerY = gc.getHeight()/2f;
        x = centerX - testMap.getWidth()*testMap.getTileWidth()/2f - 64;
        y = centerY - testMap.getHeight()*testMap.getTileHeight()/2f;
        movePlayer(15*32, 11*32);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        float dX = 0, dY = 0;
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_UP)) {
            sprite = movements[UP];
            sprite.update(delta);
            // The lower the delta the slowest the sprite will animate.
            dY += delta * 0.1f;
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            sprite = movements[DOWN];
            sprite.update(delta);
            dY -= delta * 0.1f;
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            sprite = movements[LEFT];
            sprite.update(delta);
            dX += delta * 0.1f;
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            sprite = movements[RIGHT];
            sprite.update(delta);
            dX -= delta * 0.1f;
        }
        playerBox.setX(playerX - dX);
        playerBox.setY(playerY - dY);
        boolean isInCollision = false;
        for (Rectangle ret: collisions){
            if (playerBox.intersects(ret)) {
                isInCollision = true;
            }
        }

        if (!isInCollision) {
            x += dX;
            y += dY;
            playerX -= dX;
            playerY -= dY;
        }
        //Log.debug(" X: " + playerX + " Y: " + playerY);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        testMap.render((int) x, (int) y);
        sprite.draw(gc.getWidth() / 2, gc.getHeight() / 2);
        //sprite.draw(gc.getWidth()/2, gc.getHeight()/2);
        testMap.render((int)x, (int)y, 1);
        g.drawString("Howdy!", 20, 20);

        ShapeRenderer.draw(playerBox);
        collisions.forEach(ShapeRenderer::draw);

    }

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new MainGame("Simple Slick Game"));
            appgc.setDisplayMode(640, 480, false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(MainGame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
