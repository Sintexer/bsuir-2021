package org.overheap.spolks.tcpserver.server;

import org.overheap.spolks.tcpserver.command.ClientIdGenerator;
import org.overheap.spolks.tcpserver.command.CommandStorage;
import org.overheap.spolks.tcpserver.command.DownloadDetails;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MonoThreadServer implements TcpServer {

    private static final String CLOSE_COMMAND = "close";
    private static final String SHUTDOWN_COMMAND = "shutdown";
    private boolean shutdownServer = false;

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            do {
                System.out.println("#Waiting for client...");
                try (Socket client = serverSocket.accept();
                     DataInputStream in = new DataInputStream(client.getInputStream());
                     DataOutputStream out = new DataOutputStream(client.getOutputStream())) {
                    System.out.println("#Connected to client");
                    Integer clientId = receiveClientId(in);
                    if (clientId == null) {
                        clientId = ClientIdGenerator.getInstance().next();
                        out.writeInt(clientId);
                    }
                    System.out.println("Client id is " + clientId);
                    resolvePendingDownload(in, out, clientId);
                    boolean closeConnection;
                    do {
                        closeConnection = processCommand(in.readUTF(), in, out, clientId);
                    } while (!closeConnection);
                } catch (IOException e) {
                    System.out.println("#Exception occurred: " + e.getClass().getSimpleName());
                } finally {
                    System.out.println("#Client broke connection");
                }
            } while (!shutdownServer);
            System.out.println("#Shutting down server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer receiveClientId(DataInputStream in) throws IOException {
        System.out.println("#Reading clientId");
        int clientId = in.readInt();
        return clientId == 0 ? null : clientId;
    }

    private void resolvePendingDownload(DataInputStream in, DataOutputStream out, int clientId) throws IOException {
        DownloadDetails downloadDetails = org.overheap.spolks.tcpserver.command.DownloadDetailsHolder.getInstance().getDetails(clientId);
        if (downloadDetails != null) {
            switch (downloadDetails.getDirection()) {
                case DOWNLOAD -> {
                    out.writeUTF("continue_download");
                    CommandStorage.getInstance().getCommand("download").execute(downloadDetails.getFileName(), in, out, clientId);
                }
                case UPLOAD -> {
                }
                default -> out.writeUTF("");
            }
        } else {
            out.writeUTF("");
        }
    }

    /**
     * Parses string command and calls related action.
     *
     * @param message  the message String
     * @param in       data input stream
     * @param out      data output stream
     * @param clientId client id
     * @return boolean closeConnection
     */
    private boolean processCommand(String message, DataInputStream in, DataOutputStream out, int clientId) throws IOException {
        String command = message;
        String args = "";
        if (message.contains(" ") && message.split(" ").length > 1) {
            command = message.split(" ")[0];
            args = message.substring(message.indexOf(" ") + 1);
        }
        System.out.println("#Client message: " + message);
        System.out.println("#Parsed command: " + command);
        shutdownServer = SHUTDOWN_COMMAND.equalsIgnoreCase(command);
        boolean closeConnection = CLOSE_COMMAND.equalsIgnoreCase(command) || shutdownServer;
        try {
            CommandStorage.getInstance().getCommand(command).execute(args, in, out, clientId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return closeConnection;
    }

}
