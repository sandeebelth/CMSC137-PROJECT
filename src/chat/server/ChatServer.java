package chat.server;

import chat.Message;
import chat.ReceiveStrategy;
import chat.Receiver;
import chat.Sender;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ChatServer implements ReceiveStrategy {
    private Broadcaster broadcaster = new Broadcaster();
    private HashMap<String, Client> userMap = new HashMap<>();
    private Random random = new Random();

    public ChatServer(int port) {
        try {
            Thread t = new Receiver(port, this);
            t.start();

        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Cannot find GameServer");
        }
    }

    public HashMap<String, Client> getUserMap() {
        return userMap;
    }

    public ArrayList<InetSocketAddress> getUserList() {
        return broadcaster.getUserList();
    }

    public Message receive(Message message) {
        System.out.println("New message!");
        try {
            switch(message.getToName()) {
                case "#ADD":
                    return addUserProtocol(message);
                case "#ALL":
                    broadcaster.broadcast(message);
                    break;
                case "#LOGOUT":
                    return removeUser(message);
            }
        } catch(IOException e) {
            //TODO Fix error
            System.out.println("Cannot find GameServer");
            return new Message("Unable to send message", "System", message.getFromName(), 0);
        } catch(ClassNotFoundException e) {
            System.out.println("Cant find class");
            return new Message("Error in server", "System", message.getFromName(), 0);
        }
        System.out.println("Done message!");
        return null;
    }

    private Message removeUser(Message message) throws IOException, ClassNotFoundException {
        Client user = userMap.get(message.getFromName());
        if (user == null || user.getKey() != message.getKey()) {
            return new Message("Invalid Credentials", "System", message.getFromName(), 0);
        }

        broadcaster.removeAddress(user.getReceiverAddress());
        broadcaster.broadcast(new Message(message.getFromName() + "Logged out", "System", "#ALL", 0));
        userMap.remove(message.getFromName());
        return new Message("Successfully logged out", "System", message.getFromName(), 0);
    }

    private Message addUserProtocol(Message message) {
        if (userMap.containsKey(message.getFromName())) {
            return new Message("Taken Username", "System", message.getFromName(), 0);
        }

        try {
            Sender.send(message.getTextMessage(), message.getKey(), new Message("TEST", "#SYS", "#SYS", 0));
        } catch(IOException e) {
            System.out.println("Cannot find GameServer");
            return new Message("Invalid Receive Address", "System", message.getFromName(), 0);
        } catch(ClassNotFoundException e) {
            System.out.println("Cant find class");
            return new Message("Error in server", "System", message.getFromName(), 0);
        }

        int key = random.nextInt();
        InetSocketAddress userAddress = new InetSocketAddress(message.getTextMessage(), message.getKey());
        userMap.put(message.getFromName(), new Client(message.getFromName(), key, userAddress));
        broadcaster.addAddress(userAddress);
        System.out.println("Accepting User");
        return new Message("Successfully added to users", "System", message.getFromName(), key);
    }

	public static void main(String [] args) {
        try {
            ChatServer chatServer = new ChatServer(Integer.parseInt(args[0]));//get port from second param
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("Usage: java command <port no.>");
        }
	}
}
