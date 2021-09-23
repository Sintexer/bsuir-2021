package org.overheap.spolks.tcpclient.client;

import java.io.*;
import java.net.Socket;

public class TcpClient implements Runnable {
    public static final String CLOSE_COMMAND = "close";
    private final int PORT = 911;

    @Override
    public void run() {
        boolean exit = false;
        int minWaitTime = 500;
        int maxWaitTime = 10_000;
        int waitTime = minWaitTime;
        do {
            System.out.println(">Trying connect to server...");
            try (Socket socket = new Socket("localhost", PORT);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 DataInputStream in = new DataInputStream(socket.getInputStream())) {

                System.out.println("Client connected to socket.\n");
                while (!socket.isOutputShutdown()) {
                    System.out.println(">Enter command:");
                    String clientCommand = reader.readLine();
                    out.writeUTF(clientCommand);
                    out.flush();
                    System.out.println("Client sent message: " + clientCommand);
                    readServerMessage(in);

                    if (CLOSE_COMMAND.equalsIgnoreCase(clientCommand)) {
                        System.out.println("#Closing connection");
                        exit = true;
                        break;
                    }
                }
                System.out.println("Closing connections & channels on client side");
            } catch (IOException e) {
                System.out.println("Connection failed");
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException ex) {
                }
                if (waitTime < maxWaitTime) {
                    waitTime *= 1.5;
                    waitTime = Math.min(waitTime, maxWaitTime);
                }
            }
        } while (!exit);
    }

    private void readServerMessage(DataInputStream in) throws IOException {
        String serverMessage = in.readUTF();
        System.out.println(">Server response: " + serverMessage);
    }
}
