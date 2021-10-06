package org.overheap.spolks.tcpclient.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public interface ClientCommand {
    void execute(DataInputStream in, DataOutputStream out, String message) throws IOException;
}
