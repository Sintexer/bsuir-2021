package org.overheap.spolks.tcpserver.command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandStorage {
    private static final CommandStorage INSTANCE = new CommandStorage();
    private final Map<String, Supplier<? extends ServerCommand>> commands;

    private CommandStorage() {
        commands = new HashMap<>();
        commands.put("echo", EchoCommand::new);
        commands.put("close", CloseConnectionCommand::new);
        commands.put("shutdown", ShutDownCommand::new);
        commands.put("time", TimeCommand::new);
    }

    public static CommandStorage getInstance() {
        return INSTANCE;
    }

    public ServerCommand getCommand(String command) {
        return commands.getOrDefault(command, EmptyCommand::new).get();
    }
}
