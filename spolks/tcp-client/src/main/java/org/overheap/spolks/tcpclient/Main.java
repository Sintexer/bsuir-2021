package org.overheap.spolks.tcpclient;

import org.overheap.spolks.tcpclient.client.TcpClient;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new TcpClient().run();
    }
}
