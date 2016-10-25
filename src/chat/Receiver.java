package chat;

import java.net.*;
import java.io.*;

import chat.Message;

public class Receiver extends Thread {
	private ServerSocket serverSocket;
	private ReceiveStrategy receive;
	
	public Receiver(int port, ReceiveStrategy receive) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(0);
		this.receive = receive;
	}

	public void run() {
		while(true) {
			Socket server;
			try {
				server = serverSocket.accept();

				/* Read data from the ClientSocket */
				ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
				Message sendMessage;
				while(true) {
					sendMessage = receive.receive((Message)ois.readObject());
					if(sendMessage == null) {
						break;
					}

					oos.writeObject(sendMessage);
				}
				oos.close();
				ois.close();
				
				server.close();
			}catch(SocketTimeoutException s) {
				System.err.println("Socket timed out!");
				break;
			}catch(IOException e) {
				//e.printStackTrace();
				System.out.println("Usage: java GreetingServer <port no.>");
				break;
			} catch(ClassNotFoundException e) {
				System.err.println("Error: class not found");
			}
		} 
	}
}
