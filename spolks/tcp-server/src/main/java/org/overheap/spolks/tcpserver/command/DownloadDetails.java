package org.overheap.spolks.tcpserver.command;

public class DownloadDetails {
    private final String fileName;
    private final DownloadDirection direction;
    private int startAt;
    private int offset;
    private int bytesTransferred;

    public DownloadDetails(String fileName, int startAt, DownloadDirection direction) {
        this.fileName = fileName;
        this.startAt = startAt;
        this.direction = direction;
    }

    public DownloadDetails(String fileName, DownloadDirection direction) {
        this(fileName, 0, direction);
    }

    public String getFileName() {
        return fileName;
    }

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public DownloadDirection getDirection() {
        return direction;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setBytesTransferred(int bytesTransferred) {
        this.bytesTransferred = bytesTransferred;
    }

    public int getOffset() {
        return offset;
    }

    public int getBytesTransferred() {
        return bytesTransferred;
    }
}
