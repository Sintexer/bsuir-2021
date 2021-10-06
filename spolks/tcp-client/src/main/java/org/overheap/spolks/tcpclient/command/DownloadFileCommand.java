package org.overheap.spolks.tcpclient.command;

import java.io.*;

import static org.overheap.spolks.tcpclient.Constants.*;
import static org.overheap.spolks.tcpclient.Utils.log;

public class DownloadFileCommand extends FilesBaseCommand {
    @Override
    public void execute(DataInputStream in, DataOutputStream out, String message) throws IOException {
        //TODO second arg - downloaded file rename to

        String serverResponse = in.readUTF();
        if (FILE_NOT_FOUND.equals(serverResponse)) {
            log("#File not found");
            return;
        } else if (!OK.equals(serverResponse)) {
            log("#An error occurred: " + serverResponse);
            return;
        }
        String filename;
        if(message != null) {
            String[] args = message.split(" ");
            filename = args[1].substring(1, args[1].length() - 1);
        } else {
            filename = in.readUTF();
        }

        File downloadedFile = new File(RESOURCES_FOLDER + "/" + filename);

        int startFrom = in.readInt();
        if (startFrom == 0) {
            if (downloadedFile.delete()) {
                downloadedFile.createNewFile();
            }
            out.writeUTF(CONTINUE);
        } else if (!downloadedFile.exists()) {
            log("Can't resume file download because file isn't present");
            out.writeUTF(BREAK);
            return;
        } else {
            out.writeUTF(CONTINUE);
        }

        getResourcesFolder();
        try (OutputStream fileOut = new BufferedOutputStream(new FileOutputStream(downloadedFile, startFrom != 0))) {
            out.writeInt(SEGMENT_SIZE);
            int segmentsToDownload = in.readInt();
            for (int i = 0; i < segmentsToDownload; ++i) {
                byte[] buffer = new byte[SEGMENT_SIZE];
                int bytesRead = in.read(buffer);
                fileOut.write(buffer, 0, bytesRead);
            }
        }
        int bitrate = in.readInt();
        log("#Download bitrate = " + bitrate + " B/s");
    }
}
