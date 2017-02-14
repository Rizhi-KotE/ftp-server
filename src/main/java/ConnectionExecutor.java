import commands.FTPCommands;
import core.Connection;
import core.FtpSession;
import core.CommandInterpreter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class ConnectionExecutor implements Runnable, AutoCloseable {
    static final Logger log = Logger.getLogger(ConnectionExecutor.class);

    private final Socket socket;

    ConnectionExecutor(Socket socket) {
        this.socket = socket;
        log.info(String.format("create connection. local port - [%d], remote address - [%s]",
                socket.getLocalPort(), socket.getRemoteSocketAddress().toString()));
    }

    @Override
    public void run() {
        try {
            Connection connection = new Connection(socket);
            new CommandInterpreter(new FtpSession(connection))
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
