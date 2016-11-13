package game.network;

import chat.server.ChatServer;
import chat.server.Client;
import game.components.Action;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class GameServer implements ReceiveStrategy {
    private Broadcaster broadcaster;
    private Receiver receiver;
    private ChatServer chatServer;
    private HashMap<String, Client> userMap;
    private ArrayList<InetSocketAddress> userList;

    public GameServer(int port) {
        receiver = new Receiver(port, this);
        chatServer = new ChatServer(port);
        userMap = chatServer.getUserMap();
        userList = chatServer.getUserList();
        broadcaster = new Broadcaster(userList);

        receiver.start();
    }

    public void receive(Action action) {
        try {
            //TODO add verification of user
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
