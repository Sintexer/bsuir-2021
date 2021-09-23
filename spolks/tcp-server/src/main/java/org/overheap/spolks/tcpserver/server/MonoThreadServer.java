package org.overheap.spolks.tcpserver.server;

import org.overheap.spolks.tcpserver.command.CommandStorage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MonoThreadServer implements TcpServer {

    private static final String CLOSE_COMMAND = "close";
    private static final String SHUTDOWN_COMMAND = "shutdown";
    private boolean shutdownServer = false;

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            do {
                System.out.println(">Waiting for client...");
                try (Socket client = serverSocket.accept();
                     DataInputStream in = new DataInputStream(client.getInputStream());
                     DataOutputStream out = new DataOutputStream(client.getOutputStream())) {
                    System.out.println(">Connected to client");
                    boolean closeConnection;
                    do {
                        closeConnection = processCommand(in.readUTF(), in, out);
                    } while (!closeConnection);
                } catch (SocketException e) {
                    System.out.println("#Exception occurred: " + e.getClass().getSimpleName());
                } finally {
                    System.out.println(">Client broke connection");
                }
            } while (!shutdownServer);
            System.out.println("#Shutting down server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses string command and calls related action.
     *
     * @param message the message String
     * @param in      data input stream
     * @param out     data output stream
     * @return boolean closeConnection
     */
    private boolean processCommand(String message, DataInputStream in, DataOutputStream out) throws IOException {
        String command = message;
        String args = "";
        if (message.contains(" ") && message.split(" ").length > 1) {
            command = message.split(" ")[0];
            args = message.substring(message.indexOf(" ") + 1);
        }
        System.out.println(">Client message: " + message);
        System.out.println(">Parsed command: " + command);
        shutdownServer = SHUTDOWN_COMMAND.equalsIgnoreCase(command);
        boolean closeConnection = CLOSE_COMMAND.equalsIgnoreCase(command) || shutdownServer;
        CommandStorage.getInstance().getCommand(command).execute(args, in, out);
        return closeConnection;
    }

}
