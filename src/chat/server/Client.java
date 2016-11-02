package chat.server;

import java.net.InetSocketAddress;

public class Client {
	private String name;
	private int key;
	private InetSocketAddress receiverAddress;

	public Client(String name, int key, InetSocketAddress receiverAddress) {
		this.name = name;
		this.key = key;
		this.receiverAddress = receiverAddress;
	}

	public String getName() {
		return this.name;
	}

	public int getKey() {
		return this.key;
	}

	public InetSocketAddress getReceiverAddress() {
		return this.receiverAddress;
	}
}
