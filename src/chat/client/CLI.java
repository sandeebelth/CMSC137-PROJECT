package chat.client;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import chat.Message;
import chat.Receiver;
import chat.Sender;
import chat.server.Client;

public class CLI {
	private static String username;
	private static int key;
	private static boolean connectToServer(InetSocketAddress serverAddress, InetSocketAddress userAddress) throws IOException, ClassNotFoundException {
		System.out.println(serverAddress);
		Scanner stdin = new Scanner(System.in);
		System.out.println("Enter name: ");
		String name = stdin.nextLine();

		Message message = new Message(userAddress.getHostString(), name, "#ADD", userAddress.getPort());
		
		Socket client = new Socket(serverAddress.getAddress(), serverAddress.getPort()); 

		/* Send data to the ServerSocket */
		ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
		oos.writeObject(message);

		Message reply = (Message)ois.readObject();
		System.out.println("'" + reply.getTextMessage() + "'");

		
		System.out.println("bye");
		oos.close();
		ois.close();
		client.close();

		if (!reply.getTextMessage().equals("Successfully added to users")) {
			return false;
		}

		username = name;
		key = reply.getKey();
		return true;
	}

	private static void logout(InetSocketAddress serverAddress) throws IOException, ClassNotFoundException {
		Message message = new Message("Logging out", username, "#LOGOUT", key);
		Socket client = new Socket(serverAddress.getAddress(), serverAddress.getPort()); 

		/* Send data to the ServerSocket */
		ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
		oos.writeObject(message);

		System.out.println("hi");
		Message reply = (Message)ois.readObject();
		System.out.println(reply.getTextMessage());
		
		oos.close();
		ois.close();
		client.close();
	}
	public static void main(String [] args) {
		try {
			String serverName = args[0]; //get IP address of server from first param
			int port = Integer.parseInt(args[1]); //get port from second param
			int port2 = Integer.parseInt(args[2]); //get port from second param
			Receiver receiver = new Receiver(port2, new PrintReceive());
			receiver.start();
			Sender sender = new Sender(serverName, port);

			Scanner stdin = new Scanner(System.in);

			if (connectToServer(new InetSocketAddress(serverName, port), new InetSocketAddress(receiver.getInetAddress(), port2))) {
				System.out.print("System: enter your message\n" + username + ": ");
				String text;
				Message message;
				while(stdin.hasNextLine()) {
					text = stdin.nextLine();
					message = new Message(text, username, "#ALL", key);
					sender.send(message);

					System.out.print("You: ");
				}


				logout(new InetSocketAddress(serverName, port));
			}
			receiver.close();


		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Cannot find Server");
		}catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
		} catch(ClassNotFoundException e) {
			System.out.println("Cant find class");
		}
		System.out.println("bye");
		return;
	}
}
