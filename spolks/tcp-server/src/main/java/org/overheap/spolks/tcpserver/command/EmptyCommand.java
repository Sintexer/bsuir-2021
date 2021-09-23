package org.overheap.spolks.tcpserver.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EmptyCommand implements ServerCommand{
    @Override
    public void execute(String args, DataInputStream in, DataOutputStream out) throws IOException {
        out.writeUTF("Can't recognize command");
        out.flush();
    }
}
