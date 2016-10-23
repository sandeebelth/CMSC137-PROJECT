package chat.client;

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
		//insert missing line here for creating a new socket for client and binding it to a port
		Socket client = new Socket(chatServerName, chatServerPort); 
		
		/* Send data to the ServerSocket */
		ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
		oos.writeObject(message);
		oos.close();
		
		client.close();
	}
}
