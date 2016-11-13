package game.network;

import game.components.Action;

import java.io.IOException;

public class GameServer implements ReceiveStrategy {
    private Broadcaster broadcaster;
    private Receiver receiver;
    public GameServer(int port) {
        receiver = new Receiver(port, this);
        broadcaster = new Broadcaster();
    }

    public void receive(Action action) {
        try {
            switch (action.getActionType()) {
                case MOVEMENT:
                    break;
            }
            broadcaster.broadcast(action);
        } catch (ClassNotFoundException e) {
            //TODO Try to find a better way to handle this exception
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
