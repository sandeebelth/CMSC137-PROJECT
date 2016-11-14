package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Receiver extends Thread {
	private ServerSocket serverSocket;
	private ReceiveStrategy receive;
	private boolean isOpen = true;
	
	public Receiver(int port, ReceiveStrategy receive) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(0);
		this.receive = receive;
	}

	public void run() {
		//System.out.println("Receiver Start!");
		while(true) {
			Socket server;
			try {
				server = serverSocket.accept();
				//System.out.println("Receiver Accept!");

				/* Read data from the ClientSocket */
				ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
				Message sendMessage;
				//System.out.println("Receiver Message Wait!");
				Message receivedMessage = (Message)ois.readObject();
				receivedMessage.setAddress(server.getRemoteSocketAddress().toString().split(":")[0].substring(1));
				sendMessage = receive.receive(receivedMessage);
				//System.out.println("Received Message!");
				if(sendMessage != null) {
					//System.out.println("Receiver Message!");

					oos.writeObject(sendMessage);
				}
				oos.close();
				ois.close();
				
				server.close();
			}catch(SocketTimeoutException s) {
				System.err.println("Socket timed out!");
				break;
			}catch(IOException e) {
				if (isOpen) {
					e.printStackTrace();
					System.out.println("Usage: java GreetingServer <port no.>");
				}
				break;
			} catch(ClassNotFoundException e) {
				System.err.println("Error: class not found");
			}
			//System.out.println("Looping!!!");
		} 
		//System.out.println("Exiting!!!");
	}

	public void close() throws IOException {
		serverSocket.close();
		isOpen = false;
	}

	public InetAddress getInetAddress() {
		return serverSocket.getInetAddress();
	}
}
