package chat.server;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;

import chat.Message;

public class Broadcaster {
	private LinkedList<InetSocketAddress> chatters;

	public Broadcaster() {
		chatters = new LinkedList<InetSocketAddress>();
	}

	public void broadcast(Message message) throws IOException, ClassNotFoundException {
		broadcast(this.chatters, message);
	}

	public void addAddress(InetSocketAddress address) {
		chatters.add(address);
	}

	public static void broadcast(LinkedList<InetSocketAddress> chatters, Message message) throws IOException, ClassNotFoundException {
		for(InetSocketAddress address: chatters) {
			Socket client = new Socket(address.getAddress(), address.getPort()); 
			
			/* Send data to the ServerSocket */
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			oos.writeObject(message);
			oos.close();
			
			client.close();
		}
	}
}
