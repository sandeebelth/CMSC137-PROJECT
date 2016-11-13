package game.network;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import game.components.Action;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class Broadcaster {
    private ArrayList<InetSocketAddress> chatters;

    public Broadcaster() {
        chatters = new ArrayList<>();
    }

    public Broadcaster(ArrayList<InetSocketAddress> userList) {
        this.chatters = userList;
    }

    public void broadcast(Action action) throws IOException, ClassNotFoundException {
        broadcast(this.chatters, action);
    }

    public static void broadcast(ArrayList<InetSocketAddress> chatters, Action action) throws IOException, ClassNotFoundException {
        ByteOutputStream byteOutputStream = new ByteOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
        objectOutputStream.writeObject(action);
        byte[] message = byteOutputStream.getBytes();

        for(InetSocketAddress address: chatters) {
            DatagramPacket packet = new DatagramPacket(message, message.length, address);
            DatagramSocket socket = new DatagramSocket();

            socket.send(packet);
        }
    }
}
