package game.components;

import java.io.Serializable;

public class CharacterState implements Serializable {
    private String name;
    private float x, y;
    private int direction;

    public CharacterState(String name, float x, float y, int direction) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public int getDirection() {
        return direction;
    }

    public String getName() {
        return name;
    }
}
