package org.overheap.spolks.tcpclient.command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandStorage {
    private static final CommandStorage INSTANCE = new CommandStorage();
    private final Map<String, Supplier<? extends ClientCommand>> commands;

    private CommandStorage() {
        commands = new HashMap<>();
        addCommand("download", DownloadFileCommand::new);
    }

    public static CommandStorage getInstance() {
        return INSTANCE;
    }

    public ClientCommand getCommand(String command) {
        return commands.getOrDefault(command, EmptyCommand::new).get();
    }

    private void addCommand(String command, Supplier<? extends ClientCommand> supplier) {
        commands.put(command, supplier);
    }
}
