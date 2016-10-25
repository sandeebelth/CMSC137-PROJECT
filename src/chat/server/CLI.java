package chat.server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import chat.Sender;
import chat.Receiver;

public class CLI {
	public static void main(String [] args) {
		try {
			int port = Integer.parseInt(args[0]); //get port from second param
			Thread t = new Receiver(port, new ClientReceive());
			t.start();

		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Cannot find Server");
		}catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("Usage: java command <port no.>");
		}
	}
}
