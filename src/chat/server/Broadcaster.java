package chat.server;

import chat.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Broadcaster {
	private ArrayList<InetSocketAddress> userList;

	public Broadcaster() {
		userList = new ArrayList<>();
	}

	public void broadcast(Message message) throws IOException, ClassNotFoundException {
		broadcast(this.userList, message);
	}

	public void addAddress(InetSocketAddress address) {
		userList.add(address);
	}

	public void removeAddress(InetSocketAddress address) {
		userList.remove(address);
	}

	public ArrayList<InetSocketAddress> getUserList() {
        return userList;
    }

	public static void broadcast(List<InetSocketAddress> chatters, Message message) throws IOException, ClassNotFoundException {
		for(InetSocketAddress address: chatters) {
			Socket client = new Socket(address.getAddress(), address.getPort()); 
			
			/* Send data to the ServerSocket */
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
			oos.writeObject(message);
			oos.close();
			ois.close();
			
			client.close();
		}
	}
}
