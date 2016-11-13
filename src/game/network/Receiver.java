package game.network;

import game.components.Action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receiver extends Thread {
    private int port;
    private ReceiveStrategy receiveStrategy;
    public Receiver(int port, ReceiveStrategy receiveStrategy) {
        this.port = port;
        this.receiveStrategy = receiveStrategy;
    }

    public void run() {
        try {
            while (true) {
                DatagramSocket socket = new DatagramSocket(port);
                System.out.println("Waiting for time requests...");

                byte buffer[] = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getData());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Action receivedAction = (Action)objectInputStream.readObject();
                socket.close();

                receiveStrategy.receive(receivedAction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
