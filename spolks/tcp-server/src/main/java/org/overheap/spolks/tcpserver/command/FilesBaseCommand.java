package org.overheap.spolks.tcpserver.command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FilesBaseCommand implements ServerCommand {

    public static final String RESOURCES_FOLDER = "resources";

    protected File getResourcesFolder() throws IOException {
        if(!Files.exists(Path.of(RESOURCES_FOLDER))) {
            Files.createDirectory(Path.of(RESOURCES_FOLDER));
        }
        return new File(RESOURCES_FOLDER);
    }
}
