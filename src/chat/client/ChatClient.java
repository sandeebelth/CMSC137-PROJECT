package chat.client;

import chat.Message;
import chat.Receiver;
import chat.Sender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements chat.ReceiveStrategy {
	private String username;
	private int key;
    private InetSocketAddress serverAddress;
    private InetSocketAddress userAddress;
    private Sender sender;
    private Receiver receiver;
    private NewUserListener newUserListener;
    private boolean connected = false;

	public ChatClient(InetSocketAddress serverAddress, int clientPort, NewUserListener<String> newUserListener)
            throws ClassNotFoundException, IOException {
        this.serverAddress = serverAddress;
        receiver = new Receiver(clientPort, this);
        this.userAddress = new InetSocketAddress(receiver.getInetAddress(), clientPort);
        this.sender = new Sender(serverAddress.getHostName(), serverAddress.getPort());
        this.newUserListener = newUserListener;
        receiver.start();
    }

	public boolean connectToServer(String name)
            throws IOException, ClassNotFoundException {
        //TODO Handle duplicate name problem
		Message message = new Message(userAddress.getHostString(), name, "#ADD", userAddress.getPort());
		
		Socket client = new Socket(serverAddress.getAddress(), serverAddress.getPort());

		/* Send data to the ServerSocket */
		ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
		oos.writeObject(message);

		Message reply = (Message)ois.readObject();

		oos.close();
		ois.close();
		client.close();

		if (!reply.getTextMessage().equals("Successfully added to users")) {
			return false;
		}

		username = name;
		key = reply.getKey();
        connected = true;
		return true;
	}

	public void logout() throws IOException, ClassNotFoundException {
		Message message = new Message("Logging out", username, "#LOGOUT", key);
		Socket client = new Socket(serverAddress.getAddress(), serverAddress.getPort()); 

		/* Send data to the ServerSocket */
		ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
		oos.writeObject(message);

		Message reply = (Message)ois.readObject();

		oos.close();
		ois.close();
		client.close();

        receiver.close();
	}

    public void sendMessage(String toName, String text) throws IOException, ClassNotFoundException {
        sender.send(new Message(text, username, toName, key));
        receiver.close();
    }

    public boolean isConnected() {
        return connected;
    }

    public String getUserList() {
        Message message = new Message("", username, "#USERLIST", key);

        Socket client = null;
        try {
            client = new Socket(serverAddress.getAddress(), serverAddress.getPort());
            /* Send data to the ServerSocket */
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            oos.writeObject(message);

            Message reply = (Message)ois.readObject();

            oos.close();
            ois.close();
            client.close();
            return reply.getTextMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";

    }

    public int getKey() {
        return key;
    }

    public String getUsername() {
        return username;
    }
    public Message receive(Message message) {
        if (message.getFromName().equals("System")) {
            if (message.getTextMessage().contains("has joined") && newUserListener != null) {
                newUserListener.newUser(message.getToName());
            }
        }
        System.out.println(message.getFromName() + ":" + message.getTextMessage());
        return null;
    }

    public static void main(String [] args) {
		try {
			String serverName = args[0]; //get IP address of server from first param
			int port = Integer.parseInt(args[1]); //get port from second param
			int port2 = Integer.parseInt(args[2]); //get port from second param
            ChatClient chatClient = new ChatClient(new InetSocketAddress(serverName, port), port2, null);

            System.out.println("Enter Name: ");
			Scanner stdin = new Scanner(System.in);
            String name = stdin.nextLine();

			if (chatClient.connectToServer(name)) {
				System.out.print("> ");
				String text;
				while(stdin.hasNextLine()) {
					text = stdin.nextLine();
                    chatClient.sendMessage("#ALL", text);

					System.out.print("> ");
				}


				chatClient.logout();
			}


		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Cannot find GameServer");
		}catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
		} catch(ClassNotFoundException e) {
			System.out.println("Cant find class");
		}
		System.out.println("bye");
	}
}
