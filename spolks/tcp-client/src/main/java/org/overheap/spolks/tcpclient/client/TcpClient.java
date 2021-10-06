package org.overheap.spolks.tcpclient.client;

import org.overheap.spolks.tcpclient.command.ClientCommand;
import org.overheap.spolks.tcpclient.command.CommandStorage;
import org.overheap.spolks.tcpclient.command.EmptyCommand;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.overheap.spolks.tcpclient.Utils.log;

public class TcpClient {
    public static final String EXIT_COMMAND = "exit";
    public static final String SHUTDOWN_COMMAND = "shutdown";

    public void connect(String ip, String port, int argsClientId) {
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
                int clientId = sendClientId(argsClientId, out, in);
                log("()ClientId is "+ clientId);
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

    private Integer sendClientId(int clientId, DataOutputStream out, DataInputStream in) throws IOException {
        out.writeInt(clientId);
        if(clientId == 0) {
            return in.readInt();
        }
        return clientId;
    }

    public void run(BufferedReader reader, DataOutputStream out, DataInputStream in) {
        boolean exit = false;
        try {
            processServerInstruction(in, out);
            while (!exit) {
                log("#Enter command:");
                String clientCommand = reader.readLine();
                out.writeUTF(clientCommand);
                out.flush();
                System.out.println("Client sent message: " + clientCommand);
                parseCommand(clientCommand).execute(in, out, clientCommand);
                if (EXIT_COMMAND.equalsIgnoreCase(clientCommand) || SHUTDOWN_COMMAND.equalsIgnoreCase(clientCommand)) {
                    System.out.println("#Closing connection");
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processServerInstruction(DataInputStream in, DataOutputStream out) throws IOException {
        String serverInstruction = in.readUTF();
        switch (serverInstruction) {
            case "continue_download" -> CommandStorage.getInstance().getCommand("download").execute(in, out, null);
            default -> {}
        }
    }

    private void sleep(int sleepTime){
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private ClientCommand parseCommand(String message) {
        String[] args = message.split(" ");
        if (args.length > 0) {
            return CommandStorage.getInstance().getCommand(args[0]);
        }
        return new EmptyCommand();
    }

    private void readServerMessage(DataInputStream in) throws IOException {
        String serverMessage = in.readUTF();
        log("#Server response:\n" + serverMessage);
    }
}
