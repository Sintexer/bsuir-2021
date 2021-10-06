package org.overheap.spolks.tcpclient.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.overheap.spolks.tcpclient.Utils.log;

public class EmptyCommand implements ClientCommand {
    @Override
    public void execute(DataInputStream in, DataOutputStream out, String message) throws IOException {
        String serverMessage = in.readUTF();
        log("#Server response:\n" + serverMessage);
    }
}
