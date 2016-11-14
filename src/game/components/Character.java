package game.components;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Character {
    public static final int UP = 0,
            RIGHT = 1,
            DOWN = 2,
            LEFT = 3;
    private final int DIRECTION_COUNT = 4;
    private final int MOVEMENT_FRAME_COUNT = 3;
    private static float screenOriginX, screenOriginY;

    private String name;
    private int direction;
    private Rectangle hitBox;
    private float x, y;
    private SpriteSheet characterSprite;
    private Animation sprite;
    private Animation[] movements;

    public Character(float x, float y, int width, int height, String spriteReference) throws SlickException {
        characterSprite = new SpriteSheet(spriteReference, width, height);
        movements = new Animation[DIRECTION_COUNT];
        hitBox = new Rectangle(x, y, width, height);

        Image[] holder;
        int[] duration = {175, 175, 175};
        for (int ii = 0; ii < DIRECTION_COUNT; ii++) {
            holder = new Image[MOVEMENT_FRAME_COUNT];
            for (int jj = 0; jj < MOVEMENT_FRAME_COUNT; jj++) {
                holder[jj] = characterSprite.getSprite(jj, ii);
            }
            movements[ii] = new Animation(holder, duration, false);
        }

        sprite = movements[RIGHT];
        direction = RIGHT;

        this.x = x;
        this.y = y;
    }

    public void updateDelta(int delta) {
        sprite.update(delta);
    }

    public void setMovementDirection(int direction) {
        sprite = movements[direction];
        this.direction = direction;
    }

    public void move(float x, float y) {
        this.x += x;
        this.y += y;
        this.hitBox.setX(this.x);
        this.hitBox.setY(this.y);
    }

    public void setCoordinate(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitBox.setX(this.x);
        this.hitBox.setY(this.y);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static void setOrigin(float x, float y) {
        screenOriginX = x;
        screenOriginY = y;
    }

    public void draw() {
        sprite.draw(x + screenOriginX, y + screenOriginY);
    }

    public void draw(float x, float y) {
        sprite.draw(x, y);
    }

    public CharacterState getState() {
        return new CharacterState(name, x, y, direction);
    }
}
