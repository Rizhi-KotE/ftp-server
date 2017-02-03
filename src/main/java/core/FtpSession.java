package core;

import exceptions.NotLoggedException;
import exceptions.RequestedActionNotTakenException;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.NoSuchFileException;

public class FtpSession {
    private int dataPort;
    private String dataHost;
    private File workingDirectory;
    private Connection controlConnection;
    private String user;
    private Connection dataConnection;
    private Thread currentTask;
    private boolean logged = false;

    public FtpSession(Connection controlConnection) throws IOException {
        this.controlConnection = controlConnection;
        dataPort = 0;
        dataHost = "";
        workingDirectory = new File(".");
    }


    public Thread getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Thread currentTask) {
        this.currentTask = currentTask;
    }

    public void setDataPort(int dataPort) {
        this.dataPort = dataPort;
    }

    public void setDataHost(String dataHost) {
        this.dataHost = dataHost;
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }


    public Connection getControlConnection() {
        return controlConnection;
    }

    public void putUser(String user) {
        this.user = user;
    }

    public boolean isLogged() {
        return logged;
    }

    public void putPassword(String anyPasswordMayBeHere) throws NotLoggedException {
        if ("anonymous".equals(user)) logged = true;
        else {
            user = null;
            throw new NotLoggedException(String.format("user - [%s]", user));
        }
    }

    public void logout() {
        logged = false;
    }

    public void putDataConnection(Connection connection) {
        dataConnection = connection;
    }

    public Connection getDataConnection() {
        return dataConnection;
    }

    public void putDataConnection(String host, int i) throws IOException {
        Socket socket = new Socket(host, i);
        dataConnection = new Connection(socket);
    }

    public void changeWorkingDirectory(String path) throws NoSuchFileException {
        workingDirectory = new File(path);
    }
}
