package chat;

import chat.Message;

public interface ReceiveStrategy {
	public Message receive(Message message);
}
