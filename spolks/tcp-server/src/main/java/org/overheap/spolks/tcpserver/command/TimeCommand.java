package org.overheap.spolks.tcpserver.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class TimeCommand implements ServerCommand{
    @Override
    public void execute(String args, DataInputStream in, DataOutputStream out, int clientId) throws IOException {
        out.writeUTF(LocalDateTime.now().toString());
        out.flush();
    }
}
