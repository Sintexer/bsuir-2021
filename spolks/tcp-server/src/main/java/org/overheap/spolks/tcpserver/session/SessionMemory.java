package org.overheap.spolks.tcpserver.session;

import java.util.concurrent.atomic.AtomicInteger;

public class SessionMemory {

    private static final SessionMemory INSTANCE = new SessionMemory();

    private final AtomicInteger sessionCounter = new AtomicInteger(1);

    private SessionMemory() {
    }

    public int getSessionId(){
        return sessionCounter.getAndIncrement();
    }



}
