package org.overheap.spolks.tcpserver.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

public class HelpCommand implements ServerCommand {
    @Override
    public void execute(String args, DataInputStream in, DataOutputStream out) throws IOException {
        String helpMessage = CommandStorage.getInstance().getCommandContracts().entrySet().stream()
                .map(entry -> ">" + entry.getKey() + " " + entry.getValue())
                .collect(Collectors.joining("\n"));
        out.writeUTF(helpMessage);
        out.flush();
    }
}
