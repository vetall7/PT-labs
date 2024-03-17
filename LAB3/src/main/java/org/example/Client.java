package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 7777);

            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            Scanner scanner = new Scanner(System.in);

            System.out.println("Connected to server (client " + clientSocket.getLocalAddress() + ", " + clientSocket.getLocalPort() + ")");
            String response = (String) in.readObject();
            System.out.println("Server says: " + response);

            System.out.println("Enter the number of messages you wanna send: ");
            int n = scanner.nextInt();
            out.writeObject(n);

            response = (String) in.readObject();
            System.out.println("Server says: " + response);

            for (int i = 0; i < n; i++) {
                System.out.println("Enter message " + (i + 1) + ": ");
                String message = scanner.next();
                out.writeObject(new Message(i + 1, message));
            }

            response = (String) in.readObject();
            System.out.println("Server says: " + response);


            in.close();
            out.close();
            clientSocket.close();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
