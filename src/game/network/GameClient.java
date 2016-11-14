package game.network;

import chat.client.ChatClient;
import chat.client.NewUserListener;
import game.components.Action;
import game.components.ActionType;
import game.components.Character;
import game.scene.MainGame;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.net.InetSocketAddress;

public class GameClient implements ReceiveStrategy, NewUserListener {
    private String name;
    private int key;
    private Sender sender;
    private Receiver receiver;
    private MainGame game;
    private ChatClient chatClient;

    public GameClient(InetSocketAddress serverAddress, int port, MainGame game) throws IOException, ClassNotFoundException {
        this.sender = new Sender(serverAddress);
        chatClient = new ChatClient(serverAddress, port, this);
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
        if (!action.getAffectedName().equals(name)) {
            switch (action.getActionType()) {
                case MOVEMENT:
                    game.movePlayer(action.getAffectedName(), action.getValue1(), action.getValue2());
                    break;
            }
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

    @Override
    public void newUser(String name) {
        try {
            game.addCharacter(name, new Character(2*32, 2*32, 32, 36, "Assets/Art/rpgsprites1/warrior_f.png"));
        } catch (SlickException e) {
            //TODO better exception handling
            e.printStackTrace();
        }
    }
}
