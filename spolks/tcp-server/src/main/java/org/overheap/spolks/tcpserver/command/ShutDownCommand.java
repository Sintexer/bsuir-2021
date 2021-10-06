package org.overheap.spolks.tcpserver.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShutDownCommand implements ServerCommand{
    @Override
    public void execute(String args, DataInputStream in, DataOutputStream out, int clientId) throws IOException {
        out.writeUTF("Shutting down server...");
        out.flush();
    }
}
