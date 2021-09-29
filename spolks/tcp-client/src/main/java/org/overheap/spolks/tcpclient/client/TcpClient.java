package org.overheap.spolks.tcpclient.client;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.overheap.spolks.tcpclient.Utils.log;

public class TcpClient {
    public static final String EXIT_COMMAND = "exit";
    public static final String SHUTDOWN_COMMAND = "shutdown";

    public void connect(String ip, String port) {
        log("#Trying connect to server...");
        boolean exit = false;
        int minWaitTime = 20;
        int maxWaitTime = 3_000;
        int waitTime = minWaitTime;
        int attempts = 1;
        do {
            try (Socket socket = new Socket(ip, Integer.parseInt(port));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 DataInputStream in = new DataInputStream(socket.getInputStream())) {
                log("#Connection opened with server " + ip + ":" + port + " after " + attempts + " attempts");
                run(reader, out, in);
                exit = true;
                log("#Closing connections & channels on client side");
            } catch (IOException e) {
                System.out.println("Connection failed");
                sleep(waitTime);
                if (waitTime < maxWaitTime) {
                    waitTime *= 1.5;
                    waitTime = Math.min(waitTime, maxWaitTime);
                    ++attempts;
                } else {
                    log("#Connection failed after " + attempts + " attempts");
                    exit = true;
                }
            }
        } while(!exit);
    }

    public void run(BufferedReader reader, DataOutputStream out, DataInputStream in) {
        boolean exit = false;
        try {
            while (!exit) {
                log("#Enter command:");
                String clientCommand = reader.readLine();
                out.writeUTF(clientCommand);
                out.flush();
                System.out.println("Client sent message: " + clientCommand);
                readServerMessage(in);

                if (EXIT_COMMAND.equalsIgnoreCase(clientCommand) || SHUTDOWN_COMMAND.equalsIgnoreCase(clientCommand)) {
                    System.out.println("#Closing connection");
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sleep(int sleepTime){
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void readServerMessage(DataInputStream in) throws IOException {
        String serverMessage = in.readUTF();
        log("#Server response:\n" + serverMessage);
    }
}
