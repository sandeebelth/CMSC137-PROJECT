package chat.server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import chat.Sender;

public class CLI {
	/*
	public static void main(String [] args) {
		try {
			String serverName = args[0]; //get IP address of server from first param
			int port = Integer.parseInt(args[1]); //get port from second param
			Sender sender = new Sender(serverName, port);

			Scanner stdin = new Scanner(System.in);

			System.out.print("System: enter your message\nYou: ");
			String message;
			while(stdin.hasNextLine()) {

				message = stdin.nextLine();
				sender.send(message);

				System.out.print("You: ");
			}

		}catch(IOException e) {
			//e.printStackTrace();
			System.out.println("Cannot find Server");
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
		}
	}
	*/
}
