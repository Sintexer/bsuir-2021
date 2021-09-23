package org.overheap.spolks.tcpserver;

import org.overheap.spolks.tcpserver.server.MonoThreadServer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Server started");
        new MonoThreadServer().run();
    }
}
