package rk;

import org.apache.ftpserver.ftplet.FileSystemView;
import rk.core.Connection;
import rk.core.FtpSession;
import rk.core.CommandInterpreter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * Encapsulate control connection. Close socket when connection is stopped.
 */
public class ConnectionExecutor implements Runnable, AutoCloseable {
    static final Logger log = Logger.getLogger(ConnectionExecutor.class);

    private final Socket socket;
    private final FileSystemView view;

    /**
     * @param socket - control connection of FTP-session
     */
    ConnectionExecutor(Socket socket, FileSystemView view) {
        this.socket = socket;
        this.view = view;
        log.info(String.format("create connection. local port - [%d], remote address - [%s]",
                socket.getLocalPort(), socket.getRemoteSocketAddress().toString()));
    }

    /**
     * Run interpretation of user control commands
     */
    @Override
    public void run() {
        try {
            Connection connection = new Connection(socket);
            new CommandInterpreter(new FtpSession(connection, view))
                    .run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
            log.debug(String.format("connection closed [%s]", socket.getInetAddress().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
