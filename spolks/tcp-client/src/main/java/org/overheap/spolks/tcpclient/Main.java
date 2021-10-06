package org.overheap.spolks.tcpclient;

import org.overheap.spolks.tcpclient.client.TcpClient;

import static org.overheap.spolks.tcpclient.Utils.log;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2 || args.length > 3) {
            log("!Contract: tcpClient <ip> <port> [clientId]");
        } else {
            try {
                int clientId = args.length == 3 ? Integer.parseInt(args[2]) : 0;
                new TcpClient().connect(args[0], args[1], clientId);
            } catch (IllegalArgumentException e) {
                log(e.getMessage());
            }
            log("#Exiting client");
        }
    }
}
