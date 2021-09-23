package org.overheap.spolks.tcpserver.command;

import java.io.*;
import java.net.Socket;

public class EchoCommand implements ServerCommand {
    @Override
    public void execute(String args, DataInputStream in, DataOutputStream out) {
        try {
            out.writeUTF(args);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
