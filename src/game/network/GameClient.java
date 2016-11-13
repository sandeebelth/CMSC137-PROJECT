package game.network;

import game.components.Action;
import game.components.ActionType;
import game.scene.MainGame;

import java.io.IOException;

/**
 * Created by joseph on 11/13/16.
 */
public class GameClient implements ReceiveStrategy {
    private String name;
    private int key;
    private Sender sender;
    private Receiver receiver;
    private MainGame game;

    public GameClient(Sender sender, int port, MainGame game) {
        this.sender = sender;
        this.game = game;
        receiver = new Receiver(port, this);
        receiver.run();
    }

    @Override
    public void receive(Action action) {
        switch (action.getActionType()) {
            case MOVEMENT:
                break;
        }
    }

    public void move(int x, int y) {
        //TODO Try to find a better way to handle this exception
        try {
            sender.send(new Action(name, key, name, ActionType.MOVEMENT, x, y));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
