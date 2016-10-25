package chat;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import chat.Message;

public class Sender {
	private String chatServerName;
	private int chatServerPort;

	public Sender(String serverName, int port) {
		chatServerName = serverName;
		chatServerPort = port;
	}

	public void send(Message message) throws IOException, ClassNotFoundException {
		send(chatServerName, chatServerPort, message);
	}

	public static void send(String address, int port, Message message) throws IOException, ClassNotFoundException {
		Socket client = new Socket(address, port); 
		
		/* Send data to the ServerSocket */
		ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
		oos.writeObject(message);
		oos.close();
		
		client.close();
	}
}
