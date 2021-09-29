package org.overheap.spolks.tcpclient;

import org.overheap.spolks.tcpclient.command.ConnectCommand;

import java.util.Arrays;

import static org.overheap.spolks.tcpclient.Utils.log;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            log("!Contract: tcpClient <ip> <port>");
        } else {
            try {
                ConnectCommand command = new ConnectCommand(Arrays.asList(args));
                command.run();
            } catch (IllegalArgumentException e) {
                log(e.getMessage());
            }
            log("#Exiting client");
        }
    }
}
