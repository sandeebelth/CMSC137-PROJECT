package chat.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Random;

import chat.ReceiveStrategy;
import chat.Message;
import chat.Sender;

public class ClientReceive implements ReceiveStrategy {
	private Broadcaster broadcaster;
	private HashMap<String, Client> userList;
	private Random random;
	public ClientReceive() {
		broadcaster = new Broadcaster();
		userList = new HashMap<String, Client>();
		random = new Random();
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
			}
		} catch(IOException e) {
			//TODO Fix error
			System.out.println("Cannot find Server");
			return new Message("Unable to send message", "System", message.getFromName(), 0);
		} catch(ClassNotFoundException e) {
			System.out.println("Cant find class");
			return new Message("Error in server", "System", message.getFromName(), 0);
		}
		System.out.println("Done message!");
		return null;
	}
	private Message addUserProtocol(Message message) {
		if (userList.containsKey(message.getFromName())) {
			return new Message("Taken Username", "System", message.getFromName(), 0);
		}

		try {
			Sender.send(message.getTextMessage(), message.getKey(), new Message("TEST", "#SYS", "#SYS", 0));
		} catch(IOException e) {
			System.out.println("Cannot find Server");
			return new Message("Invalid Receive Address", "System", message.getFromName(), 0);
		} catch(ClassNotFoundException e) {
			System.out.println("Cant find class");
			return new Message("Error in server", "System", message.getFromName(), 0);
		}

		int key = random.nextInt();
		InetSocketAddress userAddress = new InetSocketAddress(message.getTextMessage(), message.getKey());
		userList.put(message.getFromName(), new Client(message.getFromName(), key, userAddress));
		broadcaster.addAddress(userAddress);
		System.out.println("Accepting User");
		return new Message("Successfully added to users", "System", message.getFromName(), key);
	}
}
