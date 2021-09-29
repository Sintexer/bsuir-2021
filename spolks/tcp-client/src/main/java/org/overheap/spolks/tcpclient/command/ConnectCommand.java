package org.overheap.spolks.tcpclient.command;

import org.overheap.spolks.tcpclient.client.TcpClient;

import java.util.List;

public class ConnectCommand{
    private final List<String> args;

    public ConnectCommand(List<String> args) {
        this.args = args;
    }

    public void run() {
        new TcpClient().connect(getArgs().get(0), getArgs().get(1));
    }

    public List<String> getArgs() {
        return args;
    }
}
