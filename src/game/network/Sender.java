package game.network;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import game.components.Action;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Sender {
    private InetAddress serverAddress;
    private int serverPort;

    public Sender(String serverName, int port) throws UnknownHostException {
        serverAddress = InetAddress.getByName(serverName);
        serverPort = port;
    }

    public void send(Action action) throws IOException, ClassNotFoundException {
        ByteOutputStream byteOutputStream = new ByteOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
        objectOutputStream.writeObject(action);
        byte[] message = byteOutputStream.getBytes();
        DatagramPacket packet = new DatagramPacket(message, message.length, serverAddress, serverPort);
        //insert missing line here for creating new DatagramSocket to send the request to the server
        DatagramSocket socket = new DatagramSocket();

        socket.send(packet);
    }
}
