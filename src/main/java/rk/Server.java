package rk;

import org.apache.log4j.Logger;

import java.net.ServerSocket;


public class Server implements Runnable {
    static final Logger log = Logger.getLogger(Server.class);

    private final int port;

    public Server() {
        this(21);
    }

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket ss = new ServerSocket(port)) {
            log.info(String.format("listen port [%d]", ss.getLocalPort()));
            while (!Thread.interrupted()) {
                ConnectionExecutor executor = new ConnectionExecutor(ss.accept());
                new Thread(() -> executor.run()).start();
            }
        } catch (Exception e) {
            log.debug("", e);
        } finally {
            log.info(String.format("server stopped"));
        }
    }
}
