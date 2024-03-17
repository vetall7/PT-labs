package org.example;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Connection established (client " + clientSocket.getInetAddress() + ", " + clientSocket.getPort() + ")");
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            out.writeObject("Ready");
            int n = (Integer) in.readObject();
            out.writeObject("Ready for messages");
            for (int i = 0; i < n; i++) {
                Message message = (Message) in.readObject();
                System.out.println("Message received from client " + clientSocket.getInetAddress() + ", " + clientSocket.getPort() + ": " + message.getContent());
            }
            out.writeObject("Finished");
            System.out.println("Closing connection (client " + clientSocket.getInetAddress() + ", " + clientSocket.getPort() + ")");
            try{
                in.close();
                out.close();
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
