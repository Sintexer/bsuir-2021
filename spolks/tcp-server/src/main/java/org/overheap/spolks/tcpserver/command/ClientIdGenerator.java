package org.overheap.spolks.tcpserver.command;

import java.util.concurrent.atomic.AtomicInteger;

public class ClientIdGenerator {
    private static final ClientIdGenerator INSTANCE = new ClientIdGenerator();
    private AtomicInteger nextId = new AtomicInteger();

    private ClientIdGenerator() {
    }

    public static ClientIdGenerator getInstance() {
        return INSTANCE;
    }

    public int next(){
        return nextId.incrementAndGet();
    }
}
