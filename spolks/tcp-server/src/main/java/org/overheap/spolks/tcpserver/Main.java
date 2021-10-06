package org.overheap.spolks.tcpserver;

import org.overheap.spolks.tcpserver.command.ShowAvailableFilesCommand;
import org.overheap.spolks.tcpserver.server.MonoThreadServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Server started");
        new MonoThreadServer().run();
    }
}
