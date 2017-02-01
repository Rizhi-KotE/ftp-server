package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FtpSession {
    private int dataPort;
    private String dataHost;
    private String workingDirectory;
    private Connection controlConnection;

    private Connection dataConnection;
    private Thread currentTask;

    public FtpSession(Socket socket) throws IOException {
        controlConnection = new Connection(socket);
        dataPort = 0;
        dataHost = "";
        workingDirectory = "/";
    }

    public Thread getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Thread currentTask) {
        this.currentTask = currentTask;
    }

    public int getDataPort() {
        return dataPort;
    }

    public void setDataPort(int dataPort) {
        this.dataPort = dataPort;
    }

    public String getDataHost() {
        return dataHost;
    }

    public void setDataHost(String dataHost) {
        this.dataHost = dataHost;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    private InputStream controlInputStream;
    private OutputStream controlOutputStream;

    public InputStream getControlInputStream() {
        return controlInputStream;
    }

    public OutputStream getControlOutputStream() {
        return controlOutputStream;
    }

    public Connection getControlConnection() {
        return controlConnection;
    }
}
