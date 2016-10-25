package chat.client;

import chat.Message;
import chat.ReceiveStrategy;

public class PrintReceive implements ReceiveStrategy {
	public Message receive(Message message) {
		System.out.println(message.getFromName() + ": " + message.getTextMessage());
		return null;
	}
}
