package org.overheap.spolks.tcpclient.command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FilesBaseCommand implements ClientCommand {
    public static final String RESOURCES_FOLDER = "resources";
    public static final int SEGMENT_SIZE = 2048;

    protected File getResourcesFolder() throws IOException {
        if(!Files.exists(Path.of(RESOURCES_FOLDER))) {
            Files.createDirectory(Path.of(RESOURCES_FOLDER));
        }
        return new File(RESOURCES_FOLDER);
    }
}
