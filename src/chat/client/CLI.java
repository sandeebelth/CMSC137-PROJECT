package chat.client;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import chat.Message;
import chat.Receiver;
import chat.Sender;

public class CLI {
	public static void main(String [] args) {
		try {
			String serverName = args[0]; //get IP address of server from first param
			int port = Integer.parseInt(args[1]); //get port from second param
			Receiver receiver = new Receiver(port, new PrintReceive());
			Sender sender = new Sender(serverName, port+1);

			Scanner stdin = new Scanner(System.in);

			System.out.print("System: enter your message\nYou: ");
			String text;
			Message message;
			while(stdin.hasNextLine()) {
				text = stdin.nextLine();
				message = new Message(text, "local", "#ALL", 0);
				sender.send(message);

				System.out.print("You: ");
			}

		}catch(IOException e) {
			//e.printStackTrace();
			System.out.println("Cannot find Server");
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
		} catch(ClassNotFoundException e) {
			System.out.println("Cant find class");
		}
	}
}
