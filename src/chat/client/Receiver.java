package chat.client;

import java.net.*;
import java.io.*;

import chat.Message;

public class Receiver extends Thread {
	private ServerSocket serverSocket;
	private OutputStrategy out;
	
	public Receiver(int port, OutputStrategy out) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(0);
		this.out = out;
	}

	public void run() {
		while(true) {
			Socket server;
			try {
				server = serverSocket.accept();

				/* Read data from the ClientSocket */
				ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
				out.print((Message)ois.readObject());
				
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
