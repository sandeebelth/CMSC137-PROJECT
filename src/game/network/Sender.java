package game.network;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import game.components.Action;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

public class Sender {
    private InetSocketAddress serverAddress;

    public Sender(String serverName, int port) throws UnknownHostException {
        this(new InetSocketAddress(serverName, port));
    }

    public Sender(InetSocketAddress address) {
        serverAddress = address;
    }

    public void send(Action action) throws IOException, ClassNotFoundException {
        ByteOutputStream byteOutputStream = new ByteOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
        objectOutputStream.writeObject(action);
        byte[] message = byteOutputStream.getBytes();
        DatagramPacket packet = new DatagramPacket(message, message.length, serverAddress);
        DatagramSocket socket = new DatagramSocket();

        socket.send(packet);
    }
}
