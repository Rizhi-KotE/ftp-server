import commands.FtpSession;
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
            new CommandInterpreter(new FtpSession(socket), CommandFactory.Factory.instance.getFactory())
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
