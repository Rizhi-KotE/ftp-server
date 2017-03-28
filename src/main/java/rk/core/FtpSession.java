package rk.core;

import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.log4j.Logger;
import rk.exceptions.FTPError425Exception;
import rk.exceptions.FTPError450Exception;
import rk.exceptions.FTPError530Exception;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class FtpSession {

    private static final Logger log = Logger.getLogger(FtpSession.class);
    private final FileSystem fileSystem;
    private Connection controlConnection;
    private String user;
    private Connection dataConnection;
    private Thread currentTask;
    private boolean logged = false;
    private CompletableFuture<Void> task;

    public FtpSession(Connection controlConnection, FileSystemView view) throws IOException, FtpException {
        this.controlConnection = controlConnection;
        fileSystem = new FileSystem(view);
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
        if ("anonymous".equals(user)) logged = true;
        else {
            user = null;
            throw new FTPError530Exception(String.format("user - [%s]", user));
        }
    }

    public void logout() {
        logged = false;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }

    public Connection getDataConnection() throws FTPError425Exception {
        if (dataConnection == null && dataConnection.isClosed()) throw new FTPError425Exception();
        return dataConnection;
    }

    public void putDataConnection(String host, int i) throws IOException {

        Socket socket = new Socket(host, i);
        if (dataConnection != null) dataConnection.close();
        dataConnection = new Connection(socket);
    }

    public void putDataConnection(Socket socket) throws IOException {
        if (dataConnection != null) dataConnection.close();
        dataConnection = new Connection(socket);
    }

    public boolean isTaskRuned() {
        return task != null && task.isDone();
    }

    public void putTask(CompletableFuture<Void> voidCompletableFuture) throws FTPError450Exception {
        if (isTaskRuned()) {
            log.debug("Task've already runned");
            throw new FTPError450Exception();
        }
        this.task = voidCompletableFuture;
    }

    public void stopTask() {
        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }
}
