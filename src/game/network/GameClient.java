package game.network;

import chat.client.ChatClient;
import game.components.Action;
import game.components.ActionType;
import game.scene.MainGame;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by joseph on 11/13/16.
 */
public class GameClient implements ReceiveStrategy {
    private String name;
    private int key;
    private Sender sender;
    private Receiver receiver;
    private MainGame game;
    private ChatClient chatClient;

    public GameClient(InetSocketAddress serverAddress, int port, MainGame game) throws IOException, ClassNotFoundException {
        this.sender = new Sender(serverAddress);
        chatClient = new ChatClient(serverAddress, port);
        this.game = game;
        receiver = new Receiver(port, this);
        receiver.start();
        if (!chatClient.connectToServer(Integer.toString(port))) {
            //TODO add better error handling
            throw new IOException("Unable to connect using name");
        }
        name = chatClient.getUsername();
        key = chatClient.getKey();
    }

    @Override
    public void receive(Action action) {
        switch (action.getActionType()) {
            case MOVEMENT:
                break;
        }
    }

    public void move(float x, float y) {
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
