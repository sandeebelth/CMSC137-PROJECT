package chat.client;

import chat.Message;

public interface OutputStrategy {
	public void print(Message message);
}
