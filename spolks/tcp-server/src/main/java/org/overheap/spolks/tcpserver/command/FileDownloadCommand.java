package org.overheap.spolks.tcpserver.command;

import java.io.*;
import java.util.Date;

import static org.overheap.spolks.tcpserver.Constants.*;

public class FileDownloadCommand extends FilesBaseCommand {
    @Override
    public void execute(String filename, DataInputStream in, DataOutputStream out, int clientId) throws IOException {
        int firstQuotes = filename.indexOf('"');
        if (firstQuotes < 0 || (filename.length() - 1 != filename.indexOf('"', firstQuotes + 1))) {
            out.writeUTF("Illegal filename parameter");
            throw new IllegalArgumentException("Illegal filename passed. Pass filename in double quotes");
        }
        File file = new File(RESOURCES_FOLDER + "/" + filename.substring(1, filename.length() - 1));
        if (!file.exists()) {
            out.writeUTF(FILE_NOT_FOUND);
            throw new IllegalArgumentException("File with name " + filename + " wasn't found");
        }
        out.writeUTF(OK);

        DownloadDetails downloadDetails = DownloadDetailsHolder.getInstance().getDetails(clientId);
        if(downloadDetails == null){
            downloadDetails = DownloadDetailsHolder.getInstance().getDetails(clientId, filename, DownloadDirection.DOWNLOAD);
        } else {
            out.writeUTF(downloadDetails.getFileName().substring(1, downloadDetails.getFileName().length()-1));
        }

        int startFrom = downloadDetails.getBytesTransferred();
        out.writeInt(startFrom);

        String clientMessage = in.readUTF();
        boolean isContinueDownload = CONTINUE.equals(clientMessage);
        if(!isContinueDownload) {
            DownloadDetailsHolder.getInstance().removeDetails(clientId);
            return;
        }

        int segmentSize = in.readInt();
        int fileSize = (int) file.length();
        int segmentsAmount = (int) (((double) fileSize - downloadDetails.getBytesTransferred()) / segmentSize);
        out.writeInt(segmentsAmount);

        InputStream fileInput = new BufferedInputStream(new FileInputStream(file));
        int offset = startFrom;
        fileInput.skip(offset);
        downloadDetails.setStartAt((int)new Date().getTime());
        byte[] buffer = new byte[segmentSize];
        for (int i = 0; i < segmentsAmount; ++i) {
            int bytesRead = fileInput.read(buffer, 0, segmentSize);
            out.write(buffer, 0, bytesRead);
            downloadDetails.setBytesTransferred(downloadDetails.getBytesTransferred() + bytesRead);
        }
        int bitrate =(downloadDetails.getBytesTransferred()-startFrom)/((int)new Date().getTime()-downloadDetails.getStartAt());
        System.out.println("()Bitrate = " + bitrate);
        out.writeInt(bitrate);
        DownloadDetailsHolder.getInstance().removeDetails(clientId);
    }
}
