package org.overheap.spolks.tcpserver.command;

import java.util.concurrent.ConcurrentHashMap;

public class DownloadDetailsHolder {
    private static final DownloadDetailsHolder INSTANCE = new DownloadDetailsHolder();

    private final ConcurrentHashMap<Integer, DownloadDetails> details;

    private DownloadDetailsHolder(){
        details = new ConcurrentHashMap<>();
    }

    public static DownloadDetailsHolder getInstance() {
        return INSTANCE;
    }

    public DownloadDetails getDetails(int clientId){
        return details.get(clientId);
    }

    public DownloadDetails getDetails(int clientId, String filename, DownloadDirection direction){
        return details.compute(clientId, (key, v) -> new DownloadDetails(filename, direction));
    }

    public void removeDetails(int clientId){
        details.remove(clientId);
    }
}
