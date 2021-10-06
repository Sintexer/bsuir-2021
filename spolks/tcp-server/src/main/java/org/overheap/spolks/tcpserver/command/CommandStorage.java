package org.overheap.spolks.tcpserver.command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandStorage {
    private static final CommandStorage INSTANCE = new CommandStorage();
    private final Map<String, Supplier<? extends ServerCommand>> commands;
    private final Map<String, String> commandContracts;

    private CommandStorage() {
        commands = new HashMap<>();
        commandContracts = new HashMap<>();
        addCommand("echo", EchoCommand::new, "<message> - Echoes message back");
        addCommand("close", CloseConnectionCommand::new, "- Closes connection");
        addCommand("shutdown", ShutDownCommand::new, "- Shuts down server");
        addCommand("time", TimeCommand::new, "- Shows time");
        addCommand("help", HelpCommand::new, "- Shows help");
        addCommand("files", ShowAvailableFilesCommand::new, "- Shows available files");
        addCommand("download", FileDownloadCommand::new, "<filename in quotes> - Downloads file from server");
    }

    public static CommandStorage getInstance() {
        return INSTANCE;
    }

    public ServerCommand getCommand(String command) {
        return commands.getOrDefault(command, EmptyCommand::new).get();
    }

    public Map<String, String> getCommandContracts() {
        return new HashMap<>(commandContracts);
    }

    private void addCommand(String command, Supplier<? extends ServerCommand> supplier, String contract) {
        commands.put(command, supplier);
        commandContracts.put(command, contract);
    }
}
