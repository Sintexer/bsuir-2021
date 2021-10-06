package org.overheap.spolks.tcpserver.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public interface ServerCommand {
    void execute(String args, DataInputStream in, DataOutputStream out, int clientId) throws IOException;
}
