package chat;

import java.io.Serializable;

public class Message implements Serializable {
	private String textMessage;
	private String fromName;
	private String toName;
	private String address;
	private int key;

	public Message(String textMessage, String fromName, String toName, int key) {
		this.textMessage = textMessage;
		this.fromName = fromName;
		this.toName = toName;
		this.key = key;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public String getFromName() {
		return fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public int getKey() {
		return key;
	}
}
