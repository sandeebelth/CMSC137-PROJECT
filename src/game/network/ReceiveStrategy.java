package game.network;

import game.components.Action;

public interface ReceiveStrategy {
    void receive(Action action);
}
