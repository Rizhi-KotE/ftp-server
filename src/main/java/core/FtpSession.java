package core;

import exceptions.FTPError530Exception;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class FtpSession {
    private Connection controlConnection;
    private String user;
    private Connection dataConnection;
    private Thread currentTask;
    private boolean logged = false;
    private final FileSystem fileSystem;

    public FtpSession(Connection controlConnection) throws IOException {
        this.controlConnection = controlConnection;
        fileSystem = new FileSystem(new File(".").getAbsoluteFile());
    }


    public Thread getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Thread currentTask) {
        this.currentTask = currentTask;
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

    public void putPassword(String anyPasswordMayBeHere) throws FTPError530Exception {
        logged = true;
//        if ("anonymous".equals(user)) logged = true;
//        else {
//            user = null;
//            throw new FTPError530Exception(String.format("user - [%s]", user));
//        }
    }

    public void logout() {
        logged = false;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public Connection getDataConnection() {
        return dataConnection;
    }

    public void putDataConnection(String host, int i) throws IOException {
        Socket socket = new Socket(host, i);
        dataConnection = new Connection(socket);
    }
}
