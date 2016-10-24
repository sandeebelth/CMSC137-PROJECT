   // File Name GreetingServer.java

import java.net.*;
import java.io.*;

public class GreetingServer extends Thread
{
   private ServerSocket serverSocket;
   
   public GreetingServer(int port) throws IOException
   {
      //insert missing line here for binding a port to a socket
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(60000);
   }

   public void run()
   {
      boolean connected = true;
      while(connected)
      {
         try
         {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            
            /* Start accepting data from the ServerSocket */
            //insert missing line for accepting connection from client here]
            Socket server = serverSocket.accept();

            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            
            //******THREAD****
            /* Read data from the ClientSocket */
            DataInputStream in = new DataInputStream(server.getInputStream());
            System.out.println(in.readUTF());
            
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            //****************

            /* Send data to the ClientSocket */
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();
            
            //connected = false;
            System.out.println("Server ended the connection to "+ server.getRemoteSocketAddress());
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            //e.printStackTrace();
            System.out.println("Usage: java GreetingServer <port no.>");
            break;
         }
      } 
   }
   public static void main(String [] args)
   {
      
      try
      {
         int port = Integer.parseInt(args[0]);
         Thread t = new GreetingServer(port);
         t.start();
      }catch(IOException e)
      {
         //e.printStackTrace();
         System.out.println("Usage: java GreetingServer <port no.>");
      }catch(ArrayIndexOutOfBoundsException e)
      {
         System.out.println("Usage: java GreetingServer <port no.> ");
      }
   }
}
/**
a) Socket server = serverSocket.accept();
b) serverSocket = new ServerSocket(port);
**/