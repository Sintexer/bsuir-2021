package org.overheap.spolks.tcpserver.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ShowAvailableFilesCommand extends FilesBaseCommand {
    @Override
    public void execute(String args, DataInputStream in, DataOutputStream out, int clientId) throws IOException {
        File resourcesFolder = getResourcesFolder();
        List<String> fileNames = Optional.ofNullable(resourcesFolder.list())
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
        String availableFiles = String.join(", ", fileNames);
        out.writeUTF("[" + availableFiles + "]");
        out.flush();
    }
}
